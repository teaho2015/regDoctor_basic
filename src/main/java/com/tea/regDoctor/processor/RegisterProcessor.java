/**
 * @author teaho2015@gmail.com
 * since 2017/3/1
 */
package com.tea.regDoctor.processor;

import com.google.common.collect.ImmutableSet;
import com.tea.regDoctor.utils.HttpUtil;
import com.tea.regDoctor.vo.RegisterResource;
import com.tea.regDoctor.vo.SearchDoctor;
import com.tea.regDoctor.vo.builder.SearchDoctorBuilder;
import org.apache.http.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class RegisterProcessor implements Runnable {
    /**
     * reserved lock field for login logic
     */
    private ReentrantLock finallock = new ReentrantLock();
    /**
     * reserved lock field for login logic
     */
    private Condition condition = finallock.newCondition();

    // a temp simple syn implement to avoid registering twice
    protected final AtomicBoolean loginStatus = new AtomicBoolean(false);

    protected AtomicInteger stat = new AtomicInteger(STAT_INIT);

    private Logger logger = LoggerFactory.getLogger(getClass());

    protected LocalDate register_date;

    protected Set<String> timeRangeSet = new HashSet<>();

    //TODO refactor to javafx menu settings
    protected int scheduleNum = 20;

    protected final static int STAT_INIT = 0;

    protected final static int STAT_RUNNING = 1;

    protected final static int STAT_STOPPED = 2;

    protected ExecutorService executorService;

    protected boolean destroyWhenExit = false;


    public RegisterProcessor(LocalDate REGISTER_DATE,  Set<String> timeRangeSet) {
        this.register_date = Objects.requireNonNull(REGISTER_DATE);
//        this.register_time_range = Objects.requireNonNull(REGISTER_TIME_RANGE);
        this.timeRangeSet.addAll(Objects.requireNonNull(timeRangeSet));
    }

    public static RegisterProcessor create(LocalDate REGISTER_DATE, Set<String> timeRangeSet) {
        return new RegisterProcessor(REGISTER_DATE, timeRangeSet);
    }

    public RegisterProcessor scheduleNum(int num) {
        checkIfRunning();
        this.scheduleNum = num;
        return this;
    }

    protected void checkIfRunning() {
        if (stat.get() == STAT_RUNNING) {
            throw new IllegalStateException("Register Processor is already running!");
        }
    }

    private void checkRunningStat() {
        while (true) {
            int statNow = stat.get();
            if (statNow == STAT_RUNNING) {
                throw new IllegalStateException("Register Processor is already running!");
            }
            if (stat.compareAndSet(statNow, STAT_RUNNING)) {
                break;
            }
        }
    }



    protected void initComponent() {
        if (register_date == null) {
            register_date = LocalDate.now();
        }
        if (timeRangeSet == null) {
            timeRangeSet = new HashSet<>();
        }
        if (executorService == null || executorService.isShutdown()) {
            executorService = Executors.newFixedThreadPool(scheduleNum);
        }
    }


    public void process(SearchDoctor sd) {
        List<RegisterResource> list = null;
        final int MAX_PROCESS = Integer.MAX_VALUE/10000;
        for (int i=0; i<MAX_PROCESS && (list == null || list.size() <= 0); i++){
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                list = HttpUtil.getDoctorRegisterPageResourses(sd);
            } catch (HttpException e) {
                logger.info("Network error, while getting register resources!", e);
            }
            logger.info(" ===== " + list);
            //exit strategy
            if (executorService.isShutdown()) {
                return;
            }
        }
        Iterator registerResourceIterator = list.iterator();
        ImmutableSet<String> simpleMatchSet = null;
        if (timeRangeSet.isEmpty()) {
            ImmutableSet.builder()
                    .add("09:00~09:30")
                    .add("09:30~10:00")
                    .add("10:00~10:30")
                    .add("10:30~11:00")
                    .add("11:00~11:30")
                    .add("11:30~12:00");
        } else {
            simpleMatchSet = ImmutableSet.copyOf(timeRangeSet);
        }
        while (registerResourceIterator.hasNext()) {
            RegisterResource rr = (RegisterResource) registerResourceIterator.next();
            String period = rr.getRegDayTime();
            try {
                if (Integer.valueOf(rr.getRegisterableNum()) > 0
                        && simpleMatchSet.contains(period.trim())
                        ) {
                    if (!loginStatus.get()) {
                        synchronized (loginStatus) {
                            if (!loginStatus.get() && HttpUtil.registerDoctor(HttpUtil.login().getCookiesMap(), rr.getResourseId())) {
                                loginStatus.set(true);
                                logger.info("===== register success!!!");
                                break;
                            }
                        }
                    }

                }
            } catch (NumberFormatException e) {
                logger.info("register resource number parse error!!", e);
            }

        }
        logger.info("===== end of the program!!");
    }

    @Override
    public void run() {
        checkRunningStat();
        initComponent();
        SearchDoctor sd = SearchDoctorBuilder.newBuilder().defaultValue(register_date).build();
        logger.info("Rigister Processor is starting!");

        AtomicInteger threadCount = new AtomicInteger();
        while (!Thread.currentThread().isInterrupted()
                && stat.get() == STAT_RUNNING
                && threadCount.get() <= scheduleNum) {
            executorService.execute(()-> {
                try {
                    process(sd);
                } catch (Exception e) {
                    logger.error("Error occur!!", e);
                }
            });
            threadCount.incrementAndGet();
        }

        stat.set(STAT_STOPPED);
        if (destroyWhenExit) {
            close();
        }

    }

    public void runAsync() {
        Thread thread = new Thread(this);
        thread.setDaemon(false);
        thread.start();
    }

    /**
     * do some resource release
     */
    public void close() {
        executorService.shutdown();
        //do some resource release
    }

    /*public void closeHardly() {
        executorService.shutdownNow();
        //do some resource release
    }*/

    public Status getStatus() {
        return Status.fromValue(stat.get());
    }


    public enum Status {
        Init(0), Running(1), Stopped(2);

        private Status(int value) {
            this.value = value;
        }

        private int value;

        int getValue() {
            return value;
        }

        public static Status fromValue(int value) {
            for (Status status : Status.values()) {
                if (status.getValue() == value) {
                    return status;
                }
            }
            //default value
            return Init;
        }
    }

    public void stop() {
        if (stat.compareAndSet(STAT_RUNNING, STAT_STOPPED)) {
            logger.info("stop success!");
        } else {
            logger.info("stop fail!");
        }
    }

}

/**
 * Created by 庭亮 on 2016/9/28.
 */
package com.tea.regDoctor;

import com.tea.regDoctor.utils.HttpUtil;
import com.tea.regDoctor.vo.RegisterResource;
import com.tea.regDoctor.vo.SearchDoctor;
import org.apache.http.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main implements Runnable {


//    private static ReentrantLock finallock = new ReentrantLock();
//    private static Condition condition = finallock.newCondition();

    // a temp simple syn implement to avoid registering twice
    private static volatile AtomicBoolean status = new AtomicBoolean(false);
    private Logger logger = LoggerFactory.getLogger(getClass());

    private Date register_date;
//    private String register_time_range;
    private Set<String> timeRangeSet;


    private Main() {
    }

    public Main(Date REGISTER_DATE,  Set<String> timeRangeSet) {
        this.register_date = Objects.requireNonNull(REGISTER_DATE);
//        this.register_time_range = Objects.requireNonNull(REGISTER_TIME_RANGE);
        this.timeRangeSet = Objects.requireNonNull(timeRangeSet);
    }

    public static void main(String[] args) {

        Date REGISTER_DATE;
        try {
            REGISTER_DATE = (new SimpleDateFormat("yyyy-MM-dd")).parse(Objects.requireNonNull(args[0], "REGISTER_DATE can't be null!!"));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new Error("GG, REGISTER_TIME_RANGE parse wrong, program met a big crash...");
        }
        final int POOL_SIZE = Integer.valueOf(args[1] == null ? "20" : args[1]);

        Set<String> timeRangeSet = new HashSet<>();
        Pattern p = Pattern.compile("^\\d{2}:\\d{2}~\\d{2}:\\d{2}$");
        for (int i = 2; i < args.length; i++) {
            Matcher m = p.matcher(args[i].trim());
            if (m.matches()) {
                timeRangeSet.add(args[i].trim());
            } else {
                throw new Error("time range error!!!挂号时间段输入格式错误！！");
            }
        }


//        PropertyConfigurator.configure("  //log4j.properties");
        ExecutorService es = Executors.newFixedThreadPool(POOL_SIZE);
        for (int i = 0; i < 20; i++) {
            es.execute(new Main(REGISTER_DATE, timeRangeSet));
        }


    }


    //temp works TODO remove this
    @Override
    public void run() {
        SearchDoctor sd = new SearchDoctor();
        sd.setFrownm(1);
        sd.setId(23679);
        sd.setKsmc("中医科(门)");
        sd.setSfkyy(14);
        sd.setTc("");
        sd.setWsjksdm("50");

        sd.setWsjzkdm("50.13");
        sd.setXb("2");
        sd.setYsgh("FP037");
        sd.setYsxm("张卫华");
        sd.setYydm("4406000003");
        sd.setYymc("佛山市第二人民医院");
        sd.setYyrq(register_date);
        sd.setZc("主任医师");
        sd.setZkdm("0000046");
        sd.setZkmc("中医科(门)");
        List<RegisterResource> list = null;
        while (list == null || list.size() <= 0) {
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                list = HttpUtil.getDoctorRegisterPageResourses(sd);
            } catch (HttpException e) {
                e.printStackTrace();
                logger.info("Network error, while getting register resources!", e);
            }
            logger.info((new Date()).toString() + " ===== " + list);
        }
        Iterator registerResourceIteratorter = list.iterator();
        Set<String> simpleMatchSet = new HashSet<>();
//        simpleMatchMap.put("08:30~09:00", "");
        if (timeRangeSet.isEmpty()) {
            simpleMatchSet.add("09:00~09:30");
            simpleMatchSet.add("09:30~10:00");
            simpleMatchSet.add("10:00~10:30");
            simpleMatchSet.add("10:30~11:00");
            simpleMatchSet.add("11:00~11:30");
            simpleMatchSet.add("11:30~12:00");
        } else {
            simpleMatchSet.addAll(timeRangeSet);
        }
        while (registerResourceIteratorter.hasNext()) {
            RegisterResource rr = (RegisterResource) registerResourceIteratorter.next();
            String period = rr.getRegDayTime();
            try {
                if (Integer.valueOf(rr.getRegisterableNum()) > 0
                        && simpleMatchSet.contains(period.trim())
                        ) {
                    if (!status.get()) {
                        synchronized (status) {
                            if (!status.get() && HttpUtil.registerDoctor(HttpUtil.login().getCookiesMap(), rr.getResourseId())) {
                                status.set(true);
                                logger.info((new Date()).toString() + "===== register success!!!");
                                break;
                            }
                        }
                    }

                }
            } catch (NumberFormatException e) {
                logger.info((new Date()).toString() + "register resource number parse error!!", e);

            }

        }
        logger.info((new Date()).toString() + "===== end of the program!!");

    }
}

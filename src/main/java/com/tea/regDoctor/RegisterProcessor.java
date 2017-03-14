/**
 * @author teaho2015@gmail.com
 * since 2017/3/1
 */
package com.tea.regDoctor;

import com.tea.regDoctor.utils.HttpUtil;
import com.tea.regDoctor.vo.RegisterResource;
import com.tea.regDoctor.vo.SearchDoctor;
import org.apache.http.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class RegisterProcessor implements Runnable {

    //    private static ReentrantLock finallock = new ReentrantLock();
//    private static Condition condition = finallock.newCondition();

    // a temp simple syn implement to avoid registering twice
    private static volatile AtomicBoolean status = new AtomicBoolean(false);
    private Logger logger = LoggerFactory.getLogger(getClass());

    private Date register_date;
    //    private String register_time_range;
    private Set<String> timeRangeSet;


    private RegisterProcessor() {
    }

    public RegisterProcessor(Date REGISTER_DATE,  Set<String> timeRangeSet) {
        this.register_date = Objects.requireNonNull(REGISTER_DATE);
//        this.register_time_range = Objects.requireNonNull(REGISTER_TIME_RANGE);
        this.timeRangeSet = Objects.requireNonNull(timeRangeSet);
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
            logger.info(" ===== " + list);
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
}

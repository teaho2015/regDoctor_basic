/**
 * Created by 庭亮 on 2016/10/28.
 */
package com.tea.regDoctor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

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
            es.execute(new RegisterProcessor(REGISTER_DATE, timeRangeSet));
        }


    }
}

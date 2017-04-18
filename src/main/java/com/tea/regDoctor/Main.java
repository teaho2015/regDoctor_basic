/**
 * Created by teaho2015@gmail.com on 2016/11/28.
 */
package com.tea.regDoctor;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * keey for cmd usage
 */
@Deprecated
public class Main {

    public static void main(String[] args) {

        //TODO init UI and create controller file. controller file due to create processor

        LocalDate REGISTER_DATE;
        try {
            REGISTER_DATE = LocalDate.parse(Objects.requireNonNull(args[0], "REGISTER_DATE can't be null!!"));
        } catch (DateTimeParseException e) {
            //TODO refactor to logger handling
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

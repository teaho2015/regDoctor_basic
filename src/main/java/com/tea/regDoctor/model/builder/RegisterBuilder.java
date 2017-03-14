/**
 * @author teaho2015@gmail.com
 * since 2017/3/14
 */
package com.tea.regDoctor.model.builder;


import com.google.common.base.Strings;
import com.tea.regDoctor.Builder;
import com.tea.regDoctor.model.Register;
import com.tea.regDoctor.utils.UUIDGenerator;

import java.sql.Timestamp;
import java.util.Date;

public class RegisterBuilder implements Builder<Register> {

    private String id;
    private String doctorId;
    private String doctorName;
    private String yyrq;
    private String sex;
    private String ksdm;
    private String ysgh;
    private String yydm;
    private String registered_status; //0 registering 1 registered 2 failed
    private Timestamp start_register_time;
    private Timestamp stop_register_time;
    private Timestamp visit_time;

    public static RegisterBuilder newBuilder() {
        return new RegisterBuilder();
    }

    public RegisterBuilder id(String str) {
        id = str;
        return this;
    }

    public RegisterBuilder doctorId(String str) {
        doctorId = str;
        return this;
    }
    public RegisterBuilder doctorName(String str) {
        doctorName = str;
        return this;
    }
    public RegisterBuilder yyrq(String str) {
        yyrq = str;
        return this;
    }
    public RegisterBuilder sex(String str) {
        sex = str;
        return this;
    }
    public RegisterBuilder ksdm(String str) {
        ksdm = str;
        return this;
    }
    public RegisterBuilder ysgh(String str) {
        ysgh = str;
        return this;
    }
    public RegisterBuilder registered_status(String str) {
        registered_status = str;
        return this;
    }
    public RegisterBuilder start_register_time(Timestamp str) {
        start_register_time = str;
        return this;
    }
    public RegisterBuilder stop_register_time(Timestamp str) {
        stop_register_time = str;
        return this;
    }
    public RegisterBuilder visit_time(Timestamp str) {
        visit_time = str;
        return this;
    }


    public RegisterBuilder defaultValue() {
        id = UUIDGenerator.generateUUID();
        doctorId = "";
        doctorName = "";
        yyrq= "";
        sex= "";
        ksdm= "";
        ysgh= "";
        yydm= "";
        registered_status= "0";
        start_register_time = new Timestamp((new Date()).getTime());
        stop_register_time = new Timestamp((new Date()).getTime());
        visit_time = new Timestamp((new Date()).getTime());
        return this;
    }

    @Override
    public Register build() {
        Register register = new Register();
        register.setId(Strings.isNullOrEmpty(id) ? UUIDGenerator.generateUUID() : id);
        register.setDoctorId(doctorId);
        register.setDoctorName(doctorName);
        register.setYyrq(yyrq);
        register.setSex(sex);
        register.setKsdm(ksdm);
        register.setYsgh(ysgh);
        register.setYydm(yydm);
        register.setRegistered_status(registered_status);
        register.setStart_register_time(start_register_time);
        register.setStop_register_time(stop_register_time);
        register.setVisit_time(visit_time);
        return register;
    }



}

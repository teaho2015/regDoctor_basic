/**
 * @author teaho2015@gmail.com
 * since 2016
 */
package com.tea.regDoctor.model;


import java.sql.Timestamp;

public class Register {

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getYyrq() {
        return yyrq;
    }

    public void setYyrq(String yyrq) {
        this.yyrq = yyrq;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getKsdm() {
        return ksdm;
    }

    public void setKsdm(String ksdm) {
        this.ksdm = ksdm;
    }

    public String getYsgh() {
        return ysgh;
    }

    public void setYsgh(String ysgh) {
        this.ysgh = ysgh;
    }

    public String getYydm() {
        return yydm;
    }

    public void setYydm(String yydm) {
        this.yydm = yydm;
    }

    public String getRegistered_status() {
        return registered_status;
    }

    public void setRegistered_status(String registered_status) {
        this.registered_status = registered_status;
    }

    public Timestamp getStart_register_time() {
        return start_register_time;
    }

    public void setStart_register_time(Timestamp start_register_time) {
        this.start_register_time = start_register_time;
    }

    public Timestamp getStop_register_time() {
        return stop_register_time;
    }

    public void setStop_register_time(Timestamp stop_register_time) {
        this.stop_register_time = stop_register_time;
    }

    public Timestamp getVisit_time() {
        return visit_time;
    }

    public void setVisit_time(Timestamp visit_time) {
        this.visit_time = visit_time;
    }
}

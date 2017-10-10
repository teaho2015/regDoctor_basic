/**
 * @author teaho2015@gmail.com
 * since 2017/3/14
 */
package com.tea.regDoctor.vo.builder;


import com.tea.regDoctor.Builder;
import com.tea.regDoctor.vo.SearchDoctor;

import java.time.LocalDate;

public class SearchDoctorBuilder implements Builder<SearchDoctor> {

    private int frownm;//该查询number
    private String ysgh;//医生挂号
    private String ysxm;
    private String xb;
    private String yydm;
    private String yymc;
    private String ksdm;
    private String ksmc;
    private String zc;
    private String tc;
    private LocalDate yyrq;
    private int sfkyy;//资源行数
    private String zkmc;
    private int id;
    private String wsjksdm;
    private String wsjzkdm;
    private String zkdm;

    public static SearchDoctorBuilder newBuilder() {
        return new SearchDoctorBuilder();
    }

    public SearchDoctorBuilder frownm(int num) {
        frownm = num;
        return this;
    }

    public SearchDoctorBuilder ysgh(String str) {
        ysgh = str;
        return this;
    }

    public SearchDoctorBuilder ysxm(String str) {
        ysxm = str;
        return this;
    }

    public SearchDoctorBuilder xb(String str) {
        xb = str;
        return this;
    }

    public SearchDoctorBuilder yydm(String str) {
        yydm = str;
        return this;
    }

    public SearchDoctorBuilder yymc(String str) {
        yymc = str;
        return this;
    }

    public SearchDoctorBuilder ksdm(String str) {
        ksdm = str;
        return this;
    }

    public SearchDoctorBuilder ksmc(String str) {
        ksmc = str;
        return this;
    }

    public SearchDoctorBuilder zc(String str) {
        zc = str;
        return this;
    }

    public SearchDoctorBuilder tc(String str) {
        tc = str;
        return this;
    }

    public SearchDoctorBuilder yyrq(LocalDate str) {
        yyrq = str;
        return this;
    }

    public SearchDoctorBuilder sfkyy(int str) {
        sfkyy = str;
        return this;
    }

    public SearchDoctorBuilder zkmc(String str) {
        zkmc = str;
        return this;
    }

    public SearchDoctorBuilder id(int str) {
        id = str;
        return this;
    }

    public SearchDoctorBuilder wsjksdm(String str) {
        wsjksdm = str;
        return this;
    }

    public SearchDoctorBuilder wsjzkdm(String str) {
        wsjzkdm = str;
        return this;
    }

    public SearchDoctorBuilder zkdm(String str) {
        zkdm = str;
        return this;
    }

    public SearchDoctorBuilder defaultValue() {
        return this;
    }

    public SearchDoctorBuilder defaultValue(LocalDate register_date) {
        return this.frownm(1)
                .id(23679)
                .ksmc("中医科(门)")
                .sfkyy(14)
                .tc("")
                .wsjksdm("50")
                .wsjzkdm("50.13")
                .xb("2")
                .ysgh("FP037")
                .ysxm("张卫华")
                .yydm("4406000003")
                .yymc("佛山市第二人民医院")
                .yyrq(register_date)
                .zc("主任医师")
                .zkdm("0000046")
                .zkmc("中医科(门)");
    }

    @Override
    public SearchDoctor build() {
        SearchDoctor searchDoctor = new SearchDoctor();
        searchDoctor.setFrownm(frownm);
        searchDoctor.setYsgh(ysgh);
        searchDoctor.setYsxm(ysxm);
        searchDoctor.setXb(xb);
        searchDoctor.setYydm(yydm);
        searchDoctor.setYymc(yymc);
        searchDoctor.setKsdm(ksdm);
        searchDoctor.setKsmc(ksmc);
        searchDoctor.setZc(zc);
        searchDoctor.setTc(tc);
        searchDoctor.setYyrq(yyrq);
        searchDoctor.setSfkyy(sfkyy);
        searchDoctor.setZkmc(zkmc);
        searchDoctor.setId(id);
        searchDoctor.setWsjksdm(wsjksdm);
        searchDoctor.setWsjzkdm(wsjzkdm);
        searchDoctor.setZkdm(zkdm);
        return searchDoctor;
    }


}

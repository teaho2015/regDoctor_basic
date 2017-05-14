/**
 * Created by 庭亮 on 2016/9/28.
 */
package com.tea.regDoctor.vo;

import java.time.LocalDate;

public class SearchDoctor {
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

    public int getFrownm() {
        return frownm;
    }

    public void setFrownm(int frownm) {
        this.frownm = frownm;
    }

    public String getYsgh() {
        return ysgh;
    }

    public void setYsgh(String ysgh) {
        this.ysgh = ysgh;
    }

    public String getYsxm() {
        return ysxm;
    }

    public void setYsxm(String ysxm) {
        this.ysxm = ysxm;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getYydm() {
        return yydm;
    }

    public void setYydm(String yydm) {
        this.yydm = yydm;
    }

    public String getYymc() {
        return yymc;
    }

    public void setYymc(String yymc) {
        this.yymc = yymc;
    }

    public String getKsdm() {
        return ksdm;
    }

    public void setKsdm(String ksdm) {
        this.ksdm = ksdm;
    }

    public String getKsmc() {
        return ksmc;
    }

    public void setKsmc(String ksmc) {
        this.ksmc = ksmc;
    }

    public String getZc() {
        return zc;
    }

    public void setZc(String zc) {
        this.zc = zc;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public LocalDate getYyrq() {
        return yyrq;
    }

    public void setYyrq(LocalDate yyrq) {
        this.yyrq = yyrq;
    }

    public int getSfkyy() {
        return sfkyy;
    }

    public void setSfkyy(int sfkyy) {
        this.sfkyy = sfkyy;
    }

    public String getZkmc() {
        return zkmc;
    }

    public void setZkmc(String zkmc) {
        this.zkmc = zkmc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWsjksdm() {
        return wsjksdm;
    }

    public void setWsjksdm(String wsjksdm) {
        this.wsjksdm = wsjksdm;
    }

    public String getWsjzkdm() {
        return wsjzkdm;
    }

    public void setWsjzkdm(String wsjzkdm) {
        this.wsjzkdm = wsjzkdm;
    }

    public String getZkdm() {
        return zkdm;
    }

    public void setZkdm(String zkdm) {
        this.zkdm = zkdm;
    }
}

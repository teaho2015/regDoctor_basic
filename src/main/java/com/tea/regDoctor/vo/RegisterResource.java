/**
 * Created by 庭亮 on 2016/9/29.
 */
package com.tea.regDoctor.vo;

public class RegisterResource {
    private String regDate; //预约日期
    private String department; //预约科室
    private String major; //预约专业
    private String shift; //班次
    private String regDayTime; //预约时间
    private String registerableNum; //可预约数
    private String registeredNum;//已预约数
    private String resourseId;//预约resourse

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getRegDayTime() {
        return regDayTime;
    }

    public void setRegDayTime(String regDayTime) {
        this.regDayTime = regDayTime;
    }

    public String getRegisterableNum() {
        return registerableNum;
    }

    public void setRegisterableNum(String registerableNum) {
        this.registerableNum = registerableNum;
    }

    public String getRegisteredNum() {
        return registeredNum;
    }

    public void setRegisteredNum(String registeredNum) {
        this.registeredNum = registeredNum;
    }

    public String getResourseId() {
        return resourseId;
    }

    public void setResourseId(String resourseId) {
        this.resourseId = resourseId;
    }

    @Override
    public String toString() {
        return new StringBuffer().append("{regDate=").append(regDate)
                .append(",regDayTime=").append(regDayTime)
                .append(",resourseId=").append(resourseId)
                .append("}")
                .toString();

    }
}

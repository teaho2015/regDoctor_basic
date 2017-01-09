/**
 * Created by 庭亮 on 2016/9/27.
 */
package com.tea.regDoctor.model;


import java.sql.Timestamp;

public class Register {
    /*
     * {"total":1,
        "rows":[{"frownm":1, //该查询number
                "ysgh":"FP037", //医生挂号
                "ysxm":"张卫华",
                "xb":"2", //性别 女
                "yydm":"4406000003", //医院//√
                "yymc":"佛山市第二人民医院",
                "ksdm":"0000046",  //√
                "ksmc":"中医科(门)",
                "zc":"主任医师",
                "tc":"善治内、妇、儿科杂病及各种疑难病，对肝胆疾病、胆肾结石、老年虚症的诊疗颇有心得。对各种恶性肿瘤的中医治疗，恶性肿瘤术后防止复发，放化疗期间配合中药减毒增效及中晚期恶性肿瘤提高生存质量，延长生存期等有独到的见解。",
                "yyrq":"2016-09-30",
                "sfkyy":0,  //资源行数
                "zkmc":"中医科(门)",
                "id":23679,
                "wsjksdm":"50",
                "wsjzkdm":"50.13",
                "zkdm":"0000046"}]
        }
     */
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

package com.tea.regDoctor.utils.webmagicimpl;

import com.tea.regDoctor.vo.RegisterResource;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

/**
 *  @author teaho2015@gmail.com
 *  @since 20161006
 *  <b>DEMO FOR<b/> http://live.fshealth.gov.cn/smjkfw/wsyygh/index.html
 *
 */
public class RegDoctorResoursesPageProcessor implements PageProcessor {
    private final Logger logger = Logger.getLogger(getClass());

    private Site site = Site.me().setRetryTimes(3).setSleepTime(500)//.setDomain("live.fshealth.gov.cn")
            .setCycleRetryTimes(3)//TODO test it
            .addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
            //set header reserved code
//            .addHeader("Accept-Encoding", "gzip, deflate")
//            .addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
//            .addCookie("live.fshealth.gov.cn", "JSESSIONID", "2F41A518024C62B9EEC9C828E89BFD62")
//            .addCookie("Hm_lvt_0c4fe25684558b9335a91cadbc063882", "1473095401")
//            .addCookie("Hm_lpvt_0c4fe25684558b9335a91cadbc063882", "1473095401")
//            .addHeader("Upgrade-Insecure-Requests", "1")
//            .addHeader("Cookie", "JSESSIONID=2F41A518024C62B9EEC9C828E89BFD62")
            .addHeader("Host", "live.fshealth.gov.cn")
            .addHeader("Origin", "http://live.fshealth.gov.cn")
            .addHeader("Referer", "http://live.fshealth.gov.cn/smjkfw/wsyygh/pages/yygh.html")
            .addHeader("X-Requested-With", "XMLHttpRequest")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36")
//            .setCharset("UTF-8")
            ;

    public Site getSite() {
        return site;
    }

    public void process(Page page) {
        List<String> titles = page.getHtml().css("tr.title th", "text").all();
        logger.info("title in Resources page =" + titles);
//        tbody#t1>tr:eq(0)>td:eq(0)
        if (titles == null || titles.size() != 8) {
//            throw new Exception()
            logger.info("web source code might have changed!!!Pls check!");
            page.putField("state", false);
            return;
        } else {
            List<RegisterResource> resourceList = new ArrayList<>();
            Selectable tableSelector = page.getHtml().css("tbody#t1");
            for (int i=0; ; i++ ) {
                if (tableSelector.css("tr:eq(" + i + ")").get()==null || tableSelector.css("tr:eq(" + i + ")").get().trim().isEmpty()) {
                    break;
                }
                RegisterResource rr = new RegisterResource();
                rr.setRegDate(tableSelector.css("tr:eq(" + i + ")>th", "text").get());
                rr.setDepartment(tableSelector.css("tr:eq(" + i + ")>td:eq(1)", "text").get());
                rr.setMajor(tableSelector.css("tr:eq(" + i + ")>td:eq(2)", "text").get());
                rr.setShift(tableSelector.css("tr:eq(" + i + ")>td:eq(3)", "text").get());
                rr.setRegDayTime(tableSelector.css("tr:eq(" + i + ")>td:eq(4)", "text").get());
                rr.setRegisterableNum(tableSelector.css("tr:eq(" + i + ")>td:eq(5)", "text").get());
                rr.setRegisteredNum(tableSelector.css("tr:eq(" + i + ")>td:eq(6)>font", "text").get());
                String rid = tableSelector.css("tr:eq(" + i + ")>td:eq(7)>input", "onclick").regex("(?<=')\\d+(?=')", 0).get();
                rr.setResourseId(rid);
                if (rid != null && !"".equals(rid.trim())) {
                    resourceList.add(rr);
                } else {
                    logger.info(rr + "--- already full! " + page.getRequest().getUrl());
                }
            }
            page.putField("state", true);
            page.putField("resourceList", resourceList);

        }
    }

}

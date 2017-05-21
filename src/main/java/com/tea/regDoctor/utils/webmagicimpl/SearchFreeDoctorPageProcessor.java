package com.tea.regDoctor.utils.webmagicimpl;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.HashMap;
import java.util.Map;

/**
 *  @author teaho2015@gmail.com
 *  @since 20161006
 *  <b>DEMO FOR<b/> http://live.fshealth.gov.cn/smjkfw/wsyygh/index.html
 *
 */
public class SearchFreeDoctorPageProcessor implements PageProcessor {
    private final Logger logger = Logger.getLogger(getClass());

    private Site site = Site.me().setRetryTimes(3).setSleepTime(500)//.setDomain("live.fshealth.gov.cn")
            .addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
//            .addHeader("Accept-Encoding", "gzip, deflate")
//            .addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")

//            .addCookie("live.fshealth.gov.cn", "JSESSIONID", "2F41A518024C62B9EEC9C828E89BFD62")
//            .addCookie("Hm_lvt_0c4fe25684558b9335a91cadbc063882", "1473095401")
//            .addCookie("Hm_lpvt_0c4fe25684558b9335a91cadbc063882", "1473095401")

//            .addHeader("Upgrade-Insecure-Requests", "1")
            .addHeader("Host", "live.fshealth.gov.cn")
            .addHeader("Origin", "http://live.fshealth.gov.cn")
            .addHeader("Referer", "http://live.fshealth.gov.cn/smjkfw/wsyygh/pages/yygh.html?wsjksdm=50&wsjzkdm=0000&zjxb=0000&zjxm=%25E5%25BC%25A0%25E5%258D%25AB%25E5%258D%258E&yyrq=2016-09-14")
//            .addHeader("Cookie", "JSESSIONID=2F41A518024C62B9EEC9C828E89BFD62")
            .addHeader("X-Requested-With", "XMLHttpRequest")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36")
//            .setCharset("UTF-8")
            ;


    public Site getSite() {
        return site;
    }

    public void process(Page page) {
        logger.debug(page.getRawText());
        page.putField("result" ,page.getJson());
    }

}

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

    public static void main(String[] args) {
        Request request = new Request();
        request.setMethod(HttpConstant.Method.POST);
//        request.setUrl("http://music.163.com/weapi/song/enhance/player/url");
        request.setUrl("http://live.fshealth.gov.cn/smjkfw/wsyygh/quypages/quyjsonpages.action");//?page_zjxm=%e5%bc%a0%e5%8d%ab%e5%8d%8e
        Map<String, Object> map = new HashMap<>();
        NameValuePair[] qparams = new BasicNameValuePair[12];
        qparams[0] = new BasicNameValuePair("qid", "JKFW_YYGH");
        qparams[1] = new BasicNameValuePair("rows", "10");
        qparams[2] = new BasicNameValuePair("page", "1");
        qparams[3] = new BasicNameValuePair("page_sfkyy", "");
        qparams[4] = new BasicNameValuePair("page_ssqx", "440600");
        qparams[5] = new BasicNameValuePair("page_hosid", "4406000003");
        //TODO
//        try {
//            String utf8 = new String(new String("张卫华").getBytes( "UTF-8"));
//            String unicode = new String(utf8.getBytes(),"UTF-8");
//            String gbk = new String(unicode.getBytes("GBK"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        qparams[6] = new BasicNameValuePair("page_zjxm","张卫华");
        qparams[7] = new BasicNameValuePair("page_yyrq", "");
        qparams[8] = new BasicNameValuePair("page_zjxb", "");
        qparams[9] = new BasicNameValuePair("page_ksdm", "");
        qparams[10] = new BasicNameValuePair("page_wsjksdm", "");
        qparams[11] = new BasicNameValuePair("page_wsjzkdm", "");
        map.put("nameValuePair", qparams);
        request.setExtras(map);
        Spider spider = Spider.create(new SearchFreeDoctorPageProcessor())
                .addRequest(request)
                .setDownloader(new MyHttpClientDownloader());
        spider.run();
    }
}

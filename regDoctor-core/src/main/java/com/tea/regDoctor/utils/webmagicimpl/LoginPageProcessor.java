/**
 * Created by teaho2015@gmail.com on 2016/8/12.
 */
package com.tea.regDoctor.utils.webmagicimpl;

import com.google.common.base.Optional;
import com.tea.regDoctor.model.HttpVerificationCodeEntity;
import com.tea.regDoctor.utils.HttpUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author teaho2015@gmail.com
 * @since 20161006
 * <b>DEMO FOR<b/> http://live.fshealth.gov.cn/smjkfw/wsyygh/index.html
 */
public class LoginPageProcessor implements PageProcessor {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private Site site = Site.me().setRetryTimes(0).setSleepTime(500)//.setDomain("live.fshealth.gov.cn")
            .addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
//            .addHeader("Accept-Encoding", "gzip, deflate")
            .addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")

//            .addCookie("live.fshealth.gov.cn", "JSESSIONID", "2F41A518024C62B9EEC9C828E89BFD62")
//            .addCookie("Hm_lvt_0c4fe25684558b9335a91cadbc063882", "1473095401")
//            .addCookie("Hm_lpvt_0c4fe25684558b9335a91cadbc063882", "1473095401")

//            .addHeader("Upgrade-Insecure-Requests", "1")
            .addHeader("Host", "live.fshealth.gov.cn")
            .addHeader("Origin", "http://live.fshealth.gov.cn")
            .addHeader("Referer", "http://live.fshealth.gov.cn/smjkfw/wsyygh/loginpage.jsp")
//            .addHeader("Cookie", "JSESSIONID=2F41A518024C62B9EEC9C828E89BFD62")
            .addHeader("X-Requested-With", "XMLHttpRequest")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36")
            .setCharset("UTF-8");

    public LoginPageProcessor(Map<String, String> cookiesMap) {
        for (String _key : cookiesMap.keySet()) {
            site.addCookie(_key, cookiesMap.get(_key));
        }
    }

    //http://live.fshealth.gov.cn/smjkfw/wsyygh/login.action
    public static void main(String[] args) {
        Request request = new Request();
        request.setMethod(HttpConstant.Method.POST);
//        request.setUrl("http://music.163.com/weapi/song/enhance/player/url");
        request.setUrl("http://live.fshealth.gov.cn/smjkfw/wsyygh/login.action");
        Map<String, Object> map = new HashMap<>();
        NameValuePair[] qparams = new BasicNameValuePair[4];
        qparams[0] = new BasicNameValuePair("userId", "4406000001000271401");
        qparams[1] = new BasicNameValuePair("password", "69d5c50df527a4b8cb1cd00c19dec17e");
        Optional<HttpVerificationCodeEntity> o = HttpUtil.getVerificationCodeEntity();
        HttpVerificationCodeEntity httpVerificationCodeEntity = o.get();
        System.out.println("===>" + httpVerificationCodeEntity.getResult());
        qparams[2] = new BasicNameValuePair("yzm", httpVerificationCodeEntity.getResult());
        qparams[3] = new BasicNameValuePair("dlfs", "1");
        map.put("nameValuePair", qparams);
        request.setExtras(map);
        Spider spider = Spider.create(new LoginPageProcessor(httpVerificationCodeEntity.getCookiesMap()))
                .addRequest(request)
                .setDownloader(new MyHttpClientDownloader());
        spider.run();
    }

    public void process(Page page) {
//        if (page.getUrl().toString().startsWith("http://www\\.198620\\.com/qiufanhao/516")) {
//            page.addTargetRequests(page.getHtml().css("div.plist").links().all());
//        }
//        page.putField("seedImgUrl", page.getHtml().css("article div.infotext p>img", "src").all());
//        page.putField("seedId", page.getHtml().css("article div.infotext p>img", "title").all());
//        page.putField("html", page.getHtml().toString());
        logger.info(page.getRawText());
        page.putField("msg", page.getRawText());
//        page.putField("seedId", page.getHtml().css("article div.infotext p", "text").all());
//        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
//        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));

    }

    public Site getSite() {
        return site;
    }
}

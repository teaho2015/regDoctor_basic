/**
 * Created by 庭亮 on 2016/7/14.
 */
package com.tea.regDoctor.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Optional;
import com.tea.regDoctor.model.HttpVerificationCodeEntity;
import com.tea.regDoctor.utils.webmagicimpl.*;
import com.tea.regDoctor.vo.RegisterResource;
import com.tea.regDoctor.vo.SearchDoctor;
import org.apache.http.HttpException;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.www.protocol.http.HttpURLConnection;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.CollectorPipeline;
import us.codecraft.webmagic.pipeline.ResultItemsCollectorPipeline;
import us.codecraft.webmagic.selector.Json;
import us.codecraft.webmagic.utils.HttpConstant;

import java.io.*;
import java.lang.reflect.Type;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkNotNull;


public class HttpUtil {
    private final static String IDENTIFICATION_CODE_URL = "http://live.fshealth.gov.cn/smjkfw/wsyygh/executeValidate.action?a=0";
    public final static File tempPath = new File("D:\\test_tesseract-OCR\\.TEMP");
    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    static {
        if (!tempPath.exists() || !tempPath.isDirectory()) {
            tempPath.mkdir();
        }
    }

    /**
     * @param file empty file to store verification code
     * @return
     * @since 1.0.0
     */
    public static HttpVerificationCodeEntity getVerificationCodeEntity(File file) {
        HttpURLConnection uc = null;
        InputStream in = null;
        OutputStream os = null;
        HttpVerificationCodeEntity httpFileEntity = new HttpVerificationCodeEntity();
        try {
            uc = (HttpURLConnection) new URL(IDENTIFICATION_CODE_URL).
                    openConnection();
            uc.setConnectTimeout(10000);
            uc.setDoOutput(true);
            try {
                uc.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }

            uc.setUseCaches(false);
            in = new BufferedInputStream(uc.getInputStream());
            String[] cookiesArr = uc.getHeaderField("Set-Cookie").split(";");
            Map<String, String> cookiesMap = new HashMap<>();
            for (String _s : cookiesArr) {
                String[] mapArr = _s.split("=");
                if (mapArr.length == 2) {
                    cookiesMap.put(mapArr[0], mapArr[1]);
                }
            }
            httpFileEntity.setCookiesMap(cookiesMap);
//            Reader rd = new InputStreamReader(in, "UTF-8");
            int c = 0;
//            while ((c = rd.read()) != -1) {
//                sb.append((char) c);
//            }
            os = new BufferedOutputStream(new FileOutputStream(file));
            byte[] bs = new byte[1024];
            int len;
            while ((len = in.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            os.flush();
            in.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        httpFileEntity.setResult(getVerificationCode(file));
        return httpFileEntity;


    }

    public static Optional<HttpVerificationCodeEntity> getVerificationCodeEntity() {
        String result = null;
        File tempPic = null;
        HttpVerificationCodeEntity httpFileEntity = null;
        try {
            tempPic = File.createTempFile("tempPic", ".jpg", tempPath);
            httpFileEntity = getVerificationCodeEntity(tempPic);
//            result = getVerificationCode(httpFileEntity.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (tempPic != null) {
                tempPic.delete();
            }
        }
        return Optional.of(httpFileEntity);

    }

    /**
     * @param tempPic
     * @return
     */
    public static String getVerificationCode(File tempPic) {
        File tempResult = null;
        BufferedReader rd = null;
        StringBuffer sb = new StringBuffer();
        try {
            tempResult = File.createTempFile("tempResult", ".txt", tempPath);
            OCRUtil.runOCR(tempPic.getAbsolutePath(), tempResult.getAbsolutePath().substring(0, tempResult.getAbsolutePath().lastIndexOf(".")), "");
            rd = new BufferedReader(new FileReader(tempResult));
            String line = null;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }

            rd.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            if (tempPic != null) {
//                tempPic.delete();
//            }
            if (tempResult != null) {
                tempResult.delete();
            }
            try {
                if (rd != null) {
                    rd.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return sb.toString();
    }

    public static List<SearchDoctor> searchRegisterList() {
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
        qparams[6] = new BasicNameValuePair("page_zjxm", "张卫华");
        qparams[7] = new BasicNameValuePair("page_yyrq", "");
        qparams[8] = new BasicNameValuePair("page_zjxb", "");
        qparams[9] = new BasicNameValuePair("page_ksdm", "");
        qparams[10] = new BasicNameValuePair("page_wsjksdm", "");
        qparams[11] = new BasicNameValuePair("page_wsjzkdm", "");
        map.put("nameValuePair", qparams);
        request.setExtras(map);

        CollectorPipeline collectorPipeline = new ResultItemsCollectorPipeline();
        Spider spider = Spider.create(new SearchFreeDoctorPageProcessor())
                .addRequest(request)
                .setDownloader(new MyHttpClientDownloader())
                .addPipeline(collectorPipeline)
                .setSpawnUrl(false);
        spider.run();
        List<ResultItems> list = collectorPipeline.getCollected();
        ResultItems rss = list.get(0);
        Json json = rss.get("result");
        spider.close();
        JSONObject doctorsJson = JSONObject.parseObject(json.get());
        List<SearchDoctor> doctorsList = new ArrayList<>();
        if (doctorsJson.getInteger("total") > 0) {
            Type type = new TypeReference<List<SearchDoctor>>() {
            }.getType();
            doctorsList = JSON.parseObject(doctorsJson.getJSONArray("rows").toJSONString(), type);

        }

        return doctorsList;
    }

    public static List<RegisterResource> getDoctorRegisterPageResourses(SearchDoctor searchDoctorsVO) throws HttpException {
        checkNotNull(searchDoctorsVO);
        Request request = new Request();
        request.setMethod(HttpConstant.Method.POST);
        request.setUrl("http://live.fshealth.gov.cn/smjkfw/wsyygh/doctorrespage.action");//Post TO GET DOCTOR PAGE
        Map<String, Object> map = new HashMap<>();
        NameValuePair[] qparams = new BasicNameValuePair[5];
        qparams[0] = new BasicNameValuePair("id", String.valueOf(searchDoctorsVO.getId()));
        qparams[1] = new BasicNameValuePair("rq", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(searchDoctorsVO.getYyrq()));
        qparams[2] = new BasicNameValuePair("ysgh", searchDoctorsVO.getYsgh());
        qparams[3] = new BasicNameValuePair("yydm", searchDoctorsVO.getYydm());
        qparams[4] = new BasicNameValuePair("zkdm", searchDoctorsVO.getZkdm());
        map.put("nameValuePair", qparams);
        request.setExtras(map);

        CollectorPipeline collectorPipeline = new ResultItemsCollectorPipeline();
        Spider spider = Spider.create(new RegDoctorResoursesPageProcessor())
                .addRequest(request)
                .setDownloader(new MyHttpClientDownloader())
                .addPipeline(collectorPipeline)
                .setSpawnUrl(false);
        ResultItems rss = null;
        List<ResultItems> list = null;
        spider.run();
        list = collectorPipeline.getCollected();
//        System.out.println((new Date()).toGMTString() + "===== getDoctorRegisterPageResourses !!");
        if (list == null || list.size() == 0) {
            throw new HttpException((new Date()).toString() + "getDoctorRegisterPageResourses error!");
        }
        rss = list.get(0);
        List<RegisterResource> rrlist = new ArrayList<>();
        boolean state = rss.get("state");
        if (state) {
            rrlist = rss.get("resourceList");
        }

        spider.close();
        return rrlist;
    }

    //TODO 1, refactor : verify code function need to be wrap by the login request loop
    //TODO 1, test multiable login
    public static HttpVerificationCodeEntity login() {
        Request request = new Request();
        request.setMethod(HttpConstant.Method.POST);
//        request.setUrl("http://music.163.com/weapi/song/enhance/player/url");
        request.setUrl("http://live.fshealth.gov.cn/smjkfw/wsyygh/login.action");
        Map<String, Object> map = new HashMap<>();
        NameValuePair[] qparams = new BasicNameValuePair[4];
        qparams[0] = new BasicNameValuePair("userId", "4406000001000271401");
        qparams[1] = new BasicNameValuePair("password", "69d5c50df527a4b8cb1cd00c19dec17e");
        Optional<HttpVerificationCodeEntity> o = null;
        HttpVerificationCodeEntity httpVerificationCodeEntity = null;
        Pattern p = Pattern.compile("[0-9]{4}");
        ResultItems rss = null;
        do {
            Matcher m = null;
            while (o == null || httpVerificationCodeEntity == null || m == null || !m.matches()) {
                o = HttpUtil.getVerificationCodeEntity();
                httpVerificationCodeEntity = o.get();
                logger.info("===>" + httpVerificationCodeEntity.getResult());
                m = p.matcher(httpVerificationCodeEntity.getResult());
            }
            qparams[2] = new BasicNameValuePair("yzm", httpVerificationCodeEntity.getResult());
            qparams[3] = new BasicNameValuePair("dlfs", "1");
            map.put("nameValuePair", qparams);
            request.setExtras(map);
            CollectorPipeline cp = new ResultItemsCollectorPipeline();
            Spider spider = Spider.create(new LoginPageProcessor(httpVerificationCodeEntity.getCookiesMap()))
                    .addRequest(request)
                    .setDownloader(new MyHttpClientDownloader())
                    .addPipeline(cp);
            logger.info((new Date()).toString() + "=== running login!!");
            try {
                Thread.currentThread().sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spider.run();
            List<ResultItems> list = cp.getCollected();
            rss = list.size() == 0 ? null : list.get(0);
            spider.close();
        } while (rss == null || !"{\"msg\":1}".equals(rss.get("msg").toString().trim()));

        return httpVerificationCodeEntity;
    }

    /**
     * @param cookiesMap
     * @param resourseId match to <code>zybh</code> in {@link HttpUtil#searchRegisterList()} results
     * @return
     */
    public static boolean registerDoctor(Map<String, String> cookiesMap, String resourseId) {
        Request regRequest = new Request();
        regRequest.setMethod(HttpConstant.Method.POST);
//        request.setUrl("http://music.163.com/weapi/song/enhance/player/url");
        regRequest.setUrl("http://live.fshealth.gov.cn/smjkfw/wsyygh/gh/dowebbespeakno.action");
        Map<String, Object> regMap = new HashMap<>();
        NameValuePair[] regQparams = new BasicNameValuePair[4];
        regQparams[0] = new BasicNameValuePair("zybh", resourseId);
        regQparams[1] = new BasicNameValuePair("klx", "1"); //no usage, just a fade value
        regQparams[2] = new BasicNameValuePair("jkkh", "");
        regQparams[3] = new BasicNameValuePair("xm", "");
        regMap.put("nameValuePair", regQparams);
        regRequest.setExtras(regMap);
        CollectorPipeline cp = new ResultItemsCollectorPipeline();
        Spider spider2 = Spider.create(new RegisterDoctorPageProcessor(cookiesMap))
                .addRequest(regRequest)
                .setDownloader(new MyHttpClientDownloader())
                .addPipeline(cp);
        spider2.run();
        ResultItems rss = null;
        List<ResultItems> list = cp.getCollected();
        rss = list.size() == 0 ? null : list.get(0);
        spider2.close();
        return (rss == null || rss.get("html") == null || rss.get("html").toString().trim().contains("对不起，预约操作失败."));

    }


}

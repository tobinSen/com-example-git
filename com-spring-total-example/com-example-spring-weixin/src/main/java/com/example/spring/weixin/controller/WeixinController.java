package com.example.spring.weixin.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.spring.weixin.common.ThisTest;
import com.example.spring.weixin.configuration.City;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class WeixinController {

    /**
     * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx99e0be8584fe823e&redirect_uri=http://store.coalscc.com/wechat/notify&response_type=code&scope=snsapi_userinfo&state=&connect_redirect=1#wechat_redirect
     */

    private static final String app_id = "wx0b585522f5b4143e";
    private static final String app_secret = "6ffd0225ed6c8c5aec7a0b17d2373f5e";

    private static final String access_token_url = " https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    private static final String weixin_server_ip_url = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=%s";
    private static final String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s";

    private static final String menu = "{\"button\":[{\"type\":\"view\",\"url\":\"\",\"name\":\"个人中心\"}]}";

    private static JSONObject menuJson;

    static {
        menuJson = new JSONObject();

        JSONArray array = new JSONArray();
        JSONObject json = new JSONObject();
        json.put("type", "view");
        json.put("url", "");
        json.put("name", "个人中心");
        array.add(json);

        menuJson.put("button", array);
    }

    @Autowired
    private City city;
    @Autowired
    private ThisTest thisTest;

    @PostConstruct
    public void init() {
        String accessToken = HttpUtil.get(String.format(access_token_url, app_id, app_secret));
        JSONObject json = JSON.parseObject(accessToken);
        String access_token = json.getString("access_token");
        String body = HttpRequest.post(String.format(menu_create_url, access_token)).contentType("application/json").body(menuJson.toJSONString()).execute().body();
    }

    //验证的入口GET
    @GetMapping("weixin/check")
    public void start(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String signatures = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        response.getWriter().write(echostr); //以流的方式返回

        //QrCodeUtil.generate("tongjian123", 120, 120, "jpg", response.getOutputStream());
        //response.getWriter().write("二次的echostr"); 流只能响应一次

    }

    //获取消息接口POST
    @PostMapping("weixin/check") //@XmlRootElement(name="xml")
    public String simpleMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        String accessToken = HttpUtil.get(String.format(access_token_url, app_id, app_secret));
//        JSONObject json = JSON.parseObject(accessToken);
//        String access_token = json.getString("access_token");
//        String msg = HttpUtil.post(String.format(menu_create_url, access_token), menu);

        return "success";
    }

    /**
     * {
     * "access_token": "36_t9yIg5tldRvg4hEkAuNmOteMPp2CKixKqiVCpaDkAbgvPiIjATkR3VTXbyIenFTdVA2yeSUiKgtKA44FOYxW6p7q6m75fRJrPvAHic4fqeKGFz7QrdLyM_GhWFfRH0DDXUWc_WI-6eZYDae2ZHYcAAAPXO",
     * "expires_in": 7200
     * }
     */
    @RequestMapping("weixin/accessToken")
    public JSONObject accessToken(HttpServletRequest request) throws Exception {
        String accessToken = HttpUtil.get(String.format(access_token_url, app_id, app_secret));
        return JSON.parseObject(accessToken);

    }

    @RequestMapping("weixin/serverIp")
    public JSONObject serverIp(HttpServletRequest request) throws Exception {
        String accessToken = HttpUtil.get(String.format(access_token_url, app_id, app_secret));
        JSONObject json = JSON.parseObject(accessToken);
        String access_token = json.getString("access_token");
        String ip = HttpUtil.get(String.format(weixin_server_ip_url, access_token));
        return JSON.parseObject(ip);

    }


    @RequestMapping("ajax.do")
    public Map<String, Object> weixin() throws Exception {
        try {
            thisTest.thisTest();
            Map<String, Object> map = new HashMap<>();
            map.put("id", "123");
            map.put("code", "123");
            return map;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }


    /**
     * post请求
     *
     * @param url
     * @param json
     * @return
     */
    public static JSONObject doPost(String url, JSONObject json) {

        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        try {
            StringEntity s = new StringEntity(json.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(s);
            HttpResponse res = httpclient.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(res.getEntity());// 返回json格式：
                response = JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }


}

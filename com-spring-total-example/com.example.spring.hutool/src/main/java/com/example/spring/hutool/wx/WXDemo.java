package com.example.spring.hutool.wx;

import com.soecode.wxtools.api.IService;
import com.soecode.wxtools.api.WxConfig;
import com.soecode.wxtools.api.WxMessageRouter;
import com.soecode.wxtools.api.WxService;
import com.soecode.wxtools.bean.WxXmlMessage;
import com.soecode.wxtools.bean.WxXmlOutMessage;
import com.soecode.wxtools.util.xml.XStreamTransformer;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * wx-tools框架使用起来非常简单，关键词有6个。
 * WxConfig 基本配置库
 * WxService 微信公众平台统一API Service接口
 * WxMessageRouter 消息路由器
 * WxMessageMatcher (interface) 消息匹配器
 * WxMessageInterceptor (interface) 消息拦截器
 * WxMessageHandler (interface) 消息处理器
 */
public class WXDemo {

    public static void main(String[] args) {
        WxConfig wxConfig = new WxConfig();
        IService wxService = new WxService();
        // 创建一个路由器
        WxMessageRouter router = new WxMessageRouter(wxService);
        //router.rule().event(WxConsts.EVT_CLICK).eventKey(MenuKey.HELP).handler(HelpDocHandler.getInstance()).end();
    }

    @PostMapping
    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            // 微信服务器推送过来的是XML格式。
            WxXmlMessage wx = XStreamTransformer.fromXml(WxXmlMessage.class, request.getInputStream());
            System.out.println("消息：\n " + wx.toString());

            WxMessageRouter router = new WxMessageRouter(new WxService());

            WxXmlOutMessage xmlOutMsg = router.route(wx); //handler处理后的结果

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
}

package com.exampl.spring.oauth2.social.qq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 * QQ API 默认实现，继承 {@link AbstractOAuth2ApiBinding}。
 * 由于 Api 会使用得到的令牌来获取信息，每个用户的令牌是不同的，所以该类不是一个单例对象，每次访问 Api 都需要新建实例。
 */
public class DefaultQQApi extends AbstractOAuth2ApiBinding implements QQApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultQQApi.class);

    /**
     * QQ 获取 openId 的地址
     */
    private static final String URL_GET_OPEN_ID = "https://graph.qq.com/oauth2.0/me?access_token={accessToken}";
    /**
     * QQ 获取用户信息的地址
     */
    private static final String URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key={appId}&openid={openId}";

    /**
     * 客户端 appId
     */
    private String appId;
    /**
     * openId
     */
    private String openId;

    private ObjectMapper mapper = new ObjectMapper();

    public DefaultQQApi(String accessToken, String appId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        this.openId = getOpenId(accessToken);
    }

    @Override
    public QQUser getQQUser() {
        String result = getRestTemplate().getForObject(URL_GET_USER_INFO, String.class, appId, openId);

        QQUser user = null;
        try {
            user = mapper.readValue(result, QQUser.class);
        } catch (IOException e) {
            LOGGER.error("parse qq UserInfo error.");
        }
        if (user == null) {
            throw new RuntimeException("login.provider.user.not-found");
        }
        user.setOpenId(openId);
        return user;
    }

    /**
     * 获取用户 OpenId
     */
    private String getOpenId(String accessToken) {
        // 返回结构：callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );
        String openIdResult = getRestTemplate().getForObject(URL_GET_OPEN_ID, String.class, accessToken);
        if (StringUtils.isEmpty(openIdResult) || openIdResult.contains("code")) {
            throw new RuntimeException("获取QQ账号错误");
        }
        // 解析 openId
        String[] arr = StringUtils.substringBetween(openIdResult, "{", "}").replace("\"", "").split(",");
        String openid = null;
        for (String s : arr) {
            if (s.contains("openid")) {
                openid = s.split(":")[1];
            }
        }
        return openid;
    }
}

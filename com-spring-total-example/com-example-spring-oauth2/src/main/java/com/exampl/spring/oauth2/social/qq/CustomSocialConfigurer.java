package com.exampl.spring.oauth2.social.qq;

import org.springframework.context.annotation.Configuration;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.util.Assert;

/**
 * social 配置器，支持设置Social过滤器处理地址.
 *
 * <pre>
 *  SpringSocialConfigurer socialConfigurer = new CustomSocialConfigurer();
 *  http.apply(socialConfigurer);
 * </pre>
 */
@Configuration
public class CustomSocialConfigurer extends SpringSocialConfigurer {

    private static final String DEFAULT_FILTER_PROCESSES_URL = "/openid";

    private String filterProcessesUrl = DEFAULT_FILTER_PROCESSES_URL;

    public CustomSocialConfigurer() {
    }

    public CustomSocialConfigurer(String filterProcessesUrl) {
        Assert.notNull(filterProcessesUrl, "social filterProcessesUrl should not be null.");
        this.filterProcessesUrl = filterProcessesUrl;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessesUrl);
        return (T) filter;
    }
}

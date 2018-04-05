package com.jeekhan.wx.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 自定义拦截器配置
 * @author jeekhan
 *
 */
@Configuration
public class CustomWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter{
    @Bean   
    public HandlerInterceptor getLogInInterceptor(){
        return new LogInInterceptor();
    }

    @SuppressWarnings("deprecation")
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns 用于添加拦截规则, 这里假设拦截 /url 后面的全部链接
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(getLogInInterceptor()).addPathPatterns("/qrcode/**");
        registry.addInterceptor(getLogInInterceptor()).addPathPatterns("/fans/**");
        registry.addInterceptor(getLogInInterceptor()).addPathPatterns("/kfaccount/**");
        registry.addInterceptor(getLogInInterceptor()).addPathPatterns("/kfmessage/**");
        registry.addInterceptor(getLogInInterceptor()).addPathPatterns("/mass/**");
        registry.addInterceptor(getLogInInterceptor()).addPathPatterns("/wxmenu/**");
        registry.addInterceptor(getLogInInterceptor()).addPathPatterns("/tplmsg/**");
        
        registry.addInterceptor(getLogInInterceptor()).addPathPatterns("/index");
        registry.addInterceptor(getLogInInterceptor()).addPathPatterns("/logout");
        super.addInterceptors(registry);
    }
}

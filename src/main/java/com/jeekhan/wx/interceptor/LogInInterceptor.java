package com.jeekhan.wx.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jeekhan.wx.dto.Operator;



/**
 * 系统登陆拦截器
 * @author jeekhan
 *
 */
public class LogInInterceptor extends HandlerInterceptorAdapter{
	Logger log = LoggerFactory.getLogger(LogInInterceptor.class);
	
	/**
	 * 检查用户是否登陆，如果为登陆则返回登陆页面
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//String requestUri = request.getRequestURI();  
        String contextPath = request.getContextPath();  
        //String url = requestUri.substring(contextPath.length()); 
        	Operator operator =  (Operator)request.getSession().getAttribute("operator");   
        if(operator == null || operator.getLoginUserId() == null || operator.getLoginUserId() < 1){  
            log.info("Interceptor：跳转到login-page页面！");  
            response.sendRedirect(contextPath + "/login-page");
            return false;  
        }else {
            return true; 
        }
	}
}

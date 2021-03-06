package com.zonesion.layout.controller;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zonesion.layout.model.AdminEntity;

/**  
 * 在业务处理器处理请求之前preHandle被调用  
 * 如果返回false  
 *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链 
 * 如果返回true  
 *    执行下一个拦截器,直到所有的拦截器都执行完毕  
 *    再执行被拦截的Controller  
 *    然后进入拦截器链,  
 *    从最后一个拦截器往回执行所有的postHandle()  
 *    接着再从最后一个拦截器往回执行所有的afterCompletion()  
 */    
public class LoginInterceptor extends HandlerInterceptorAdapter{
	private final Logger logger = Logger.getLogger(LoginInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		super.afterConcurrentHandlingStarted(request, response, handler);
	}
	
	/** 
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作    
     * 可在modelAndView中加入数据，比如当前时间 
     */  
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
	    logger.info("==============执行顺序: 1、preHandle================");    
        String requestUri = request.getRequestURI();  
        String contextPath = request.getContextPath();  
        String url = requestUri.substring(contextPath.length());  
        String params = request.getQueryString();
        logger.info("requestUri:"+requestUri);    
        logger.info("contextPath:"+contextPath);    
        logger.info("url:"+url); //url:/template/editUI
        logger.info("params:"+params);
        if(url.startsWith("/admin") || url.startsWith("/project") || url.startsWith("/template")){
        	AdminEntity admin =  (AdminEntity)request.getSession().getAttribute("admin");   
        	if(admin == null ){
        		logger.info("Interceptor：跳转到login页面！");
        		if(params != null && params.equals("")){
        			request.getSession().setAttribute("from", url+"?"+params);
        		}else{
        			request.getSession().setAttribute("from", url);
        		}
        		response.sendRedirect(contextPath+"/admin/loginUI");
        		return false;  //不放行
        	}else  
        		return true;   //放行
        }else{
        	response.sendRedirect(contextPath+"/error/404.jsp");
        	return false; //不放行
        }
	}
	
	public String getParams(HttpServletRequest request){
		StringBuffer result = new StringBuffer("");
		@SuppressWarnings("rawtypes")
		Enumeration paramNames = request.getParameterNames();  
        while (paramNames.hasMoreElements()) {  
            String paramName = (String) paramNames.nextElement();  
            String[] paramValues = request.getParameterValues(paramName);  
            if (paramValues.length == 1) {  
                String paramValue = paramValues[0];  
                if (paramValue.length() != 0) {  
                	result.append(paramName).append("=").append(paramValue).append("&");
                }  
            }  
        }
        result.delete(result.length()-1, result.length());
        return result.toString();
	}

}

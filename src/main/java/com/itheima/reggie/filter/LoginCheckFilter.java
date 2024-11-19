package com.itheima.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.itheima.reggie.handler.RequestContextHolder;
import com.itheima.reggie.common.R;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.AntPathMatcher;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.itheima.reggie.service.impl.EmployeeServiceImpl.SESSION_EMPLOYEE_KEY;
import static com.itheima.reggie.service.impl.UserServiceImpl.SESSION_USER_KEY;

/**
 * 检查用户是否已经完成登录
 * @author ellie
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    /**
     * 路径匹配器
     */
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    /**
     * 检查用户是否已经完成登录
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //方便与postman测试
        if (StringUtils.equals(request.getHeader("key"), "postman")) {
            filterChain.doFilter(request, response);
            return;
        }
        //1. 获取本次请求的URL
        String requestUrl = request.getRequestURI();
        //2. 判断本次请求是否需要处理 -> 检查登录状态
        //2.1 不需要处理的请求
        String[] urls = new String[]{
                "/employee/forgotPassword",
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/sendMsg",
                "/user/login"
        };
        //2.2 本次url是否在urls里面。
        boolean match = checkUrl(requestUrl, urls);
        //3. 如果不需要处理则直接放行
        if (match) {
            RequestContextHolder.setCurrentId(1L);
            filterChain.doFilter(request, response);
            return;
        }

        //4. 判断登录状态，已登录直接放行
        if (request.getSession().getAttribute(SESSION_EMPLOYEE_KEY) != null){
            Long id = (Long) request.getSession().getAttribute(SESSION_EMPLOYEE_KEY);
            RequestContextHolder.setCurrentId(id);
            //已登录直接放行
            filterChain.doFilter(request, response);
            return;
        }

        if (request.getSession().getAttribute(SESSION_USER_KEY) != null){
            Long id = (Long) request.getSession().getAttribute(SESSION_USER_KEY);
            RequestContextHolder.setCurrentId(id);
            //已登录直接放行
            filterChain.doFilter(request, response);
            return;
        }

        //5.如果未登录则返回登录结果，通过输出流方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }

    /**
     * 判断本次url是否在urls里面
     * @param requestUrl
     * @param urls
     * @return
     */
    public boolean checkUrl(String requestUrl, String[] urls){
        for(String url: urls){
            boolean match = PATH_MATCHER.match(url, requestUrl);
            if (match) {
                return true;
            }
        }
        return false;
    }
}

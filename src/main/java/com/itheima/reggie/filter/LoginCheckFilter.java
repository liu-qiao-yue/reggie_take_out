package com.itheima.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.itheima.reggie.handler.RequestContextHolder;
import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查用户是否已经完成登录
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*") // 过滤器,拦截所有请求，filterName为过滤器名称
public class LoginCheckFilter implements Filter {

    //路径匹配器
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
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //方便与postman测试
        if (StringUtils.equals(request.getHeader("key"), "postman")) {
            filterChain.doFilter(request, response);
            return;
        }
        //1. 获取本次请求的URL
        String requestURI = request.getRequestURI();
        //2. 判断本次请求是否需要处理 -> 检查登录状态
        //2.1 不需要处理的请求
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"
        };
        //2.2 本次url是否在urls里面。
        boolean match = checkUrl(requestURI, urls);
        //3. 如果不需要处理则直接放行
        if (match) {
            filterChain.doFilter(request, response);
            return;
        }

        //4. 判断登录状态，已登录直接放行
        if (request.getSession().getAttribute("employee") != null){
            Long id = (Long) request.getSession().getAttribute("employee");
            RequestContextHolder.setCurrentId(id);
            filterChain.doFilter(request, response);//直接具体的业务处理逻辑
            return;
        }

        //5.如果未登录则返回登录结果，通过输出流方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }

    /**
     * 判断本次url是否在urls里面
     * @param requestURI
     * @param urls
     * @return
     */
    public boolean checkUrl(String requestURI, String[] urls){
        for(String url: urls){
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) return true;
        }
        return false;
    }
}

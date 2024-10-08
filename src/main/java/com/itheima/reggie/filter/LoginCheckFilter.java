package com.itheima.reggie.filter;

import lombok.extern.slf4j.Slf4j;
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
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    //路径匹配器
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
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
            filterChain.doFilter(request, response);
            return;
        }

        //5.如果未登录则返回登录结果，通过输出流方向

        log.info("there is a filter about {}", request.getRequestURI());
        filterChain.doFilter(request, response);
    }

    /**
     *
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

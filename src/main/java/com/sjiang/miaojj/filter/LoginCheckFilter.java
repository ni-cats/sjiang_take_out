package com.sjiang.miaojj.filter;

import com.alibaba.fastjson.JSON;
import com.sjiang.miaojj.common.BaseContext;
import com.sjiang.miaojj.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @BelongsProject: sjiang_take_out
 * @BelongsPackage: com.sjiang.reggie.filter
 * @Author: Ni_cats
 * @email: Ni_cats@163.com
 * @CreateTime: 2023-04-14  17:03
 * @Description: TODO
 * @Version: 1.0
 */

@Slf4j
@WebFilter(filterName = "LoginCheckFilter", urlPatterns = {"/*"})
public class LoginCheckFilter implements Filter {

    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        log.info("拦截到请求：{}", request.getRequestURI());

        //1、获取本次请求的URI
        String requestURI = request.getRequestURI();

        //不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg", //移动端发送短信
                "/user/login" ,//移动端登录
                "/doc.html",
                "/webjars/**",
                "/swagger-resources",
                "/v2/api-docs"
        };

        //2、判断本次请求是否需要处理
        boolean check = check(urls, requestURI);

        //3、如果不需要处理，则直接放行

        if (check) {
            filterChain.doFilter(request, response);
            log.info("拦截到请求：{},该请求不需要处理！", request.getRequestURI());
            return;
        }

        //4-1、判断登录状态，如果已登录，则直接放行
        if (request.getSession().getAttribute("employee") != null) {

            long id = Thread.currentThread().getId();
            log.info("当前线程id：{}", id);

            log.info("拦截到请求：{},用户已登录，id为{}！", request.getRequestURI(), request.getSession().getAttribute("employee"));

            //给当前线程存储当前登录用户的id
            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
            filterChain.doFilter(request, response);
            return;
        }


        //4-2、判断移动端登录状态，如果已登录，则直接放行
        if (request.getSession().getAttribute("user") != null) {

            long id = Thread.currentThread().getId();
            log.info("当前线程id：{}", id);

            log.info("拦截到请求：{},用户已登录，id为{}！", request.getRequestURI(), request.getSession().getAttribute("user"));

            //给当前线程存储当前登录用户的id
            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);
            filterChain.doFilter(request, response);
            return;
        }

        //5、如果未登录则返回未登录结果，通过输出流方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        log.info("拦截到请求：{},用户未登录！", request.getRequestURI());
    }

    /**
     * @param urls        : 不需要处理的请求路径
     * @param requestURI: 该次请求的URL地址
     * @return boolean
     * @throws
     * @Description TODO 检查本次请求是否需要放行
     * @author Ni_cats
     * @email Ni_cats@163.com
     * @date 2023/4/14 17:16
     */

    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}

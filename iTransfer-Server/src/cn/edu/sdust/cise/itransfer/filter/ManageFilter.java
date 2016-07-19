package cn.edu.sdust.cise.itransfer.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**登陆拦截过滤器
 * Created by 宇强 on 2016/4/30 0030.
 */
@WebFilter(filterName = "ManageFilter")
public class ManageFilter implements Filter {
    //登录标记key
    public static final String LOGIN_TOKEN = "user";
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        //检查是否是登录状态，不是登录状态跳转到登录页面
        Object object = request.getSession().getAttribute(LOGIN_TOKEN);
        if(object==null){
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request,resp);
            return;
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}

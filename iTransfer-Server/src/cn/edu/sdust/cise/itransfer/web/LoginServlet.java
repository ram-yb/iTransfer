package cn.edu.sdust.cise.itransfer.web;

import cn.edu.sdust.cise.itransfer.filter.ManageFilter;
import cn.edu.sdust.cise.itransfer.utils.ConfigUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by 宇强 on 2016/4/30 0030.
 */
@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //验证用户密码
        if (username != null && username.equals(ConfigUtil.getString("username")) && password != null && password.equals(ConfigUtil.getString("password"))) {
            HttpSession session = request.getSession();
            session.setAttribute(ManageFilter.LOGIN_TOKEN,"user");
            response.sendRedirect(request.getContextPath()+"/manage");
            return;
        }else {
            request.setAttribute("message","用户名或密码不正确！！！");
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request,response);
            return;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

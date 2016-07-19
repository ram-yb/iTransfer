package cn.edu.sdust.cise.itransfer.web;

import cn.edu.sdust.cise.itransfer.service.impl.FileServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 宇强 on 2016/4/30 0030.
 */
@WebServlet(name = "ManageServlet")
public class ManageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String init = request.getParameter("init");
        if (init == null || init.trim().equals("")) {
            //不是初始化命令，直接显示管理页面
            request.getRequestDispatcher("/WEB-INF/jsp/manage.jsp").forward(request, response);
            return;
        } else if (init.equals("true")) {
            //初始化数据库
            FileServiceImpl service = new FileServiceImpl();
            service.initDatabase();
            request.setAttribute("message","数据表初始化成功！！！");
            request.getRequestDispatcher("/WEB-INF/jsp/manage.jsp").forward(request, response);
            return;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

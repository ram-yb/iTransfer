package cn.edu.sdust.cise.itransfer.web;

import cn.edu.sdust.cise.itransfer.domain.FileLog;
import cn.edu.sdust.cise.itransfer.service.impl.CheckServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by 宇强 on 2016/6/7 0007.
 */
@WebServlet(name = "ScanServlet")
public class ScanServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filecode = request.getParameter("filecode");
        String password = request.getParameter("password");
        String filename = request.getParameter("filename");

        PrintWriter writer = response.getWriter();
        if(CheckServiceImpl.getInstance().checkExist(filename)){
            FileLog log = new FileLog();
            log.setFilecode(Integer.parseInt(filecode));
            log.setPassword(password);
            CheckServiceImpl.getInstance().setElement(filename,log,1);

            writer.write("扫描成功！");
        }else {
            writer.write("验证码已过时，请重新刷新网页！");
        }
        writer.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}

package cn.edu.sdust.cise.itransfer.web;

import cn.edu.sdust.cise.itransfer.domain.Check;
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
@WebServlet(name = "CheckQueryServlet")
public class CheckQueryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String filename = request.getParameter("filename");
        //System.out.println(filename);
        Check value = CheckServiceImpl.getInstance().query(filename);

        PrintWriter writer = response.getWriter();
        if (value == null) {
            writer.write("null");
        } else if (value.getFlag() == 0) {
            writer.write("false");
        } else {
            String result = "filecode=" + value.getFilecode() + "&password=" + value.getPassword();
            CheckServiceImpl.getInstance().remove(filename,this.getServletContext().getRealPath("/"));
            writer.write(result);
        }
        writer.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

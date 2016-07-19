package cn.edu.sdust.cise.itransfer.web;

import cn.edu.sdust.cise.itransfer.domain.FileLog;
import cn.edu.sdust.cise.itransfer.domain.Result;
import cn.edu.sdust.cise.itransfer.exception.FileExpiredException;
import cn.edu.sdust.cise.itransfer.service.impl.FileServiceImpl;
import cn.edu.sdust.cise.itransfer.utils.WebUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by 宇强 on 2016/4/30 0030.
 */
@WebServlet(name = "QueryServlet")
public class QueryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        String filecodeString = request.getParameter("filecode");
        String password = request.getParameter("password");
        String client = request.getParameter("client");
        String img = request.getParameter("filename");

        //filecode转成int
        int filecode = 0;
        try {
            filecode = Integer.parseInt(filecodeString);
        }catch (Exception e){
            responseMessage(client,request,response,"文件id只能是数字哦！！！");
            return;
        }

        //查询文件
        FileServiceImpl service = new FileServiceImpl();
        List<FileLog> fileLogs = null;
        try {
            fileLogs = service.queryFile(filecode, password);
        }catch (FileExpiredException e){
            responseMessage(client,request,response,"所有文件已过期！！！");
            return;
        }

        //密码不正确
        if(fileLogs==null){
            responseMessage(client,request,response,"文件id或验证密码不正确！！！");
            return;
        }

        //获取每个文件对应的图标
        WebUtils.setFileIcons(fileLogs);

        //app反馈
        if(client!=null&&client.equals("app")){
            Result result = new Result();
            result.setFileLogs(fileLogs);
            result.setType("file");
            response.getWriter().write(new Gson().toJson(result));
            response.getWriter().close();
            return;
        }else {
            //浏览器反馈
            //密码正确，跳转到文件页面
            request.setAttribute("files", fileLogs);
            request.setAttribute("type", "query");
            request.getRequestDispatcher("/WEB-INF/jsp/filesquery.jsp").forward(request, response);
        }

        File image = new File(this.getServletContext().getRealPath("/")+img);
        if(image.exists()){
            System.out.println("image delete...");
            image.delete();
        }
    }

    private void responseMessage(String client,HttpServletRequest request,HttpServletResponse response,String message) throws IOException, ServletException {
        if(client!=null&&client.equals("app")){
            Result result = new Result();
            result.setMessage(message);
            result.setType("message");
            response.getWriter().write(new Gson().toJson(result));
            response.getWriter().close();
            return;
        }else {
            request.setAttribute("message", message);
            request.getRequestDispatcher("/WEB-INF/jsp/message.jsp").forward(request, response);
            return;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}

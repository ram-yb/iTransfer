package cn.edu.sdust.cise.itransfer.web;

import cn.edu.sdust.cise.itransfer.domain.Result;
import cn.edu.sdust.cise.itransfer.service.impl.FileServiceImpl;
import cn.edu.sdust.cise.itransfer.utils.ConfigUtil;
import cn.edu.sdust.cise.itransfer.utils.WebUtils;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * Created by 宇强 on 2016/4/30 0030.
 */
@WebServlet(name = "DownloadServlet")
public class DownloadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String savePath = this.getServletContext().getRealPath(
                "/WEB-INF/upload");

        String appDownload = request.getParameter("appDownload");
        if (appDownload != null && appDownload.equals("itransfer")) {
            //app下载
            String appPath = this.getServletContext().getRealPath(
                    "/WEB-INF/") + ConfigUtil.getString("appName");
            File app = new File(appPath);
            FileInputStream fis = new FileInputStream(app);
            response.setHeader("content-disposition", "attachment;filename="
                    + URLEncoder.encode(app.getName(), "UTF-8"));
            response.setHeader("content-length", app.length() + "");
            response.setContentType("application/zip");
            OutputStream os = response.getOutputStream();
            IOUtils.copy(fis, os);
            fis.close();
            os.close();
            return;
        }

        //获取参数
        String storeName = request.getParameter("storeName");
        String filename = request.getParameter("filename");
        String type = request.getParameter("type");
        String fid = request.getParameter("fid");
        String client = request.getParameter("client");
        //获取真实路径
        String realPath = WebUtils.EncodePath(storeName, savePath);
        //截取出显示路径
        System.out.println("最终路径=" + realPath);
        File file = new File(realPath);

        if (!file.exists()) {
            // 文件不存在
            if (client != null && client.equals("app")) {
                Result result = new Result();
                result.setMessage("您要下载的文件已删除！！！");
                result.setType("message");
                response.getWriter().write(new Gson().toJson(result));
                response.getWriter().close();
                return;
            } else {
                request.setAttribute("message", "您要下载的文件已删除！！！");
                request.getRequestDispatcher("/WEB-INF/jsp/message.jsp").forward(request, response);
                return;
            }
        }

        //文件下载响应头
        response.setHeader("content-disposition", "attachment;filename="
                + URLEncoder.encode(filename, "UTF-8"));
        response.setHeader("content-length", file.length() + "");
        response.setContentType("application/zip");
        System.out.println(file.getPath());

        //文件传输
        FileInputStream inputStream = new FileInputStream(file);
        OutputStream outputStream = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, len);
        }
        inputStream.close();
        outputStream.close();

        //中转完成，删除文件
        if (type != null && type.equals("query")) {
            //设置文件过期
            FileServiceImpl service = new FileServiceImpl();
            service.expiredFile(Integer.parseInt(fid));
            //删除服务器文件
            file.delete();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

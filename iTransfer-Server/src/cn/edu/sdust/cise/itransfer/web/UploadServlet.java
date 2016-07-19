package cn.edu.sdust.cise.itransfer.web;

import cn.edu.sdust.cise.itransfer.domain.FileLog;
import cn.edu.sdust.cise.itransfer.domain.Result;
import cn.edu.sdust.cise.itransfer.service.impl.CheckServiceImpl;
import cn.edu.sdust.cise.itransfer.service.impl.FileServiceImpl;
import cn.edu.sdust.cise.itransfer.utils.WebUtils;
import com.google.gson.Gson;
import com.google.zxing.WriterException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by 宇强 on 2016/4/30 0030.
 */
@WebServlet(name = "UploadServlet")
public class UploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // /服务器文件保存根目录
        String savePath = this.getServletContext().getRealPath(
                "/WEB-INF/upload");
        System.out.println("savePath = " + savePath);
        // 初始化upload对象
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");// 上传文件名中文乱码问题

        if (!upload.isMultipartContent(request)) {
            // /普通类型的表单
            return;
        }
        // /获得上传数据
        List<FileItem> list = null;
        try {
            list = upload.parseRequest(request);
        } catch (FileUploadException e1) {
            throw new RuntimeException(e1);
        }
        // 设置单个文件大小
        upload.setFileSizeMax(1024 * 10);
        // 设置总文件大小
        upload.setSizeMax(1024 * 50);
        // 监听文件上传进度
        upload.setProgressListener(new ProgressListener() {

            public void update(long readedSize, long totalSize, int items) {
                System.out.println("文件总大小：" + totalSize + " , 已上传大小："
                        + readedSize);
            }
        });

        List<Map<String, String>> files = new ArrayList<Map<String, String>>();
        String password = "";
        String client = "";
        // 进行文件保存操作
        for (FileItem item : list) {
            if (item.isFormField()) {
                //表单中的普通类型数据
                String name = item.getFieldName();
                // 普通表单中文乱码问题手工转换
                String value = item.getString("UTF-8");
                System.out.println(name + " = " + value);
                //提取密码
                if (name != null && name.equals("password"))
                    password = value;
                else if (name != null && name.equals("client")) {
                    client = value;
                }
            } else {
                Map<String, String> params = new HashMap<String, String>();
                // 获得文件名
                String filename = item.getName();

                // 判断上传文件为空
                if (filename == null || filename.trim().equals(""))
                    continue;

                //截取文件名
                if (filename.contains("\\"))
                    filename = filename.substring(filename.lastIndexOf("\\") + 1);
                else if (filename.contains("/"))
                    filename = filename.substring(filename.lastIndexOf("/") + 1);
                System.out.println("upload : filename = " + filename);
                // /原文件名生成对应的随即目录

                //封装数据到map
                params.put("filename", filename);
                filename = WebUtils.EnCodeFileName(filename);
                params.put("storeName", filename);
                if (filename.contains("."))
                    params.put("extension", filename.substring(filename.lastIndexOf(".")));
                else
                    params.put("extension", "");

                String realPath = WebUtils.EncodePath(filename, savePath);
                System.out.println("最终路径=" + realPath);

                // 获取文件流
                InputStream inputStream = item.getInputStream();
                File file = new File(realPath);
                file.createNewFile();
                FileOutputStream outputStream = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, len);
                }
                inputStream.close();
                outputStream.close();
                item.delete();

                //封装数据到Map
                params.put("md5", WebUtils.fileMd5(file));
                files.add(params);
            }
        }

        if (files.size() <= 0) {
            // 文件不存在
            if (client != null && client.equals("app")) {
                Result result = new Result();
                result.setMessage("您没有选择上传文件！！！");
                result.setType("message");
                response.getWriter().write(new Gson().toJson(result));
                response.getWriter().close();
                return;
            } else {
                request.setAttribute("message", "您没有选择上传文件！！！");
                request.getRequestDispatcher("/WEB-INF/jsp/message.jsp").forward(request, response);
                return;
            }
        }
        //保存相关数据到数据库
        FileServiceImpl service = new FileServiceImpl();
        List<FileLog> fileLogs = new ArrayList<FileLog>();
        for (Map<String, String> map : files) {
            FileLog fileLog = new FileLog();
            fileLog.setExtension(map.get("extension"));
            fileLog.setFilename(map.get("filename"));
            fileLog.setMd5(map.get("md5"));
            fileLog.setPassword(password);
            fileLog.setStoreName(map.get("storeName"));
            fileLogs.add(fileLog);
        }


        fileLogs = service.uploadFile(fileLogs);

        if (client != null && client.equals("app")) {
            Result result = new Result();
            result.setFileLogs(fileLogs);
            result.setType("file");
            response.getWriter().write(new Gson().toJson(result));
            response.getWriter().close();
            return;
        } else {
            //跳转反馈页面
//            request.setAttribute("files", fileLogs);
//            request.setAttribute("type", "upload");
//            request.getRequestDispatcher("/WEB-INF/jsp/file.jsp").forward(request, response);

            try {
                // 文件相对于web根目录的相对路径，UUID生成文件名
                String filename = "images/" + WebUtils.EnCodeFileName("1.gif");
                String img = WebUtils.zxing("filecode=" + fileLogs.get(0).getFilecode() + "&password=" + password, filename, this.getServletContext().getRealPath("/"));
                request.setAttribute("img", img);
                request.setAttribute("filecode", fileLogs.get(0).getFilecode());
                request.setAttribute("password", fileLogs.get(0).getPassword());

                //反向登陆状态二维码生成
//                String filename1 = "images/query/" + WebUtils.EnCodeFileName("1.gif");
//                String queryImg = WebUtils.zxing("time=" + System.currentTimeMillis(), filename1, this.getServletContext().getRealPath("/"));
//                request.setAttribute("queryImg", queryImg);
//                request.setAttribute("filename", filename);

                //将当前的用户加入缓存
                CheckServiceImpl.getInstance().setElement(filename, CheckServiceImpl.NULL, 0);

                request.getRequestDispatcher("/WEB-INF/jsp/filesend.jsp").forward(request, response);
            } catch (WriterException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

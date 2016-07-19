package cn.edu.sdust.cise.itransfer.listener; /**
 * Created by 宇强 on 2016/4/30 0030.
 */

import cn.edu.sdust.cise.itransfer.service.impl.CheckServiceImpl;
import cn.edu.sdust.cise.itransfer.utils.ConfigUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.util.*;

@WebListener()
public class FileDeleteListener implements ServletContextListener {

    // Public constructor is required by servlet spec
    public FileDeleteListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(final ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */
        System.out.println("监听器启动...");
        final ServletContext context = sce.getServletContext();
        //当前时间
        Date now = new Date();
        Timer timer = new Timer();
        //定时器，每天晚上12点清空过时文件，间隔时间24小时，在配置文件中配置
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String savePath = context.getRealPath(
                        "/WEB-INF/upload");
                List<File> list = new ArrayList<File>();
                //扫描文件
                listFiles(new File(savePath), list);

                //System.out.println(list);

                //遍历删除过期上传文件
                for (File file : list) {
                    String str = file.getName();
                    //System.out.println(str);
                    long time = Long.parseLong(str.substring(0, str.indexOf("_")));
                    //System.out.println("文件时间："+new Date(time));
                    if (System.currentTimeMillis() - time > ConfigUtil.getInt("filelog.time") * 1000)
                        file.delete();
                }

                savePath = context.getRealPath(
                        "/images");
                list.clear();
                //扫描文件
                listFiles(new File(savePath), list);

                //System.out.println(list);

                //遍历删除过期二维码图片
                for (File file : list) {
                    String str = file.getName();
                    //System.out.println(str);
                    long time = Long.parseLong(str.substring(0, str.indexOf("_")));
                    //System.out.println("文件时间："+new Date(time));
                    if (System.currentTimeMillis() - time > ConfigUtil.getInt("filelog.time") * 1000)
                        file.delete();
                }

                savePath = context.getRealPath(
                        "/images/query");
                list.clear();
                //扫描文件
                listFiles(new File(savePath), list);

                //System.out.println(list);

                //遍历删除过期反向登录状态查询二维码图片
                for (File file : list) {
                    String str = file.getName();
                    //System.out.println(str);
                    long time = Long.parseLong(str.substring(0, str.indexOf("_")));
                    //System.out.println("文件时间："+new Date(time));
                    if (System.currentTimeMillis() - time > ConfigUtil.getInt("filelog.time") * 1000) {
                        //删除二维码文件
                        file.delete();
                        //删除数据库记录
                        String filename = "images/query/"+file.getName();
                        CheckServiceImpl.getInstance().remove(filename, context.getRealPath("/"));
                    }
                }
            }

            /**递归扫描文件夹中的所有文件
             * @param file
             * @param list
             */
            public void listFiles(File file, List<File> list) {
                if (file.isDirectory()) {
                    File[] files = file.listFiles();
                    for (File f : files)
                        listFiles(f, list);
                } else {
                    list.add(file);
                }
            }
        }, new Date(now.getYear(), now.getMonth(), now.getDay(), 23, 59, 59), ConfigUtil.getInt("file.delete.hour") * 3600 * 1000);
    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
    }
}

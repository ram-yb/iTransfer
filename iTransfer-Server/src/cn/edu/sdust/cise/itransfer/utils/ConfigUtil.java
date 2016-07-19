package cn.edu.sdust.cise.itransfer.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**配置文件工具类
 * Created by 宇强 on 2016/4/30 0030.
 */
public class ConfigUtil {

    private static Properties properties = null;
    //初始化，加载配置文件到内存
    static {
        InputStream is = ConfigUtil.class.getClassLoader().getResourceAsStream("config.properties");
        properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(is!=null) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String getString(String name){
        return properties.getProperty(name);
    }

    public static int getInt(String name){
        return Integer.parseInt(properties.getProperty(name));
    }
}

package cn.edu.sdust.cise.itransfer.utils;

import cn.edu.sdust.cise.itransfer.domain.FileLog;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;

/**
 * Created by 宇强 on 2016/4/30 0030.
 */
public class WebUtils {

    private static final Map<String,String> fileIcons = new HashMap<String,String>();

    static {
        fileIcons.put(".apk","image/apk_icon.png");

        fileIcons.put(".gif","image/gif_icon.png");
        fileIcons.put(".jpg","image/jpg_icon.png");
        fileIcons.put(".png","image/png_icon.png");

        fileIcons.put(".mp3","image/mp3_icon.png");

        fileIcons.put(".mp4","image/mp4_icon.png");
        fileIcons.put(".avi","image/avi_icon.png");
        fileIcons.put(".rmvb","image/rmvb_icon.png");

        fileIcons.put(".pdf","image/pdf_icon.png");
        fileIcons.put(".doc","image/doc_icon.png");
        fileIcons.put(".docx","image/docx_icon.png");
        fileIcons.put(".ppt","image/ppt_icon.png");
        fileIcons.put(".pptx","image/pptx_icon.png");
        fileIcons.put(".xls","image/xls_icon.png");
        fileIcons.put(".xlsx","image/xlsx_icon.png");

        fileIcons.put(".txt","image/txt_icon.png");
        fileIcons.put(".xml","image/xml_icon.png");
        fileIcons.put(".zip","image/zip_icon.png");
        fileIcons.put(".rar","image/zip_icon.png");
        fileIcons.put(".7z","image/zip_icon.png");
        fileIcons.put(".file","image/file_icon.png");
    }

    /**获取文件MD5
     * @param file
     * @return
     */
    public static String fileMd5(File file){
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                in = null;
            }
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

    /**通过哈希打散文件名找到文件路径
     * @param filename
     * @param savePath
     * @return
     */
    public static String EncodePath(String filename, String savePath) {

        int hashCode = filename.hashCode();
        int dir1 = hashCode & 0xf;
        int dir2 = (hashCode & 0xf0) >> 4;
        String path = savePath + File.separator + dir1 + File.separator + dir2 + File.separator;
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();
        return path + filename;
    }

    /**合成唯一文件名
     * @param filename
     * @return
     */
    public static String EnCodeFileName(String filename) {

        String extension = null;
        if(filename.contains("."))
            extension = filename.substring(filename.lastIndexOf("."));
        return System.currentTimeMillis()+"_"+UUID.randomUUID().toString() + "_" + extension;
    }

    /**
     * @param text 二维码内容
     * @param realPath 网站根目录真实路径
     * @return 返回图片相对网站根目录的相对路径
     * @throws IOException
     * @throws com.google.zxing.WriterException
     */
    public static String zxing(String text,String filename,String realPath) throws IOException, WriterException {
        int width = 200; // 二维码图片宽度
        int height = 200; // 二维码图片高度
        String format = "gif";// 二维码的图片格式

        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");   // 内容所使用字符集编码

        BitMatrix bitMatrix = new MultiFormatWriter().encode(text+"&img="+filename, BarcodeFormat.QR_CODE, width, height, hints);

        // 生成二维码
        File outputFile = new File(realPath+"/"+filename);
        MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
        //返回相对路径，供网页访问使用
        return filename;
    }

    public static void setFileIcons(List<FileLog> fileLogs){
        for(FileLog log:fileLogs){
            String path = fileIcons.get(log.getExtension());
            if(path==null){
             log.setImagePath("image/notfound_icon.png");
            }else {
                log.setImagePath(path);
            }
        }
    }
}

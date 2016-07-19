package cn.edu.sdust.cise.itransfer.service.impl;

import cn.edu.sdust.cise.itransfer.dao.FileLogDao;
import cn.edu.sdust.cise.itransfer.dao.impl.FileLogDaoImpl;
import cn.edu.sdust.cise.itransfer.domain.FileLog;
import cn.edu.sdust.cise.itransfer.exception.FileExpiredException;
import cn.edu.sdust.cise.itransfer.utils.ConfigUtil;
import cn.edu.sdust.cise.itransfer.utils.JdbcUtils;

import java.io.File;
import java.util.*;

/**
 * 文件Service
 * Created by 宇强 on 2016/4/30 0030.
 */
public class FileServiceImpl {

    private FileLogDao fileLogDao = new FileLogDaoImpl();

    private FileCodePool fileCodePool = new FileCodePool(fileLogDao);
    /**
     * 上传文件
     *
     * @param fileLogs
     * @return
     */
    public List<FileLog> uploadFile(List<FileLog> fileLogs) {
        try {
            // 设置事务隔离级别
            JdbcUtils
                    .setTransactionIsolation(JdbcUtils.TRANSACTION_READ_COMMITTED);
            // 开启事务
            JdbcUtils.startTransaction();

            //从filecode池中获取filecode
            List<Map.Entry<Integer,Integer>> filecodes = fileCodePool.getFilecodes(fileLogs.size());
            for (int i = 0; i < fileLogs.size(); i++) {
                FileLog fileLog = fileLogs.get(i);
                //设置fid和filecode
                fileLog.setFid(filecodes.get(i).getKey());
                fileLog.setFilecode(filecodes.get(i).getValue());
                fileLog.setTime(new Date());
            }

            //覆盖数据库操作
            fileLogDao.updateFileLog(fileLogs);

            // 提交事务
            JdbcUtils.commit();
            return fileLogs;
        } catch (Exception e) {
            // 异常回滚
            JdbcUtils.rollback();
            throw new RuntimeException(e);
        } finally {
            // 关闭连接
            JdbcUtils.release();
        }
    }

    /**
     * 初始化数据库
     */
    public void initDatabase() {
        try {
            // 设置事务隔离级别
            JdbcUtils
                    .setTransactionIsolation(JdbcUtils.TRANSACTION_READ_COMMITTED);
            // 开启事务
            JdbcUtils.startTransaction();

            //清空原表
            fileLogDao.updateBySQL("truncate table filelog", null);
            //插入配置文件中配置的总文件数
            FileLog fileLog = new FileLog();
            for (int i = 0; i < ConfigUtil.getInt("filecode.length"); i++) {
                fileLog.init(i + "", i + "", i + "", ".ttt", new Date(System.currentTimeMillis() - 100000000), i, "123");
                fileLogDao.saveFileLog(fileLog);
            }

            // 提交事务
            JdbcUtils.commit();
        } catch (Exception e) {
            // 异常回滚
            JdbcUtils.rollback();
            throw new RuntimeException(e);
        } finally {
            // 关闭连接
            JdbcUtils.release();
        }
    }

    /**
     * 查询中转文件
     *
     * @param filecode
     * @param password
     * @return
     */
    public List<FileLog> queryFile(int filecode, String password) throws FileExpiredException{
        try {
            // 设置事务隔离级别
            JdbcUtils
                    .setTransactionIsolation(JdbcUtils.TRANSACTION_READ_COMMITTED);
            // 开启事务
            JdbcUtils.startTransaction();

            List<FileLog> list = fileLogDao.queryFileLogsBySQL("select * from filelog where filecode=? and password=?;", new Object[]{filecode, password});
//            //中转只有一次性，查询过后，把时间设置过期
//            if(list.size()>0){
//                for(FileLog fileLog:list) {
//                    Date tmp = fileLog.getTime();
//                    fileLog.setTime(new Date(fileLog.getTime().getTime() - 10000000));
//                }
//                fileLogDao.updateFileLog(list);
//                //fileLog.setTime(tmp);
//            }

            //检查要查询的文件是否在5分钟以内
            if (list.size() > 0) {
                Iterator<FileLog> iterator = list.iterator();
                long current = System.currentTimeMillis();
                while(iterator.hasNext()){
                    FileLog t = iterator.next();
                    long time = t.getTime().getTime();
                    if (current - time > Long.parseLong(ConfigUtil.getString("filelog.time")) * 1000) {
                        iterator.remove();
                    }
                }

                //文件全部过期
                if (list.size()<=0) {
                    JdbcUtils.commit();
                    throw new FileExpiredException("所有文件已过期");
                }
            }

            // 提交事务
            JdbcUtils.commit();
            return list.size() > 0 ? list : null;
        }catch (FileExpiredException e1){
            throw e1;
        }catch (Exception e) {
            // 异常回滚
            JdbcUtils.rollback();
            throw new RuntimeException(e);
        } finally {
            // 关闭连接
            JdbcUtils.release();
        }
    }
    /**
     * 文件下载完成后设置文件过期
     *
     * @param fid
     * @return
     */
    public void expiredFile(int fid) {
        try {
            // 设置事务隔离级别
            JdbcUtils
                    .setTransactionIsolation(JdbcUtils.TRANSACTION_READ_COMMITTED);
            // 开启事务
            JdbcUtils.startTransaction();

            FileLog fileLog = fileLogDao.queryFileLog(fid);
            fileLog.getTime().setTime(fileLog.getTime().getTime()-1000000);
            fileLogDao.updateBySQL("update filelog set time=? where fid=?;",new Object[]{fileLog.getTime(),fid});

            // 提交事务
            JdbcUtils.commit();
        } catch (Exception e) {
            // 异常回滚
            JdbcUtils.rollback();
            throw new RuntimeException(e);
        } finally {
            // 关闭连接
            JdbcUtils.release();
        }
    }
}

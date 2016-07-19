package cn.edu.sdust.cise.itransfer.service.impl;

import cn.edu.sdust.cise.itransfer.dao.CheckDao;
import cn.edu.sdust.cise.itransfer.dao.impl.CheckDaoImpl;
import cn.edu.sdust.cise.itransfer.domain.Check;
import cn.edu.sdust.cise.itransfer.domain.FileLog;
import cn.edu.sdust.cise.itransfer.utils.JdbcUtils;

import java.io.File;


/**反向登录状态监测
 * Created by 宇强 on 2016/6/7 0007.
 */
public class CheckServiceImpl {

    private static CheckServiceImpl service = null;
    public static FileLog NULL = null;
    private CheckServiceImpl(){}
    public static CheckServiceImpl getInstance(){
        if(service==null) {
            service = new CheckServiceImpl();
            NULL = new FileLog();
            NULL.setPassword("");
        }
        return service;
    }

    private CheckDao checkDao = new CheckDaoImpl();

    /**检查主页二维码是否存在
     * @param key
     * @return
     */
    public boolean checkExist(String key){
        try {
            // 设置事务隔离级别
            JdbcUtils
                    .setTransactionIsolation(JdbcUtils.TRANSACTION_READ_COMMITTED);
            // 开启事务
            JdbcUtils.startTransaction();

            Check check = checkDao.query(key);

            // 提交事务
            JdbcUtils.commit();
            return check!=null;
        } catch (Exception e) {
            // 异常回滚
            JdbcUtils.rollback();
            throw new RuntimeException(e);
        } finally {
            // 关闭连接
            JdbcUtils.release();
        }
    }

    /**查询完成，删除记录
     * @param key
     * @param realPath
     */
    public void remove(String key, String realPath){
        try {
            // 设置事务隔离级别
            JdbcUtils
                    .setTransactionIsolation(JdbcUtils.TRANSACTION_READ_COMMITTED);
            // 开启事务
            JdbcUtils.startTransaction();

            checkDao.delete(key);

            File file = new File(realPath+"/"+key);
            if(file.exists()){
                file.delete();
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

    /**设置临时记录
     * @param key
     * @param value
     * @param flag
     */
    public void setElement(String key,FileLog value,int flag){
        try {
            // 设置事务隔离级别
            JdbcUtils
                    .setTransactionIsolation(JdbcUtils.TRANSACTION_READ_COMMITTED);
            // 开启事务
            JdbcUtils.startTransaction();

            Check check = new Check();
            check.setFilecode(value.getFilecode());
            check.setPassword(value.getPassword());
            check.setFilename(key);
            check.setFlag(flag);

            Check tmp = checkDao.query(key);
            if(tmp==null) {
                checkDao.add(check);
            }else {
                check.setId(tmp.getId());
                checkDao.update(check);
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

    /**查询记录
     * @param filename
     * @return
     */
    public Check query(String filename){
        try {
            // 设置事务隔离级别
            JdbcUtils
                    .setTransactionIsolation(JdbcUtils.TRANSACTION_READ_COMMITTED);
            // 开启事务
            JdbcUtils.startTransaction();

            Check check = checkDao.query(filename);

            // 提交事务
            JdbcUtils.commit();
            return check;
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

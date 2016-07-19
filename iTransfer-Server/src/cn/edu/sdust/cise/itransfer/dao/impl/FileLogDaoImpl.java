package cn.edu.sdust.cise.itransfer.dao.impl;

import cn.edu.sdust.cise.itransfer.domain.FileLog;
import cn.edu.sdust.cise.itransfer.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 宇强 on 2016/4/30 0030.
 */
public class FileLogDaoImpl implements cn.edu.sdust.cise.itransfer.dao.FileLogDao {

    /**数据库增
     * @param file
     * @return
     * @throws SQLException
     */
    @Override
    public long saveFileLog(FileLog file) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "insert into filelog(filename,storeName,md5,time,extension,password,filecode) values(?,?,?,?,?,?,?);";
        Object[] params = {file.getFilename(),file.getStoreName(),file.getMd5(),file.getTime(),file.getExtension(),file.getPassword(),file.getFilecode()};
        long result = runner.insert(JdbcUtils.getConnection(),sql,new ScalarHandler<Long>(),params);
        return result;
    }

    /**数据库删
     * @param fid
     * @throws SQLException
     */
    @Override
    public void deleteFileLog(int fid) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "delete from filelog where fid=?;";
        runner.update(JdbcUtils.getConnection(),sql,fid);
    }

    /**数据库改
     * @param file
     * @throws SQLException
     */
    @Override
    public void updateFileLog(List<FileLog> files) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "update filelog set filename=?,storeName=?,md5=?,time=?,extension=?,password=?,filecode=? where fid=?;";
        if(files!=null&&files.size()>0) {
            for(FileLog file:files) {
                Object[] params = {file.getFilename(), file.getStoreName(), file.getMd5(), file.getTime(), file.getExtension(), file.getPassword(), file.getFilecode(), file.getFid()};
                runner.update(JdbcUtils.getConnection(), sql, params);
            }
        }
    }

    /**数据库查
     * @param fid
     * @return
     * @throws SQLException
     */
    @Override
    public FileLog queryFileLog(int fid) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "select * from filelog where fid=?;";
        FileLog fileLog = runner.query(JdbcUtils.getConnection(),sql, new BeanHandler<FileLog>(FileLog.class),fid);
        return fileLog;
    }

    /**根据SQL进行数据表修改
     * @param sql
     * @param params
     * @throws SQLException
     */
    @Override
    public int updateBySQL(String sql, Object[] params) throws SQLException {
        QueryRunner runner = new QueryRunner();
        return runner.update(JdbcUtils.getConnection(),sql,params);
    }

    /**根据SQL进行查询
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    @Override
    public List<FileLog> queryFileLogsBySQL(String sql, Object[] params) throws SQLException {
        QueryRunner runner = new QueryRunner();
        List<FileLog> list = runner.query(JdbcUtils.getConnection(),sql, new BeanListHandler<FileLog>(FileLog.class),params);
        return list;
    }
}

package cn.edu.sdust.cise.itransfer.dao.impl;

import cn.edu.sdust.cise.itransfer.domain.Check;
import cn.edu.sdust.cise.itransfer.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;

/**
 * Created by 宇强 on 2016/6/7 0007.
 */
public class CheckDaoImpl implements cn.edu.sdust.cise.itransfer.dao.CheckDao {

    @Override
    public Check query(String filename) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "select * from check_t where filename=?;";
        return runner.query(JdbcUtils.getConnection(),sql,new BeanHandler<Check>(Check.class),filename);
    }

    @Override
    public long add(Check check) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "insert into check_t(filecode,filename,password,flag) values(?,?,?,?);";
        Object[] params = {check.getFilecode(),check.getFilename(),check.getPassword(),check.getFlag()};
        return runner.insert(JdbcUtils.getConnection(),sql,new ScalarHandler<Long>(),params);
    }

    @Override
    public void delete(String filename) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "delete from check_t where filename=?;";
        runner.update(JdbcUtils.getConnection(), sql, filename);
    }

    @Override
    public void update(Check check) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "update check_t set filecode=?,filename=?,password=?,flag=? where id=?;";
        Object[] params = {check.getFilecode(),check.getFilename(),check.getPassword(),check.getFlag(),check.getId()};
        runner.update(JdbcUtils.getConnection(),sql,params);
    }
}

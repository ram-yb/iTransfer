package cn.edu.sdust.cise.itransfer.dao;

import cn.edu.sdust.cise.itransfer.domain.FileLog;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 宇强 on 2016/4/30 0030.
 */
public interface FileLogDao {
    long saveFileLog(FileLog file) throws SQLException;

    void deleteFileLog(int fid) throws SQLException;

    void updateFileLog(List<FileLog> file) throws SQLException;

    FileLog queryFileLog(int fid) throws SQLException;

    int updateBySQL(String sql, Object[] params) throws SQLException;

    List<FileLog> queryFileLogsBySQL(String sql, Object[] params) throws SQLException;
}

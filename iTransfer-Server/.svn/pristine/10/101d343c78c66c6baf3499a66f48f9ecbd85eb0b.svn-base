package junit.test;

import cn.edu.sdust.cise.itransfer.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import java.io.File;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by 宇强 on 2016/4/30 0030.
 */
public class DaoTest {

    @Test
    public void testNow() throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "select now(),now()-INTERVAL 1 SECOND";
        Object[] params = {};
        double result = runner.query(JdbcUtils.getConnection(),sql, new ScalarHandler<Double>(),params);
        System.out.println(result);
    }

    @Test
    public void initDataBase() throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "insert into filelog(filename,storeName,md5,extension,time,filecode,password) values(?,?,?,?,?,?,?)";
        for(int i=0;i<100000;i++) {
            Object[] params = {i+"",i+"",i+"",".ttt",new Date(System.currentTimeMillis()-100000000),i+"1","123"};
            runner.update(JdbcUtils.getConnection(), sql, params);
        }
    }

    @Test
    public void testFile(){
        File file = new File("D:\\用户\\宇强\\Desktop\\中国软件杯2016\\WebServer\\out\\artifacts\\iTransfer_war_exploded\\images\\query\\1465299591201+40c85da9-bcc7-43ca-82b1-eb79040e6b98_.gif");
        System.out.println(file.getName());
    }
}

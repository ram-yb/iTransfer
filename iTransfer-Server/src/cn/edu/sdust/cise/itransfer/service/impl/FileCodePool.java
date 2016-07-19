package cn.edu.sdust.cise.itransfer.service.impl;

import cn.edu.sdust.cise.itransfer.dao.FileLogDao;
import cn.edu.sdust.cise.itransfer.domain.FileLog;
import cn.edu.sdust.cise.itransfer.utils.ConfigUtil;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**filecode池
 * Created by 宇强 on 2016/7/2 0002.
 */
public class FileCodePool {

    private FileLogDao fileLogDao;
    //filecode池容器，key是fid，value是filecode
    private Map<Integer,Integer> map;
    private Random random;
    private int poolSize;//池大小

    public FileCodePool(FileLogDao fileLogDao) {
        this.poolSize = ConfigUtil.getInt("filecode.pool.size");
        this.fileLogDao = fileLogDao;
        this.map = new ConcurrentHashMap<Integer, Integer>(poolSize);
        this.random = new Random();
    }

    //获取单个filecode
    public Map.Entry<Integer,Integer> getFilecode() throws SQLException {
        while(map.size()<=0){
            //读取数据库，批量获取
            List<FileLog> last = fileLogDao.queryFileLogsBySQL("select fid from filelog where fid>? and time+INTERVAL " + ConfigUtil.getString("filelog.time") + " SECOND<now() limit 0,?;", new Object[]{random.nextInt(ConfigUtil.getInt("filecode.length")-100),poolSize});
            for(FileLog fileLog:last){
                //设置filecode
                int filecode = (random.nextInt(9) + 1) * ConfigUtil.getInt("filecode.length") + fileLog.getFid();
                map.put(fileLog.getFid(),filecode);
            }
        }
        Iterator<Map.Entry<Integer,Integer>> iterator = map.entrySet().iterator();
        Map.Entry<Integer,Integer> entry = iterator.next();
        iterator.remove();
        return entry;
    }

    //获取多个fid，对应相同的filecode
    public List<Map.Entry<Integer,Integer>> getFilecodes(int nums) throws SQLException {
        List<Map.Entry<Integer,Integer>> list = new ArrayList<Map.Entry<Integer, Integer>>(nums);
        //先获取第一个，得到filecode
        Map.Entry<Integer,Integer> first = getFilecode();
        int filecode = first.getValue();
        list.add(first);
        //再获取剩下的，filecode都设置统一的
        for(int i=1;i<nums;i++){
            Map.Entry<Integer,Integer> t = getFilecode();
            t.setValue(filecode);
            list.add(t);
        }
        return list;
    }
}

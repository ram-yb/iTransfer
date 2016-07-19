package cn.edu.sdust.cise.itransfer.domain;

/**
 * Created by 宇强 on 2016/6/7 0007.
 */
public class Check {

    private int id;
    private int filecode;
    private String password;
    private String filename ;
    private int flag;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Check{" +
                "id=" + id +
                ", filecode=" + filecode +
                ", password='" + password + '\'' +
                ", filename='" + filename + '\'' +
                ", flag=" + flag +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFilecode() {
        return filecode;
    }

    public void setFilecode(int filecode) {
        this.filecode = filecode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}

package cn.edu.nuc.cushion.bean;

/**
 * Created by Yangyulin on 2019/9/10.
 */
public class Record {

    /**
     * id : 1
     * site_id : 1
     * admin_id : 1
     * time : 2019-09-03 15:14:24
     */

    private int id;//自动生成得不用管
    private int site_id; //去过的站点
    private int admin_id; //user得id
    private String time; //去这个站点得时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSite_id() {
        return site_id;
    }

    public void setSite_id(int site_id) {
        this.site_id = site_id;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", site_id=" + site_id +
                ", admin_id=" + admin_id +
                ", time='" + time + '\'' +
                '}';
    }
}

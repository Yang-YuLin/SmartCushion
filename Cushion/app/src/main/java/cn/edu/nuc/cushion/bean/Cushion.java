package cn.edu.nuc.cushion.bean;

/**
 * Created by Yangyulin on 2019/9/2.
 */
public class Cushion {
    private Integer id;
    private String qrcode;
    private Integer site_id;
    private String sitename;
    private String is_sitting;
    private double temperature;
    private String time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public Integer getSite_id() {
        return site_id;
    }

    public void setSite_id(Integer site_id) {
        this.site_id = site_id;
    }

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    public String getIs_sitting() {
        return is_sitting;
    }

    public void setIs_sitting(String is_sitting) {
        this.is_sitting = is_sitting;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Cushion{" +
                "id=" + id +
                ", qrcode='" + qrcode + '\'' +
                ", site_id=" + site_id +
                ", sitename='" + sitename + '\'' +
                ", is_sitting='" + is_sitting + '\'' +
                ", temperature=" + temperature +
                ", time='" + time + '\'' +
                '}';
    }
}

package cn.edu.nuc.cushion.bean;

/**
 * Created by Yangyulin on 2019/9/3.
 */
public class Route {
    /**
     * id : 1
     * site_id : 5
     * sitename : null
     * start : 1
     * end : 5
     */

    private int id;
    private int site_id;
    private String sitename;
    private int start;
    private int end;

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

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", site_id=" + site_id +
                ", sitename=" + sitename +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}

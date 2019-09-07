package cn.edu.nuc.cushion.bean;

/**
 * Created by Yangyulin on 2019/9/3.
 */
public class Site {
    /**
     * id : 1
     * names : 一道门
     */

    private int id;
    private String names;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return names;
    }

    public void setName(String name) {
        this.names = name;
    }

    @Override
    public String toString() {
        return "Site{" +
                "id=" + id +
                ", names='" + names + '\'' +
                '}';
    }
}

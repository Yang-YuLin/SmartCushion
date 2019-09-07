package cn.edu.nuc.cushion.bean;

import android.widget.TextView;

public class Push {

    private String title;
    private String time;

    private int imageId;


    public Push(){
        super();
    }
    public Push(String title,String time,int imageId){
        this.time = time;
        this.title = title;
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }



}

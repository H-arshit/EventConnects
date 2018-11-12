package com.TheEventWelfare.EventConnects;


import android.graphics.Bitmap;

public class EventNewsGenericDataType<L> {

    private int id;
    private L data;
    private int islastcontent ;
    private String description;
    private String image_path = "N.A";
    private Bitmap image ;


    public Bitmap getImage() {
        return image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getDescription() {
        return description;
    }

    public String getImage_path() {
        return image_path;
    }

    public int getId() {
        return id;
    }

    public int islastcontent() {
        return islastcontent;
    }

    public void setIslastcontent(int islastcontent) {
        this.islastcontent = islastcontent;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setData(L data) {
        this.data = data;
    }

    public L getData() {
        return data;
    }

}

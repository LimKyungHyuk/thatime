package net.khim.thatime.values;

import android.graphics.drawable.Drawable;

public class ValuePlace {


    private long key;
    private Drawable iconDrawable ;
    private String title;
    private String date;
    private String type;
    private String gallery;
    private String address;
    private String mapx;
    private String mapy;
    private String memo;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMapx() {
        return mapx;
    }

    public void setMapx(String mapx) {
        this.mapx = mapx;
    }

    public String getMapy() {
        return mapy;
    }

    public void setMapy(String mapy) {
        this.mapy = mapy;
    }

    public void setImg(Drawable icon) {
        iconDrawable = icon ;
    }

    public Drawable getImg() {
        return iconDrawable ;
    }

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGallery() {
        return gallery;
    }

    public void setGallery(String gallery) {
        this.gallery = gallery;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String text) {
        this.memo = text;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String toString(){

        String str = "key:" + getKey()
                + ", title:" + getTitle()
                + ", date:" + getDate()
                + ", type:" + getType()
                + ", gallery:" + getGallery()
                + ", address:" + getAddress()
                + ", mapx:" + getMapx()
                + ", mapy:" + getMapy()
                + ", memo:" + getMemo();

        return str;
    }

}

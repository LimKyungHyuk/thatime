package net.khim.thatime.values;

import android.graphics.drawable.Drawable;

public class ValueCategory {


    private long key;
    private String name;

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){

        String str = "key:" + key + ", title:" + name;

        return str;
    }

}

package com.map.mobile_job_finder.Model;

import android.text.Editable;

public class Foto {
    public String photos;
    public String url;

    public Foto(String s){}
    public Foto(String photos,String url){
        this.photos = photos;
        this.url = url;
    }


    public String getNamefile() {
        return photos;
    }

    public void setNamefile(String namefile) {
        this.photos = namefile;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

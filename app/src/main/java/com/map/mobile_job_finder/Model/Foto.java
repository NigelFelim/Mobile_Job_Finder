package com.map.mobile_job_finder.Model;

public class Foto {
    public String photos;
    public String url;

    public Foto(){}
    public Foto(String photos){
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

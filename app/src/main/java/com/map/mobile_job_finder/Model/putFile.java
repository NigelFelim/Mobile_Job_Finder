package com.map.mobile_job_finder.Model;

public class putFile {

    public String namefile;
    public String url;

    public putFile(String s){}
    public putFile(String namefile, String url){
        this.namefile = namefile;
        this.url = url;
    }

    public String getNamefile() {
        return namefile;
    }

    public void setNamefile(String namefile) {
        this.namefile = namefile;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

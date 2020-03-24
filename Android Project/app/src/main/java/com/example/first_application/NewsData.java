package com.example.first_application;

import java.io.Serializable;

public class NewsData implements Serializable {
    //데이터 캡슐화하기 위해 private한 것이다.
    private String title;
    private String urlImage;
    private String content;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

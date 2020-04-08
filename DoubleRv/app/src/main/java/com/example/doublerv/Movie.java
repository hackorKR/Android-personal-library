package com.example.doublerv;

import android.graphics.Bitmap;

public class Movie {
    private String title;
    private String sentence;
    private Bitmap bitmap;


    public Movie(Bitmap bitmap, String title, String sentence){
        this.bitmap = bitmap;
        this.title = title;
        this.sentence = sentence;
    }


    public String getTitle() {
        return title;
    }


    public String getSentence() {
        return sentence;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

}

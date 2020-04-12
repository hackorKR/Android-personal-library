package com.example.doublerv.ClassData;

import android.graphics.Bitmap;

public class Book {
    private String title;
    private String sentence;
    private Bitmap bitmap;


    public Book(Bitmap bitmap, String title, String sentence){
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

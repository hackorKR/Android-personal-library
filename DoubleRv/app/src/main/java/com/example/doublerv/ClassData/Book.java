package com.example.doublerv.ClassData;

import android.graphics.Bitmap;

public class Book {
    private String title;
    private String sentence;
    private Bitmap bitmap;
    private String author;
    private int shelf_position;

    private int image;


    public int getShelf_position() {
        return shelf_position;
    }

    public void setShelf_position(int shelf_position) {
        this.shelf_position = shelf_position;
    }

    public Book(Bitmap bitmap, String title, String author, String sentence, int shelf_position){
        this.bitmap = bitmap;
        this.title = title;
        this.sentence = sentence;
        this.author = author;
        this.shelf_position = shelf_position;
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

    public String getAuthor() {
        return author;
    }

}

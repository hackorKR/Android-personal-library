package com.example.doublerv;

public class Movie {
    private String title;
    private String sentence;

    private int ResourceID;

    public Movie(int id, String title, String sentence)
    {
        this.ResourceID = id;
        this.title = title;
        this.sentence = sentence;
    }

    public String getTitle() {
        return title;
    }

    public int getResourceID() {
        return ResourceID;
    }

    public String getSentence() {
        return sentence;
    }

}

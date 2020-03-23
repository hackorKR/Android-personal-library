package com.example.first_application;

public class SampleData {
    private int num;
    private String authorName;
    private String workName;

    public SampleData(int num, String authorName, String workName){
        this.num = num;
        this.authorName = authorName;
        this.workName = workName;
    }

    public int getNum(){
        return this.num;
    }

    public String getAuthorName(){
        return this.authorName;
    }
    public String getWorkName(){
        return this.workName;
    }



}

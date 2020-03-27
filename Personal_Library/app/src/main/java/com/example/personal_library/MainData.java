package com.example.personal_library;

import java.io.Serializable;

public class MainData implements Serializable {

    private int ImageView_profile;
    private String TextView_title;
    private String TextView_content;

    public MainData(int imageView_profile, String textView_title, String textView_content) {
        ImageView_profile = imageView_profile;
        TextView_title = textView_title;
        TextView_content = textView_content;
    }

    public String toString(){
        return String.format("MainData{imageView_profile='%s', textView_title='%s' ,textView_content='%s'}",ImageView_profile,TextView_title,TextView_content);
    }


    public int getImageView_profile() {
        return ImageView_profile;
    }

    public void setImageView_profile(int imageView_profile) {
        ImageView_profile = imageView_profile;
    }

    public String getTextView_title() {
        return TextView_title;
    }

    public void setTextView_title(String textView_title) {
        TextView_title = textView_title;
    }

    public String getTextView_content() {
        return TextView_content;
    }

    public void setTextView_content(String textView_content) {
        TextView_content = textView_content;
    }
}

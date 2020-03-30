package com.example.first_project;

public class Item_Data {

    private  int item_profile;
    private  String item_title;
    private  String item_sentence;

    public Item_Data(int item_profile, String item_title, String item_sentence) {
        this.item_profile = item_profile;
        this.item_title = item_title;
        this.item_sentence = item_sentence;
    }

    public int getItem_profile() {
        return item_profile;
    }

    public void setItem_profile(int item_profile) {
        this.item_profile = item_profile;
    }

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public String getItem_sentence() {
        return item_sentence;
    }

    public void setItem_sentence(String item_sentence) {
        this.item_sentence = item_sentence;
    }
}

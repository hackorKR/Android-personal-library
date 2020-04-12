package com.example.doublerv.ClassData;

import java.io.Serializable;
import java.util.ArrayList;

public class Shelf implements Serializable {
    private  static final long serialVersionUID = 1L;

    public String shelf_title;

    public ArrayList<Book> bookList = new ArrayList<>();

    public Shelf(String shelf_title){
        this.shelf_title = shelf_title;
    }

    public String getShelf_title() {
        return shelf_title;
    }

    public void setShelf_title(String shelf_title) {
        this.shelf_title = shelf_title;
    }
}

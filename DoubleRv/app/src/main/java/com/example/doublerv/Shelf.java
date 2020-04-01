package com.example.doublerv;

import java.io.Serializable;

public class Shelf implements Serializable {
    private  static final long serialVersionUID = 1L;

    public String shelf;

    public Shelf(String shelf){
        this.shelf = shelf;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }
}

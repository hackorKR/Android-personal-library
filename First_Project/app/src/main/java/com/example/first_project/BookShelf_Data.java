package com.example.first_project;

import java.io.Serializable;

public class BookShelf_Data implements Serializable {

    private static final long serialVersionUID = 1L;

    public String bookshelf_title;

    public BookShelf_Data(String bookshelf_title) {
        this.bookshelf_title = bookshelf_title;
    }

    public String getBookshelf_title() {
        return bookshelf_title;
    }

    public void setBookshelf_title(String bookshelf_title) {
        this.bookshelf_title = bookshelf_title;
    }
}

package com.roitraining.demos.reactivefunk.domain;

import java.util.List;

public class Shelve {
    private int shelveId;
    private String name;
    private List<Book> books;

    public Shelve() {
    }

    public Shelve(int shelveId, String name, List<Book> books) {
        this.shelveId = shelveId;
        this.books = books;
        this.name = name;
    }

    public int getShelveId() {
        return shelveId;
    }

    public void setShelveId(int shelveId) {
        this.shelveId = shelveId;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

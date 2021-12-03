package com.roitraining.demos.reactivefunk.domain;

public class Book {
    private String title;
    private String author;
    private int id;
    private int ISBN;

    public Book() {
    }

    public Book(String title, String author, int id, int ISBN) {
        this.title = title;
        this.author = author;
        this.id = id;
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getISBN() {
        return ISBN;
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }
}

package com.library.model;

public class Book extends AbstractMaterial {
    private String author;

    public Book(String id, String title, String author) {
        super(id, title);
        this.author = author;
    }

    public Book(String id, String title) {
        super(id, title);
        this.author = "Unknown Author";
    }

    @Override
    public int getMaxLoanDays() {
        return 15;
    }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
}
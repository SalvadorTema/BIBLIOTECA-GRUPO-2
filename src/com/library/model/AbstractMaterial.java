package com.library.model;

public abstract class AbstractMaterial {
    private String id;
    private String title;
    private boolean isAvailable;

    public AbstractMaterial(String id, String title) {
        this.id = id;
        this.title = title;
        this.isAvailable = true;
    }

    public abstract int getMaxLoanDays();
 
    public String getId() { return id; }
    public String getTitle() { return title; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { this.isAvailable = available; }

    @Override
    public String toString() {
        return "[" + id + "] " + title + " (Available: " + isAvailable + ")";
    }
}
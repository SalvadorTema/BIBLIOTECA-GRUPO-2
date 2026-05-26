package com.library.model;

import java.util.Objects;

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
    
 // NUEVO: Permite que .contains() y .remove() encuentren el material por su ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractMaterial that = (AbstractMaterial) o;
        return Objects.equals(id, that.id);
    }

    // NUEVO: Buena práctica obligatoria al implementar equals
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

	public abstract int getLoanDurationInDays();
}
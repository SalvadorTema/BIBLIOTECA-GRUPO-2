package com.library.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String carnet;
    private String name;
    private List<AbstractMaterial> activeLoans;

    public User(String carnet, String name) {
        this.carnet = carnet;
        setName(name); 
        this.activeLoans = new ArrayList<>();
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Error: User name cannot be empty.");
        }
        this.name = name;
    }

    public String getCarnet() { return carnet; }
    public String getName() { return name; }
    public List<AbstractMaterial> getActiveLoans() { return activeLoans; }

    public void addLoan(AbstractMaterial material) {
        if (activeLoans.size() < 3) {
            activeLoans.add(material);
        }
    }
}
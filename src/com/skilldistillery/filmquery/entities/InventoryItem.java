package com.skilldistillery.filmquery.entities;

public class InventoryItem {
    private int id; 
    private String condition; 

    public InventoryItem(int id, String condition) {
        this.id = id;
        this.condition = condition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Condition: " + condition;
    }
}


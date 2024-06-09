package com.pma101.lapmarket.models;

public class CartItem {
    private Laptop laptop;
    private int quantity;

    public CartItem(Laptop laptop, int quantity) {
        this.laptop = laptop;
        this.quantity = quantity;
    }

    public Laptop getLaptop() {
        return laptop;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

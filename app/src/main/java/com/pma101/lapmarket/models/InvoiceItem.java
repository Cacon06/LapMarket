package com.pma101.lapmarket.models;

public class InvoiceItem {
    private Laptop laptop;
    private int quantity;
    private String customerName;
    private String customerPhone;
    private String customerAddress;
    private String status; // "Chưa thanh toán" hoặc "Đã thanh toán"

    public InvoiceItem(Laptop laptop, int quantity, String customerName, String customerPhone, String customerAddress) {
        this.laptop = laptop;
        this.quantity = quantity;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerAddress = customerAddress;
        this.status = "Chưa thanh toán";
    }

    public Laptop getLaptop() {
        return laptop;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

package com.pma101.lapmarket;

import com.pma101.lapmarket.models.InvoiceItem;

import java.util.ArrayList;

public class InvoiceRepository {
    private static InvoiceRepository instance;
    private ArrayList<InvoiceItem> invoiceItems;

    private InvoiceRepository() {
        invoiceItems = new ArrayList<>();
    }

    public static synchronized InvoiceRepository getInstance() {
        if (instance == null) {
            instance = new InvoiceRepository();
        }
        return instance;
    }

    public void addInvoiceItem(InvoiceItem invoiceItem) {
        invoiceItems.add(invoiceItem);
    }

    public ArrayList<InvoiceItem> getInvoiceItems() {
        return new ArrayList<>(invoiceItems);
    }
}

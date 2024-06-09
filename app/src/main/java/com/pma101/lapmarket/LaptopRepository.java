package com.pma101.lapmarket;
// LaptopRepository.java
import com.pma101.lapmarket.models.Laptop;

import java.util.ArrayList;
import java.util.List;

public class LaptopRepository {
    private static LaptopRepository instance;
    private List<Laptop> laptops;
    private List<DataChangeListener> listeners;

    private LaptopRepository() {
        laptops = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    public static synchronized LaptopRepository getInstance() {
        if (instance == null) {
            instance = new LaptopRepository();
        }
        return instance;
    }

    public List<Laptop> getLaptops() {
        return laptops;
    }

    public void addLaptop(Laptop laptop) {
        laptops.add(laptop);
        notifyListeners();
    }

    public void setLaptops(List<Laptop> laptops) {
        this.laptops.clear();
        this.laptops.addAll(laptops);
        notifyListeners();
    }

    public void registerListener(DataChangeListener listener) {
        listeners.add(listener);
    }

    public void unregisterListener(DataChangeListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (DataChangeListener listener : listeners) {
            listener.onDataChanged();
        }
    }

    public interface DataChangeListener {
        void onDataChanged();
    }
}

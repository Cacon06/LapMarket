package com.pma101.lapmarket;

import com.pma101.lapmarket.models.CartItem;
import com.pma101.lapmarket.models.Laptop;

import java.util.ArrayList;

public class CartRepository {
    private static CartRepository instance;
    private ArrayList<CartItem> cartItems;
    private ArrayList<CartChangeListener> listeners;

    private CartRepository() {
        cartItems = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    public static CartRepository getInstance() {
        if (instance == null) {
            instance = new CartRepository();
        }
        return instance;
    }


    public void addToCart(Laptop laptop) {
        for (CartItem item : cartItems) {
            if (item.getLaptop().equals(laptop)) {
                item.setQuantity(item.getQuantity() + 1);
                notifyCartChanged();
                return;
            }
        }
        cartItems.add(new CartItem(laptop, 1));
        notifyCartChanged();
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public void clearCart() {
        cartItems.clear();
        notifyCartChanged();
    }

    public void registerListener(CartChangeListener listener) {
        listeners.add(listener);
    }

    public void unregisterListener(CartChangeListener listener) {
        listeners.remove(listener);
    }

    private void notifyCartChanged() {
        for (CartChangeListener listener : listeners) {
            listener.onCartChanged();
        }
    }

    public interface CartChangeListener {
        void onCartChanged();
    }

    public void removeFromCart(CartItem cartItem) {
        cartItems.remove(cartItem);
        notifyCartChanged();
    }
}

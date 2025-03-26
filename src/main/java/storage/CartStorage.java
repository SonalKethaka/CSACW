package storage;

import model.CartItem;

import java.util.*;

public class CartStorage {
    private static final Map<Integer, List<CartItem>> customerCarts = new HashMap<>();

    public static List<CartItem> getCart(int customerId) {
        return customerCarts.getOrDefault(customerId, new ArrayList<>());
    }

    public static void addToCart(int customerId, CartItem item) {
        List<CartItem> cart = customerCarts.getOrDefault(customerId, new ArrayList<>());

        // If book already in cart, update quantity
        for (CartItem existing : cart) {
            if (existing.getBookId() == item.getBookId()) {
                existing.setQuantity(existing.getQuantity() + item.getQuantity());
                customerCarts.put(customerId, cart);
                return;
            }
        }

        cart.add(item);
        customerCarts.put(customerId, cart);
    }

    public static boolean updateItem(int customerId, int bookId, int newQty) {
        List<CartItem> cart = customerCarts.get(customerId);
        if (cart == null) return false;

        for (CartItem item : cart) {
            if (item.getBookId() == bookId) {
                item.setQuantity(newQty);
                return true;
            }
        }

        return false;
    }

    public static boolean removeItem(int customerId, int bookId) {
        List<CartItem> cart = customerCarts.get(customerId);
        if (cart == null) return false;

        boolean removed = cart.removeIf(item -> item.getBookId() == bookId);
        if (cart.isEmpty()) {
            customerCarts.remove(customerId);
        }
        return removed;
    }
}
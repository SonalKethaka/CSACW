package model;

import java.util.List;

public class Order {
    private int id;
    private int customerId;
    private List<CartItem> items;
    private double total;

    public Order() {}

    public Order(int id, int customerId, List<CartItem> items, double total) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
        this.total = total;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}
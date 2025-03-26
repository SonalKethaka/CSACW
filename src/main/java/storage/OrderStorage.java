package storage;

import model.Order;

import java.util.*;

public class OrderStorage {
    private static final Map<Integer, List<Order>> ordersByCustomer = new HashMap<>();
    private static int currentOrderId = 1;

    public static Order addOrder(int customerId, List<Order> customerOrders, Order newOrder) {
        newOrder.setId(currentOrderId++);
        ordersByCustomer.put(customerId, customerOrders);
        customerOrders.add(newOrder);
        return newOrder;
    }

    public static List<Order> getOrders(int customerId) {
        return ordersByCustomer.getOrDefault(customerId, new ArrayList<>());
    }

    public static Order getOrderById(int customerId, int orderId) {
        return getOrders(customerId).stream()
                .filter(order -> order.getId() == orderId)
                .findFirst()
                .orElse(null);
    }
}
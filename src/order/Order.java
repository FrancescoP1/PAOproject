package order;

import address.Address;
import food.MenuItem;
import restaurant.Restaurant;
import user.Driver;
import user.User;

import java.util.ArrayList;
import java.util.UUID;

public class Order implements Comparable<Order>{
    private UUID orderId;
    private User client;
    private Restaurant restaurant;
    private ArrayList<MenuItem> orderItems;
    private double totalPrice;
    private String orderStatus;
    private Driver driver;

    public Order(User client, Restaurant restaurant, ArrayList<MenuItem> orderItems, Driver driver) {
        this.orderId = UUID.randomUUID();
        this.client = client;
        this.restaurant = restaurant;
        this.orderItems = new ArrayList<MenuItem>();
        this.orderItems.addAll(orderItems);
        this.totalPrice = this.getTotalPrice();
        this.orderStatus = "DELIVERING";
        this.driver = driver;
        if(driver != null) {
            this.driver.setAssignedOrder(this);
        }
    }

    public Order(UUID orderId,User client, Restaurant restaurant, ArrayList<MenuItem> orderItems, Driver driver) {
        this.orderId = orderId;
        this.client = client;
        this.restaurant = restaurant;
        this.orderItems = new ArrayList<MenuItem>();
        this.orderItems.addAll(orderItems);
        this.totalPrice = this.getTotalPrice();
        this.orderStatus = "DELIVERING";
        this.driver = driver;
        if(driver != null) {
            this.driver.setAssignedOrder(this);
        }
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getClientId() {
        return client.getUserId();
    }

    public String getClientName() {
        return client.getName();
    }

    public String getClientEmail() {
        return this.client.getEmailAddress();
    }

    public String getClientPhoneNumber() {
        return this.client.getPhoneNumber();
    }

    public Address getClientAddress() {
        return this.client.getAddress();
    }

    public double getTotalPrice() {
        double total = 0;
        for(MenuItem m1 : orderItems) {
            total += m1.getItemPrice();
        }
        return total;
    }

    public String getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus(String status) {
        this.orderStatus = status;
    }

    public UUID getDriverId() {
        if(this.driver != null)
            return this.driver.getUserId();
        return null;
    }

    /*
    public User getClient() {
        return client;
    }
    */
    /*
    public void setClient(User client) {
        this.client = client;
    }
    */
    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public ArrayList<MenuItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ArrayList<MenuItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void showOrderInfo() {
        System.out.println("Pick-up restaurant: " + this.restaurant.getRestaurantName() + this.restaurant.getAddress().toString());
        System.out.println("Delivery " + this.client.getAddress().toString() + ", client: " + this.getClientName() + ", phone number: " + this.getClientPhoneNumber());
        System.out.println("Order Items: ");
        for(int i = 0; i < orderItems.size(); ++i) {
            System.out.println(i + ". " + orderItems.get(i).getBasicDescription());
        }
        System.out.println("Total price to pay: " + this.totalPrice);
        System.out.println("Order status: " + this.getOrderStatus());
    }
    public String writeToCsv() {
        //order id, client id, restaurant id, driver id, item1, item2,..., itemN
        StringBuilder str = new StringBuilder(this.getOrderId().toString()).append(",");
        str.append(this.getClientId()).append(",");
        str.append(this.restaurant.getRestaurantId()).append(",");
        str.append(this.getDriverId()).append(",");
        for(int i = 0; i < this.orderItems.size(); ++i) {
            str.append((this.orderItems.get(i)).getItemId());
            if(i != this.orderItems.size() - 1) {
                str.append(",");
            }
        }
        str.append("\n");
        return str.toString();
    }
    @Override
    public int compareTo(Order o) {
        if(this.getTotalPrice() > o.getTotalPrice()) {
            return 1;
        } else if(this.getTotalPrice() == o.getTotalPrice()) {
            return 0;
        }
            return -1;
    }
}

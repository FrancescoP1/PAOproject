package user;

import address.Address;
import order.Order;

import java.util.UUID;

public class Driver extends User {
    Order assignedOrder;
    boolean isAvailable;

    public Driver(String name, Address address, String phoneNumber, String emailAdress, String password) {
        super(name, address, phoneNumber, emailAdress, password);
        this.assignedOrder = null;
        this.isAvailable = true;
    }

    public Driver(UUID userId, String name, Address address, String phoneNumber, String emailAdress, String password) {
        super(userId, name, address, phoneNumber, emailAdress, password);
        this.assignedOrder = null;
        this.isAvailable = true;
    }

    public Order getAssignedOrder() {
        return this.assignedOrder;
    }

    public void setAssignedOrder(Order assignedOrder) {
        this.assignedOrder = assignedOrder;
    }

    public boolean isDriverAvailable() {
        return this.isAvailable;
    }

    public void setDriverAvailable(boolean available) {
        isAvailable = available;
    }

    public void finishOrder() {
        this.assignedOrder.setOrderStatus("DELIVERED");
        this.assignedOrder = null;
        this.isAvailable = true;
    }

    @Override
    public String getType() {
        return "driver";
    }
}

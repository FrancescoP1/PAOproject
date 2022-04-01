package restaurant;

import address.Address;
import food.*;

import java.util.ArrayList;

public class Restaurant {
    String restaurantName;
    Address address;
    String phoneNumber;
    ArrayList<MenuItem> menu;

    public Restaurant(String restaurantName, Address address, String phoneNumber, ArrayList<MenuItem> menu) {
        this.restaurantName = restaurantName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.menu = new ArrayList<MenuItem>();
        this.menu.addAll(menu);
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ArrayList<MenuItem> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<MenuItem> menu) {
        this.menu = menu;
    }

    public void addToMenu(MenuItem m1) {
        this.menu.add(m1);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurantName='" + restaurantName + '\'' +
                ", address=" + address +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", menu=" + menu +
                '}';
    }
}

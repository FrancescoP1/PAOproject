package restaurant;

import address.Address;
import food.*;

import java.util.ArrayList;
import java.util.UUID;

public class Restaurant {
    String restaurantName;
    UUID restaurantId;
    Address address;
    String phoneNumber;
    ArrayList<MenuItem> menu;

    public Restaurant(String restaurantName, Address address, String phoneNumber, ArrayList<MenuItem> menu) {
        this.restaurantId = UUID.randomUUID();
        this.restaurantName = restaurantName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.menu = new ArrayList<MenuItem>();
        this.menu.addAll(menu);
    }

    public Restaurant(UUID restaurantId, String restaurantName, Address address, String phoneNumber, ArrayList<MenuItem> menu) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.menu = new ArrayList<MenuItem>();
        this.menu.addAll(menu);
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
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
    public String writeToCsv() {
        //d245eb55-4add-420f-be7e-54bdeef1f7a7,Kapo,Constanta,Str. Cazinoului,7,079
        StringBuilder str = new StringBuilder(this.getRestaurantId().toString()).append(",");
        str.append(this.getRestaurantName()).append(",");
        str.append(this.getAddress().writeToCsv()).append(",");
        str.append(this.getPhoneNumber()).append("\n");
        return str.toString();
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

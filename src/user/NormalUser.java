package user;

import address.Address;

import java.util.UUID;

public class NormalUser extends User{
    //private ArrayList<Order> orderList;

    public NormalUser(String name, Address address, String phoneNumber, String emailAdress, String password) {
        super(name, address, phoneNumber, emailAdress, password);
      //  this.orderList = orderList;
    }

    public NormalUser(UUID userId, String name, Address address, String phoneNumber, String emailAdress, String password) {
        super(userId, name, address, phoneNumber, emailAdress, password);
        //  this.orderList = orderList;
    }

    @Override
    public String getType() {
        return "normal-user";
    }
}

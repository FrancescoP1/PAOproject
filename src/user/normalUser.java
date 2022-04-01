package user;

import address.Address;
import order.Order;

import java.util.ArrayList;

public class normalUser extends User{
    //private ArrayList<Order> orderList;

    public normalUser(String name, Address address, String phoneNumber, String emailAdress, String password) {
        super(name, address, phoneNumber, emailAdress, password);
      //  this.orderList = orderList;
    }

}

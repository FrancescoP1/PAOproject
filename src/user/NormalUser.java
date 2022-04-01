package user;

import address.Address;

public class NormalUser extends User{
    //private ArrayList<Order> orderList;

    public NormalUser(String name, Address address, String phoneNumber, String emailAdress, String password) {
        super(name, address, phoneNumber, emailAdress, password);
      //  this.orderList = orderList;
    }

}

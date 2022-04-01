package user;

import address.Address;

public class Admin extends User {
    public Admin(String name, Address address, String phoneNumber, String emailAdress, String password) {
        super(name, address, phoneNumber, emailAdress, password);
    }
}

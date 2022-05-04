package user;

import address.Address;

import java.util.UUID;

public class Admin extends User {
    public Admin(String name, Address address, String phoneNumber, String emailAdress, String password) {
        super(name, address, phoneNumber, emailAdress, password);
    }
    public Admin(UUID userId, String name, Address address, String phoneNumber, String emailAdress, String password) {
        super(userId, name, address, phoneNumber, emailAdress, password);
    }

    @Override
    public String getType() {
        return "admin";
    }
}

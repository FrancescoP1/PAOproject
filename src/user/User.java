package user;

import address.Address;

import java.util.Objects;

public abstract class User {
    private static int numberOfUsers = 0;
    private int userId;
    private String name;
    private Address address;
    private String phoneNumber;
    private String emailAdress;
    private String password;

    public User(String name, Address address, String phoneNumber, String emailAdress, String password) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.userId = User.numberOfUsers++;
        this.emailAdress = emailAdress;
        this.password = password;
    }

    public User() {
        this.name = "";
        this.address = new Address();
        this.phoneNumber = "";
    }

    public static int getNumberOfUsers() {
        return numberOfUsers;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getEmailAddress() {
        return this.emailAdress;
    }

    public void setEmailAddress(String emailAdress) {
        this.emailAdress = emailAdress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAdress() {
        return emailAdress;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", emailAdress='" + emailAdress + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getUserId() == user.getUserId() && getEmailAdress().equals(user.getEmailAdress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getEmailAdress());
    }
}

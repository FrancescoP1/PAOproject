package user;

import address.Address;

import java.util.Objects;
import java.util.UUID;

public abstract class User {
    private static int numberOfUsers = 0;
    private UUID userId;
    private String name;
    private Address address;
    private String phoneNumber;
    private String emailAddress;
    private String password;

    public User(String name, Address address, String phoneNumber, String emailAddress, String password) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.userId = UUID.randomUUID();
        this.emailAddress = emailAddress;
        this.password = password;
        User.numberOfUsers++;
    }

    public User(UUID userId, String name, Address address, String phoneNumber, String emailAddress, String password) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
        this.emailAddress = emailAddress;
        this.password = password;
        User.numberOfUsers++;
    }


    public User() {
        this.name = "";
        this.address = new Address();
        this.phoneNumber = "";
    }

    public static int getNumberOfUsers() {
        return numberOfUsers;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
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
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAddress = emailAdress;
    }
    public String WriteToCsv() {
        //driver,68108442-990d-43c5-b7d2-8591e9a9edb0,Fane,Constanta,Str. Cazinoului,7,075,fane@yahoo.com,123456
        StringBuilder str = new StringBuilder(this.getType()).append(",");
        str.append(this.getUserId().toString()).append(",");
        str.append(this.getName()).append(",");
        str.append(this.address.writeToCsv()).append(",");
        str.append(this.getPhoneNumber()).append(",");
        str.append(this.getEmailAddress()).append(",");
        str.append(this.getPassword()).append("\n");
        return str.toString();
    }

    public abstract String getType();
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", emailAdress='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getUserId() == user.getUserId() && getEmailAddress().equals(user.getEmailAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getEmailAddress());
    }
}

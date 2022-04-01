package address;

import java.util.Objects;
import java.util.Scanner;

public class Address {
    private String district;
    private String street;
    private int number;

    public Address() {
        this.district = "";
        this.street = "";
        this.number = -1;
    }

    public Address(String district, String street, int number) {
        this.district = district;
        this.street = street;
        this.number = number;
    }

    public static Address addNewAddress() {
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Please choose your district: ");
        String district = sc1.nextLine();
        System.out.println("Please choose your street: ");
        String street = sc1.nextLine();
        System.out.println("Please choose your building number: ");
        int number = sc1.nextInt();
        return new Address(district, street, number);
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "address: " +
                "district='" + district + '\'' +
                ", street='" + street + '\'' +
                ", number=" + number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return getNumber() == address.getNumber() && Objects.equals(getDistrict(), address.getDistrict()) && Objects.equals(getStreet(), address.getStreet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDistrict(), getStreet(), getNumber());
    }
}

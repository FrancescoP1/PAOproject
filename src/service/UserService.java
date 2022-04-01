package service;

import address.Address;
//import restaurant.Restaurant;
import user.Driver;
import user.User;
import user.normalUser;

//import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.ArrayList;

public class UserService {
    //accounts
    private static ArrayList<User>registeredUsers = new ArrayList<User>();
    //
    //private User loggedUser = null;

    public UserService(){

    }

    public static User extractUser(String email, String password) {
        for(User user : registeredUsers) {
            if(user.getEmailAddress().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public static User login() {
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Email: ");
        String email = sc1.nextLine();
        System.out.println("Password: ");
        String password = sc1.nextLine();
        return extractUser(email, password);
    }

    public static void registerUser(String type) {
        System.out.println("Register");
        Scanner sc1 = new Scanner(System.in);
        System.out.println("E-mail: ");
        String email = sc1.nextLine();
        email = email.trim();
        System.out.println("Adresa: ");
        Address address = Address.addNewAddress();
        System.out.println("Password: ");
        String password = sc1.nextLine();
        password = password.trim();
        System.out.println("Your complete name: ");
        String name = sc1.nextLine();
        System.out.println("Your phone number: ");
        String phoneNumber = sc1.nextLine();
        if(type.equals("normalUser")) {
            registeredUsers.add(new normalUser(name, address, phoneNumber, email, password));
        } else if (type.equals("driver")) {
            registeredUsers.add(new Driver(name, address, phoneNumber, email, password));
        }
    }

    public static User logOff() {
        return null;
    }
    public static void deleteAccount(User userToDelete){
        registeredUsers.remove(userToDelete);
    }

    public static ArrayList<User> getRegisteredUsers() {
        return registeredUsers;
    }

    public static void addUser(User user) {
        registeredUsers.add(user);
    }
}

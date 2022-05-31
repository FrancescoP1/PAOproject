package service;

import address.Address;
//import restaurant.Restaurant;
import dao.repository.UserRepository;
import user.Admin;
import user.Driver;
import user.User;
import user.NormalUser;

//import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.ArrayList;
import dao.repository.UserRepository;
public class UserService {
    //accounts
    private static ArrayList<User>registeredUsers = new ArrayList<>();
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

    public static User registerUser(String type) {
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
        switch (type) {
            case "normalUser":
                registeredUsers.add(new NormalUser(name, address, phoneNumber, email, password));
                break;
            case "driver":
                registeredUsers.add(new Driver(name, address, phoneNumber, email, password));
                break;
            case "admin":
                registeredUsers.add(new Admin(name, address, phoneNumber, email, password));
                break;
            default:
                return null;
        }
        //adaugare in baza de date
        UserRepository.getUserRepository().insertUser(registeredUsers.get(registeredUsers.size() - 1));
        return registeredUsers.get(registeredUsers.size() - 1);
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
    public static void removeUser(User user) {
        registeredUsers.remove(user);
    }
    public static User removeUser(String email) {
        User userToDelete = null;
        for(int i = 0; i < registeredUsers.size(); ++i) {
            //System.out.println(registeredUsers.get(i).getEmailAddress());
            if(registeredUsers.get(i).getEmailAddress().equals(email)){
                //System.out.println("yes");
                userToDelete = registeredUsers.get(i);
                break;
            }
        }
        registeredUsers.remove(userToDelete);
        return userToDelete;
    }
}

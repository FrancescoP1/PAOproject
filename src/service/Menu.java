package service;

import address.Address;
import dao.repository.RestaurantRepository;
import dao.repository.UserRepository;
import exceptions.UserAccountException;
import food.MenuItem;
import order.Order;
import restaurant.Restaurant;
import user.Admin;
import user.Driver;
import user.User;
import user.NormalUser;

import java.util.*;

public class Menu {
    private static ArrayList<Restaurant> restaurants;
    private static ArrayList<Order> allOrders;
    private static Queue<Driver> availableDrivers;
    private static User loggedUser;
    //private static UserService userLink;
    private static DataWriter csvWriter;
    private static AuditService logger;

    public Menu() {
        restaurants = new ArrayList<Restaurant>();
        allOrders = new ArrayList<Order>();
        loggedUser = null;
        availableDrivers = new LinkedList<Driver>();
        csvWriter = DataWriter.getInstance();
        logger = AuditService.getAudit();
        logger.log("System,Menu initialized,");
        //userLink = new UserService();
    }

    //function that pauses the program for secondsToSleep seconds
    //intended to use with clearscreen, which is currently not implemented
    public static void redirectPause(int secondsToSleep) {
        try {
            Thread.sleep(secondsToSleep * 1000);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    public static void addRestaurant(Restaurant restaurant) {
        restaurants.add(restaurant);
        logger.log("System,Added restaurant id: " + restaurant.getRestaurantId() + " to restaurant list,");
    }

    //adds a user to the registered users ArrayList -> to remove
    public static void addUser(User user) {
        UserService.addUser(user);
        //csvWriter.writeToDb(user, User.class);
    }

    //asigns the drivers in the driverQueue -> called at the start of the program
    public static void initializeDriverQueue() {
        ArrayList<User> allUsers = UserService.getRegisteredUsers();
        for(User user : allUsers) {
            if(user instanceof Driver) {
                Driver aux = (Driver) user;
                if(aux.getAssignedOrder() == null) {
                    Menu.availableDrivers.add(aux);
                }
            }
        }
    }

    //returns the driver in the front of the queue
    public static Driver getAvailableDriver() {
        if(availableDrivers.size() > 0) {
            return availableDrivers.remove();
        }
        return null;
    }

    //shows a different menu for each type of user
    public static void showMenu() {
        String message = "System,Displayed %s menu,";
        if(loggedUser == null) {
            logger.log(String.format(message, "guest"));
            guest();
        } else if(loggedUser instanceof Driver) {
            logger.log(String.format(message, "driver"));
            driver();
        } else if(loggedUser instanceof Admin) {
            logger.log(String.format(message, "admin"));
            admin();
        } else if(loggedUser instanceof NormalUser) {
            logger.log(String.format(message, "normal-user"));
            basicUser();
        }
    }

    //guest menu (a user that is not logged in)
    public static void guest() {
        System.out.println("Please choose one of the following options:");
        System.out.println("1. Register (press 1, then enter)");
        System.out.println("2. Login (press 2, then enter)");
        Scanner sc1 = new Scanner(System.in);
        int option = sc1.nextInt();
        if(option == 1) {
            //logger.log("Guest,Chose option 1: register,");
            User registeredUser = UserService.registerUser("normalUser");
            csvWriter.writeToDb(registeredUser, User.class);
            if(registeredUser != null) {
                logger.log("Guest,Registered new user id: " + registeredUser.getUserId() + ",");
                UserRepository.getUserRepository().insertUser(registeredUser);
            }
        } else if (option == 2) {
            /*
            loggedUser =  UserService.login();
            if(loggedUser != null) {
                logger.log("User id: " + loggedUser.getUserId() + ",Logged in,");
            }

             */
            try {
                loggedUser = UserService.login();
                if(loggedUser == null){
                    throw new UserAccountException("Wrong email/password");
                }
            } catch(UserAccountException ex) {
                System.err.println(ex.toString());
            }

            if(loggedUser != null) {
                logger.log("User id: " + loggedUser.getUserId() + ",Logged in,");
            }

        }
    }

    //the menu for a normal user
    public static void basicUser() {
        System.out.println("Please choose one of the following options:");
        System.out.println("1. Make an order (press1, then enter)");
        System.out.println("2. View my order history (press 2, then enter)");
        System.out.println("3. Logout (press 3, then enter)");
        System.out.println("4. Update my account (press 4, then enter)");
        System.out.println("5. Go  back (press 5, then enter)");
        Scanner sc1 = new Scanner(System.in);
        int option = sc1.nextInt();
        if(option == 1) {
            Menu.selectRestaurants();
        } else if(option == 2) {
            showUserOrders(loggedUser);
            logger.log("User id: " + loggedUser.getUserId() + ",Viewed his/her orders,");
        } else if (option == 3) {
            logger.log("User id: " + loggedUser.getUserId() + ",Logged off,");
            loggedUser = UserService.logOff();
            showMenu();
        } else if (option == 4) {
            updateAccount();
        } else if (option == 5) {
            showMenu();
        }
    }

    //shows all the restaurants in the app
    public static void selectRestaurants() {
        logger.log("System,Displayed list of restaurants,");
        System.out.println("Please choose the restaurant you want to order from: ");
        for(int i = 0; i < Menu.restaurants.size(); ++i) {
            System.out.println("ID: " + i + " " + Menu.restaurants.get(i).getRestaurantName() + " (press " + i + ", then enter");
        }
        Scanner sc1 = new Scanner(System.in);
        int option = sc1.nextInt();
        if(option >= 0 && option < Menu.restaurants.size()) {
            showRestaurantMenu(option);
        } else{
            selectRestaurants();
        }
    }

    //shows the menu of the restaurant id given as parameter -> could be moved inside the Restaurant Class???
    public static void showRestaurantMenu(int id) {
        if(id >= 0 && id < Menu.restaurants.size()) {
            Restaurant restaurant = restaurants.get(id);
            logger.log("User id: " + loggedUser.getUserId() + ", viewed restaurant id: " + restaurant.getRestaurantId() + ",");
            ArrayList<MenuItem> foods = restaurant.getMenu();
            int i;
            int option = -1;
            for(i = 0; i < foods.size(); ++i) {
                System.out.println(i + ". " + foods.get(i).getBasicDescription());
            }
            System.out.println(foods.size() + ". Comanda");
            ArrayList<MenuItem> chosenItems = new ArrayList<>();
            System.out.println("Choose the IDs of the foods that you want to order. You can select multiple ids, separate them with a space");
            while(option != foods.size()) {
                Scanner sc1 = new Scanner(System.in);
                option = sc1.nextInt();
                if(option >= 0 && option < foods.size()) {
                    chosenItems.add(foods.get(option));
                    logger.log("User id: " + loggedUser.getUserId() + ",Picked menu item id: " + foods.get(option).getItemId() + ",");
                }
            }
            allOrders.add(new Order(loggedUser, restaurants.get(id), chosenItems, Menu.getAvailableDriver()));

            if(allOrders.size() > 0) {
                csvWriter.writeToDb(allOrders.get(allOrders.size() - 1), Order.class);
                logger.log("User id: " + loggedUser.getUserId() + ",Launched order id: " + allOrders.get(allOrders.size() - 1).getOrderId() + ",");
            }
            basicUser();
        }

    }

    //shows the menu for a driver (a courier).
    public static void driver() {
        System.out.println("Please choose one of the following options:");
        System.out.println("1. View order to deliver information (press1, then enter)");
        System.out.println("2. Mark current order as delivered (press 2, then enter)");
        System.out.println("3. View my delivery history (press 3, then enter)");
        System.out.println("4. Logout (press 4, then enter)");
        System.out.println("5. Update account (press 5, then enter)");
        Scanner sc1 = new Scanner(System.in);
        int option = sc1.nextInt();
        String message = "Driver id: " + loggedUser.getUserId() + ",";
        if(option == 4) {
            logger.log(message + "logged off,");
            loggedUser = UserService.logOff();
        } else if(option == 1) {
            if(loggedUser instanceof Driver) {
                Driver aux = (Driver) loggedUser;
                Order currentOrder = aux.getAssignedOrder();
                if(currentOrder != null) {
                    logger.log(message + "viewed his assigned order,");
                    currentOrder.showOrderInfo();
                }
                else {
                    System.out.println("You have no orders assigned!");
                    redirectPause(2);
                    driver();
                }
            }
        } else if(option == 2) {
            Driver aux = (Driver) loggedUser;
            System.out.println("Congratulations for delivering your order!");
            Order currentOrder = aux.getAssignedOrder();
            if(currentOrder != null) {
                logger.log(message + "Delivered order id: " + currentOrder.getOrderId() + ",");
                aux.finishOrder();
            } else {
                System.out.println("You currently have no order assigned");
            }
        } else if(option == 3) {
            logger.log(message + "Viewed previously delivered orders,");
            showUserOrders(loggedUser);
        } else if (option == 5) {
            updateAccount();
        }
        showMenu();
    }

    //used to show a user's orders
    public static void deleteAccount() {
        System.out.println("You chose to delete your account! Please confirm you choice!");
        System.out.println("1. Confirm choice -> permanently delete my account!");
        System.out.println("2. Cancel -> go back!");
        Scanner sc1 = new Scanner(System.in);
        int option = sc1.nextInt();
        if(option == 1) {
            UserRepository.getUserRepository().deleteUser(loggedUser.getUserId().toString());
            UserService.removeUser(loggedUser);
            loggedUser = UserService.logOff();
            System.out.println("You have successfully deleted your account!");
            redirectPause(2);
            showMenu();
        } else {
            showMenu();
        }
    }

    public static void updateAccount(){
        System.out.println("1. Change email (press 1, then enter)");
        System.out.println("2. Change name (press 2, then enter)");
        System.out.println("3. Change password (press 3, then enter)");
        System.out.println("4. Delete account (press 4, then enter");
        System.out.println("5. Go back (press 5, then enter)");
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();
        if(option == 1){
            changeEmail();
        } else if (option == 2) {
            changeName();
        } else if (option == 3) {
            changePassword();
        } else if (option == 4) {
            deleteAccount();
        } else {
            System.out.println("Invalid option...redirecting you to the main menu!");
        }
        redirectPause(2);
        showMenu();
    }
    public static void changePassword() {
        System.out.println("You chose to change your password! Please enter your new password:");
        Scanner sc1 = new Scanner(System.in);
        String pass1 = sc1.nextLine();
        pass1 = pass1.trim();
        System.out.println("Re-type the password you previously entered: ");
        String pass2 = sc1.nextLine();
        pass2 = pass2.trim();
        if(pass1.equals(pass2)){
            UserRepository.getUserRepository().updateUserPassword(loggedUser.getUserId().toString(), pass1);
            System.out.println("You changed your password successfully! You will be redirected to the main menu!");
            loggedUser.setPassword(pass1);
            System.out.println("Successfully modified you password!");
            redirectPause(2);
            showMenu();
        } else {
            System.out.println("Passwords do not match!");
            redirectPause(2);
            showMenu();
        }
    }
    public static void changeEmail(){
        System.out.println("Your current email address is: " + loggedUser.getEmailAddress());
        System.out.println("Please enter your new email address, or press 1 to go back: ");
        Scanner sc = new Scanner(System.in);
        String newEmail = sc.nextLine();
        if(newEmail.chars().allMatch(Character::isDigit)){
            System.out.println("You chose to go back!");
        } else {
            newEmail = newEmail.strip();
            UserRepository.getUserRepository().updateUser(loggedUser.getUserId().toString(), "email", newEmail);
            loggedUser.setEmailAddress(newEmail);
            System.out.println("Successfully modified your email!");
        }
        redirectPause(2);
        showMenu();

    }

    public static void changeName() {
        System.out.println("Your current name is: " + loggedUser.getName());
        System.out.println("Please enter your new name, or press 1 to go back: ");
        Scanner sc = new Scanner(System.in);
        String newName = sc.nextLine();
        newName = newName.strip();
        if(newName.chars().allMatch(Character::isDigit)){
            System.out.println("You chose to go back!");
        } else {
            UserRepository.getUserRepository().updateUser(loggedUser.getUserId().toString(), "name", newName);
            loggedUser.setName(newName);
            System.out.println("Successfully modified your name!");
        }
        redirectPause(2);
        showMenu();
    }

    public static void showUserOrders(User user) {
        //sortare crescatoare dupa pretul total al unei comenzi
        Collections.sort(allOrders);
        //lambda expressions
        if(user instanceof NormalUser) {
            allOrders.forEach((order) -> {
                if(order.getClientId().compareTo(user.getUserId()) == 0) {
                    order.showOrderInfo();
                }
            });
            /*
            for(Order order : allOrders) {
                if(order.getClientId().compareTo(user.getUserId()) == 0) {
                    order.showOrderInfo();
                }
            }

             */
        } else if(user instanceof Driver) {
            allOrders.forEach((order) -> {
                if(order.getDriverId().compareTo(user.getUserId()) == 0) {
                    order.showOrderInfo();
                }
            });

            /*
            for(Order order : allOrders) {
                if(order.getDriverId().compareTo(user.getUserId()) == 0) {
                    order.showOrderInfo();
                }
            }

             */
        }

    }

    //used for admin user type -> could be integrated inside showUserOrders (another else if).
    public static void showAllOrders() {
        Collections.sort(allOrders);
        for(Order order : allOrders) {
            order.showOrderInfo();
            System.out.println();
        }
    }

    public static void showAllUsers() {

        ArrayList<User> allUsers = UserService.getRegisteredUsers();
        for(User user : allUsers) {
            System.out.println(user.toString());
        }
    }


    public static void admin() {
        System.out.println("Please choose one of the following options:");
        System.out.println("1. View all orders (press1, then enter)");
        System.out.println("2. View all user's information (press 2, then enter)");
        System.out.println("3. Create new Driver (press 3, then enter)");
        System.out.println("4. Create new Admin (press 4, then enter");
        System.out.println("5. Delete user (press 5, then enter)");
        System.out.println("6. Update my account (press 6, then enter)");
        System.out.println("7. Manage restaurants (press 7, then enter)");
        System.out.println("8. Logout (press 8, then enter)");
        //-> more functionalities to be implemented, such as editing restaurant properties, etc.
        Scanner sc1 = new Scanner(System.in);
        int option = sc1.nextInt();
        //sc1.next();
        String message = "Admin id: " + loggedUser.getUserId() + ",";
        if(option == 1) {
            logger.log(message + ",Viewed all previous orders,");
            showAllOrders();
        } else if(option == 2) {
            logger.log(message + ",Viewed all registered users,");
            showAllUsers();
        } else if(option == 3) {
            logger.log(message + ",Registered new driver,");
            User registeredUser = UserService.registerUser("driver");
            csvWriter.writeToDb(registeredUser, User.class);
        } else if(option == 4) {
            logger.log(message + ",Registered new user,");
            User registeredUser = UserService.registerUser("admin");
            csvWriter.writeToDb(registeredUser, User.class);
        } else if(option == 5){
            System.out.println("Write the email of the user that you want to delete: ");
            Scanner sc2 = new Scanner(System.in);
            String email =  sc2.nextLine();
            email = email.trim();
            //System.out.println(email);
            User deleteUser = UserService.removeUser(email);
            UserRepository.getUserRepository().deleteUserByEmail(email);
            logger.log(message + ",Deleted a user,");
        } else if(option == 6) {
            updateAccount();
        } else if (option == 7){
            manageRestaurants();
        }
        else if(option == 8) {
            logger.log(message + ",Logged off,");
            loggedUser = UserService.logOff();
        }
    }

    public static void manageRestaurants() {
        logger.log("System,Displayed list of restaurants,");
        System.out.println("Please choose the restaurant you want to order from: ");
        for (int i = 0; i < Menu.restaurants.size(); ++i) {
            System.out.println("ID: " + i + " " + Menu.restaurants.get(i).getRestaurantName() + " (press " + i + ", then enter");
        }
        System.out.println("Add new restaurant (press " + restaurants.size() + " then enter)");
        System.out.println("Go back (press " + (restaurants.size() + 1) +  " then enter)");
        Scanner sc1 = new Scanner(System.in);
        int option = sc1.nextInt();
        if (option >= 0 && option < Menu.restaurants.size()) {
            editRestaurant(Menu.restaurants.get(option));
        } else if(option == Menu.restaurants.size()){
            addNewRestaurant();
        } else {
            admin();
        }

    }

    public static void editRestaurant(Restaurant rest){
        System.out.println("You are now editing restaurant" + rest.getRestaurantName() + "id: " + rest.getRestaurantId());
        System.out.println("1. Change restaurant name (press 1, then enter)");
        System.out.println("2. Change restaurant phone (press 2, then enter)");
        System.out.println("3. Delete restaurant (press 3, then enter)");
        System.out.println("4. Go back (press 4, then enter)");
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();
        sc.nextLine();
        if(option == 1) {
            System.out.println("Please enter the new name for the restaurant");
            String name = sc.nextLine();
            name = name.strip();
            RestaurantRepository.getRestaurantRepository().updateRestaurant(rest.getRestaurantId().toString(), "name", name);
            rest.setRestaurantName(name);

        } else if(option == 2) {
            System.out.println("Please enter the new phone number: ");
            String phone = sc.nextLine();
            phone = phone.strip();
            if(phone.length() > 2){
                RestaurantRepository.getRestaurantRepository().updateRestaurant(rest.getRestaurantId().toString(), "phone", phone);
                rest.setPhoneNumber(phone);
                System.out.println("Sucessfully updated restaurant phone number");
            }
        } else if(option == 3) {
            System.out.println("You chose to delete restaurant id " + rest.getRestaurantId());
            System.out.println("1. Confirm my choice (press 1, then enter)");
            System.out.println("2. Cancel and go back (press 2, then enter)");
            option = sc.nextInt();
            if(option == 1){
                System.out.println("Deleted restaurant id " + rest.getRestaurantId());
                RestaurantRepository.getRestaurantRepository().deleteRestaurant(rest.getRestaurantId().toString());
                restaurants.remove(rest);
            } else {
                System.out.println("We are redirecting you to the previous menu");
            }
            redirectPause(2);
            manageRestaurants();
        }
    }

    public static void addNewRestaurant() {
        System.out.println("You chose to add a new restaurant");
        System.out.println("Please enter the new restaurant's name");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        name = name.strip();
        Address address = Address.addNewAddress();
        System.out.println("Please enter the phone number: ");
        String phone = sc.nextLine();
        phone = phone.strip();
        Restaurant rest = new Restaurant(name, address, phone, new ArrayList<>());
        restaurants.add(rest);
        RestaurantRepository.getRestaurantRepository().insertRestaurant(rest);
        System.out.println("Succesfully added new restaurant!");
        redirectPause(2);
    }
}

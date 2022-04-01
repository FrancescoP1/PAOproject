package service;

import food.MenuItem;
import order.Order;
import restaurant.Restaurant;
import user.Admin;
import user.Driver;
import user.User;
import user.normalUser;

import java.lang.reflect.Array;
import java.util.*;

public class Menu {
    private static ArrayList<Restaurant> restaurants;
    private static ArrayList<Order> allOrders;
    private static Queue<Driver> availableDrivers;
    private static User loggedUser;
    //private static UserService userLink;

    public Menu() {
        restaurants = new ArrayList<Restaurant>();
        allOrders = new ArrayList<Order>();
        loggedUser = null;
        availableDrivers = new LinkedList<Driver>();
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
    }

    //adds a user to the registered users ArrayList -> to remove
    public static void addUser(User user) {
        UserService.addUser(user);
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
        if(loggedUser == null) {
            guest();
        } else if(loggedUser instanceof Driver) {
            driver();
        } else if(loggedUser instanceof Admin) {
            admin();
        } else if(loggedUser instanceof normalUser) {
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
            UserService.registerUser("normalUser");
        } else if (option == 2) {
            loggedUser =  UserService.login();
        }
    }

    //the menu for a normal user
    public static void basicUser() {
        System.out.println("Please choose one of the following options:");
        System.out.println("1. Make an order (press1, then enter)");
        System.out.println("2. View my order history (press 2, then enter)");
        System.out.println("3. Logout (press 3, then enter)");
        Scanner sc1 = new Scanner(System.in);
        int option = sc1.nextInt();
        if(option == 1) {
            Menu.selectRestaurants();
        } else if(option == 2) {
            showUserOrders(loggedUser);
        } else if (option == 3) {
            loggedUser = UserService.logOff();
            showMenu();
        }
    }

    //shows all the restaurants in the app
    public static void selectRestaurants() {
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
            ArrayList<MenuItem> foods = restaurant.getMenu();
            int i;
            int option = -1;
            for(i = 0; i < foods.size(); ++i) {
                System.out.println(i + ". " + foods.get(i).getBasicDescription());
            }
            System.out.println(foods.size() + ". Comanda");
            ArrayList<MenuItem> chosenItems = new ArrayList<MenuItem>();
            System.out.println("Choose the IDs of the foods that you want to order. You can select multiple ids, separate them with a space");
            while(option != foods.size()) {
                Scanner sc1 = new Scanner(System.in);
                option = sc1.nextInt();
                if(option >= 0 && option < foods.size()) {
                    chosenItems.add(foods.get(option));
                }
            }
            allOrders.add(new Order(loggedUser, restaurants.get(id), chosenItems, Menu.getAvailableDriver()));
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
        Scanner sc1 = new Scanner(System.in);
        int option = sc1.nextInt();
        if(option == 4) {
            loggedUser = UserService.logOff();
        } else if(option == 1) {
            if(loggedUser instanceof Driver) {
                Driver aux = (Driver) loggedUser;
                Order currentOrder = aux.getAssignedOrder();
                if(currentOrder != null) {
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
                aux.finishOrder();
            } else {
                System.out.println("You currently have no order assigned");
            }
        } else if(option == 3) {
            showUserOrders(loggedUser);
        }
    }

    //used to show a user's orders

    public static void showUserOrders(User user) {
        //sortare crescatoare dupa pretul total al unei comenzi
        Collections.sort(allOrders);
        if(user instanceof normalUser) {
            for(Order order : allOrders) {
                if(order.getClientId() == user.getUserId()) {
                    order.showOrderInfo();
                }
            }
        } else if(user instanceof Driver) {
            for(Order order : allOrders) {
                if(order.getDriverId() == user.getUserId()) {
                    order.showOrderInfo();
                }
            }
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
        System.out.println("4. Logout (press 4, then enter)");
        //-> more functionalities to be implemented, such as editing restaurant properties, etc.
        Scanner sc1 = new Scanner(System.in);
        int option = sc1.nextInt();
        if(option == 1) {
            showAllOrders();
        } else if(option == 2) {
            showAllUsers();
        } else if(option == 3) {
            UserService.registerUser("driver");
        }
    }
}

import food.*;
import address.Address;
import restaurant.Restaurant;
import service.DataReader;
import service.Menu;
import user.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import dao.repository.*;
public class Main {
    public static void main(String[] args) throws SQLException {
        /*
        //System.out.println("Hello world");
        Desert d1 = new Desert("Tiramisu", 4.5, 50, 100);
        System.out.println(d1.getBasicDescription());
        Sushi sh1 = new Sushi("Ragnarok", 30, 300, 20);
        System.out.println(sh1.getBasicDescription());
        sh1.description();
        ArrayList<MenuItem>menuArr = new ArrayList<MenuItem>();
        menuArr.add(d1);
        menuArr.add(sh1);

        System.out.println(menuArr);
        Address adr1 = new Address("Valcea", "Castana", 34);
        Restaurant rest1 = new Restaurant("La Mitica", adr1, "076", menuArr);
        User user1 = new normalUser("Matei Pop", adr1, "075", "mateipop@yahoo.com", "123");
        Driver driver1 = new Driver("Cosmin Matei", adr1, "073", "mateicosmin@yahoo.com", "123");
        Order order1 = new Order(user1, rest1, menuArr, driver1);
        System.out.println(order1.getOrderStatus());
        driver1.finishOrder();
        System.out.println(order1.getOrderStatus());
        System.out.println(rest1);
        */
        //User loggedUser = null;
        /*
        Address adr1 = new Address("Valcea", "Castana", 34);
        Address adr2 = new Address("Bucuresti", "Lalelor", 28);
        Address adr3 = new Address("Constanta", "Cazinoului", 7);

        //8 obiecte
        NormalUser marcel = new NormalUser("Marcel", adr1, "073", "marcel@yahoo.com", "123456");
        Driver costel = new Driver("Costel", adr2, "078", "costel@yahoo.com", "123456");
        Admin fane = new Admin("Fane", adr3, "075", "fane@yahoo.com", "123456");
        Desert d1 = new Desert("Tiramisu", 4.5, 50, 100);
        Sushi sh1 = new Sushi("Ragnarok", 30, 300, 20);
        Drink dr1 = new Drink("Coca Cola", 2.5, 500, 0);
        FastFood fast1 = new FastFood("Burger", 5.3, 720.3, 450);
        MainCourse main1 = new MainCourse("Somon", 5.0, 100, 30);

        ArrayList<MenuItem>menuArr = new ArrayList<MenuItem>();
        menuArr.add(d1);
        menuArr.add(sh1);
        menuArr.add(dr1);
        menuArr.add(fast1);
        menuArr.add(main1);


        Restaurant rest1 = new Restaurant("Kapo", adr3, "079", menuArr);
        Menu AppMenu = new Menu();
        Menu.addUser(marcel);
        Menu.addUser(costel);
        Menu.addUser(fane);
        Menu.addRestaurant(rest1);
        Menu.initializeDriverQueue();

        while(true) {
            Menu.showMenu();
        }
         */
        DataReader readData = DataReader.getInstance();
        ArrayList<User> registeredUsers = UserRepository.getUserRepository().loadRegisteredUsers();
        /*
        for(int i = 0; i < registeredUsers.size(); ++i){
            userRepository.insertUser(registeredUsers.get(i));
        }
        */
        ArrayList<Restaurant> restaurants = readData.readRestaurants();

        HashMap<UUID, Restaurant> restaurantsById = DataReader.getRestaurantsByID(restaurants);
        //restaurantsById.forEach((k, v) -> System.out.println(k));
        ArrayList <MenuItem> menuItems = readData.readFoods(restaurantsById);


        Menu AppMenu = new Menu();
        for(int i = 0; i < registeredUsers.size(); ++i) {
            Menu.addUser(registeredUsers.get(i));
        }

        for(int i = 0; i < restaurants.size(); ++i) {
            Menu.addRestaurant(restaurants.get(i));
        }
        Menu.initializeDriverQueue();
        while(true) {
            Menu.showMenu();
        }
    }
}

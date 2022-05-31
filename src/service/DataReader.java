package service;

import address.Address;
import food.*;
import order.Order;
import restaurant.Restaurant;
import user.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class DataReader {
    private static DataReader reader;
    public static DataReader getInstance() {
        if(reader == null) {
            reader = new DataReader();
        }
        return reader;
    }

    public ArrayList<User> readRegisteredUsers(){
        ArrayList<User>registeredUsers = new ArrayList<>();
        String dataEntry;

        try{
            BufferedReader buff = new BufferedReader(new FileReader("data/users.csv"));
            dataEntry = buff.readLine();
            while(dataEntry != null) {
                //System.out.println(dataEntry);
                String[] userData = dataEntry.split(",");
                User user = null;
                if(userData.length > 0) {
                    if(userData[0].compareTo("admin") == 0) {
                        //System.out.println("yes");
                        //normal-user, userId, Marcel, Valcea, Str. Castana, 34, 073, marcel@yahoo.com, 123456
                        //driver, userId, Fane, Constanta, Str. Cazinoului, 7, 075, fane@yahoo.com, 123456
                        //admin, userId, Costel, Bucuresti, Str. Lalelor, 28, 078, costel@yahoo.com, 123456
                        registeredUsers.add(new Admin(UUID.fromString(userData[1]), userData[2], new Address(userData[3], userData[4], Integer.parseInt(userData[5])), userData[6], userData[7], userData[8]));
                        //System.out.println(user);
                    } else if(userData[0].compareTo("normal-user") == 0) {
                        registeredUsers.add(new NormalUser(UUID.fromString(userData[1]), userData[2], new Address(userData[3], userData[4], Integer.parseInt(userData[5])), userData[6], userData[7], userData[8]));
                        //registeredUsers.add(user);
                    } else if(userData[0].compareTo("driver") == 0) {
                        registeredUsers.add(new Driver(UUID.fromString(userData[1]), userData[2], new Address(userData[3], userData[4], Integer.parseInt(userData[5])), userData[6], userData[7], userData[8]));
                        //registeredUsers.add(user);
                    }
                    dataEntry = buff.readLine();
                }
            }
        }catch(Exception exception) {
            exception.printStackTrace();
        }
        return registeredUsers;
    }

    public ArrayList<Restaurant> readRestaurants() {
        ArrayList<Restaurant> registeredRestaurants = new ArrayList<>();
        String dataEntry;
        try{
            BufferedReader buff = new BufferedReader(new FileReader("data/restaurants.csv"));
            dataEntry = buff.readLine();
            while (dataEntry != null) {
                String[] data = dataEntry.split(",");
                //d245eb55-4add-420f-be7e-54bdeef1f7a7, Kapo, Constanta, Str. Cazinoului, 7, "079"
                try{
                   registeredRestaurants.add(new Restaurant(UUID.fromString(data[0]), data[1], new Address(data[2], data[3], Integer.parseInt(data[4])), data[5], new ArrayList<MenuItem>()));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                dataEntry = buff.readLine();

            }
        } catch(Exception exception) {
            exception.printStackTrace();
        }
        return registeredRestaurants;
    }

    public static HashMap<UUID, Restaurant> getRestaurantsByID(ArrayList<Restaurant> restArr) {
        HashMap<UUID, Restaurant> restMap = new HashMap<>();
        for (Restaurant currentRest : restArr) {
            restMap.put(currentRest.getRestaurantId(), currentRest);
        }
        return restMap;
    }

    public ArrayList<MenuItem> readFoods(HashMap<UUID, Restaurant> restMap) {
        ArrayList <MenuItem>registeredMenuItems = new ArrayList<>();
        String dataEntry;
        try {
            BufferedReader buff = new BufferedReader(new FileReader("data/menuItems.csv"));
            dataEntry = buff.readLine();
            while(dataEntry != null) {
                String[] data = dataEntry.split(",");
                if(data[1].compareTo("desert") == 0) {
                    registeredMenuItems.add(readItem(data, Desert.class));
                } else if(data[1].compareTo("sushi") == 0) {
                    registeredMenuItems.add(readItem(data, Sushi.class));
                } else if(data[1].compareTo("non-alcoholic drink") == 0 || data[1].compareTo("alcoholic drink") == 0){
                    registeredMenuItems.add(readItem(data, Drink.class));
                } else if(data[1].compareTo("fast-food") == 0) {
                    registeredMenuItems.add(readItem(data, FastFood.class));
                } else if(data[1].compareTo("main-course") == 0) {
                    registeredMenuItems.add(readItem(data, MainCourse.class));
                }
                UUID restaurantId = UUID.fromString(data[6]);
                //System.out.println(restaurantId);
                //adaugam item-ul, la meniul restaurantului corespunzator.
                if(registeredMenuItems.size() > 0) {
                    restMap.get(restaurantId).addToMenu(registeredMenuItems.get(registeredMenuItems.size() - 1));
                }

                dataEntry = buff.readLine();
            }
        } catch(Exception exception) {
            exception.printStackTrace();
        }
        return registeredMenuItems;
    }

    public <T extends MenuItem> MenuItem readItem(String[] data, Class<T>Type){
        //6a7fb99c-5298-4e02-a004-6ccc78b7ed71, desert, Tiramisu, 4.5, 50, 100, d245eb55-4add-420f-be7e-54bdeef1f7a7
        //a571ba67-ca84-4437-9da3-933e2b9b6ed4, sushi, Ragnarok, 30, 300, 20, d245eb55-4add-420f-be7e-54bdeef1f7a7
        //ca0507d3-a4a0-4483-905a-fe51b2063f5c, non-alcoholic drink, Coca Cola, 2.5, 500, 0, d245eb55-4add-420f-be7e-54bdeef1f7a7
        //d2840941-e25b-405c-b1ab-bbe194cec579, fast-food, Burger, 5.3, 720.3, 450, d245eb55-4add-420f-be7e-54bdeef1f7a7
        //ed75161b-c2c3-48b6-ba54-865dc58f9331, main-course, Somon, 5.0, 100, 30, d245eb55-4add-420f-be7e-54bdeef1f7a7
        try {
            switch (Type.toString()) {
                case "class food.Desert":
                    return new Desert(UUID.fromString(data[0]), data[2], Double.parseDouble(data[3]), Double.parseDouble(data[4]), Double.parseDouble(data[5]));
                case "class food.Sushi":
                    return new Sushi(UUID.fromString(data[0]), data[2], Double.parseDouble(data[3]), Double.parseDouble(data[4]), Double.parseDouble(data[5]));
                case "class food.Drink":
                    return new Drink(UUID.fromString(data[0]), data[2], Double.parseDouble(data[3]), Integer.parseInt(data[4]), Float.parseFloat(data[5]));
                case "class food.FastFood":
                    return new FastFood(UUID.fromString(data[0]), data[2], Double.parseDouble(data[3]), Double.parseDouble(data[4]), Double.parseDouble(data[5]));
                case "class food.MainCourse":
                    return new MainCourse(UUID.fromString(data[0]), data[2], Double.parseDouble(data[3]), Double.parseDouble(data[4]), Double.parseDouble(data[5]));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public ArrayList<Order> readOrders(HashMap <UUID, User> usersByID, HashMap <UUID, Restaurant> restById, HashMap<UUID, MenuItem> foodById) {
        ArrayList<Order> orders = new ArrayList<>();
        String dataEntry;
        try{
            BufferedReader buff = new BufferedReader(new FileReader("data/restaurants.csv"));
            dataEntry = buff.readLine();
            while (dataEntry != null) {
                String[] data = dataEntry.split(",");
                //order id, client id, restaurant id, driver id, item1, item2,..., itemN
                try {
                    UUID orderId = UUID.fromString(data[0]);
                    UUID clientId = UUID.fromString(data[1]);
                    UUID restId = UUID.fromString(data[2]);
                    UUID driverId = UUID.fromString(data[3]);
                    ArrayList<MenuItem> orderItems = new ArrayList<>();
                    for(int i = 4; i < data.length; ++i) {
                        orderItems.add(foodById.get(UUID.fromString(data[i])));
                    }
                    User driverUser = usersByID.get(driverId);
                    Driver driver = null;
                    if(driverUser instanceof Driver){
                        driver = (Driver) driverUser;
                    }
                    orders.add(new Order(orderId, usersByID.get(clientId), restById.get(restId), orderItems, driver));
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
                dataEntry = buff.readLine();
            }
        } catch(Exception exception) {
            exception.printStackTrace();
        }
        return orders;
    }


}

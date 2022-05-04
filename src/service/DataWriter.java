package service;

import order.Order;
import restaurant.Restaurant;
import user.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataWriter {
    private static DataWriter writeData = null;
    private BufferedWriter buffer;
    public static DataWriter getInstance() {
        if(writeData == null) {
            writeData = new DataWriter();
        }
        return writeData;
    }
    public <T> void writeToDb(Object object, Class<T> objType) {
        try {
            String filePath;
            if(objType.toString().equals("class user.User")) {
                filePath = "data/users.csv";
                buffer = new BufferedWriter(new FileWriter(filePath, true));
                buffer.write(((User)object).WriteToCsv());
            } else if(objType.toString().equals("class restaurant.Restaurant")) {
                filePath = "data/restaurants.csv";
                buffer = new BufferedWriter(new FileWriter(filePath, true));
                buffer.write(((Restaurant) object).writeToCsv());
            } else if(objType.toString().equals("class order.Order")){
                filePath = "data/orders.csv";
                buffer = new BufferedWriter(new FileWriter(filePath, true));
                buffer.write(((Order)object).writeToCsv());
            }
            buffer.flush();
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }
}

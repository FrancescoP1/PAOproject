package dao.repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

import address.Address;
import dao.configuration.DatabaseConfiguration;
import user.*;
import user.Driver;


public class UserRepository {
    private static UserRepository userRepository;
    private UserRepository(){

    }

    public static UserRepository getUserRepository(){
        if(userRepository == null){
            userRepository = new UserRepository();
        }
        return userRepository;
    }

    //insert new User to database

    public void insertUser(String uid, String userType, String name, String district, String street, int number, String phone, String email, String password){
        String insertUserQuery = "INSERT INTO USERS(ID, USER_TYPE, NAME, DISTRICT, STREET, NUMBER, PHONE, EMAIL, PASSWORD) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection con = DatabaseConfiguration.getDatabaseConnection();

        try{
            PreparedStatement statement = con.prepareStatement(insertUserQuery);
            statement.setString(1, uid);
            statement.setString(2, userType);
            statement.setString(3, name);
            statement.setString(4, district);
            statement.setString(5, street);
            statement.setInt(6, number);
            statement.setString(7, phone);
            statement.setString(8, email);
            statement.setString(9, password);
            statement.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void insertUser(User user){
        userRepository.insertUser(
                user.getUserId().toString(),
                user.getType(),
                user.getName(),
                user.getAddress().getDistrict(),
                user.getAddress().getStreet(),
                user.getAddress().getNumber(),
                user.getPhoneNumber(),
                user.getEmailAddress(),
                user.getPassword()
        );
    }

    public ResultSet selectAllUsers() {
        String selectUsersQuery = "SELECT * FROM USERS";
        ResultSet resultSet = null;
        Connection con = DatabaseConfiguration.getDatabaseConnection();
        try {
            Statement statement = con.createStatement();
            resultSet = statement.executeQuery(selectUsersQuery);
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void updateUserPassword(String userId, String newPassword){
        String updateQuery = "UPDATE USERS SET PASSWORD = ? WHERE ID = ?";
        Connection con = DatabaseConfiguration.getDatabaseConnection();
        try {
            PreparedStatement statement = con.prepareStatement(updateQuery);
            statement.setString(1, newPassword);
            statement.setString(2, userId);
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateUser(String userId, String whatToUpdate, String newValue){
        String updateQuery = "UPDATE USERS SET " + whatToUpdate + " = ? WHERE ID = ?";
        Connection con = DatabaseConfiguration.getDatabaseConnection();
        try {
            PreparedStatement statement = con.prepareStatement(updateQuery);
            statement.setString(2, userId);
            if(whatToUpdate.equalsIgnoreCase("number")){
                statement.setInt(1, Integer.parseInt(newValue));
            } else {
                statement.setString(1, newValue);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String userId){
        String deleteQuery = "DELETE FROM USERS WHERE ID = ?";
        Connection con = DatabaseConfiguration.getDatabaseConnection();
        try {
            PreparedStatement statement = con.prepareStatement(deleteQuery);
            statement.setString(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUserByEmail(String email){
        String deleteQuery = "DELETE FROM USERS WHERE EMAIL = ?";
        Connection con = DatabaseConfiguration.getDatabaseConnection();
        try {
            PreparedStatement statement = con.prepareStatement(deleteQuery);
            statement.setString(1, email);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable(){
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (\n" +
                "  ID varchar(40) NOT NULL,\n" +
                "  USER_TYPE enum('normal-user','admin','driver','') NOT NULL,\n" +
                "  NAME varchar(50) NOT NULL,\n" +
                "  DISTRICT varchar(30) NOT NULL,\n" +
                "  STREET varchar(30) NOT NULL,\n" +
                "  NUMBER int NOT NULL,\n" +
                "  PHONE varchar(10) NOT NULL,\n" +
                "  EMAIL varchar(50) NOT NULL,\n" +
                "  PASSWORD varchar(40) NOT NULL,\n" +
                "  ASSIGNED_ORDER varchar(40) DEFAULT NULL,\n" +
                "  PRIMARY KEY (ID)\n"+
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
        Connection con = DatabaseConfiguration.getDatabaseConnection();
        try {
            Statement statement = con.createStatement();
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean userExists(String email) {
        String selectUsersQuery = "SELECT * FROM USERS WHERE EMAIL = ?";
        ResultSet resultSet = null;
        Connection con = DatabaseConfiguration.getDatabaseConnection();
        try {
            PreparedStatement statement = con.prepareStatement(selectUsersQuery);
            statement.setString(1, email);
            resultSet = statement.executeQuery(selectUsersQuery);
            if (resultSet != null) {
                return true;
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList <User> loadRegisteredUsers() throws SQLException {
        ArrayList<User> regUsers = new ArrayList<>();
        try(ResultSet rs = selectAllUsers()){
            while(rs.next()){
                UUID id = UUID.fromString(rs.getString(1));
                String userType = rs.getString(2);
                String name = rs.getString(3);
                String district = rs.getString(4);
                String street = rs.getString(5);
                int number = rs.getInt(6);
                String phone = rs.getString(7);
                String email = rs.getString(8);
                String password = rs.getString(9);
                if(userType.equals("driver")){
                    regUsers.add(new Driver(id, name, new Address(district, street, number), phone, email, password));
                } else if(userType.equals("admin")) {
                    regUsers.add(new Admin(id, name, new Address(district, street, number), phone, email, password));
                } else if(userType.equals("normal-user")){
                    regUsers.add(new NormalUser(id, name, new Address(district, street, number), phone, email, password));
                }
            }
        }
        return regUsers;
    }
}

package food;

import java.util.UUID;

public abstract class MenuItem {
    private static int numberOfMenuItems = 0;
    private UUID itemId;
    private String itemName;
    private double itemPrice;

    public MenuItem(String itemName, double itemPrice) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemId = UUID.randomUUID();
        MenuItem.numberOfMenuItems++;
    }

    public MenuItem(UUID itemId, String itemName, double itemPrice){
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        MenuItem.numberOfMenuItems++;
    }

    public MenuItem() {
        this.itemName = "";
        this.itemPrice = 0;
    }
    public MenuItem(MenuItem m1) {
        this.itemId = m1.getItemId();
        this.itemName = m1.getItemName();
        this.itemPrice = m1.getItemPrice();
    }

    public static int getNumberOfMenuItems() {
        return numberOfMenuItems;
    }

    public UUID getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public abstract void description();
    /*{
        System.out.print("This item: " + this.getItemName() + " (item-id: " + this.getItemId() + ") has a price of: " + this.getItemPrice());
    } */
    public abstract String getType();
    //public abstract String writeToCsv();
    public String getBasicDescription() {
        return ("type: " + this.getType() + " | name: " + this.getItemName() + " | price: " + this.getItemPrice());
    }



}

package food;

import java.util.UUID;

public class Desert extends MenuItem{
    private double sugar; //grams of sugar
    private double mass;

    public Desert(String itemName, double itemPrice, double sugar, double mass) {
        super(itemName, itemPrice);
        this.mass = mass;
        this.sugar = sugar;
    }

    public Desert(UUID itemId, String itemName, double itemPrice, double sugar, double mass) {
        super(itemId, itemName, itemPrice);
        this.mass = mass;
        this.sugar = sugar;
    }

    //copy constructor
    public Desert(Desert d1) {
        super(d1);
        this.mass = d1.getMass();
        this.sugar = d1.getSugar();
    }

    public double getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    public double getSugar() {
        return sugar;
    }

    public void setSugar(float sugar) {
        this.sugar = sugar;
    }

    @Override
    public void description(){
        System.out.print("The desert " + this.getItemName() + " has a total weight of " + this.getMass());
        System.out.print(" grams and it contains " + this.getSugar() + " grams of sugar. The price is " + this.getItemPrice() + "$.\n");
        //System.out.println("Full list of ingredients: " + this.getIngredients()); will be implemented at a later time.
    }

    @Override
    public String getType() {
        return "desert";
    }

}

package food;

import java.util.UUID;

public class Sushi extends MenuItem{
    double mass; //in grams
    double omegaThree; //in grams

    public Sushi(String itemName, double itemPrice, double mass, double omegaThree) {
        super(itemName, itemPrice);
        this.mass = mass;
        this.omegaThree = omegaThree;
    }

    public Sushi(UUID itemId, String itemName, double itemPrice, double mass, double omegaThree) {
        super(itemId, itemName, itemPrice);
        this.mass = mass;
        this.omegaThree = omegaThree;
    }

    public Sushi() {
        super();
        this.mass = 0;
        this.omegaThree = 0;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getOmegaThree() {
        return omegaThree;
    }

    public void setOmegaThree(double omegaThree) {
        this.omegaThree = omegaThree;
    }

    @Override
    public void description() {
        System.out.print("The sushi " + this.getItemName() + " has a total weight of " + this.getMass());
        System.out.print(" grams, of which Omega 3: " + this.getOmegaThree() + ". The price is " + this.getItemPrice() + "$.\n");
        //ingredients will be added later
    }

    @Override
    public String getType() {
        return "sushi";
    }
}

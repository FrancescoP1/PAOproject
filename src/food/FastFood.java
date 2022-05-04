package food;

import java.util.UUID;

public class FastFood extends MenuItem{
    double calories;
    double mass;

    public FastFood() {
        super();
        this.calories = 0;
        this.mass = 0;
    }

    public FastFood(String itemName, double itemPrice, double calories, double mass) {
        super(itemName, itemPrice);
        this.calories = calories;
        this.mass = mass;
    }

    public FastFood(UUID itemId, String itemName, double itemPrice, double calories, double mass) {
        super(itemId, itemName, itemPrice);
        this.calories = calories;
        this.mass = mass;
    }

    public FastFood(FastFood f1) {
        super(f1);
        this.calories = f1.calories;
        this.mass = f1.mass;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    @Override
    public void description() {
        System.out.print("The fast-food item: " + this.getItemName() + " has a total weight of " + this.getMass());
        System.out.print(" grams and it has a total of " + this.getCalories() + " Kcal. The price is " + this.getItemPrice() + "$.\n");
    }

    @Override
    public String getType() {
        return "fast-food";
    }
}

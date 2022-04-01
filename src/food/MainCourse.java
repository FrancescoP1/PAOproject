package food;

public class MainCourse extends MenuItem{
    double mass; //in grams
    double proteins; //in grams

    public MainCourse() {
        super();
        this.mass = 0;
        this.proteins = 0;
    }

    public MainCourse(String itemName, double itemPrice, double mass, double proteins) {
        super(itemName, itemPrice);
        this.mass = mass;
        this.proteins = proteins;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getProteins() {
        return proteins;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    @Override
    public void description() {
        System.out.print("Main course: " +  this.getItemName() + ", has a total weight of: " + this.getMass() + " grams, of which proteins: " + this.getProteins());
        System.out.print("kcal. The price is " + this.getItemPrice() + "$.\n");
        //System.out.println("Full list of ingredients: " + this.getIngredients()); -> will be implemented in the future
    }

    @Override
    public String getType() {
        return "main course";
    }
}

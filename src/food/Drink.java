package food;

public class Drink extends MenuItem{
    private int volume; //in ml
    private float alcConcentration; //

    public Drink(String itemName, double itemPrice, int volume, float alcConcentration) {
        super(itemName, itemPrice);
        this.volume = volume;
        if(alcConcentration < 100 && alcConcentration >= 0){
            this.alcConcentration = alcConcentration;
        } else {
            this.alcConcentration = 0;
        }

    }
    //copy constructor
    public Drink(Drink dr1) {
        super(dr1);
        this.volume = dr1.getVolume();
        this.alcConcentration = dr1.alcConcentration;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public float getAlcConcentration() {
        return alcConcentration;
    }

    public void setAlcConcentration(float alcConcentration) {
        this.alcConcentration = alcConcentration;
    }

    @Override
    public void description(){
        System.out.println(this.getItemName() + " has a volume of " + this.getVolume() + "ml and it costs " + this.getItemPrice() + "$.");
        if(this.alcConcentration > 0) {
            System.out.println("This drink (" + this.getItemName() + ") contains " + this.getAlcConcentration() + "% alcohol and should only be consumed by adults!");
        }
    }

    @Override
    public String getType() {
        if(this.alcConcentration > 0) {
            return "alcoholic drink";
        } else {
            return "non-alcoholic drink";
        }
    }

}

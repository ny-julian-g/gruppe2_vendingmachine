import java.util.concurrent.atomic.AtomicInteger;

public class Snack {
    private String name;
    private int count;
    private float price;
    private int initialAmount;
    private final int id;
    private static AtomicInteger idCounter = new AtomicInteger(0);

    public Snack(String name, int initialAmount, float price){
       this.name= name;
       this.initialAmount = initialAmount;
       this.count = initialAmount;
       this.price = price;
       this.id = idCounter.getAndAdd(1);
    }

    public String toString(){
        return name + ": " + price + "$ (" + count + ")";
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getInitialAmount(){
        return initialAmount;
    }

    public void setInitialAmount(int initialAmount){
        this.initialAmount = initialAmount;
    }

    public int getId(){
        return id;
    }

}

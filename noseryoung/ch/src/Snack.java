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
        return name + ": " + price + "$, ID: " + id + " (" + count + " available)";
    }

    public int getCount() {
        return count;
    }

    public void setCount(int newCount) {
        if (count < 0){
            throw new IllegalArgumentException("count must be a positive integer");
        }
        count = newCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()){
            throw new IllegalArgumentException("name cannot be null");
        }
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        if (price < 0.f){
            throw new IllegalArgumentException("price must be positive");
        }
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

    public boolean isAvailable(){
        return count > 0;
    }
}

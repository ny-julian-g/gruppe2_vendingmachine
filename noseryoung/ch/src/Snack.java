public class Snack {
    private String name;
    private int count;
    private int price;

    public Snack(String name, int count, int price){
       this.name= name;
       this.count = count;
       this.price = price;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

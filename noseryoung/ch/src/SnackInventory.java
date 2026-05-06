import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/*
 * How to use this Class:
 *
 * !Important!
 * No Function ever returns null! Either it returns an empty List,
 * an empty optional field, that should be handled safely to avoid a:
 * "NoSuchElementException". A Function that returns void, will be able to throw
 * an exception.
 *
 * All Functions are marked with either:
 * - (empty)    -> can return an empty ArrayList
 * - (optional) -> can return an empty optional field.
 * - (exception)-> will throw an exception, that has to be handled
 *
 *---------------------------------------------
 * Initialisation (Constructor):
 * for now, you have to give a ArrayList of all Snacks as parameters to the constructor.
 *
 * implemented Functions:
 *      User Functions:
 *          getSnack (optional)
 *          getAvailableSnacks (empty)
 *          getUnavailableSnacks (empty)
 *
 *      SecretKey Functions:
 *          restockSnacks (exception)
 *          setPrice (exception)
 *          changeSnack (exception)
 */
public class SnackInventory {
    private List<Snack> snacks;
    private List<Snack> emptyList = List.of();

    public SnackInventory(List<Snack> snacks) {
        this.snacks = snacks;
    }

   //getSnack (optional): Returns an Object of the desired Snack, if available. Reduces the Snack Count by 1.
    public Optional<Snack> getSnack(Snack desiredSnack) {
        if (snacks.isEmpty()) return Optional.empty();
        for (Snack snack : snacks) {
            int count = snack.getCount();
            if (snack.equals(desiredSnack)) {
                if (count > 0) {
                    snack.setCount(count - 1);
                    return Optional.of(snack);
                } else return Optional.empty();
            }
        }
        System.out.println("No Snack found");
        return Optional.empty();
    }

   //getAvailableSnacks (empty): Returns a List of all Snacks, that are available.
    public List<Snack> getAvailableSnacks() {
        if (snacks.isEmpty()) return emptyList;
        ArrayList<Snack> availableSnacks = new ArrayList<>();

        for (Snack snack : snacks) {
            int count = snack.getCount();
            if (count > 0) {
                availableSnacks.add(snack);
            }
        }
        return availableSnacks;
    }

    //getUnavailableSnacks (empty): Returns a List of all Snacks, that are unavailable.
    public List<Snack> getUnavailableSnacks(){
        if (snacks.isEmpty()) return emptyList;
        ArrayList<Snack> availableSnacks = new ArrayList<>();

        for (Snack snack : snacks) {
            int count = snack.getCount();
            if (count <= 0) {
                availableSnacks.add(snack);
            }
        }
        return availableSnacks;
    }

    //restockSnacks (exception): restores all snacks to the initial value or
    // optionally a set value, if you add a List of Integers (Wrapper).
    public void restockSnacks(Optional<List<Integer>> values) {
        List<Integer> amounts = values.orElseGet(this::getInitialValues);
        if (amounts.size() != snacks.size()) throw new IllegalArgumentException
                    ("List of snack amounts doesn't match amount of snacks");
            for (int i = 0; i < snacks.size(); i++){
                Snack s = snacks.get(i);
                s.setCount(amounts.get(i));
            }
    }

    //setPrice (exception): You can set a new price for a snack
    public void setPrice(Snack snack, int price){
        if (snacks.isEmpty()) throw new IllegalArgumentException("No Snacks available");
        if (price < 0) throw new IllegalArgumentException("Price can't be lower than zero(0)");
        boolean isInexistent = true;
        for (Snack currentSnack : snacks) {
            if (currentSnack == snack){
                snack.setPrice(price);
                isInexistent = false;
            }
        }
        if(isInexistent) throw new NoSuchElementException("Snack not available");

    }

    //changeSnack (exception): You can change the attributes (e.g. Name: Cola -> Pepsi)
    public void changeSnack(Snack oldSnack, String name, int count, int price){
      int snackPosition = indexOf(oldSnack);
        if (count < 0 )
           throw new IllegalArgumentException("Count can't be below zero(0)");
       if (price < 0)
           throw new IllegalArgumentException("Price can't be below zero(0)");
       if (name == null || name.isEmpty())
           throw new IllegalArgumentException("Name can't be empty");

       Snack newSnack = new Snack(name, count, price);
       snacks.set(snackPosition, newSnack);
    }

    private int indexOf(Object o){
        for (int i = 0; i < snacks.size(); i++){
            if (snacks.get(i) == o) return i;
        }
        throw new NoSuchElementException("Snack couldn't be found in List");
    }

    private List<Integer> getInitialValues(){
        List<Integer> initValues = new ArrayList<>();
        for (Snack snack : snacks){
            initValues.add(snack.getInitialAmount());
        }
        return initValues;
    }

    public float getPrice(Snack snack){
       return snack.getPrice();
    }
}
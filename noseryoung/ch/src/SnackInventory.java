import java.util.ArrayList;
import java.util.List;

/**
 * How to use this Class:
 * <p>
 * All Functions are marked with either:
 * - (empty) -> can return an empty ArrayList
 * - (null) -> can return null
 * - (exception)-> will throw an exception, that has to be handled
 */
public class SnackInventory {
    private final List<Snack> snacks;

    public SnackInventory(List<Snack> snacks) {
        this.snacks = snacks;
    }

    // getSnack (null): Returns an Object of the desired Snack or null, if available. Reduces the Snack Count by 1.
    public Snack getSnack(Snack desiredSnack) {
        for (Snack snack : snacks) {
            int count = snack.getCount();

            // referential comparison works here since
            // there is always only one instance of each snack
            if (snack == desiredSnack && count > 0) {
                snack.setCount(count - 1);
                return snack;
            }
        }
        return null;
    }

    // getAvailableSnacks (empty): Returns a List of all Snacks, that are available.
    public List<Snack> getAvailableSnacks() {
        List<Snack> availableSnacks = new ArrayList<>();

        for (Snack snack : snacks) {
            if (snack.getCount() > 0) {
                availableSnacks.add(snack);
            }
        }
        return availableSnacks;
    }

    // restockSnacks (exception): restores all snacks to the initial value or
    public void restockAllSnacks() {
        for (Snack s : snacks) {
            s.setCount(s.getInitialAmount());
        }
    }

    public Snack getSnackById(int id){
        // Ids can just be used as an array index
        return snacks.get(id);
    }
}
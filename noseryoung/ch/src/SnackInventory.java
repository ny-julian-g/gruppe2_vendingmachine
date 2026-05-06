import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
 * How to use this Class:
 *
 * !Important!
 * No Function ever returns null! Either it returns an empty List
 * or it returns an empty optional field, that should be handled safely to avoid a:
 * "NoSuchElementException".
 *
 * All Functions are marked with either:
 * - (empty)    -> can return an empty ArrayList
 * - (optional) -> can return an empty optional field.
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
}
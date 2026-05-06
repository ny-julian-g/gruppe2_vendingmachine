import java.util.ArrayList;

/*
 * For this class, there are several requirements and methods, I can think of:
 * 1. If SnackMachine wants a snack, it should give it over to him
 *
 * 2. When the SnackMachine wants a snack, it should check, if there are any
 *  of the required Snack available
 *
 * 3. When the SnackMachine wants to GET all the Snacks, it should be able
 *  to list all the Snacks, that are available
 *
 * 4. I think it's usefull to also implement a Method, that returns all Snacks,
 *  that are empty.
 *
 * 5. The Inventory should of course have a dynamic List of all Snacks as Objects.
 *--------------------------------------------
 * How to use this Class
 *
 * Initialisation (Constructor):
 * for now, you have to give a ArrayList of all Snacks as parameters to the constructor.
 *
 * Functions:
 * getSnack: Returns an Object of the desired Snack, if available.
 * getAvailableSnacks: Returns an ArrayList of all Snacks, that are available.
 * getUnavailableSnacks: Returns an ArrayList of all Snacks, that are unavailable.
 */
public class SnackInventory {
    ArrayList<Snack> snacks;

    public SnackInventory(ArrayList snacks) {
        this.snacks = snacks;
    }

    public Snack getSnack(Snack desiredSnack) {
        if (snacks.isEmpty()) return null;
        for (Snack snack : snacks) {
            int count = snack.getCount();
            if (snack.equals(desiredSnack)) {
                if (count > 0) {
                    snack.setCount(count - 1);
                    return snack;
                } else return null;
            }
        }
        System.out.println("No Snack found");
        return null;
    }

    public ArrayList getAvailableSnacks() {
        if (snacks.isEmpty()) return null;
        ArrayList<Snack> availableSnacks = new ArrayList<>();

        for (Snack snack : snacks) {
            int count = snack.getCount();
            if (count > 0) {
                availableSnacks.add(snack);
            }
        }
        return availableSnacks;
    }

    public ArrayList getUnavailableSnacks(){
        if (snacks.isEmpty()) return null;
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
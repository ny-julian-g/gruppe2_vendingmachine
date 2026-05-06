import javax.swing.*;
import java.util.ArrayList;

public class CLI {
    static ArrayList<Snack> snacks = new ArrayList<>();
    static SnackInventory inventory = new SnackInventory(snacks);

    static void Menue () {
        String[] options = {"Show available Snacks", "Buy snack"};

        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose action:",
                "Menue",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) {
            showSnacks(inventory);
        }
        else {

        }
    }

    public static void showSnacks(SnackInventory inventory) {
        ArrayList<Snack> snacks = inventory.getAvailableSnacks();

        StringBuilder text = new StringBuilder("Available snacks:\n\n");

        if (snacks == null || snacks.isEmpty()) {
            text.append("No snacks available.");
        } else {
            for (Snack snack : snacks) {
                text.append(snack).append("\n");
            }
        }

        JOptionPane.showMessageDialog(
                null,
                text.toString(),
                "Snack List",
                JOptionPane.PLAIN_MESSAGE
        );
    }
}

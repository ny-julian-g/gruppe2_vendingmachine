import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class UI {
    static ArrayList<Snack> snacks = new ArrayList<>();
    static SnackInventory inventory = new SnackInventory(snacks);

    public static boolean login() {
        JPasswordField passwordField = new JPasswordField();

        int option = JOptionPane.showConfirmDialog(
                null,
                passwordField,
                "Enter password",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option == JOptionPane.OK_OPTION) {
            char[] pwd = passwordField.getPassword();

            boolean authenticated = SecretKeyAuthenticator.authenticatePassphrase(pwd);

            if (!authenticated) {
                JOptionPane.showMessageDialog(null, "Wrong password!");
            }

            return authenticated;
        }

        return false;
    }

    static void Menue() {
        String[] options = {"Show available Snacks", "Buy snack"};

        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose action:",
                "Menue",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) {
            showSnacks(inventory);
        }
        else {
            buySnack();
        }
    }

    public static void showSnacks(SnackInventory inventory) {
        List<Snack> snacks = inventory.getAvailableSnacks();

        StringBuilder text = new StringBuilder("Available snacks:\n\n");

        if (snacks == null || snacks.isEmpty()) {
            text.append("No snacks available.");
        } else {
            for (Snack snack : snacks) {
                text.append(snack.toString()).append("\n");
            }
        }

        JOptionPane.showMessageDialog(
                null,
                text.toString(),
                "Snack List",
                JOptionPane.PLAIN_MESSAGE
        );
    }

     public static void buySnack(){
        List<Snack> availableSnacks = inventory.getAvailableSnacks();

        if (availableSnacks == null || availableSnacks.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No snacks available.");
            return;
        }

        showSnacks(inventory);

        String[] options = availableSnacks.stream()
                .map(Snack::toString)
                .toArray(String[]::new);

        String choice = (String) JOptionPane.showInputDialog(
                null,
                "Please enter the number of the snack you want to buy, if you want to cancel press 0:",
                "Buy Snack",
                JOptionPane.PLAIN_MESSAGE
        );

        if (choice == null) {
            JOptionPane.showMessageDialog(null, "Cancel");
        }
        int index = Integer.parseInt(choice);
        Snack selectedSnack = snacks.get(index);

    }
}

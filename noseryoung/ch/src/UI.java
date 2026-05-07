import exceptions.NotEnoughMoneyException;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

// This class displays the vending machine UI and contains no business logic.

public class UI {
    ArrayList<Snack> snacks = new ArrayList<>(
            List.of(
                    new Snack("Monster energy Ultra", 2, 5.f),
                    new Snack("Coca Cola", 10, 3.5f),
                    new Snack("Eistee", 8, 3.5f),
                    new Snack("Red bull", 6, 5.f),
                    new Snack("Snickers", 13, 4.6f),
                    new Snack("Bubble Gum",20,1.5f),
                    new Snack("Chips", 6, 5f),
                    new Snack("Wasser", 10, 3.5f),
                    new Snack("Nüsse", 10, 4f)
            )
    );

    SnackInventory inventory = new SnackInventory(snacks);
    SnackMachine snackMachine = new SnackMachine(inventory);
    Customer customer = new Customer(snackMachine);

    void main(String[] args) {
        while (Menu()) {
        }
    }

    boolean Menu() {
        String[] options = {"Show available Snacks", "Put money in machine", "buy snack", "exit"};

            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Choose action:",
                    "Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (choice == 0) {
                showSnacks();
            } else if (choice == 1) {
                putMoneyInMachine();
            } else if (choice == 2) {
                buySnack();
            } else {
                return;
            }
        }
    }

    void showSnacks() {

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

    void buySnack() {
        String choice = JOptionPane.showInputDialog(
                null,
                "Please enter the snack ID. There are " + snackMachine.getMoney() + "$ in the machine",
                "Buy Snack",
                JOptionPane.PLAIN_MESSAGE
        );

        if (choice == null) {
            return;
        }

        if (SecretKeyAuthenticator.authenticatePassphrase(choice.toCharArray())) {
            JOptionPane.showMessageDialog(
                    null,
                    "Admin access granted!"
            );
            AdminMenu();
            return;
        }
        try {
            int id = Integer.parseInt(choice);
            Snack selectedSnack = inventory.getSnackById(id);
            customer.buySnack(selectedSnack);
            JOptionPane.showMessageDialog(null, "Purchase successful!" + selectedSnack.getName());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid Number.");
        } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "Option is not available :(");
        } catch (NotEnoughMoneyException e) {
            JOptionPane.showMessageDialog(null, "Not enough money in the machine :(");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unexpected Error occurred: " + e);
        }
    }

    void putMoneyInMachine() {
        String choice = JOptionPane.showInputDialog(
                null,
                "Enter the amount of money to put in the machine. You have " + customer.getMoney() + "$ available",
                "put money in the machine",
                JOptionPane.PLAIN_MESSAGE
        );
        if (choice == null) return;
        try {
            customer.putMoneyIntoMachine(Integer.parseInt(choice));

            JOptionPane.showMessageDialog(null, "Success");
        } catch (NotEnoughMoneyException e) {
            JOptionPane.showMessageDialog(null, "Not enough Money :(");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please just enter plain numbers");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unexpected Error occurred: " + e);
        }
    }

// The admin menu can only be accessed by entering the passphrase in the buy Snack window.

    void AdminMenu() {
        while (true) {

            String[] options = {
                    "Change Snack",
                    "Restock all Snacks",
                    "Restock Snack",
                    "set Snack Price",
                    "Exit Admin Mode"
            };

            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Admin Menu",
                    "Admin Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (choice == 0) {
                changeSnackUI();
            } else if (choice == 1) {
                restockAllSnacksUI();
            } else if (choice == 2) {
                restockSnackUI();
            } else if (choice == 3) {
                setPriceUI();
            } else {
                return;
            }
        }
    }

    private void restockSnackUI() {
        try {

            String snackId = JOptionPane.showInputDialog(
                    null,
                    "enter ID of snack to restock",
                    "Restock Snack",
                    JOptionPane.QUESTION_MESSAGE
            );

            Snack snack = inventory.getSnackById(Integer.parseInt(snackId));


            String newCount = JOptionPane.showInputDialog(
                    null,
                    "enter new amount of the snack",
                    "Restock Snack",
                    JOptionPane.QUESTION_MESSAGE
            );

            snack.setCount(Integer.parseInt(newCount));


            JOptionPane.showMessageDialog(
                    null,
                    "Snack restocked!"
            );
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number.");
        } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "Snack does not exist");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    void restockAllSnacksUI() {
        try{
            inventory.restockAllSnacks();

            JOptionPane.showMessageDialog(
                    null,
                    "All snacks restocked!"
            );
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "Unexpected exception occurred: " + e.getMessage());
        }
    }

    void setPriceUI() {
        try {

            String idInput = JOptionPane.showInputDialog(
                    null,
                    "Enter snack ID: "
            );
            if (idInput == null) return;
            int id = Integer.parseInt(idInput);

            Snack selectedSnack = inventory.getSnackById(id);

            String priceInput = JOptionPane.showInputDialog(
                    null,
                    "Enter new price: "
            );
            if (priceInput == null) return;
            float newPrice;

            if (priceInput.trim().isEmpty()) newPrice = selectedSnack.getPrice();
            else newPrice = Float.parseFloat(priceInput);

            selectedSnack.setPrice(newPrice);

            JOptionPane.showMessageDialog(
                    null,
                    "Price updated successfully!"
            );

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number.");
        } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "Invalid snack ID.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    void changeSnackUI() {
        try {

            String idInput = JOptionPane.showInputDialog(
                    null,
                    "Enter snack ID to change: "
            );
            if (idInput == null) return;
            int id = Integer.parseInt(idInput);

            Snack snack = inventory.getSnackById(id);

            String newName = JOptionPane.showInputDialog(
                    null,
                    "Enter new name: "
            );

            String input = JOptionPane.showInputDialog(
                    null,
                    "Enter new count: "
            );
            if (input == null) return;
            int newCount = Integer.parseInt(input);
            input = null;

            input = JOptionPane.showInputDialog(
                    null,
                    "Enter new price: "
            );
            if (input == null) return;
            float newPrice = Float.parseFloat(input);

            snack.setPrice(newPrice);
            snack.setCount(newCount);
            snack.setName(newName);

            JOptionPane.showMessageDialog(
                    null,
                    "Snack updated successfully!"
            );

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number.");
        } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "Invalid snack ID.");
        }
    }
}

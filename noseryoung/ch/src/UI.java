import exceptions.NotEnoughMoneyException;
import javax.swing.*;
import java.util.List;
import java.util.Optional;

// This class displays the vending machine UI and contains no business logic.

public class UI {
    List<Snack> snacks;

    SnackInventory inventory ;
    SnackMachine snackMachine ;
    Customer customer;

    public UI(List<Snack> snacks){
        this.snacks = snacks;
        this.inventory = new SnackInventory(snacks);
        this.snackMachine = new SnackMachine(inventory);
        this.customer = new Customer(snackMachine);
    }

    void Menu() {
        while (true) {
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
                break;
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
        Optional<String> input = NumpadDialog.show(
                "There are " + snackMachine.getMoney() + "$ in the machine"
        );

        if (input.isEmpty()) {
            return;
        }

        String choice = input.get();

        if (SecretKeyAuthenticator.authenticatePassphrase(choice.toCharArray())) {
            JOptionPane.showMessageDialog(null, "Admin access granted!");
            AdminMenu();
            return;
        }

        try {
            int id = Integer.parseInt(choice);

            Snack selectedSnack = inventory.getSnackById(id);

            customer.buySnack(selectedSnack);

            JOptionPane.showMessageDialog(null,
                    "Purchase successful! You now have " + customer.getMoney() + "$ and a " + selectedSnack.getName());

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
        Optional<String> input = NumpadDialog.show(
                "You have " + customer.getMoney() + "$ available",
                "Amount"
        );
        if (input.isEmpty()) return;
        String choice = input.get();
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

            Optional<String> snackIdInput = NumpadDialog.show(
                    "enter ID of snack to restock",
                    "ID"
            );
            if (snackIdInput.isEmpty()) return;

            Snack snack = inventory.getSnackById(Integer.parseInt(snackIdInput.get()));

            Optional<String> newCountInput = NumpadDialog.show(
                    "enter new amount of the snack",
                    "Amount"
            );
            if (newCountInput.isEmpty()) return;

            snack.setCount(Integer.parseInt(newCountInput.get()));


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

            Optional<String> idInput = NumpadDialog.show(
                    "Enter snack ID: ",
                    "ID"
            );
            if (idInput.isEmpty()) return;
            int id = Integer.parseInt(idInput.get());

            Snack selectedSnack = inventory.getSnackById(id);

            Optional<String> priceInput = NumpadDialog.showDecimal(
                    "Enter new price: ",
                    "Price"
            );
            if (priceInput.isEmpty()) return;
            float newPrice;

            if (priceInput.get().trim().isEmpty()) newPrice = selectedSnack.getPrice();
            else newPrice = Float.parseFloat(priceInput.get());

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

            Optional<String> idInput = NumpadDialog.show(
                    "Enter snack ID to change: ",
                    "ID"
            );
            if (idInput.isEmpty()) return;
            int id = Integer.parseInt(idInput.get());

            Snack snack = inventory.getSnackById(id);

            String newName = JOptionPane.showInputDialog(
                    null,
                    "Enter new name: "
            );
            if (newName.isEmpty()) return;

            Optional<String> input = NumpadDialog.show(
                    "Enter new count: ",
                    "Count"
            );
            if (input.isEmpty()) return;
            int newCount = Integer.parseInt(input.get());

            input = NumpadDialog.showDecimal(
                    "Enter new price: ",
                    "Price"
            );
            if (input.isEmpty()) return;
            float newPrice = Float.parseFloat(input.get());

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

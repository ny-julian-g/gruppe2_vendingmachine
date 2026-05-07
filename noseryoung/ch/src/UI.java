import exceptions.NotEnoughMoneyException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class UI {
    List<Snack> snacks;

    SnackInventory inventory;
    SnackMachine snackMachine;
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
                "Please enter the snack ID. there are " + snackMachine.getMoney() + "$ in the machine",
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
            JOptionPane.showMessageDialog(null, "Purchase successful! you now have " + customer.getMoney() + "$ and a " + selectedSnack.getName());

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
                "Enter the amount of money to put in the machine. you have " + customer.getMoney() + "$",
                "put money in machine",
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
            JOptionPane.showMessageDialog(null, "Unexpected Error occurred:" + e);
        }
    }

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
                    "Enter snack ID:"
            );
            if (idInput == null) return;
            int id = Integer.parseInt(idInput);

            Snack selectedSnack = inventory.getSnackById(id);

            String priceInput = JOptionPane.showInputDialog(
                    null,
                    "Enter new price:"
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
                    "Enter snack ID to change:"
            );
            if (idInput == null) return;
            int id = Integer.parseInt(idInput);

            Snack snack = inventory.getSnackById(id);

            String newName = JOptionPane.showInputDialog(
                    null,
                    "Enter new name:"
            );

            String input = JOptionPane.showInputDialog(
                    null,
                    "Enter new count:"
            );
            if (input == null) return;
            int newCount = Integer.parseInt(input);
            input = null;

            input = JOptionPane.showInputDialog(
                    null,
                    "Enter new price:"
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

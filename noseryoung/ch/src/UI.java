import exceptions.NotEnoughMoneyException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UI {
    ArrayList<Snack> snacks = new ArrayList<>(
            List.of(
                    new Snack("white monster", 100, 8)
            )
    );

    SnackInventory inventory = new SnackInventory(snacks);
    SnackMachine snackMachine = new SnackMachine(inventory);
    Customer customer = new Customer(snackMachine);

    public void main(String[] args) {
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
            return false;
        }
        return true;
    }

    void showSnacks() {

        List<Snack> snacks = customer.getAvailableSnacks();

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
        List<Snack> availableSnacks = customer.getAvailableSnacks();

        if (availableSnacks == null || availableSnacks.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No snacks available.");
            return;
        }

        String[] options = availableSnacks.stream()
                .map(Snack::toString)
                .toArray(String[]::new);

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
            Snack selectedSnack = availableSnacks.get(id);
            customer.buySnack(selectedSnack);
            JOptionPane.showMessageDialog(null, "Purchase successful! " + selectedSnack.getName());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number");
        } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "Option is not available :(");
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (NotEnoughMoneyException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "unexpected Error occurred: " + e);
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

            JOptionPane.showMessageDialog(null, "success");
        } catch (NotEnoughMoneyException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please just enter plain numbers");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unexpected Error occurred:" + e);
        }
    }

    void AdminMenu() {

        String[] options = {
                "Change Snack",
                "restore Snack",
                "set Price",
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
            AdminMenu();
        } else if (choice == 1) {
            restockSnacksUI();
            AdminMenu();
        } else if (choice == 2) {
            setPriceUI();
            AdminMenu();
        } else if (choice == 3) {
            return;
        }
    }

    void restockSnacksUI() {

        List<Snack> snacks = customer.getAvailableSnacks();

        if (snacks.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No snacks available.");
            return;
        }

        try {

            List<Integer> amounts = new ArrayList<>();

            for (Snack snack : snacks) {

                String input = JOptionPane.showInputDialog(
                        null,
                        "Enter new stock for: " + snack.getName() + " (Leave empty to keep " + snack.getCount() + ")",
                        "Restock",
                        JOptionPane.QUESTION_MESSAGE
                );
                if (input == null) return;
                if (input.trim().isEmpty()) amounts.add(snack.getCount());
                else amounts.add(Integer.parseInt(input.trim()));
            }
            customer.restockSnacks(Optional.of(amounts));

            JOptionPane.showMessageDialog(
                    null,
                    "Snacks restocked successfully!"
            );

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    void setPriceUI() {

        List<Snack> snacks = customer.getAvailableSnacks();

        if (snacks.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No snacks available.");
            return;
        }

        try {

            String idInput = JOptionPane.showInputDialog(
                    null,
                    "Enter snack ID:"
            );
            if (idInput == null) return;
            int id = Integer.parseInt(idInput);

            Snack selectedSnack = snacks.get(id);

            String priceInput = JOptionPane.showInputDialog(
                    null,
                    "Enter new price:"
            );
            if (priceInput == null) return;
            float newPrice;

            if (priceInput.trim().isEmpty()) newPrice = selectedSnack.getPrice();
            else newPrice = Float.parseFloat(priceInput);

            customer.setPrice(selectedSnack, newPrice);

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

        List<Snack> availableSnacks = customer.getAvailableSnacks();

        if (availableSnacks.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No snacks available.");
            return;
        }

        try {

            String idInput = JOptionPane.showInputDialog(
                    null,
                    "Enter snack ID to change:"
            );
            if (idInput == null) return;
            int id = Integer.parseInt(idInput);

            Snack oldSnack = availableSnacks.get(id);

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
            input = null;

            customer.changeSnack(
                    oldSnack,
                    newName,
                    newCount,
                    newPrice
            );

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

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

    public void main(String[] args){
        AdminMenu();
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
        }
        else if (choice == 1){
            putMoneyInMachine();
        }
        else if (choice == 2) {
            buySnack();
        }
        else{
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

     void buySnack(){
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
                "Please enter the snack ID, enter -1 to cancel. there are " + snackMachine.getMoney()  + "$ in the machine",
                "Buy Snack",
                JOptionPane.PLAIN_MESSAGE
        );

        if(choice == null){
            return;
        }

        if (choice.equals("-1")) {
            JOptionPane.showMessageDialog(null, "Cancelled");
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
            JOptionPane.showMessageDialog(null, "Purchase successful! " + selectedSnack.getName() );
        }
        catch (NotEnoughMoneyException e) {
            JOptionPane.showMessageDialog(null, "Not enough money :(");
        }
    }

    void putMoneyInMachine(){
        String choice = JOptionPane.showInputDialog(
                null,
                "Enter the amount of money to put in the machine. you have " + customer.getMoney() + "$",
                "put money in machine",
                JOptionPane.PLAIN_MESSAGE
        );

        try{
            customer.putMoneyIntoMachine(Integer.parseInt(choice));

            JOptionPane.showMessageDialog(null, "success");
        }
        catch (NotEnoughMoneyException e){
            JOptionPane.showMessageDialog(null, "Not enough money :(");
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
        }
        else if (choice == 1) {
            restockSnacksUI();
            AdminMenu();
        }
        else if (choice == 2) {
            setPriceUI();
            AdminMenu();
        }
        else if (choice == 3) {
            Menu();
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
                        "Enter new stock for: " + snack.getName()
                );

                amounts.add(Integer.parseInt(input));
            }

            customer.restockSnacks(Optional.of(amounts));

            JOptionPane.showMessageDialog(
                    null,
                    "Snacks restocked successfully!"
            );

        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number.");
        }
        catch (IllegalArgumentException e) {
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

            int id = Integer.parseInt(idInput);

            Snack selectedSnack = snacks.get(id);

            String priceInput = JOptionPane.showInputDialog(
                    null,
                    "Enter new price:"
            );

            int newPrice = Integer.parseInt(priceInput);

            customer.setPrice(selectedSnack, newPrice);

            JOptionPane.showMessageDialog(
                    null,
                    "Price updated successfully!"
            );

        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number.");
        }
        catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "Invalid snack ID.");
        }
        catch (IllegalArgumentException e) {
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

            int id = Integer.parseInt(idInput);

            Snack oldSnack = availableSnacks.get(id);

            String newName = JOptionPane.showInputDialog(
                    null,
                    "Enter new name:"
            );

            int newCount = Integer.parseInt(
                    JOptionPane.showInputDialog(
                            null,
                            "Enter new count:"
                    )
            );

            int newPrice = Integer.parseInt(
                    JOptionPane.showInputDialog(
                            null,
                            "Enter new price:"
                    )
            );

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

        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number.");
        }
        catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "Invalid snack ID.");
        }
    }
}

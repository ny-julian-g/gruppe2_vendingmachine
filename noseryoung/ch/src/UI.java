import exceptions.NotEnoughMoneyException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

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
        while (Menu()){
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
}

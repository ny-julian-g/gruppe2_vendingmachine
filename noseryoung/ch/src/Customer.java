import exceptions.NotEnoughMoneyException;

import java.util.List;
import java.util.Optional;

/**
* this class manages the money the customer has.
* you can put money into the snack machine, get a snack
* and cancel midway.
* */
public class Customer {
    private float money = 100.f;
    SnackMachine snackMachine;

    public Customer(SnackMachine snackMachine){
        this.snackMachine = snackMachine;
    }

    public void putMoneyIntoMachine(float amount){
        if (amount > money){
            throw new NotEnoughMoneyException("not enough money");
        }
        snackMachine.addMoney(amount);
        money -= amount;
    }

    public void buySnack(Snack snack){
        snackMachine.buySnack(snack);
        money += snackMachine.refundLeftoverMoney();
    }

    public void cancel(){
        money += snackMachine.refundLeftoverMoney();
    }

    public float getMoney(){ return money; }

    public List<Snack> getAvailableSnacks() {
        return snackMachine.getAvailableSnacks();
    }

    public void restockSnacks(Optional<List<Integer>> values) {
        snackMachine.restockSnacks(values);
    }

    public void setPrice(Snack snack, int price) {
        snackMachine.setPrice(snack, price);
    }

    public void changeSnack(Snack oldSnack, String name, int count, int price) {
        snackMachine.changeSnack(oldSnack, name, count, price);
    }
}

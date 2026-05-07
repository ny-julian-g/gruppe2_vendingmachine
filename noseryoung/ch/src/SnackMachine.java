import exceptions.NotEnoughMoneyException;

import java.util.List;
import java.util.Optional;

/**
 * this class manages the money inside the snackmachine and removing items from the
 * snackInventory when you buy an item.
 * */
public class SnackMachine {
    private float money = 0.f;
    SnackInventory snackInventory;

    public SnackMachine(SnackInventory snackInventory){
        this.snackInventory = snackInventory;
    }

    public void addMoney(float amount){
        money += amount;
    }

    // returns the amount of money that can be refunded
    public float refundLeftoverMoney(){
        float tmp_money = money;
        money = 0;
        return tmp_money;
    }

    public void buySnack(Snack snack){
        if (money < snackInventory.getPrice(snack)){
            throw new NotEnoughMoneyException("not enough money");
        }

       if(snackInventory.getSnack(snack).isEmpty())throw new IllegalStateException("Snack is unavailable");
        money -= snackInventory.getPrice(snack);
    }

    public List<Snack> getAvailableSnacks() {
        return snackInventory.getAvailableSnacks();
    }

    public float getMoney(){
        return money;
    }
}

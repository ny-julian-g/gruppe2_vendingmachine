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
        snackMachine.addMoney(amount);
    }

    public void buySnack(Snack snack){
        snackMachine.buySnack(snack);
        money += snackMachine.refundLeftoverMoney();
    }

    public void cancel(){
        money += snackMachine.refundLeftoverMoney();
    }

    public float getMoney(){ return money; }
}

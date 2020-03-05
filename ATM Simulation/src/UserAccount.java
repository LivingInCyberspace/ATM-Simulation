import java.util.Random;

public class UserAccount extends Account {
    private double balance;

    public UserAccount(int id) {
        super(id);
        setRandomFirstName();
        setRandomBalance();
        adminStatus(false);
    }

    /* Returns balance */
    public double getBalance() {
        return balance;
    }

    /* Gives account a random balance.
     * For testing purposes */
    public void setRandomBalance() {
        Random randVal = new Random();

        balance = (randVal.nextDouble() * 10000.1) * (randVal.nextBoolean() ? 1: -1);
        balance = Math.floor(balance) * 100;
        balance /= 100;

        // Keeps account balance from exceeding max or min of +/- $10,000
        if (balance > 10000) {
            balance = 10000;
        } else if (balance < -10000) {
            balance = -10000;
        }
    }

    /* Withdraws specified amount */
    public void withdraw(double amount) {
        balance = Math.floor((balance - amount) * 100);
        balance /= 100;
    }

    /* Deposits specified amount */
    public void deposit(double amount) {
        balance = Math.floor((balance + amount) * 100);
        balance /= 100;
    }

    /* Determines account standing based off positive or negative balance */
    public String accountStanding() {
        if(balance >= 0)
            return "Good Standing";
        else
            return "Bad Standing";
    }
}

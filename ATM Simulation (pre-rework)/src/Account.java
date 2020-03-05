import java.util.*;

class Account {
    /* PIN is String to support multiple 0 values such as 0000
     * real ATM would only have numpad, so no worrying about other characters  */
    private int id;
    private String pin, firstName, lastName;
    private double balance;
    private boolean adminAcc;
    private static ArrayList<Integer> existingIDs = new ArrayList<>();      // Array for ID checking

    // Constructor for IDs.
    // Used for manually created standard.
    public Account(int id) {
        adminAcc = false;
        checkID(id);
        setPIN("0000");
        setRandomFirstName();
        setRandomLastName();
        setRandomBalance();
    }

    // Used for creating an admin account.
    public Account(int id, boolean isAdmin) {
        adminAcc = isAdmin;
        checkID(id);
        setPIN("admin");
        setFirstName("The");
        setLastName("Dude");
    }


    // General Methods
    /* Ensures inserted account ID does not already exist. */
    public void checkID(int id) {
        this.id = id;

        // loop checks if ID already exists and keeps generating a new one if it does
        while(existingIDs.contains(id)) {
            id = new Random().nextInt(9999);
        }

        // adds the ID once a unique one is found.
        existingIDs.add(id);
    }

    /* Determines account standing based off positive or negative balance */
    public String accountStanding() {
        if(balance >= 0)
            return "Good Standing";
        else
            return "Bad Standing";
    }

    // Accessor methods
    /* Returns admin status */
    public boolean isAdmin() {
        return adminAcc;
    }

    /* Returns ID */
    public int getID() {
        return id;
    }

    /* Returns PIN */
    public String getPIN() {
        return pin;
    }

    /* Returns balance */
    public double getBalance() {
        return balance;
    }

    /* Returns first name */
    public String getFirstName() {
        return firstName;
    }

    /* Returns last name */
    public String getLastName() {
        return lastName;
    }


    // Mutator methods
    /* Sets PIN of account */
    public void setPIN(String pin) {
        this.pin = pin;
    }

    /* Gives account the name "Jane" or "John" Doe.
     * For testing purposes */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /* Sets account last name */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /* Gives account the first name "Jane" or "John".
     * For testing purposes */
    public void setRandomFirstName() {
        Random randVal = new Random();

        // Keeps account balance from exceeding max or min of +/- $10,000
        if (randVal.nextBoolean()) {
            firstName = "John";
        } else
            firstName = "Dottie";     // Tribute to the goddess of UNF gym
    }

    /* Gives account the last name "Doe" or "Dorian".
     * For testing purposes */
    public void setRandomLastName() {
        Random randVal = new Random();

        // Keeps account balance from exceeding max or min of +/- $10,000
        if (randVal.nextBoolean()) {
            lastName = "Doe";
        } else
            lastName = "Dorian";
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
}
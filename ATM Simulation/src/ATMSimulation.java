/* Jinel Johnson
 * Purpose of Program: User is prompted to enter a valid customer ID and PIN to access their account.
 * User will then be prompted with various account options. */

import java.util.*;

class ATMSimulation {

    public static void main(String[] args) {
        // Test IDs
        int[] accIDs = {9801, 2345, 3025, 1024, 4096, 6021, 8192, 5768, 7113, 1497};
        Account accounts = new Account();
        // Admin account credentials
        accounts.add(0,  true);

        // Gives values of accIDs array to the account data fields
        for (int accID : accIDs) {
            accounts.add(accID, false);    // Creates object in the array with specified ID
        }

        // Welcomes user and requests correct ID and PIN infinitely
        boolean notShutDown = true;
        while (notShutDown) {
            printWelcomeScreen();
            retrievePass(accounts, loginID(accounts));
            }
    }

    /* Welcome screen method
     * Prints welcome screen, requests and returns ID */
    private static void printWelcomeScreen() {

        // Acquires ID
        System.out.print("\n\n\n**********************************************"
                + "\n****************** WELCOME! ******************"
                + "\n**********************************************"
                + "\nEnter customer ID: ");
    }

    private static int loginID(Map<Integer, Account> accounts) {
        Scanner input;
        int inputtedID;

        while (true) {
            try {
                input = new Scanner(System.in);
                inputtedID = input.nextInt();

                // Launches login depending on account's admin status
                while (!accounts.containsKey(inputtedID)) {
                    System.out.print("Invalid ID. Please try again: ");
                    inputtedID = input.nextInt();
                }

                return inputtedID;
            } catch (InputMismatchException e) {
                System.out.print("Only NUMBER values are needed. Please try again: ");
            }
        }
    }

        private static void retrievePass(Map<Integer, Account> accounts, int inputtedID) {
        Scanner input = new Scanner(System.in);

        if(accounts.get(inputtedID).isAdmin()) {
            System.out.print("Enter administrator password: ");
            String inputtedPass = input.next();

            if (inputtedPass.equals(accounts.get(inputtedID).getPIN())) {
                // Invokes hidden admin menu if password is correct
                adminMenu(accounts, inputtedID);
            } else if (!inputtedPass.equals(accounts.get(inputtedID).getPIN())) {
                /* Restarts to welcome screen if password is incorrect to discourage admin pass guessing.
                 * (Pseudo failsafe, can keep retrying login with admin ID) */
                System.out.println("Invalid administrator password. Restarting...");
            }
        } else {
            // Acquires PIN
            System.out.print("Enter PIN (Default = 0000): ");
            while(true) {
                String inputtedPIN = input.next();
                if(inputtedPIN.equals(accounts.get(inputtedID).getPIN())) {
                    mainMenu(accounts, inputtedID);
                    break;
                } else System.out.print("Invalid PIN. Please try again: ");
            }
        }
    }

    /* Main menu controls
     * Prints menu options and performs actions based on selection */
    private static void mainMenu(Map<Integer, Account> accounts, int inputtedID) {
        int inputtedOpt;
        double bal, amount;
        Scanner input;

        // Prints the main menu (0 for Admin Menu)
        System.out.print("\n**********************************************"
                + "\n***************** MAIN MENU ******************"
                + "\n**********************************************"
                + "\n\t\t\t\t1: Check balance" + "\n\t\t\t\t2: Withdraw" + "\n\t\t\t\t3: Deposit"
                + "\n\t\t\t\t4: Change PIN" + "\n\t\t\t\t5: Logout"
                + "\n**********************************************"
                + "\nSelect between options 1 and 5: ");

        // Requests option input and ensures only 1-6 are valid
        while(true) {
            try {
                input = new Scanner(System.in);
                inputtedOpt = input.nextInt();
                if(inputtedOpt < 1 || inputtedOpt > 5) {
                    System.out.print("Invalid option.\nPlease select between options 1 and 5: ");
                    continue;
                }
                break;
            } catch(InputMismatchException e) {
                System.out.print("Only NUMBER values are needed. Please try again: ");
            }
        }

        // Acquires the balance for the matching account
        bal = accounts.get(inputtedID).getBalance();

        // Performs action based off inputted choice
        switch(inputtedOpt) {
            case 1:        // Checks balance
                System.out.println("**********************************************");
                System.out.printf("Your balance is: $%.2f\n", bal);
                System.out.println("Your account is in " + accounts.get(inputtedID).accountStanding());
                mainMenu(accounts, inputtedID);
                break;

            case 2:        // Withdraws and prints remaining balance
                // Checks if account is in good standing. If not, returns to main menu.
                if (accounts.get(inputtedID).accountStanding().equals("Bad Standing")) {
                    System.out.println("\nYour account must be in good standing to make withdrawals.");
                    mainMenu(accounts, inputtedID);
                    break;
                }

                System.out.println("**********************************************");
                System.out.print("Enter withdrawal amount: $ ");

                // Requests valid withdrawal value
                while (true) {
                    try {
                        input = new Scanner(System.in);
                        amount = input.nextDouble();

                        if (amount <= 0) {
                            System.out.print("Invalid amount. Please try again: ");
                        } else if (bal - amount < -10000) {
                            System.out.print("Insufficient funds. Please try again: ");
                        } else break;
                    } catch (InputMismatchException e) {
                        System.out.print("Only NUMBER values are needed. Please try again: ");
                    }
                }

                // Withdraws money
                accounts.get(inputtedID).withdraw(amount);

                // Prints new balance and then recursively calls menu
                System.out.printf("\nWithdrew: $%.2f\nYour new balance is: $%.2f\n", amount, accounts.get(inputtedID).getBalance());
                mainMenu(accounts, inputtedID);
                break;

            case 3:        // Deposits and prints new balance
                System.out.println("**********************************************");
                System.out.print("Enter deposit amount: $ ");

                // Requests valid deposit value
                while (true) {
                    try {
                        input = new Scanner(System.in);
                        amount = input.nextDouble();
                        if (amount <= 0) {
                            System.out.print("Invalid amount. Please try again: ");
                        } else break;
                    } catch (InputMismatchException e) {
                        System.out.print("Only NUMBER values are needed. Please try again: ");
                    }
                }

                // Deposits money
                accounts.get(inputtedID).deposit(amount);

                // Prints new balance and then recursively calls menu
                System.out.printf("\nDeposited: $%.2f\nYour new balance is: $%.2f\n", amount, accounts.get(inputtedID).getBalance());
                mainMenu(accounts, inputtedID);
                break;

            case 4:     // Requests current PIN, then asks for and sets new PIN
                System.out.print("**********************************************"
                        + "\nEnter current PIN: ");

                input = new Scanner(System.in);
                String inputtedPIN = input.next();

                while (!inputtedPIN.equals(accounts.get(inputtedID).getPIN())) {

                    // Ensures PIN is 4 number characters
                        System.out.print("Invalid PIN. Please try again: ");
                        inputtedPIN = input.next();
                    }
                        // Sets new PIN
                        System.out.print("\nEnter new 4-digit PIN: ");
                        inputtedPIN = input.next();

                        // Ensures PIN is 4 number characters
                        while (!inputtedPIN.matches("[0-9]{4}")) {
                            System.out.print("New PIN must be 4 digits. Please try again: ");
                            inputtedPIN = input.next();
                        }

                    // Sets account PIN to the new one
                    accounts.get(inputtedID).setPIN(inputtedPIN);
                    System.out.println("PIN successfully changed!");
                    mainMenu(accounts, inputtedID);
                break;

            case 5:     // Prints exit message, breaks to reset login loop
                System.out.print("\n**********************************************"
                        + "\n************* HAVE A NICE DAY! ***************"
                        + "\n**********************************************");
                break;
        }
    }


    private static void adminMenu(Map<Integer, Account> accounts, int inputtedID) {
        int inputtedOpt;
        String inputtedPass;
        Scanner input;

        System.out.print("\n**********************************************"
                + "\n***************** ADMIN MENU *****************"
                + "\n**********************************************"
                + "\n\t\t\t\t1: Show All Accounts" + "\n\t\t\t\t2: Add New Account" + "\n\t\t\t\t3: Delete Existing Account"
                + "\n\t\t\t\t4: Change Admin PIN" + "\n\t\t\t\t5: Logout" + "\n\t\t\t\t0: Shut Down"
                + "\n**********************************************"
                + "\nSelect between options 0 and 5: ");

        while(true) {
            try {
                input = new Scanner(System.in);
                inputtedOpt = input.nextInt();
                if(inputtedOpt < 0 || inputtedOpt > 5) {
                    System.out.print("Invalid option.\nPlease select between options 1 and 5: ");
                    continue;
                }
                break;
            } catch(InputMismatchException e) {
                System.out.print("Only NUMBER values are needed. Please try again: ");
            }
        }

        switch (inputtedOpt) {     // hashItr.getKey()
            case 1:    // Shows details of all accounts
                for (Map.Entry<Integer, Account> hashItr : accounts.entrySet()) {
                    int currentID = hashItr.getKey();    // Assigned key to temp variable for readability.

                    System.out.println("Owner: " + accounts.get(currentID).getFirstName() + " " + accounts.get(hashItr.getKey()).getLastName()
                            + "\nID: " + accounts.get(currentID).getID()
                            + " — PIN: " + accounts.get(currentID).getPIN());
                    System.out.printf("Balance: $%.2f (%s)\n\n",
                            accounts.get(currentID).getBalance(), accounts.get(currentID).accountStanding());
                }

                // Recalls admin menu after account details are posted
                adminMenu(accounts, inputtedID);
                break;

            case 2:    // Adds new account to accounts array, has $0 balance by default
                // Will implement first and last name data fields another time
                // Creates new account with random ID
                System.out.println("\n**********************************************"
                        + "\nENTER THE FOLLOWING DETAILS FOR NEW ACCOUNT"
                        + "\n**********************************************");
                int newID = new Random().nextInt(9999) + 1000;
                Account newAccount = new Account(newID);
                accounts.put(newID, newAccount);

                // Requests and binds accountholder's info
                System.out.print("Accountholder's First name: ");
                accounts.get(newID).setFirstName(input.next());
                // Ensures first name uses valid characters with at least 2 letters
                while (!accounts.get(newID).getFirstName().matches("[a-zA-Z]{2,36}")) {
                    System.out.print("Only valid characters permitted. Please try again: ");
                    accounts.get(newID).setFirstName(input.next());
                }

                System.out.print("Accountholder's Last name: ");
                accounts.get(newID).setLastName(input.next());
                // Ensures last name uses valid characters with at least 2 letters
                while (!accounts.get(newID).getLastName().matches("[a-zA-Z]{2,36}")) {
                    System.out.print("Please enter at least 2 valid characters: ");
                    accounts.get(newID).setLastName(input.next());
                }

                System.out.print("4-digit PIN: ");
                accounts.get(newID).setPIN(input.next());
                // Ensures PIN is 4 number characters
                while (!accounts.get(newID).getPIN().matches("[0-9]{4}")) {
                    System.out.print("PIN must be 4 digits. Please try again: ");
                    accounts.get(newID).setPIN(input.next());
                }

                // Prints that the account has been created
                System.out.print("Account #" + accounts.get(newID).getID() + " created!\n\n");

                adminMenu(accounts, inputtedID);
                break;

            case 3:    // Removes specified account from database
                // Prints formatted list of accounts
                System.out.println("\nList of existing accounts:");

                int spacingCount = 0;     // To be incremented for new lines
                for (Map.Entry<Integer, Account> hashItr : accounts.entrySet()) {
                    if(spacingCount == 7) {
                        System.out.print(hashItr.getKey() + "\n");
                    } else if (spacingCount == accounts.size() - 1){
                        System.out.print(hashItr.getKey());
                    } else System.out.print(hashItr.getKey() + ", ");

                    spacingCount++;
                }

                System.out.println("\n\n- Enter 'Exit'  to exit -");
                System.out.print("Enter account ID to erase: ");

                // Conducts search to see if the ID is within accounts map
                while (true) {
                    try {
                        input = new Scanner(System.in);
                        inputtedID = input.nextInt();

                        if(!accounts.containsKey(inputtedID)) {
                            System.out.print("Invalid account ID. Please try again: ");
                            continue;
                        } else if (accounts.get(inputtedID).isAdmin()) {
                            System.out.print("Cannot delete admin account. Please try again: ");
                            continue;
                        }
                    } catch(InputMismatchException ex) {
                        System.out.print("Only NUMBER values are permitted.");
                        continue;
                    }
                    break;
                }

                // Requests admin pass and checks if valid
                System.out.println("Enter administrator password to confirm deletion: ");
                inputtedPass = input.next();

                while (!inputtedPass.equals(accounts.get(0).getPIN())) {
                    System.out.print("Invalid password. Please try again: ");
                    inputtedPass = input.next();
                }

                // Removes account while accIDHolder keeps the ID for printing
                int accIDHolder = accounts.get(inputtedID).getID();
                accounts.remove(inputtedID);
                System.out.print("Account #" + accIDHolder + " successfully erased.\n\n");

                // Recalls admin menu after account is erased
                adminMenu(accounts, inputtedID);
                break;

            case 4:     // Requests current PIN, then asks for and sets new PIN
                System.out.println("**********************************************");
                System.out.print("Enter current administrator password: ");
                input = new Scanner(System.in);
                inputtedPass = input.next();

                // Checks to see if PIN is valid
                while (!inputtedPass.equals(accounts.get(0).getPIN())) {
                    System.out.print("Invalid password. Please try again: ");
                    inputtedPass = input.next();
                }

                // Sets new admin password once correct one is entered
                System.out.print("\nEnter new administrator password: ");
                accounts.get(0).setPIN(input.next());
                System.out.println("Password successfully changed!");

                adminMenu(accounts, inputtedID);
                break;

            case 5:     // Prints exit message, breaks to reset login loop
                System.out.print("\n**********************************************"
                        + "\n***************** GOODBYE! *******************"
                        + "\n**********************************************");
                break;

            case 0:     // Exits program if shutdown is selected
                System.out.print("\n**********************************************"
                        + "\n************* SHUTTING DOWN... ***************"
                        + "\n**********************************************");

                input.close();
                System.exit(0);
        }
    }
}
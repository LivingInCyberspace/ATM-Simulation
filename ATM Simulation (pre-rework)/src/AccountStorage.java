import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AccountStorage {
    private static ArrayList<Integer> existingIDs = new ArrayList<>();      // Array for ID checking
    private static Map<Integer, Account> accountDetails = new HashMap<>();     // HashMap for all accounts

    // Storage Methods



    /* Ensures inserted account ID does not already exist. */
    public void checkID(int id) {

        // loop checks if ID already exists and keeps generating a new one if it does
        while(existingIDs.contains(id)) {
            id = new Random().nextInt(9999) + 1000;
        }

        // adds the ID once a unique one is found.
        existingIDs.add(id);
    }
}

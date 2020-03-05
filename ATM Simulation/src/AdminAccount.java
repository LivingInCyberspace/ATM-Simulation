public class AdminAccount extends Account {

    public AdminAccount(int id) {
        super(id);
        setPIN("admin");
        adminStatus(true);
    }

}

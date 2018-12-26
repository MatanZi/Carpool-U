package johannt.carpool_2.Users;

public class Admin extends User {
    private boolean isAdmin;

    public Admin(String firstName, String lastName, String email, String password, String phoneNumber, String city, String university, String id, boolean isAdmin , String UID) {
        super(firstName, lastName, email, password, phoneNumber, city, university, id, UID);
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}

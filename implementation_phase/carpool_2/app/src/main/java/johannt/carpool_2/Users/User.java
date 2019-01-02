package johannt.carpool_2.Users;

public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String city;
    private String university;
    private String id; // id =0 reserved for admin.
    private String UID;
    private String ImgProfile;
    private String ImgCar;


    //--------------------------------- Constractor ---------------------------------


    public User() {
    }


    public User(String firstName, String lastName, String email, String password, String phoneNumber, String city, String university, String id, String UID){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.university = university;
        this.id = id;
        this.UID = UID;
        ImgProfile = "";
        ImgCar = "";
    }


    //--------------------------------- Getter / Setters ---------------------------------


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgProfile(){ return ImgProfile ;}

    public void setImgProfile(String imgProfile){ ImgProfile = imgProfile;}

    public String getImgCar() {
        return ImgCar;
    }
    public void setImgCar(String imgCar) {
        ImgCar = imgCar;
    }
}

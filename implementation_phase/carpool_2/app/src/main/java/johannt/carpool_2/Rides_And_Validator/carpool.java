package johannt.carpool_2.Rides_And_Validator;

public class Carpool {

    public Carpool() {
    }

    private String id , firstName , lastName ,  date, endTime, startTime, price, freeSits, src, dst;

    //--------------------------------- Constructor ---------------------------------

    public Carpool(String id, String firstName, String lastName, String date, String endTime, String startTime, String price, String freeSits, String src, String dst) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.endTime = endTime;
        this.startTime = startTime;
        this.price = price;
        this.freeSits = freeSits;
        this.src = src;
        this.dst = dst;
    }


    //--------------------------------- Getter / Setters ---------------------------------


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFreeSits() {
        return freeSits;
    }

    public void setFreeSits(String freeSits) {
        this.freeSits = freeSits;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }



}

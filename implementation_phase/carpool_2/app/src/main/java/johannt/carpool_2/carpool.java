package johannt.carpool_2;

public class carpool {

    private String carpoolId;
    private String driverId;
    private int numfreeplace;
    private int participation;
    private Itinerary itinerary;


    public carpool() {
    }

    public carpool(String carpoolId, String driverId, int numfreeplace, int participation, Itinerary itinerary) {
        this.carpoolId = carpoolId;
        this.driverId = driverId;
        this.numfreeplace = numfreeplace;
        this.participation = participation;
        this.itinerary = itinerary;
    }

    public String getCarpoolId() {
        return carpoolId;
    }

    public void setCarpoolId(String carpoolId) {
        this.carpoolId = carpoolId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public int getNumfreeplace() {
        return numfreeplace;
    }

    public void setNumfreeplace(int numfreeplace) {
        this.numfreeplace = numfreeplace;
    }

    public int getParticipation() {
        return participation;
    }

    public void setParticipation(int participation) {
        this.participation = participation;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }
}

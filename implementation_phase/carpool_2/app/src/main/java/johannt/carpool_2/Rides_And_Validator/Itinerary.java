package johannt.carpool_2.Rides_And_Validator;

import java.util.Calendar;

public class Itinerary {

    private String source;
    private String destination;
    private Calendar srcCalend ;
    private Calendar destCalend ;

    public Itinerary(String source, String destination, Calendar srcCalend, Calendar destCalend) {
        this.source = source;
        this.destination = destination;
        this.srcCalend = srcCalend;
        this.destCalend = destCalend;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Calendar getSrcCalend() {
        return srcCalend;
    }

    public void setSrcCalend(Calendar srcCalend) {
        this.srcCalend = srcCalend;
    }

    public Calendar getDestCalend() {
        return destCalend;
    }

    public void setDestCalend(Calendar destCalend) {
        this.destCalend = destCalend;
    }
}

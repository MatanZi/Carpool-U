package johannt.carpool_2.Rides_And_Validator;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import johannt.carpool_2.R;

public class RideHistoryAdapter extends ArrayAdapter<Carpool> {

    private Activity context;
    private List<Carpool> carpoolList;
    private View listview;
    private TextView Date , From, To , startTime , endTime , price;
    private Carpool carpool;

    public RideHistoryAdapter(Activity context, List<Carpool> carpoolList) {
        super(context, R.layout.ride_history_list_view, carpoolList);
        this.context = context;
        this.carpoolList = carpoolList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        listview = inflater.inflate(R.layout.ride_list_view,null,true);

        Date = listview.findViewById(R.id.textViewFullName);
        startTime = listview.findViewById(R.id.textViewStartTimeHistory);
        endTime = listview.findViewById(R.id.textViewEndTimeHistory);
        From = listview.findViewById(R.id.textViewFreeSits);
        To = listview.findViewById(R.id.textViewPhoneNumber);
        price = listview.findViewById(R.id.textViewPriceHistory);

        carpool = carpoolList.get(position);
        Date.setText("Date: "+ carpool.getDate());
        startTime.setText("Start Time: "+ carpool.getStartTime());
        endTime.setText("End Time: "+ carpool.getEndTime());
        From.setText("From: "+ carpool.getSrc());
        To.setText("To: "+ carpool.getDst());
        price.setText("Price Taken:"+carpool.getPrice());

        return listview;
    }

}

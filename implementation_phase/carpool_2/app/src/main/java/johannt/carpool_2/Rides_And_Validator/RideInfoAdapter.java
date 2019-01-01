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

public class RideInfoAdapter extends ArrayAdapter<Carpool> {

    private Activity context;
    private List<Carpool> carpoolList;
    private View listview;
    private  TextView fullName , freeSits, phoneNumber;
    private Carpool carpool;

    public RideInfoAdapter(Activity context, List<Carpool> carpoolList) {
        super(context, R.layout.ride_list_view, carpoolList);
        this.context = context;
        this.carpoolList = carpoolList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        listview = inflater.inflate(R.layout.ride_list_view,null,true);

        TextView name = listview.findViewById(R.id.driverName);
        TextView src = listview.findViewById(R.id.textViewSource);
        TextView dst = listview.findViewById(R.id.textViewDest);
        TextView date = listview.findViewById(R.id.textViewDate);
        TextView hoursrc = listview.findViewById(R.id.textViewHoursrc);
        TextView hourdst = listview.findViewById(R.id.textViewHourdst);
        TextView freeplace = listview.findViewById(R.id.textViewNumFreePlaces);

        carpool = carpoolList.get(position);

        name.setText(carpool.getFirstName());
        src.setText(carpool.getSrc());
        dst.setText(carpool.getDst());
        date.setText(carpool.getDate());
        hoursrc.setText(carpool.getStartTime());
        hourdst.setText(carpool.getEndTime());
        freeplace.setText(carpool.getFreeSits());

        return listview;
    }
}

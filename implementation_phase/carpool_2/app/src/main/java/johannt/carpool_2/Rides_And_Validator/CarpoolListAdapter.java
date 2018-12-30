package johannt.carpool_2.Rides_And_Validator;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import johannt.carpool_2.R;

public class CarpoolListAdapter extends ArrayAdapter<Carpool> {

    private Activity context;
     List<Carpool> carpools;
     private View listview;
     private Carpool carpool;


    public CarpoolListAdapter(@NonNull Activity context, List<Carpool> carpools) {
        super(context, R.layout.carpool_list, carpools);
        this.carpools = carpools;
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        listview = inflater.inflate(R.layout.carpool_list, null, true);

        TextView src = (TextView) listview.findViewById(R.id.textViewSource);
        TextView dst = (TextView) listview.findViewById(R.id.textViewDest);
        TextView date = (TextView) listview.findViewById(R.id.textViewDate);
        TextView hoursrc = (TextView) listview.findViewById(R.id.textViewHoursrc);
        TextView hourdst = (TextView) listview.findViewById(R.id.textViewHourdst);
        TextView freeplace = (TextView) listview.findViewById(R.id.textViewNumFreePlaces);


         carpool = carpools.get(position);
        src.setText(carpool.getSrc());
        dst.setText(carpool.getDst());
        date.setText(carpool.getDate());
        hoursrc.setText(carpool.getStartTime());
        hourdst.setText(carpool.getEndTime());
        freeplace.setText(carpool.getFreeSits());

        return listview;
    }


}

package johannt.carpool_2.Rides_And_Validator;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import johannt.carpool_2.R;

public class RideInfoAdapter extends ArrayAdapter<Carpool> {

    private Activity context;
    private ArrayList<Carpool> carpoolList;
    private View listview;
    private  TextView fullName , freeSits, phoneNumber;
    private Carpool carpool;

    public RideInfoAdapter(Activity context, ArrayList<Carpool> carpoolList) {
        super(context, R.layout.ride_list_view, carpoolList);
        this.context = context;
        carpoolList = carpoolList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        listview = inflater.inflate(R.layout.ride_list_view,null,true);

        fullName = (TextView)listview.findViewById(R.id.textViewFullName);
        freeSits = (TextView)listview.findViewById(R.id.textViewFreeSits);
        phoneNumber = (TextView)listview.findViewById(R.id.textViewPhoneNumber);

        carpool = carpoolList.get(position);
        fullName.setText(carpool.getFirstName() +" "+ carpool.getLastName());
        freeSits.setText(carpool.getFreeSits());
        phoneNumber.setText(carpool.getPhoneNumber());

        return listview;
    }
}

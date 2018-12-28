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

        fullName = listview.findViewById(R.id.textViewFullName);
        freeSits = listview.findViewById(R.id.textViewFreeSits);
        phoneNumber = listview.findViewById(R.id.textViewPhoneNumber);

        carpool = carpoolList.get(position);
        fullName.setText("Full name: "+ carpool.getFirstName() +" "+ carpool.getLastName());
        freeSits.setText("Free sits: "+ carpool.getFreeSits());
        phoneNumber.setText("Phone Number: "+ carpool.getPhoneNumber());

        return listview;
    }
}

package johannt.carpool_2.Firebase_Messaging;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import johannt.carpool_2.R;

public class chatService extends AppCompatActivity {


    private FirebaseListAdapter<chatMessage> adapter;
    private RelativeLayout activity_chatService;
    private FloatingActionButton sendBtn;
    private TextView messageText , messageUser, messageTime;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_service);

        activity_chatService = (RelativeLayout)findViewById(R.id.activity_chat_service);

        sendBtn = (FloatingActionButton)findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = (EditText)findViewById(R.id.input_message);
                FirebaseDatabase.getInstance().getReference("Messages").push().setValue(new chatMessage(input.getText().toString() ,
                        FirebaseAuth.getInstance().getCurrentUser().getDisplayName()));
                input.setText("");
            }
        });

    //    displayChatMessage();
    }

//    private void displayChatMessage() {
//        ListView listViewMessage = (ListView)findViewById(R.id.list_of_messages);
//        adapter = new FirebaseListAdapter<chatMessage>(this,chatMessage.class,R.layout.list_info_message,FirebaseDatabase.getInstance().getReference("Messages")){
//            @Override
//            protected void populateView(View v,  chatMessage model, int position) {
//
//                messageText = (TextView)v.findViewById(R.id.message_text);
//                messageUser = (TextView)v.findViewById(R.id.message_user);
//                messageTime = (TextView)v.findViewById(R.id.message_time);
//
//                messageText.setText(model.getMessageText());
//                messageUser.setText(model.getMessageUser());
//                messageTime.setText(android.text.format.DateFormat.format("dd-mm-yyyy (HH:mm:ss)" , model.getMessageTime()));
//            }
//        };
//        listViewMessage.setAdapter(adapter);
//
//    }


}

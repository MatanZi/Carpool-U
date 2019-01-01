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
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import johannt.carpool_2.R;

public class chatService extends AppCompatActivity {


    private FirebaseListAdapter<chatMessage> adapter;
    private RelativeLayout activity_chatService;
    private FloatingActionButton sendBtn;
    private FirebaseListOptions<chatMessage> options;
    private Query query;
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

        activity_chatService = findViewById(R.id.activity_chat_service);

        sendBtn = findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = findViewById(R.id.input_message);
                FirebaseDatabase.getInstance().getReference("ChatService").push().setValue(new chatMessage(input.getText().toString() ,
                        FirebaseAuth.getInstance().getCurrentUser().getDisplayName()));
                input.setText("");
            }
        });

        displayChatMessage();
    }

    private void displayChatMessage() {
        ListView listViewMessage = findViewById(R.id.list_of_messages);

        query = FirebaseDatabase.getInstance().getReference("ChatService");
        options = new FirebaseListOptions.Builder<chatMessage>()
                .setQuery(query, chatMessage.class)
                .setLayout(R.layout.list_info_message)
                .build();

        adapter = new FirebaseListAdapter<chatMessage>(options){
            @Override
            protected void populateView(View v,  chatMessage model, int position) {

                messageText = v.findViewById(R.id.message_text);
                messageUser = v.findViewById(R.id.message_user);
                messageTime = v.findViewById(R.id.message_time);

                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(android.text.format.DateFormat.format("dd-mm-yyyy (HH:mm:ss)" , model.getMessageTime()));
            }
        };
        listViewMessage.setAdapter(adapter);

    }
}

package com.mhealth.nishauri.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mhealth.nishauri.Models.BotChatMessage;
import com.mhealth.nishauri.Models.ChatBotQuestion;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.adapters.BotChatAdapter;
import com.mhealth.nishauri.interfaces.APIInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NishauriChatBotActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText editTextMessage;
    private ImageButton buttonSend;
    private BotChatAdapter chatAdapter;
    private LinearLayout typingIndicator;
    private TextView typingText;
    private Handler handler;
    private List<BotChatMessage> messages = new ArrayList<>();
    private Toolbar toolbar;
    private long lastInteractionTime = 0;

    /**
     * Initializes the activity layout and components.
     *
     * @param savedInstanceState Instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nishauri_chat_bot);

        toolbar = findViewById(R.id.toolbarMsg);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        recyclerView = findViewById(R.id.recyclerView);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);
        typingIndicator = findViewById(R.id.typingIndicator);
        typingText = findViewById(R.id.typingText);

        chatAdapter = new BotChatAdapter(messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chatAdapter);

        handler = new Handler(Looper.getMainLooper());

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editTextMessage.getText().toString().trim();
                if (!message.isEmpty()) {
                    sendMessage(message);
                    editTextMessage.setText("");
                }
            }
        });
    }

    /**
     * Sends a message to the chat bot and processes the response.
     *
     * @param message The message to send
     */
    private void sendMessage(String message) {
        // Hide the EditText and Button
        editTextMessage.setVisibility(View.GONE);
        buttonSend.setVisibility(View.GONE);

        // Add the sent message to the list
        chatAdapter.addMessage(new BotChatMessage(message, true));
        recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
        simulateTyping();

        // Create Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ushauriapi.kenyahmis.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Prepare request body
        APIInterface service = retrofit.create(APIInterface.class);
        ChatBotQuestion requestBody = new ChatBotQuestion(message);

        // Make POST request
        Call<BotChatMessage> call = service.sendMessage(requestBody);
        call.enqueue(new Callback<BotChatMessage>() {
            @Override
            public void onResponse(Call<BotChatMessage> call, Response<BotChatMessage> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Receive the response message and add it to the list
                    receiveMessage(response.body().getMsg());
                }
                typingIndicator.setVisibility(View.GONE);

                // Show the EditText and Button again
                editTextMessage.setVisibility(View.VISIBLE);
                buttonSend.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<BotChatMessage> call, Throwable t) {
                // Handle failure
                typingIndicator.setVisibility(View.GONE);

                // Show the EditText and Button again
                editTextMessage.setVisibility(View.VISIBLE);
                buttonSend.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * Simulates typing indicator animation.
     */
    private void simulateTyping() {
        typingIndicator.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.typing_animation);
        typingText.startAnimation(animation);
    }

    /**
     * Receives a message from the chat bot and adds it to the chat.
     *
     * @param message The message received
     */
    private void receiveMessage(String message) {
        // Add the received message to the list
        chatAdapter.addMessage(new BotChatMessage(message, false));
        recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
    }
}

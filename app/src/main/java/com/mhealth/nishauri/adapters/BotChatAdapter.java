package com.mhealth.nishauri.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mhealth.nishauri.Models.BotChatMessage;
import com.mhealth.nishauri.R;

import java.util.List;

/**
 * Adapter class for displaying chat messages in a RecyclerView.
 */
public class BotChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BotChatMessage> messages;

    private static final int MESSAGE_SENT = 0;
    private static final int MESSAGE_RECEIVED = 1;

    /**
     * Constructor to initialize the BotChatAdapter with a list of messages.
     *
     * @param messages The list of chat messages
     */
    public BotChatAdapter(List<BotChatMessage> messages) {
        this.messages = messages;
    }

    /**
     * Inflates the layout for each message item based on its view type.
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }
    }

    /**
     * Binds the message data to the appropriate ViewHolder based on its view type.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BotChatMessage message = messages.get(position);

        switch (holder.getItemViewType()) {
            case MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
                break;
        }
    }

    /**
     * Returns the total number of messages in the list.
     */
    @Override
    public int getItemCount() {
        return messages.size();
    }

    /**
     * Returns the view type of the message at the specified position.
     */
    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).isSent()) {
            return MESSAGE_SENT;
        } else {
            return MESSAGE_RECEIVED;
        }
    }

    /**
     * Adds a new message to the list and notifies the adapter of the change.
     */
    public void addMessage(BotChatMessage message) {
        messages.add(message);
        notifyItemInserted(messages.size() - 1);
    }

    /**
     * ViewHolder for sent messages.
     */
    private static class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        SentMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_message_body_sent);
        }

        void bind(BotChatMessage message) {
            messageText.setText(message.getMsg());
        }
    }

    /**
     * ViewHolder for received messages.
     */
    private static class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_message_body);
        }

        void bind(BotChatMessage message) {
            messageText.setText(message.getMsg());
        }
    }
}

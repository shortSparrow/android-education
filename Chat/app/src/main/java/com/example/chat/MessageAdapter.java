package com.example.chat;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<Message> {
    public MessageAdapter(@NonNull Context context, int resource, List<Message> messageList) {
        super(context, resource, messageList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.message_item, parent, false);
        }
            ImageView messagePhoto = convertView.findViewById(R.id.messagePhoto);
            TextView senderName =  convertView.findViewById(R.id.message_sender);
            TextView messageText =  convertView.findViewById(R.id.message_text);
            TextView messageCreatedAt =  convertView.findViewById(R.id.message_created_at);

            Message message = getItem(position);

            Log.d("message_1", message.getText() + "");
        Log.d("message_2", message.getImageUrl() + "");

            boolean isText = message.getImageUrl() == null;
            if (isText) {
                messagePhoto.setVisibility(View.GONE);
                messageText.setVisibility(View.VISIBLE);
                messageText.setText(message.getText());
            } else {
                messageText.setVisibility(View.GONE);
                messagePhoto.setVisibility(View.VISIBLE);
                Glide.with(messagePhoto.getContext()).load(message.getImageUrl()).into(messagePhoto);
            }
            senderName.setText(message.getSenderName());
            messageCreatedAt.setText(message.getCreatedAt());

        return convertView;
    }
}

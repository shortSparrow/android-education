package com.example.chat;

import android.app.Activity;
import android.view.LayoutInflater;
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
   private List<Message> messageList;
   private Activity activity;

    public MessageAdapter(@NonNull Activity context, int resource, List<Message> messageList) {
        super(context, resource, messageList);
        this.messageList = messageList;
        this.activity = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        Message message = getItem(position);
        int layoutResource = 0;
        int viewType = getItemViewType(position); // here we get our flag from override method getItemViewType

        if (viewType == 0) {
            layoutResource = R.layout.my_message_item;
        } else {
            layoutResource = R.layout.someone_message_item;
        }

        if(convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = layoutInflater.inflate(layoutResource, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        boolean isText = message.getImageUrl() == null;
        if (isText) {
            viewHolder.messageTextView.setVisibility(View.VISIBLE);
            viewHolder.messageTextView.setText(message.getText());
            viewHolder.avatarImageView.setVisibility(View.GONE);
        } else {
            viewHolder.messageTextView.setVisibility(View.GONE);
            viewHolder.avatarImageView.setVisibility(View.VISIBLE);
            Glide.with(viewHolder.avatarImageView.getContext()).load(message.getImageUrl()).into(viewHolder.avatarImageView);
        }

        viewHolder.messageCreatedTextView.setText(message.getCreatedAt());

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        int flag;
        Message message = messageList.get(position);

        if (message.getIsMine()) {
            flag = 0;
        } else {
            flag = 1;
        }

        return flag;
    }

    @Override
    public int getViewTypeCount() {
        return 2; // we have 2 message types
    }

    private class ViewHolder {
        private TextView messageTextView;
        private TextView messageCreatedTextView;
        private ImageView avatarImageView;

        public ViewHolder(View view) {
            messageTextView = view.findViewById(R.id.message_text_view);
            avatarImageView = view.findViewById(R.id.message_avatar_photo);
            messageCreatedTextView = view.findViewById(R.id.message_created_at);
        }
    }
}

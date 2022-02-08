package com.example.chat;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private ListView messageListView;
    private MessageAdapter messageAdapter;
    private ProgressBar progressBar;
    private Button sendMessageButton;
    private ImageView sendPhotoButton;
    private EditText messageEditText;

    private String userName;
    private String recipientUserId;
    private String chatName;

    private FirebaseDatabase database;
    private DatabaseReference databaseMessageReference;
    private DatabaseReference databaseUserReference;
    private ChildEventListener userChildEventListener;
    private ChildEventListener messageChildEventListener;

    private FirebaseStorage storage;
    private StorageReference chatImagesStorageReference;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        recipientUserId = intent.getStringExtra("recipientUserId");
        chatName = intent.getStringExtra("chatName");

        setTitle("Chat with " + chatName);

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();

        databaseMessageReference = database.getReference("message");
        databaseUserReference = database.getReference("users");
        chatImagesStorageReference = storage.getReference().child("chat_images");


        userName = "unknown";
        messageListView = findViewById(R.id.message_list);
        List<Message> messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(this, R.layout.message_item, messageList);
        messageListView.setAdapter(messageAdapter);

        progressBar = findViewById(R.id.progress_load_message_list);
        sendMessageButton = findViewById(R.id.send_message);
        sendPhotoButton = findViewById(R.id.send_photo_button);
        messageEditText = findViewById(R.id.message_edit_text);

        progressBar.setVisibility(ProgressBar.INVISIBLE);


        userChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                Log.d("name", user.getName());
                if (user.getId().equals(FirebaseAuth.getInstance().getUid())) {
                    userName = user.getName();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        databaseUserReference.addChildEventListener(userChildEventListener);

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageEditText.getText().toString().trim();

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String millisInString = dateFormat.format(new Date());

                Message message = new Message();
                message.setText(messageText);
                message.setSenderName(userName);
                message.setSender(auth.getCurrentUser().getUid());
                message.setRecipient(recipientUserId);
                message.setImageUrl(null);
                message.setCreatedAt(millisInString);
                databaseMessageReference.push().setValue(message);
                messageEditText.setText("");
            }
        });


        sendPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");
            }
        });

        messageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    sendMessageButton.setEnabled(true);
                } else {
                    sendMessageButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        messageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(500)}); // message has max length 500 characters

        messageChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message message = snapshot.getValue(Message.class);

                if ((message.getSender().equals(auth.getCurrentUser().getUid()) && message.getRecipient().equals(recipientUserId))) {
                    message.setIsMine(true);
                    messageAdapter.add(message);
                } else if (message.getSender().equals(recipientUserId) && message.getRecipient().equals(auth.getCurrentUser().getUid())){
                    message.setIsMine(false);
                    messageAdapter.add(message);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        databaseMessageReference.addChildEventListener(messageChildEventListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out:
                FirebaseAuth.getInstance().signOut();


                // sign out if user sign in by google auth
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(String.valueOf(R.string.firebase_server_client_id))
                        .requestEmail()
                        .build();

                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
                mGoogleSignInClient.signOut();


                startActivity(new Intent(ChatActivity.this, LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    Log.d("URL", uri.toString()); // content://com.android.providers.downloads.documents/document/raw%3A%2Fstorage%2Femulated%2F0%2FDownload%2FCategoryProductsContainer.png
                    Log.d("getLastPathSegment", uri.getLastPathSegment()); // raw:/storage/emulated/0/Download/CategoryProductsContainer.png

                    uploadImage(uri);
                }
            });


    public void uploadImage(Uri uri) {
        StorageReference imageReference = chatImagesStorageReference.child(uri.getLastPathSegment());
        UploadTask uploadTask = imageReference.putFile(uri);


        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return imageReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String millisInString = dateFormat.format(new Date());

                    Message message = new Message();
                    message.setImageUrl(downloadUri.toString());
                    message.setSender(auth.getCurrentUser().getUid());
                    message.setRecipient(recipientUserId);
                    message.setSenderName(userName);
                    message.setCreatedAt(millisInString);
                    databaseMessageReference.push().setValue(message);
                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }
}
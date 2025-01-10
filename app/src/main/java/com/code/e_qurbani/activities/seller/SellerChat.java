package com.code.e_qurbani.activities.seller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.code.e_qurbani.R;
import com.code.e_qurbani.activities.FcmNotifications.FcmNotificationSender;
import com.code.e_qurbani.activities.FcmNotifications.NotificationTemplate;
import com.code.e_qurbani.activities.Message;
import com.code.e_qurbani.activities.MessageAdapter;
import com.code.e_qurbani.chat.ChatMessage;
import com.code.e_qurbani.chat.ChatMessageAdapter;
import com.code.e_qurbani.databinding.ActivityChatScreenBinding;
import com.code.e_qurbani.databinding.ActivitySellerChatBinding;
import com.code.e_qurbani.firebase.entity.Seller;
import com.code.e_qurbani.firebase.entity.User;
import com.code.e_qurbani.utils.Constant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SellerChat extends AppCompatActivity {

    private ActivitySellerChatBinding binding;

    private Seller seller;

    private List<ChatMessage> messageList = new ArrayList<>();

    private ChatMessageAdapter chatMessageAdapter;
    String senderEmail;
    String receiverEmail;
    private DatabaseReference reference2;
    String senderRoom;
    String receiverRoom;

    private DatabaseReference reference;

    private FirebaseDatabase database;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser = mAuth.getCurrentUser();

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySellerChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        seller = getIntent().getParcelableExtra(Constant.SELLER);

        if (seller != null) {
            receiverEmail = encodeEmail(seller.getEmail());
            Toast.makeText(this, receiverEmail + "ajdfkjaldfjadfjasksdj", Toast.LENGTH_SHORT).show();
            binding.tvName.setText(seller.getFullName());
        } else {
            Intent intent = getIntent();
            user = intent.getStringExtra("key");
            receiverEmail = user;
            binding.tvName.setText(user);
        }

        if (currentUser != null) {
            senderEmail = encodeEmail(currentUser.getEmail().toString());
            Toast.makeText(this, senderEmail, Toast.LENGTH_SHORT).show();
        }

        senderRoom = senderEmail + "_" + receiverEmail;
        receiverRoom = receiverEmail + "_" + senderEmail;

        Log.i("Sender Room", senderRoom);

        database = FirebaseDatabase.getInstance();

        reference = database.getReference().child("chats");
        reference2 = database.getReference("chats").child("Information for unread messages").child(receiverRoom);

        getChat();
        buildRecyclerView();

        binding.sendBtn.setOnClickListener(view -> {
            ChatMessage chatMessage = sendMessage();
            findReceiverToken(chatMessage);
            binding.messageEditText.setText("");
        });
    }

    private ChatMessage sendMessage() {
        ChatMessage chatMessage = null;
        if (TextUtils.isEmpty(binding.messageEditText.getText().toString())) {
            Toast.makeText(this, "enter some text", Toast.LENGTH_SHORT).show();
        } else {
            String messageText = binding.messageEditText.getText().toString();

            chatMessage = new ChatMessage(messageText, senderEmail, receiverEmail);

            Map<String, String> map = new HashMap<String, String>();

            map.put("message", messageText);
            map.put("senderEmail", senderEmail);
            map.put("receiverEmail", receiverEmail);

            reference.child("messages").child(senderRoom).push().setValue(map);
            reference.child("messages").child(receiverRoom).push().setValue(map);

            reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    GenericTypeIndicator<Map<String, Object>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Object>>() {
                    };

                    Map<String, Object> map1 = snapshot.getValue(genericTypeIndicator);

                    reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            GenericTypeIndicator<Map<String, Object>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Object>>() {
                            };

                            Map<String, Object> map1 = snapshot.getValue(genericTypeIndicator);

                            if (map1 != null) {
                                String status = (String) map1.get("status");
                                if (map1.containsKey("count")) {
                                    if (!status.equals("online")) {
                                        String count = (String) map1.get("count");
                                        int val = Integer.parseInt(count);
                                        map.put("count", String.valueOf(++val));
                                        map.put("status", "offline");
                                        reference2.setValue(map);
                                        Log.d("VALUE", "YES" + val);
                                    } else {
                                        String count = (String) map1.get("count");
                                        int val = Integer.parseInt(count);
                                        map.put("count", String.valueOf(val++));
                                        map.put("status", "online");
                                        reference2.setValue(map);
                                        Log.d("VALUE", "No");
                                    }
                                } else {
                                    map.put("count", String.valueOf(1));
                                    map.put("status", "offline");
                                    reference2.setValue(map);
                                    Log.d("VALUE", "No");
                                }
                            } else {
                                map.put("count", String.valueOf(1));
                                map.put("status", "offline");
                                reference2.setValue(map);
                                Log.d("VALUE", "first time");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        return chatMessage;
    }

    private String encodeEmail(String email) {
        int atIndex = email.indexOf('@');

        String username = null;

        if (atIndex != -1) { // Check if "@" exists in the email
            username = email.substring(0, atIndex);
            System.out.println("Username: " + username);
        } else {
            System.out.println("Invalid email format.");
        }

        return username;
    }

    private void buildRecyclerView() {
        chatMessageAdapter = new ChatMessageAdapter(messageList);
        binding.recyclerView.setAdapter(chatMessageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(llm);
    }

    void addToChat(String message, String sentBy) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new ChatMessage(message, sentBy));
                chatMessageAdapter.notifyDataSetChanged();
                binding.recyclerView.smoothScrollToPosition(chatMessageAdapter.getItemCount());
            }
        });
    }

    void sendNotification(String token, ChatMessage chatMessage) {
        if (chatMessage != null) {
            new FcmNotificationSender().send(String.format(NotificationTemplate.message, token, chatMessage.getText(), chatMessage.getSenderEmail(), "Chat Screen", chatMessage.getSenderEmail()), new Callback() {

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    SellerChat.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (response.code() == 200) {
                            }
                        }
                    });
                }

                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                }
            });
        }
    }

    void findReceiverToken(ChatMessage chatMessage) {
        if (chatMessage != null && receiverEmail != null) {
            FirebaseDatabase.getInstance().getReference().child("user tokens").child(receiverEmail).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String token = snapshot.getValue(String.class);

                    if (token != null) {
                        sendNotification(token, chatMessage);
                    } else {
                        Log.d("Token", "not found");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }

    void getChat() {
        reference.child("messages").child(senderRoom).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                GenericTypeIndicator<Map<String, Object>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Object>>() {
                };

                Map<String, Object> data = dataSnapshot.getValue(genericTypeIndicator);
                String messageText = (String) data.get("message");
                Log.i("checking messages", messageText);

                addToChat(messageText, data.get("senderEmail").toString());
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
        });
    }
}

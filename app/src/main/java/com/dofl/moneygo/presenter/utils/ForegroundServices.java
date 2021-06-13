package com.dofl.moneygo.presenter.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.dofl.moneygo.R;
import com.dofl.moneygo.view.activity.MenuActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class ForegroundServices extends Service {
    private final String CHANNEL_ID = "1999";
    private final String CHANNEL_1_ID = "2000";
    private NotificationManagerCompat notificationManagerCompat;
    private int notificationId = 2;
    private DatabaseReference databaseReference;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String accountId = intent.getStringExtra("accountId");
        String username = intent.getStringExtra("username");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MenuActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Notification Service")
                .setContentText("Người dùng: " + username)
                .setSmallIcon(R.drawable.icon_launcher)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        notificationManagerCompat = NotificationManagerCompat.from(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Account").child(accountId).child("Notification")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot,
                                             @Nullable String previousChildName) {
                        notificationId++;
                        Notification notification;
                        if (Objects.requireNonNull(snapshot.getKey()).contains("added")) {
                            notification = new NotificationCompat.
                                    Builder(getApplicationContext(), CHANNEL_1_ID)
                                    .setSmallIcon(R.drawable.icon_launcher)
                                    .setContentTitle("Thông báo: Có bản ghi mới!") //Thông báo
                                    .setContentText(Objects.requireNonNull(snapshot
                                            .getValue()).toString())
                                    .setStyle(new NotificationCompat.BigTextStyle())
                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                    .build();
                        } else if (Objects.requireNonNull(snapshot.getKey()).contains("changed")) {
                            notification = new NotificationCompat.
                                    Builder(getApplicationContext(), CHANNEL_1_ID)
                                    .setSmallIcon(R.drawable.icon_launcher)
                                    .setContentTitle("Thông báo: Có bản ghi bị thay đổi!") //Thông báo
                                    .setContentText(Objects.requireNonNull(snapshot
                                            .getValue()).toString())
                                    .setStyle(new NotificationCompat.BigTextStyle())
                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                    .build();
                        } else if (Objects.requireNonNull(snapshot.getKey()).contains("removed")) {
                            notification = new NotificationCompat.
                                    Builder(getApplicationContext(), CHANNEL_1_ID)
                                    .setSmallIcon(R.drawable.icon_launcher)
                                    .setContentTitle("Thông báo: Có bản ghi bị xóa!") //Thông báo
                                    .setContentText(Objects.requireNonNull(snapshot
                                            .getValue()).toString())
                                    .setStyle(new NotificationCompat.BigTextStyle())
                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                    .build();
                        } else if (Objects.requireNonNull(snapshot.getKey())
                                .contains("Settlement")) {
                            notification = new NotificationCompat.
                                    Builder(getApplicationContext(), CHANNEL_1_ID)
                                    .setSmallIcon(R.drawable.icon_launcher)
                                    .setContentTitle("Thông báo: Nộp tiền!") //Thông báo
                                    .setContentText(Objects.requireNonNull(snapshot
                                            .getValue()).toString())
                                    .setStyle(new NotificationCompat.BigTextStyle())
                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                    .build();
                        } else {
                            notification = new NotificationCompat.
                                    Builder(getApplicationContext(), CHANNEL_1_ID)
                                    .setSmallIcon(R.drawable.icon_launcher)
                                    .setContentTitle("Thông báo: Thanh toán!") //Thông báo
                                    .setContentText(Objects.requireNonNull(snapshot
                                            .getValue()).toString())
                                    .setStyle(new NotificationCompat.BigTextStyle())
                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                    .build();
                        }
                        notificationManagerCompat.notify(notificationId, notification);
                        databaseReference.child("Account").child(accountId).child("Notification")
                                .child(Objects.requireNonNull(snapshot.getKey())).removeValue();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot,
                                               @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot,
                                             @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Money Go!",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Notification",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
            manager.createNotificationChannel(channel1);
        }
    }
}

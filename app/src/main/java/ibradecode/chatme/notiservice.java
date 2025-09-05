package ibradecode.chatme;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class notiservice extends Service {

    private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
    private DatabaseReference Database = _firebase.getReference("pending");
    private int num = 0;
    private ChildEventListener _Database_child_listener;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _CreateChannel();
        startForeground(1, createSilentNotification()); // Silent foreground notification
        startDatabaseListener();
    }

    private void startDatabaseListener() {
        com.google.firebase.FirebaseApp.initializeApp(this);

        _Database_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot _param1, String _param2) {
                HashMap<String, Object> _childValue = (HashMap<String, Object>) _param1.getValue();

                if (_childValue != null) {
                    String messageKey = _param1.getKey();

                    if (_childValue.containsKey("system")) {
                        String systemTitle = _childValue.containsKey("systemTitle") ? _childValue.get("systemTitle").toString() : "System Notification";
                        String systemMessage = _childValue.containsKey("systemMessage") ? _childValue.get("systemMessage").toString() : "No additional information available.";
                        buildSystemNotification(systemTitle, systemMessage, messageKey);
                    } else if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        String currentUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        if (_childValue.containsKey("re") && _childValue.get("re").equals(currentUserUid)) {
                            if (!_childValue.containsKey("status") || !_childValue.get("status").equals("seen")) {
                                final String fireTitle = _childValue.containsKey("sender") ? _childValue.get("sender").toString() : "Message currently unavailable";
                                final String fireMessage = _childValue.containsKey("p") ? _childValue.get("p").toString() : "user not found";
                                final String dpUrl = _childValue.containsKey("dp") ? _childValue.get("dp").toString() : null;
                                final String senderid = _childValue.containsKey("senderid") ? _childValue.get("senderid").toString() : null;
                                final String finalMessageKey = messageKey;

                                if (dpUrl != null) {
                                    Glide.with(notiservice.this)
                                            .asBitmap()
                                            .load(dpUrl)
                                            .circleCrop()
                                            .into(new CustomTarget<Bitmap>() {
                                                @Override
                                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                                    buildCustomNotification(fireTitle, fireMessage, resource, finalMessageKey, senderid);
                                                }

                                                @Override
                                                public void onLoadCleared(@Nullable Drawable placeholder) {
                                                }
                                            });
                                } else {
                                    buildCustomNotification(fireTitle, fireMessage, null, finalMessageKey, senderid);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot _param1, String _param2) {
            }

            @Override
            public void onChildMoved(DataSnapshot _param1, String _param2) {
            }

            @Override
            public void onChildRemoved(DataSnapshot _param1) {
            }

            @Override
            public void onCancelled(DatabaseError _param1) {
            }
        };

        Database.addChildEventListener(_Database_child_listener);
    }

    private void buildCustomNotification(String title, String message, Bitmap dp, String messageKey, String senderid) {
        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.notification_layout);

        notificationLayout.setTextViewText(R.id.notification_title, title);
        notificationLayout.setTextViewText(R.id.notification_message, message);
        if (dp != null) {
            notificationLayout.setImageViewBitmap(R.id.notification_dp, dp);
        } else {
            notificationLayout.setImageViewResource(R.id.notification_dp, R.drawable.default_dp);
        }

        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("user2", senderid);
        intent.putExtra("senderid", senderid);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                num,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "id_1")
                .setSmallIcon(R.drawable.logo)
                .setCustomContentView(notificationLayout)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(num++, builder.build());

        Database.child(messageKey).removeValue();
    }

    private void buildSystemNotification(String title, String message, String messageKey) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "id_1")
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.logo)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(num++, builder.build());

        Database.child(messageKey).removeValue();
    }

    private Notification createSilentNotification() {
        return new NotificationCompat.Builder(this, "id_1")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("")
                .setContentText("")
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setSilent(true)
                .setOngoing(true)
                .build();
    }

    private void _CreateChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "id_1",
                    "Background Service",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Service Channel");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        setAlarmForRestart();
        super.onTaskRemoved(rootIntent);
    }

    private void setAlarmForRestart() {
        Intent intent = new Intent(this, RestartBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            long triggerAtMillis = System.currentTimeMillis() + 5000;
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
        }
    }
}

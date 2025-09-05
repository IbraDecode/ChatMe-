package ibradecode.chatme;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FriendRequestService extends Service {

    private static final String CHANNEL_ID = "friend_requests";
    private DatabaseReference friendRequestRef;
    private String myUid;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();

        // Get current user's UID
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            myUid = auth.getCurrentUser().getUid();
            listenForFriendRequests();
        } else {
            stopSelf(); // Stop service if the user is not logged in
        }
    }

    private void listenForFriendRequests() {
        // Reference to FriendRequests/{myUid}
        friendRequestRef = FirebaseDatabase.getInstance().getReference("FriendRequests").child(myUid);

        friendRequestRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChildName) {
                // A new friend request is added
                if (snapshot.exists()) {
                    String senderId = snapshot.getKey(); // The sender's UID
                    String status = snapshot.child("status").getValue(String.class);
                    String username = snapshot.child("username").getValue(String.class);

                    if ("pending".equals(status)) {
                        sendNotification(senderId, username);
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, String previousChildName) {
                // Handle updates to existing requests if needed
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // Handle request removal if needed
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, String previousChildName) {
                // Handle request reordering if needed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database errors
            }
        });
    }

    private void sendNotification(String senderId, String username) {
        // Intent to open UserProfileActivity
        Intent intent = new Intent(this, UserprofileActivity.class);
        intent.putExtra("user2", senderId);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Build notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo) // Replace with your app icon
                .setContentTitle("New friend request from " + username)
                .setContentText("Tap to view profile")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Show notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(senderId.hashCode(), builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Friend Request Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null; // Not a bound service
    }
}

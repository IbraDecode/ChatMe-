package ibradecode.chatme;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.*;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.color.MaterialColors;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import de.hdodenhof.circleimageview.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;

public class UserprofileActivity extends AppCompatActivity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private String uuid = "";
	private HashMap<String, Object> get = new HashMap<>();
	private String Myuuid = "";
	private String nldp = "";
	private String fontName = "";
	private String typeace = "";
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private CircleImageView circleimageview1;
	private TextView textview1;
	private TextView textview2;
	private Button button1;
	
	private DatabaseReference users = _firebase.getReference("users");
	private ChildEventListener _users_child_listener;
	private FirebaseAuth fauth;
	private OnCompleteListener<AuthResult> _fauth_create_user_listener;
	private OnCompleteListener<AuthResult> _fauth_sign_in_listener;
	private OnCompleteListener<Void> _fauth_reset_password_listener;
	private OnCompleteListener<Void> fauth_updateEmailListener;
	private OnCompleteListener<Void> fauth_updatePasswordListener;
	private OnCompleteListener<Void> fauth_emailVerificationSentListener;
	private OnCompleteListener<Void> fauth_deleteUserListener;
	private OnCompleteListener<Void> fauth_updateProfileListener;
	private OnCompleteListener<AuthResult> fauth_phoneAuthListener;
	private OnCompleteListener<AuthResult> fauth_googleSignInListener;
	private SharedPreferences file;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.userprofile);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		linear3 = findViewById(R.id.linear3);
		linear4 = findViewById(R.id.linear4);
		circleimageview1 = findViewById(R.id.circleimageview1);
		textview1 = findViewById(R.id.textview1);
		textview2 = findViewById(R.id.textview2);
		button1 = findViewById(R.id.button1);
		fauth = FirebaseAuth.getInstance();
		file = getSharedPreferences("f", Activity.MODE_PRIVATE);
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				/*

// Get the UIDs (replace with actual logic to retrieve friendUid)
final String friendUid = uuid;  // Replace with the actual friend's UID
final String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

// Firebase reference to friend requests
final DatabaseReference friendRequestRef = FirebaseDatabase.getInstance()
        .getReference("FriendRequests");

// Set the click listener for button1
button1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        // Disable the button to prevent multiple clicks
        button1.setEnabled(false);
        button1.setText("Requesting...");

        // Send friend request
        friendRequestRef.child(friendUid).child(myUid)
            .setValue("pending")
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        // Request sent successfully, update button state
                        button1.setText("Requested");
                    } else {
                        // Handle failure
                        Toast.makeText(
                            v.getContext(),
                            "Failed to send request. Try again.",
                            Toast.LENGTH_SHORT
                        ).show();

                        // Re-enable the button
                        button1.setEnabled(true);
                        button1.setText("Add Friend");
                    }
                }
            });
    }
});
*/
			}
		});
		
		_users_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(uuid)) {
					if (!"null".equals(_childValue.get("dp").toString())) {
						Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("dp").toString())).into(circleimageview1);
					} else {
						circleimageview1.setImageResource(R.drawable.logo);
						get = new HashMap<>();
						get.put("dp", file.getString("nulldp", ""));
						users.child(uuid).updateChildren(get);
						get.clear();
					}
					textview1.setText(_childValue.get("username").toString());
					textview2.setText(" @".concat(_childValue.get("user").toString()));
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(uuid)) {
					if (!"null".equals(_childValue.get("dp").toString())) {
						Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("dp").toString())).into(circleimageview1);
					} else {
						circleimageview1.setImageResource(R.drawable.logo);
					}
					textview1.setText(_childValue.get("username").toString());
					textview2.setText(" @".concat(_childValue.get("user").toString()));
				}
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		users.addChildEventListener(_users_child_listener);
		
		fauth_updateEmailListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_updatePasswordListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_emailVerificationSentListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_deleteUserListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_phoneAuthListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task) {
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		fauth_updateProfileListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_googleSignInListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task) {
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		_fauth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_fauth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_fauth_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				
			}
		};
	}
	
	private void initializeLogic() {
		_changeActivityFont("fn1");
		_updated_nulls();
		_rippleRoundStroke(button1, "#488395", "#ffffff", 25, 0, "#ffffff");
		linear1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)40, 0xFFEFF1FE));
		uuid = getIntent().getStringExtra("user2");
		Myuuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
		/*
// Get the UIDs (replace with actual logic to retrieve friendUid)
final String friendUid = uuid;  // Replace with the actual friend's UID
final String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

// Firebase references
final DatabaseReference friendRequestRef = FirebaseDatabase.getInstance().getReference("FriendRequests");
final DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
final DatabaseReference inboxRef = FirebaseDatabase.getInstance().getReference("inbox");

// Retrieve current user's data (e.g., "dp" and "username")
usersRef.child(myUid).addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists()) {
            // Store dp and username in final variables to avoid scope issues
            final String myDp = dataSnapshot.child("dp").getValue(String.class);
            final String myUsername = dataSnapshot.child("username").getValue(String.class);

            // Check if the friend is already added in the inbox
            inboxRef.child(myUid).child(friendUid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot inboxSnapshot) {
                    if (inboxSnapshot.exists()) {
                        // Friend is already added, update button state
                        button1.setText("Already Friend");
                        button1.setEnabled(false);
                    } else {
                        // Check if a friend request was already sent
                        friendRequestRef.child(friendUid).child(myUid)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot requestSnapshot) {
                                    if (requestSnapshot.exists()) {
                                        // Request already sent, update button state
                                        button1.setText("Requested");
                                        button1.setEnabled(false);
                                    } else {
                                        // Allow sending a friend request
                                        button1.setText("Add Friend");
                                        button1.setEnabled(true);

                                        // Set click listener to send the friend request
                                        button1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                // Disable button and show progress
                                                button1.setEnabled(false);
                                                button1.setText("Requesting...");

                                                // Create the friend request object with "dp" and "username"
                                                Map<String, String> requestData = new HashMap<>();
                                                requestData.put("dp", myDp);  // Now accessible
                                                requestData.put("username", myUsername);  // Now accessible
                                                requestData.put("status", "pending");
                                                requestData.put("id", myUid);
                                                requestData.put("senderId", friendUid);

                                                // Send the friend request
                                                friendRequestRef.child(friendUid).child(myUid)
                                                    .setValue(requestData)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                // Request sent successfully
                                                                button1.setText("Requested");
                                                            } else {
                                                                // Handle failure
                                                                button1.setEnabled(true);
                                                                button1.setText("Add Friend");
                                                            }
                                                        }
                                                    });
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // Handle request check error
                                }
                            });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle inbox check error
                }
            });
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        // Handle error fetching user data
    }
});

*/
		// Firebase references
		final String friendUid = uuid;  // Replace with the actual friend's UID
		final String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
		final DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
		final DatabaseReference inboxRef = FirebaseDatabase.getInstance().getReference("inbox");
		final DatabaseReference friendRequestRef = FirebaseDatabase.getInstance().getReference("FriendRequests");
		
		// Check the user's type
		usersRef.child(friendUid).child("type").addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				if (snapshot.exists()) {
					String userType = snapshot.getValue(String.class);
					if ("group".equals(userType)) {
						// Add group to inbox
						usersRef.child(friendUid).addListenerForSingleValueEvent(new ValueEventListener() {
							@Override
							public void onDataChange(@NonNull DataSnapshot userSnapshot) {
								if (userSnapshot.exists()) {
									String groupDp = userSnapshot.child("dp").getValue(String.class);
									String groupName = userSnapshot.child("username").getValue(String.class);
									
									Map<String, String> groupData = new HashMap<>();
									groupData.put("dp", groupDp != null ? groupDp : "https://cdn-icons-png.flaticon.com/128/12882/12882578.png");
									groupData.put("username", groupName);
									groupData.put("type", "group");
									groupData.put("id", friendUid);
									
									inboxRef.child(myUid).child(friendUid).setValue(groupData)
									.addOnCompleteListener(new OnCompleteListener<Void>() {
										@Override
										public void onComplete(@NonNull Task<Void> task) {
											if (task.isSuccessful()) {
												finish();
												Toast.makeText(getApplicationContext(), "Group added to inbox!", Toast.LENGTH_SHORT).show();
											} else {
												Toast.makeText(getApplicationContext(), "Failed to add group to inbox!", Toast.LENGTH_SHORT).show();
											}
										}
									});
								}
							}
							
							@Override
							public void onCancelled(@NonNull DatabaseError error) {
								Toast.makeText(getApplicationContext(), "Error fetching group details: " + error.getMessage(), Toast.LENGTH_SHORT).show();
							}
						});
					} else {
						// If not a group, process friend request logic
						usersRef.child(myUid).addListenerForSingleValueEvent(new ValueEventListener() {
							@Override
							public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
								if (dataSnapshot.exists()) {
									final String myDp = dataSnapshot.child("dp").getValue(String.class);
									final String myUsername = dataSnapshot.child("username").getValue(String.class);
									
									inboxRef.child(myUid).child(friendUid).addListenerForSingleValueEvent(new ValueEventListener() {
										@Override
										public void onDataChange(@NonNull DataSnapshot inboxSnapshot) {
											if (inboxSnapshot.exists()) {
												button1.setText("Already Friend");
												button1.setEnabled(false);
											} else {
												friendRequestRef.child(friendUid).child(myUid)
												.addListenerForSingleValueEvent(new ValueEventListener() {
													@Override
													public void onDataChange(@NonNull DataSnapshot requestSnapshot) {
														if (requestSnapshot.exists()) {
															button1.setText("Requested");
															button1.setEnabled(false);
														} else {
															button1.setText("Add Friend");
															button1.setEnabled(true);
															
															button1.setOnClickListener(new View.OnClickListener() {
																@Override
																public void onClick(View v) {
																	button1.setEnabled(false);
																	button1.setText("Requesting...");
																	
																	Map<String, String> requestData = new HashMap<>();
																	requestData.put("dp", myDp);
																	requestData.put("username", myUsername);
																	requestData.put("status", "pending");
																	requestData.put("id", myUid);
																	requestData.put("senderId", friendUid);
																	
																	friendRequestRef.child(friendUid).child(myUid)
																	.setValue(requestData)
																	.addOnCompleteListener(new OnCompleteListener<Void>() {
																		@Override
																		public void onComplete(@NonNull Task<Void> task) {
																			if (task.isSuccessful()) {
																				button1.setText("Requested");
																			} else {
																				button1.setEnabled(true);
																				button1.setText("Add Friend");
																			}
																		}
																	});
																}
															});
														}
													}
													
													@Override
													public void onCancelled(@NonNull DatabaseError databaseError) {
													}
												});
											}
										}
										
										@Override
										public void onCancelled(@NonNull DatabaseError databaseError) {
										}
									});
								}
							}
							
							@Override
							public void onCancelled(@NonNull DatabaseError databaseError) {
							}
						});
					}
				} else {
					Toast.makeText(getApplicationContext(), "User type not found.", Toast.LENGTH_SHORT).show();
				}
			}
			
			@Override
			public void onCancelled(@NonNull DatabaseError error) {
				Toast.makeText(getApplicationContext(), "Error checking user type: " + error.getMessage(), Toast.LENGTH_SHORT).show();
			}
		});
		
	}
	
	public void _rippleRoundStroke(final View _view, final String _focus, final String _pressed, final double _round, final double _stroke, final String _strokeclr) {
		android.graphics.drawable.GradientDrawable GG = new android.graphics.drawable.GradientDrawable();
		GG.setColor(Color.parseColor(_focus));
		GG.setCornerRadius((float)_round);
		GG.setStroke((int) _stroke,
		Color.parseColor("#" + _strokeclr.replace("#", "")));
		android.graphics.drawable.RippleDrawable RE = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.parseColor(_pressed)}), GG, null);
		_view.setBackground(RE);
	}
	
	
	public void _convertToBottomSheet() {
	}
	private androidx.coordinatorlayout.widget.CoordinatorLayout mCoordinatorLayout;
	@Override
	public void finish(){
		com.google.android.material.bottomsheet.BottomSheetBehavior.from(mCoordinatorLayout.getChildAt(0)).setState(com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED);
	}
	private void superFinish(){
		super.finish();
	}
	@Override
	public void setContentView(int layId){
		if(mCoordinatorLayout == null){
			overridePendingTransition(0,0);
			mCoordinatorLayout = new androidx.coordinatorlayout.widget.CoordinatorLayout(this);
			makeActivityTransparent();
			mCoordinatorLayout.setBackgroundColor(0x80000000);
			mCoordinatorLayout.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick (View v){
					finish();
				}
			});
		}
		mCoordinatorLayout.removeAllViews();
		androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams params = new androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
		final com.google.android.material.bottomsheet.BottomSheetBehavior behavior = new com.google.android.material.bottomsheet.BottomSheetBehavior();
		params.setBehavior(behavior);
		behavior.setBottomSheetCallback(new com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback(){
			@Override
			public void onSlide(View bottomSheet, float slideOffset){
				
			}
			@Override
			public void onStateChanged(View bottomSheet, int newState){
				if(newState == com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED){
					superFinish();
					overridePendingTransition(0,0);
				}
			}
		});
		View inflated = getLayoutInflater().inflate(layId,null);	
		mCoordinatorLayout.addView(inflated,params);
		
		if(mCoordinatorLayout.getParent()!= null)((ViewGroup)mCoordinatorLayout.getParent()).removeView(mCoordinatorLayout);
		setContentView(mCoordinatorLayout);
		inflated.post(new Runnable(){
			@Override
			public void run() {
				behavior.setState(com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED);
			}});
		
	}
	
	private void makeActivityTransparent(){
		getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(0));
		try {
			java.lang.reflect.Method getActivityOptions = Activity.class.getDeclaredMethod("getActivityOptions"); 
			getActivityOptions.setAccessible(true);
			Object options = getActivityOptions.invoke(this);
			Class<?>[] classes = Activity.class.getDeclaredClasses();
			Class<?> translucentConversionListenerClazz = null;
			for (Class clazz : classes) { 
				if (clazz.getSimpleName().contains("TranslucentConversionListener")) { 
					translucentConversionListenerClazz = clazz;
				} 
			} 
			java.lang.reflect.Method convertToTranslucent = Activity.class.getDeclaredMethod("convertToTranslucent", translucentConversionListenerClazz, ActivityOptions.class); 
			convertToTranslucent.setAccessible(true); 
			convertToTranslucent.invoke(this, null, options); 
		} catch (Throwable t) {
		}
	}
	
	{
	}
	
	
	public void _changeActivityFont(final String _fontname) {
		fontName = "fonts/".concat(_fontname.concat(".ttf"));
		overrideFonts(this,getWindow().getDecorView()); 
	} 
	private void overrideFonts(final android.content.Context context, final View v) {
		
		try {
			Typeface 
			typeace = Typeface.createFromAsset(getAssets(), fontName);;
			if ((v instanceof ViewGroup)) {
				ViewGroup vg = (ViewGroup) v;
				for (int i = 0;
				i < vg.getChildCount();
				i++) {
					View child = vg.getChildAt(i);
					overrideFonts(context, child);
				}
			} else {
				if ((v instanceof TextView)) {
					((TextView) v).setTypeface(typeace);
				} else {
					if ((v instanceof EditText )) {
						((EditText) v).setTypeface(typeace);
					} else {
						if ((v instanceof Button)) {
							((Button) v).setTypeface(typeace);
						}
					}
				}
			}
		}
		catch(Exception e)
		
		{
			SketchwareUtil.showMessage(getApplicationContext(), "Error Loading Font");
		};
	}
	
	
	public void _updated_nulls() {
		nldp = String.valueOf((long)(SketchwareUtil.getRandom((int)(1), (int)(20))));
		if ("1".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868698.png?alt=media&token=8043c6c6-dbec-4634-9ce7-9021f17420e8").commit();
		}
		if ("2".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868700.png?alt=media&token=1e6fff6c-ae6d-4e2b-b885-5b6d3f4e3a25").commit();
		}
		if ("3".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868704.png?alt=media&token=d1707b42-ae70-46f2-a6e5-d863b0498e9c").commit();
		}
		if ("4".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868708.png?alt=media&token=80710c1b-7852-49de-b5de-a7b4a061ccf9").commit();
		}
		if ("5".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868711.png?alt=media&token=fbad192d-c541-4e11-b442-1c49bbacb8c6").commit();
		}
		if ("6".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868712.png?alt=media&token=9302987f-1762-4291-b7f2-b63265971e98").commit();
		}
		if ("7".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868713.png?alt=media&token=7dc60c31-364c-411f-bc0e-79e7b552f043").commit();
		}
		if ("8".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868714.png?alt=media&token=59192bec-864c-4c62-ab42-e7f9c7be4979").commit();
		}
		if ("9".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868715.png?alt=media&token=70d17325-2b4e-41ba-89a6-58ee88297761").commit();
		}
		if ("10".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868718.png?alt=media&token=356fb064-52ad-4099-b30c-1bdb91f288d3").commit();
		}
		if ("11".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868719.png?alt=media&token=f08ed490-9c8c-4ee3-97e3-3993520d6142").commit();
		}
		if ("12".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868725.png?alt=media&token=67b37819-3c6e-4f5b-84a3-560e7e90a14b").commit();
		}
		if ("13".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868726.png?alt=media&token=b6af1f43-f230-43dd-9710-369060c9f42c").commit();
		}
		if ("14".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868728.png?alt=media&token=b729c111-8ef0-405f-b47f-5b160335981b").commit();
		}
		if ("15".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868729.png?alt=media&token=51e1858e-787d-4395-a300-2fed645e2a21").commit();
		}
		if ("16".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868732.png?alt=media&token=e60b5c99-cbc2-4b30-a821-3c176a66af4f").commit();
		}
		if ("17".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868739.png?alt=media&token=35f5c79d-0fba-4483-b0fc-62aaa0de23a5").commit();
		}
		if ("18".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868742.png?alt=media&token=48910e86-5e38-4340-9ebf-1e84bb8a3362").commit();
		}
		if ("19".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868745.png?alt=media&token=ef00538d-f8f9-4348-8489-537cb3061faa").commit();
		}
		if ("20".equals(nldp)) {
			file.edit().putString("nulldp", "https://firebasestorage.googleapis.com/v0/b/chat-2024-ff149.appspot.com/o/Fake%2F5868747.png?alt=media&token=9b7011f0-9a0c-4950-a453-edf7d4a5003c").commit();
		}
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}
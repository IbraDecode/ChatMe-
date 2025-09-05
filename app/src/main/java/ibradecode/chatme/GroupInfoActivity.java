package ibradecode.chatme;

import android.animation.*;
import android.app.*;
import android.content.*;
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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import de.hdodenhof.circleimageview.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;

public class GroupInfoActivity extends AppCompatActivity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private String key = "";
	private HashMap<String, Object> mos = new HashMap<>();
	private String seacrhT = "";
	private String adminName = "";
	private boolean srt = false;
	private String group_uuid = "";
	private String friendUid = "";
	
	private ArrayList<HashMap<String, Object>> listMap = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private CircleImageView icon;
	private LinearLayout linear4;
	private TextView groupename;
	private TextView adminTextView;
	private LinearLayout linear5;
	private TextView members;
	private TextView button1;
	private ListView ListView1;
	private LinearLayout linear_noinfo;
	private ImageView icon_cloud;
	private TextView noinfo_txt;
	
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
	private DatabaseReference FirebaseDB1 = _firebase.getReference("users");
	private ChildEventListener _FirebaseDB1_child_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.group_info);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		icon = findViewById(R.id.icon);
		linear4 = findViewById(R.id.linear4);
		groupename = findViewById(R.id.groupename);
		adminTextView = findViewById(R.id.adminTextView);
		linear5 = findViewById(R.id.linear5);
		members = findViewById(R.id.members);
		button1 = findViewById(R.id.button1);
		ListView1 = findViewById(R.id.ListView1);
		linear_noinfo = findViewById(R.id.linear_noinfo);
		icon_cloud = findViewById(R.id.icon_cloud);
		noinfo_txt = findViewById(R.id.noinfo_txt);
		fauth = FirebaseAuth.getInstance();
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		_users_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (key.equals(_childKey)) {
					if (_childValue.containsKey("username")) {
						groupename.setText(_childValue.get("username").toString());
					}
					if (_childValue.containsKey("dp")) {
						Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("dp").toString())).into(icon);
					}
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
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
		
		_FirebaseDB1_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
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
		FirebaseDB1.addChildEventListener(_FirebaseDB1_child_listener);
		
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
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
			Window w =GroupInfoActivity.this.getWindow();
			w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(0xFF1A1A1A);
		}
		if (getIntent().hasExtra("user2")) {
			key = getIntent().getStringExtra("user2");
			friendUid = getIntent().getStringExtra("user2");
			if (getIntent().hasExtra("mb")) {
				/*
listMap = new Gson().fromJson(getIntent().getStringExtra("mb"), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
/// Create a Set to store unique items
HashSet<String> uniqueItems = new HashSet<>();

// Loop through the ListMap and add items to the Set
for (int i = 0; i < listMap.size(); i++) {
    String item = listMap.get(i).toString();
    uniqueItems.add(item); // Set ignores duplicates
}

// Get the unique item count
int uniqueCount = uniqueItems.size();

// Show the count in a message or TextView



// Initialize the usernames list
final ArrayList<String> usernamesList = new ArrayList<>();

// Loop through unique member IDs and fetch usernames
for (String memberId : uniqueItems) {
    FirebaseDB1.child(memberId + "/username").addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
              final  String username = dataSnapshot.getValue(String.class);

                if (username != null) {
                    usernamesList.add(username);
                    
                    // Update ListView with usernames
                    
                
ListView1.setAdapter(new ListView1Adapter(listMap));
((BaseAdapter)ListView1.getAdapter()).notifyDataSetChanged();
}
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            showMessage("Error: " + databaseError.getMessage());
        }
    });
}


*/
				listMap = new Gson().fromJson(getIntent().getStringExtra("mb"), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
				
				// Create a Set to store unique items
				HashSet<String> uniqueItems = new HashSet<>();
				
				// Loop through the ListMap and add items to the Set
				for (int i = 0; i < listMap.size(); i++) {
					String item = listMap.get(i).toString();
					uniqueItems.add(item); // Set ignores duplicates
				}
				
				// Get the unique item count
				int uniqueCount = uniqueItems.size();
				showMessage("Unique Item Count: " + uniqueCount);
				
				// Initialize the usernames list as HashMap (for the adapter)
				final ArrayList<HashMap<String, Object>> usernamesList = new ArrayList<>();
				
				// Loop through unique member IDs and fetch usernames
				for (String memberId : uniqueItems) {
					FirebaseDB1.child(memberId + "/username").addListenerForSingleValueEvent(new ValueEventListener() {
						@Override
						public void onDataChange(DataSnapshot dataSnapshot) {
							if (dataSnapshot.exists()) {
								final String username = dataSnapshot.getValue(String.class);
								
								if (username != null) {
									// Create a HashMap for each user
									HashMap<String, Object> userMap = new HashMap<>();
									userMap.put("username", username);
									usernamesList.add(userMap);
									
									// Update ListView with the custom adapter
									ListView1.setAdapter(new ListView1Adapter(usernamesList));
									
									// Force ListView to refresh
									((BaseAdapter) ListView1.getAdapter()).notifyDataSetChanged();
								}
							}
						}
						
						@Override
						public void onCancelled(DatabaseError databaseError) {
							showMessage("Error: " + databaseError.getMessage());
						}
					});
				}
				
				members.setText("Group • ".concat(String.valueOf((long)(uniqueCount)).concat("  members")));
			}
			String groupId = key; // Example group ID
			
			// Step 1: Get the admin ID from the group
			FirebaseDB1.child(groupId + "/admin").addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot adminSnapshot) {
					if (adminSnapshot.exists()) {
						final String adminId = adminSnapshot.getValue(String.class);
						
						if (adminId != null && !adminId.isEmpty()) {
							// Step 2: Search for the matching user ID in /users/
							FirebaseDB1.addListenerForSingleValueEvent(new ValueEventListener() {
								@Override
								public void onDataChange(DataSnapshot usersSnapshot) {
									boolean adminFound = false;
									
									for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
										String userId = userSnapshot.getKey();
										
										if (adminId.equals(userId)) {
											adminFound = true;
											
											// Step 3: Get the admin’s username
											String adminUsername = userSnapshot.child("username").getValue(String.class);
											if (adminUsername != null) {
												adminTextView.setText("Admin: " + adminUsername);
											} else {
												adminTextView.setText("Admin username not found");
											}
											break;
										}
									}
									
									if (!adminFound) {
										showMessage("Admin ID not found in user list");
									}
								}
								
								@Override
								public void onCancelled(DatabaseError databaseError) {
									showMessage("Error: " + databaseError.getMessage());
								}
							});
						} else {
							showMessage("Admin ID is empty or invalid");
						}
					} else {
						showMessage("Admin ID not found in group data");
					}
				}
				
				@Override
				public void onCancelled(DatabaseError databaseError) {
					showMessage("Error: " + databaseError.getMessage());
				}
			});
			
			// Get the UIDs (replace with actual logic to retrieve friendUid)
			// Replace with the actual friend's UID
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
									button1.setText("Leave Group");
									button1.setEnabled(true);
									
									// Set click listener to remove the friend
									button1.setOnClickListener(new View.OnClickListener() {
										@Override
										public void onClick(View v) {
											// Disable button and show progress
											button1.setEnabled(false);
											button1.setText("Leaving...");
											
											// Remove friend from both users' inboxes
											inboxRef.child(myUid).child(friendUid).removeValue()
											.addOnCompleteListener(new OnCompleteListener<Void>() {
												@Override
												public void onComplete(@NonNull Task<Void> task) {
													if (task.isSuccessful()) {
														inboxRef.child(friendUid).child(myUid).removeValue()
														.addOnCompleteListener(new OnCompleteListener<Void>() {
															@Override
															public void onComplete(@NonNull Task<Void> task) {
																if (task.isSuccessful()) {
																	// Remove friend request from both users
																	friendRequestRef.child(friendUid).child(myUid).removeValue()
																	.addOnCompleteListener(new OnCompleteListener<Void>() {
																		@Override
																		public void onComplete(@NonNull Task<Void> task) {
																			if (task.isSuccessful()) {
																				friendRequestRef.child(myUid).child(friendUid).removeValue()
																				.addOnCompleteListener(new OnCompleteListener<Void>() {
																					@Override
																					public void onComplete(@NonNull Task<Void> task) {
																						if (task.isSuccessful()) {
																							// Friend removed successfully
																							button1.setText("Join Group");
																							button1.setEnabled(true);
																						} else {
																							// Handle failure
																							button1.setEnabled(true);
																							button1.setText("Leave Group");
																						}
																					}
																				});
																			} else {
																				// Handle failure
																				button1.setEnabled(true);
																				button1.setText("Remove Group");
																			}
																		}
																	});
																} else {
																	// Handle failure
																	button1.setEnabled(true);
																	button1.setText("Remove Group");
																}
															}
														});
													} else {
														// Handle failure
														button1.setEnabled(true);
														button1.setText("Remove Group");
													}
												}
											});
										}
									});
								} else {
									// Check if a friend request was already sent
									friendRequestRef.child(friendUid).child(myUid)
									.addListenerForSingleValueEvent(new ValueEventListener() {
										@Override
										public void onDataChange(@NonNull DataSnapshot requestSnapshot) {
											if (requestSnapshot.exists()) {
												// Request already sent, update button state
												button1.setText("Requesting..");
												button1.setEnabled(false);
											} else {
												// Allow sending a friend request
												button1.setText("Join Group");
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
		} else {
			SketchwareUtil.showMessage(getApplicationContext(), "information not found!");
			finish();
		}
	}
	
	public class ListView1Adapter extends BaseAdapter {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public ListView1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = getLayoutInflater();
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.member_info, null);
			}
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final de.hdodenhof.circleimageview.CircleImageView circleimageview1 = _view.findViewById(R.id.circleimageview1);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			
			textview1.setText(_data.get((int)_position).get("username").toString());
			
			return _view;
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
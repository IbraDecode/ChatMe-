package ibradecode.chatme;

import android.animation.*;
import android.animation.ObjectAnimator;
import android.app.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.os.Bundle;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.webkit.*;
import android.widget.*;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
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
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import java.text.Normalizer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class SettingsActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private HashMap<String, Object> status = new HashMap<>();
	private String myUuid = "";
	private String fontName = "";
	private String typeace = "";
	private HashMap<String, Object> sd = new HashMap<>();
	private String Dv = "";
	private String delete = "";
	
	private ScrollView vscroll1;
	private LinearLayout linear1;
	private LinearLayout linear2;
	private TextView profile_instra;
	private LinearLayout Myprofile;
	private TextView intra_themes;
	private LinearLayout linear_darkMode;
	private TextView intra_security;
	private LinearLayout linear_applock;
	private TextView instra_privacy;
	private LinearLayout linear_lock;
	private LinearLayout linear_DIS;
	private TextView insta_database;
	private LinearLayout linear_clear_saved_accounts;
	private LinearLayout linear_blank;
	private TextView settings;
	private LinearLayout linear_logout;
	private TextView logout_txt;
	private ImageView imageview_logout;
	private ImageView profile;
	private TextView Myprof_txt;
	private ImageView go_to;
	private ImageView theme_ico;
	private Switch darkmode;
	private ImageView applock_img;
	private Switch applock;
	private ImageView lock_ico;
	private Switch lock;
	private ImageView dis_ico;
	private Switch dis;
	private ImageView clear_saved_accounts;
	private TextView acc_clear_info;
	private TextView textview4;
	
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
	private DatabaseReference get_status = _firebase.getReference("get_status");
	private ChildEventListener _get_status_child_listener;
	private Intent a = new Intent();
	private TimerTask ts;
	private SharedPreferences file;
	private DatabaseReference users = _firebase.getReference("users");
	private ChildEventListener _users_child_listener;
	private AlertDialog.Builder ad;
	private DatabaseReference saved = _firebase.getReference("saved");
	private ChildEventListener _saved_child_listener;
	private ObjectAnimator aw = new ObjectAnimator();
	private ObjectAnimator aq = new ObjectAnimator();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.settings);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		vscroll1 = findViewById(R.id.vscroll1);
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		profile_instra = findViewById(R.id.profile_instra);
		Myprofile = findViewById(R.id.Myprofile);
		intra_themes = findViewById(R.id.intra_themes);
		linear_darkMode = findViewById(R.id.linear_darkMode);
		intra_security = findViewById(R.id.intra_security);
		linear_applock = findViewById(R.id.linear_applock);
		instra_privacy = findViewById(R.id.instra_privacy);
		linear_lock = findViewById(R.id.linear_lock);
		linear_DIS = findViewById(R.id.linear_DIS);
		insta_database = findViewById(R.id.insta_database);
		linear_clear_saved_accounts = findViewById(R.id.linear_clear_saved_accounts);
		linear_blank = findViewById(R.id.linear_blank);
		settings = findViewById(R.id.settings);
		linear_logout = findViewById(R.id.linear_logout);
		logout_txt = findViewById(R.id.logout_txt);
		imageview_logout = findViewById(R.id.imageview_logout);
		profile = findViewById(R.id.profile);
		Myprof_txt = findViewById(R.id.Myprof_txt);
		go_to = findViewById(R.id.go_to);
		theme_ico = findViewById(R.id.theme_ico);
		darkmode = findViewById(R.id.darkmode);
		applock_img = findViewById(R.id.applock_img);
		applock = findViewById(R.id.applock);
		lock_ico = findViewById(R.id.lock_ico);
		lock = findViewById(R.id.lock);
		dis_ico = findViewById(R.id.dis_ico);
		dis = findViewById(R.id.dis);
		clear_saved_accounts = findViewById(R.id.clear_saved_accounts);
		acc_clear_info = findViewById(R.id.acc_clear_info);
		textview4 = findViewById(R.id.textview4);
		fauth = FirebaseAuth.getInstance();
		file = getSharedPreferences("f", Activity.MODE_PRIVATE);
		ad = new AlertDialog.Builder(this);
		
		Myprofile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
					Intent intent = new Intent(SettingsActivity.this, MyprofileActivity.class);
					intent.putExtra("id", FirebaseAuth.getInstance().getCurrentUser().getUid());
					startActivity(intent);
					finish();
					overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
					
				}
			}
		});
		
		linear_logout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				// Get current user's UID
				final String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
				
				// Firebase references
				final DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
				final DatabaseReference inboxRef = FirebaseDatabase.getInstance().getReference("inbox");
				
				// Fetch the current user's data
				usersRef.child(myUid).addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(@NonNull DataSnapshot myDataSnapshot) {
						if (myDataSnapshot.exists()) {
							// Store user's data to be copied
							final Map<String, Object> myData = (Map<String, Object>) myDataSnapshot.getValue();
							
							// Iterate over each user's inbox to find occurrences of myUid
							inboxRef.addListenerForSingleValueEvent(new ValueEventListener() {
								@Override
								public void onDataChange(@NonNull DataSnapshot inboxSnapshot) {
									for (DataSnapshot userInbox : inboxSnapshot.getChildren()) {
										final String userUid = userInbox.getKey();  // Marked as final
										if (userInbox.hasChild(myUid)) {
											// Get current data for this inbox entry
											inboxRef.child(userUid).child(myUid).addListenerForSingleValueEvent(new ValueEventListener() {
												@Override
												public void onDataChange(@NonNull DataSnapshot snapshot) {
													// Prepare a map to merge data
													HashMap<String, Object> mergedData = new HashMap<>();
													
													// Retain existing keys if they exist
													if (snapshot.hasChild("lastMessage")) {
														mergedData.put("lastMessage", snapshot.child("lastMessage").getValue());
													}
													if (snapshot.hasChild("timestamp")) {
														mergedData.put("timestamp", snapshot.child("timestamp").getValue());
													}
													if (snapshot.hasChild("unreadCount")) {
														mergedData.put("unreadCount", snapshot.child("unreadCount").getValue());
													}
													if (snapshot.hasChild("subimg")) {
														mergedData.put("subimg", snapshot.child("subimg").getValue());
													}
													
													// Add new data
													mergedData.putAll(myData);
													
													// Update the inbox entry with merged data
													inboxRef.child(userUid).child(myUid)
													.updateChildren(mergedData)
													.addOnCompleteListener(new OnCompleteListener<Void>() {
														@Override
														public void onComplete(@NonNull Task<Void> task) {
															if (task.isSuccessful()) {
																// Successfully updated
															} else {
																SketchwareUtil.showMessage(getApplicationContext(), "Failed");
															}
														}
													});
												}
												
												@Override
												public void onCancelled(@NonNull DatabaseError error) {
													SketchwareUtil.showMessage(getApplicationContext(), "Error: " + error.getMessage());
												}
											});
										}
									}
								}
								
								@Override
								public void onCancelled(@NonNull DatabaseError databaseError) {
									SketchwareUtil.showMessage(getApplicationContext(), "Error: " + databaseError.getMessage());
								}
							});
						} else {
							a.setClass(getApplicationContext(), UsernameActivity.class);
							a.putExtra("set", "set");
							startActivity(a);
							finish();
							
							SketchwareUtil.showMessage(getApplicationContext(), "Failed to fetch your data.");
						}
					}
					
					@Override
					public void onCancelled(@NonNull DatabaseError error) {
						SketchwareUtil.showMessage(getApplicationContext(), "Error: " + error.getMessage());
					}
				});
				
				delete = String.valueOf((long)(file.getString("email", "").hashCode()));
				ad.setTitle("Save Account info");
				ad.setPositiveButton("Save", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						if (SketchwareUtil.isConnected(getApplicationContext())) {
							sd = new HashMap<>();
							sd.put("dp", file.getString("dp", ""));
							sd.put("username", file.getString("username", ""));
							sd.put("user", file.getString("user", ""));
							sd.put("email", file.getString("email", ""));
							sd.put("en", file.getString("password", ""));
							sd.put("key", delete);
							saved.child("device/".concat(Dv.concat("/".concat(delete)))).updateChildren(sd);
							sd.clear();
							file.edit().remove("email").commit();
							file.edit().remove("password").commit();
							_setStatus("Deactivate");
							a.setClass(getApplicationContext(), AllAccountsActivity.class);
							a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(a);
							ts = new TimerTask() {
								@Override
								public void run() {
									runOnUiThread(new Runnable() {
										@Override
										public void run() {
											if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
												FirebaseAuth.getInstance().signOut();
											}
										}
									});
								}
							};
							_timer.schedule(ts, (int)(500));
							finish();
						} else {
							SketchwareUtil.showMessage(getApplicationContext(), "Check Network Connection!.");
						}
					}
				});
				ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						if (SketchwareUtil.isConnected(getApplicationContext())) {
							file.edit().remove("email").commit();
							file.edit().remove("password").commit();
							_setStatus("Deactivate");
							a.setClass(getApplicationContext(), LogActivity.class);
							a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(a);
							ts = new TimerTask() {
								@Override
								public void run() {
									runOnUiThread(new Runnable() {
										@Override
										public void run() {
											if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
												FirebaseAuth.getInstance().signOut();
											}
										}
									});
								}
							};
							_timer.schedule(ts, (int)(500));
							finish();
						} else {
							SketchwareUtil.showMessage(getApplicationContext(), "Check Network Connection!.");
						}
					}
				});
				ad.create().show();
			}
		});
		
		darkmode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					file.edit().putString("theme", "dark").commit();
					darkmode.setText("Dark Mode: ON");
					_theme("");
				} else {
					file.edit().putString("theme", "false").commit();
					darkmode.setText("Dark Mode: OFF");
					_theme("");
				}
			}
		});
		
		applock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					file.edit().putString("bio_lock", "true").commit();
					file.edit().putString("bio_default", "0000").commit();
					applock.setText("App Lock (Enable)");
					status = new HashMap<>();
					status.put("applock", "true");
					users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(status);
					status.clear();
				} else {
					file.edit().remove("bio_lock").commit();
					applock.setText("App Lock (Disable)");
					users.child(FirebaseAuth.getInstance().getCurrentUser().getUid().concat("/applock")).removeValue();
				}
			}
		});
		
		lock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					status = new HashMap<>();
					status.put("lock", "true");
					users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(status);
					status.clear();
				} else {
					users.child(FirebaseAuth.getInstance().getCurrentUser().getUid().concat("/lock")).removeValue();
				}
			}
		});
		
		dis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				
			}
		});
		
		textview4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		_get_status_child_listener = new ChildEventListener() {
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
		get_status.addChildEventListener(_get_status_child_listener);
		
		_users_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
					if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
						if ("null".equals(_childValue.get("dp").toString())) {
							Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("dp").toString())).into(profile);
						}
						file.edit().putString("dp", _childValue.get("dp").toString()).commit();
						file.edit().putString("username", _childValue.get("username").toString()).commit();
						file.edit().putString("user", _childValue.get("user").toString()).commit();
						if (_childValue.containsKey("lock")) {
							if ("true".equals(_childValue.get("lock").toString())) {
								lock.setChecked(true);
							} else {
								lock.setChecked(false);
							}
						}
						if (_childValue.containsKey("applock")) {
							if ("true".equals(_childValue.get("applock").toString())) {
								applock.setChecked(true);
							} else {
								applock.setChecked(false);
							}
						}
					}
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
					if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
						if (_childValue.containsKey("lock")) {
							if ("true".equals(_childValue.get("lock").toString())) {
								lock.setChecked(true);
							} else {
								lock.setChecked(false);
							}
						}
						if (_childValue.containsKey("applock")) {
							if ("true".equals(_childValue.get("applock").toString())) {
								applock.setChecked(true);
							} else {
								applock.setChecked(false);
							}
						}
					}
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
		
		_saved_child_listener = new ChildEventListener() {
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
		saved.addChildEventListener(_saved_child_listener);
		
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
		_setCustomSwitchs(darkmode);
		_setCustomSwitchs(lock);
		_setCustomSwitchs(dis);
		_setCustomSwitchs(applock);
		Dv = android.provider.Settings.Secure.getString(getApplicationContext().getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
		_changeActivityFont("fn1");
		imageview_logout.setColorFilter(0xFFF44336, PorterDuff.Mode.MULTIPLY);
		_theme("");
		_setStatus("online");
		dis.setEnabled(false);
		if ("dark".equals(file.getString("theme", ""))) {
			darkmode.setChecked(true);
		}
		if ("true".equals(file.getString("bio_lock", ""))) {
			applock.setChecked(true);
		}
		
		
		// Example dynamic device ID
		final String deviceId = Dv; // Replace with your logic to get the device ID
		
		// Reference to the entire device node under /saved/device/{device_id}
		final DatabaseReference device2Ref = FirebaseDatabase.getInstance()
		.getReference("/saved/device/" + deviceId );
		
		// Set a click listener on the ImageView
		textview4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//animations---->
				_Pop(aw, aq, textview4);
				
				// Delete the entire device node from the database
				device2Ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
					@Override
					public void onComplete(@NonNull Task<Void> task) {
						if (task.isSuccessful()) {
							// Notify the user of success
							Toast.makeText(getApplicationContext(), "Data Cleared successfully", Toast.LENGTH_SHORT).show();
						} else {
							// Handle failure
							Toast.makeText(getApplicationContext(), "Failed to  Clear Data", Toast.LENGTH_SHORT).show();
						}
					}
				});
			}
		});
		
	}
	
	@Override
	public void onBackPressed() {
		if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
			a.setClass(getApplicationContext(), UsersActivity.class);
			startActivity(a);
			finish();
		} else {
			a.setClass(getApplicationContext(), MainActivity.class);
			startActivity(a);
			finish();
		}
	}
	
	public void _setStatus(final String _status) {
		status = new HashMap<>();
		status.put("status", _status.toLowerCase().trim());
		get_status.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(status);
		status.clear();
	}
	
	
	public void _theme(final String _color) {
		if ("dark".equals(file.getString("theme", ""))) {
			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
				Window w =SettingsActivity.this.getWindow();
				w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
				w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(0xFF1A1A1A);
			}
			vscroll1.setBackgroundColor(0xFF1A1A1A);
			settings.setTextColor(0xFFFFFFFF);
			applock.setTextColor(0xFFFFFFFF);
			Myprof_txt.setTextColor(0xFFFFFFFF);
			dis.setTextColor(0xFFFFFFFF);
			darkmode.setTextColor(0xFFFFFFFF);
			lock.setTextColor(0xFFFFFFFF);
			linear_darkMode.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, 0xFF212121));
			linear_lock.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, 0xFF212121));
			linear_DIS.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, 0xFF212121));
			Myprofile.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, 0xFF212121));
			linear_applock.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, 0xFF212121));
			linear_clear_saved_accounts.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, 0xFF212121));
			acc_clear_info.setTextColor(0xFFFFFFFF);
		} else {
			getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			getWindow().setStatusBarColor(0xFFFFFFFF);
			vscroll1.setBackgroundColor(0xFFFFFFFF);
			settings.setTextColor(0xFF000000);
			lock.setTextColor(0xFF091C2B);
			darkmode.setTextColor(0xFF091C2B);
			dis.setTextColor(0xFF091C2B);
			Myprof_txt.setTextColor(0xFF091C2B);
			applock.setTextColor(0xFF091C2B);
			linear_clear_saved_accounts.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, 0xFFEFF1FE));
			Myprofile.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, 0xFFEFF1FE));
			linear_applock.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, 0xFFEFF1FE));
			linear_lock.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, 0xFFEFF1FE));
			linear_darkMode.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, 0xFFEFF1FE));
			linear_DIS.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, 0xFFEFF1FE));
			acc_clear_info.setTextColor(0xFF091C2B);
		}
		_iconColor("#488295");
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
	
	
	public void _setCustomSwitchs(final Switch _switch1) {
		_switch1.setThumbDrawable(getResources().getDrawable(R.drawable.thumb_switch));
		_switch1.setTrackDrawable(getResources().getDrawable(R.drawable.track_switch));
		
		_switch1.setPadding(3, 3, 3, 3);
	}
	
	
	public void _Pop(final ObjectAnimator _x, final ObjectAnimator _y, final View _view) {
		_x.setTarget(_view);
		_x.setPropertyName("scaleX");
		_x.setFloatValues((float)(1), (float)(0.8d));
		_x.setDuration((int)(100));
		_x.setRepeatMode(ValueAnimator.REVERSE);
		_x.setRepeatCount((int)(1));
		_x.setInterpolator(new LinearInterpolator());
		_x.start();
		_y.setTarget(_view);
		_y.setPropertyName("scaleY");
		_y.setFloatValues((float)(1), (float)(0.8d));
		_y.setDuration((int)(100));
		_y.setRepeatMode(ValueAnimator.REVERSE);
		_y.setRepeatCount((int)(1));
		_y.setInterpolator(new LinearInterpolator());
		_y.start();
	}
	
	
	public void _ImageColor(final ImageView _image, final String _color) {
		_image.setColorFilter(Color.parseColor(_color),PorterDuff.Mode.SRC_ATOP);
	}
	
	
	public void _Setcolor(final TextView _view, final String _color) {
		_view.setTextColor(Color.parseColor(_color)); // Set text color to white
		
	}
	
	
	public void _iconColor(final String _color) {
		_ImageColor(profile, _color);
		_ImageColor(theme_ico, _color);
		_ImageColor(applock_img, _color);
		_ImageColor(lock_ico, _color);
		_ImageColor(dis_ico, _color);
		_ImageColor(clear_saved_accounts, _color);
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
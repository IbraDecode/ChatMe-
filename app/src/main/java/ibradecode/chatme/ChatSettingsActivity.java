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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.*;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import java.text.*;
import java.util.*;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;
import android.app.Activity;
import android.app.ActivityOptions;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ChatSettingsActivity extends AppCompatActivity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private String uuid = "";
	private String data = "";
	private String fontName = "";
	private String typeace = "";
	private String chatroom = "";
	private String chatcopy = "";
	private String userid = "";
	private String myid = "";
	private HashMap<String, Object> mo = new HashMap<>();
	private boolean blockedbyMe = false;
	
	private LinearLayout linear3;
	private LinearLayout linear1;
	private TextView textview2;
	private TextView intra_1;
	private LinearLayout linear_block;
	private ImageView block;
	private TextView textview_username;
	private TextView blockthis;
	
	private SharedPreferences file;
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
	private DatabaseReference inbox = _firebase.getReference("inbox");
	private ChildEventListener _inbox_child_listener;
	private DatabaseReference users = _firebase.getReference("users");
	private ChildEventListener _users_child_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.chat_settings);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear3 = findViewById(R.id.linear3);
		linear1 = findViewById(R.id.linear1);
		textview2 = findViewById(R.id.textview2);
		intra_1 = findViewById(R.id.intra_1);
		linear_block = findViewById(R.id.linear_block);
		block = findViewById(R.id.block);
		textview_username = findViewById(R.id.textview_username);
		blockthis = findViewById(R.id.blockthis);
		file = getSharedPreferences("f", Activity.MODE_PRIVATE);
		fauth = FirebaseAuth.getInstance();
		
		blockthis.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (blockedbyMe) {
					_Unblock(userid, myid);
				} else {
					_blockUser(userid, userid);
				}
			}
		});
		
		_inbox_child_listener = new ChildEventListener() {
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
		inbox.addChildEventListener(_inbox_child_listener);
		
		_users_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(userid)) {
					if (_childValue.containsKey("user")) {
						textview_username.setText("Block ".concat(_childValue.get("user").toString()));
					} else {
						textview_username.setText("failed to get username!");
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
		userid = getIntent().getStringExtra("user2");
		myid = FirebaseAuth.getInstance().getCurrentUser().getUid();
		// Get Firebase reference to the specific user's data in "users" with dynamic block key
		DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(myid).child("block_" + userid);
		
		// Check if the dynamic "block_userUid" key exists
		userRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				// Set switch1 based on the existence of the "block_userUid" key in "users"
				blockthis.setText("Unblock");
				blockedbyMe = true;
			}
			
			
			
			@Override
			public void onCancelled(@NonNull DatabaseError error) {
				// Handle any potential errors
				SketchwareUtil.showMessage(getApplicationContext(), "Error: " + error.getMessage());
			}
		});
		
		_changeActivityFont("fn1");
		uuid = getIntent().getStringExtra("user2");
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
	
	
	public void _blockUser(final String _userUid, final String _myUid) {
		DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
		
		
		DatabaseReference blockRef = usersRef.child(_myUid);
		
		Map<String, Object> blockData = new HashMap<>();
		blockData.put("block_" + _userUid, "true");  // Set to true to indicate the user is blocked
		
		blockRef.updateChildren(blockData).addOnCompleteListener(new OnCompleteListener<Void>() {
			@Override
			public void onComplete(@NonNull Task<Void> task) {
				if (task.isSuccessful()) {
					
					blockedbyMe = true;
					SketchwareUtil.showMessage(getApplicationContext(), "User Blocked");
				} else {
					SketchwareUtil.showMessage(getApplicationContext(), "Failed to add block key");
				}
			}
		});
		
		
		// Get current user's UID
		final String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
		
		// Firebase references
		
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
									// Copy myUid's data to this user's inbox
									inboxRef.child(userUid).child(myUid)
									.setValue(myData)
									.addOnCompleteListener(new OnCompleteListener<Void>() {
										@Override
										public void onComplete(@NonNull Task<Void> task) {
											if (task.isSuccessful()) {
												
											} else {
												SketchwareUtil.showMessage(getApplicationContext(), "Failed to copy data to " + userUid + "'s inbox");
											}
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
					
					
					SketchwareUtil.showMessage(getApplicationContext(), "Failed to fetch your data.");
				}
			}
			
			@Override
			public void onCancelled(@NonNull DatabaseError error) {
				SketchwareUtil.showMessage(getApplicationContext(), "Error: " + error.getMessage());
			}
		});
		
	}
	
	
	public void _Unblock(final String _userUid, final String _myUid) {
		DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
		
		
		DatabaseReference blockRef = usersRef.child(_myUid).child("block_" + _userUid);
		
		blockRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
			@Override
			public void onComplete(@NonNull Task<Void> task) {
				if (task.isSuccessful()) {
					SketchwareUtil.showMessage(getApplicationContext(), "Unblocked");
					blockedbyMe = false;
				} else {
					SketchwareUtil.showMessage(getApplicationContext(), "Failed to remove block key");
				}
			}
		});
		
		// Get current user's UID
		final String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
		
		// Firebase references
		
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
									// Copy myUid's data to this user's inbox
									inboxRef.child(userUid).child(myUid)
									.setValue(myData)
									.addOnCompleteListener(new OnCompleteListener<Void>() {
										@Override
										public void onComplete(@NonNull Task<Void> task) {
											if (task.isSuccessful()) {
												
											} else {
												SketchwareUtil.showMessage(getApplicationContext(), "Failed to copy data to " + userUid + "'s inbox");
											}
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
					
					
					SketchwareUtil.showMessage(getApplicationContext(), "Failed to fetch your data.");
				}
			}
			
			@Override
			public void onCancelled(@NonNull DatabaseError error) {
				SketchwareUtil.showMessage(getApplicationContext(), "Error: " + error.getMessage());
			}
		});
		
	}
	
	
	public void _iconColor(final String _color) {
		_ImageColor(block, _color);
	}
	
	
	public void _ImageColor(final ImageView _image, final String _color) {
		_image.setColorFilter(Color.parseColor(_color),PorterDuff.Mode.SRC_ATOP);
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
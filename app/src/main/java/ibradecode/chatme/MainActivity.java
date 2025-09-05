package ibradecode.chatme;

import android.Manifest;
import android.animation.*;
import android.animation.ObjectAnimator;
import android.app.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import android.view.View.*;
import android.view.animation.*;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.webkit.*;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.*;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.util.Log;
import android.media.MediaRecorder;
import android.provider.Settings;

public class MainActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private String fontName = "";
	private String typeace = "";
	private String myUuid = "";
	private String username = "";
	private String newDp = "";
	private HashMap<String, Object> data = new HashMap<>();
	private HashMap<String, Object> map = new HashMap<>();
	private boolean check = false;
	private boolean user_set = false;
	private boolean up_dp = false;
	private String uuid = "";
	private String nldp = "";
	private HashMap<String, Object> theme_colors = new HashMap<>();
	private double i = 0;
	
	private ArrayList<HashMap<String, Object>> bg = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear3;
	private ImageView logo;
	private TextView logo_text;
	private TextView textview2;
	private LinearLayout linear_loading_dots;
	private LinearLayout linear2;
	private LinearLayout bottom;
	private TextView dot1;
	private TextView dot2;
	private TextView dot3;
	private ImageView watermark;
	
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
	private Intent a = new Intent();
	private RequestNetwork net;
	private RequestNetwork.RequestListener _net_request_listener;
	private TimerTask ts;
	private DatabaseReference inbox = _firebase.getReference("inbox");
	private ChildEventListener _inbox_child_listener;
	private DatabaseReference log = _firebase.getReference("log");
	private ChildEventListener _log_child_listener;
	private ObjectAnimator anima = new ObjectAnimator();
	private ObjectAnimator anim = new ObjectAnimator();
	private DatabaseReference users = _firebase.getReference("users");
	private ChildEventListener _users_child_listener;
	private AlertDialog.Builder user;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		} else {
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		linear3 = findViewById(R.id.linear3);
		logo = findViewById(R.id.logo);
		logo_text = findViewById(R.id.logo_text);
		textview2 = findViewById(R.id.textview2);
		linear_loading_dots = findViewById(R.id.linear_loading_dots);
		linear2 = findViewById(R.id.linear2);
		bottom = findViewById(R.id.bottom);
		dot1 = findViewById(R.id.dot1);
		dot2 = findViewById(R.id.dot2);
		dot3 = findViewById(R.id.dot3);
		watermark = findViewById(R.id.watermark);
		file = getSharedPreferences("f", Activity.MODE_PRIVATE);
		fauth = FirebaseAuth.getInstance();
		net = new RequestNetwork(this);
		user = new AlertDialog.Builder(this);
		
		_net_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
					/*
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    if (currentUser != null) {
        final String currentUserId = currentUser.getUid();
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users").child(uuid).child("id");

        // Retrieve the id from the database
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String databaseId = dataSnapshot.getValue(String.class);
                    if (databaseId != null && databaseId.equals(currentUserId)) {
                        // If the id matches the current user's UUID
                        Toast.makeText(getApplicationContext(), "verified!", Toast.LENGTH_SHORT).show();
                    } else {
                        // If the id does not match, delete the current user
                        FirebaseAuth.getInstance().getCurrentUser().delete()
.addOnCompleteListener(fauth_deleteUserListener);
                    }
                } else {
                    // Handle case where the uuid/id does not exist in the database
                    Toast.makeText(getApplicationContext(), "ID not found in database!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
  
  
//new code!



FirebaseAuth mAuth = FirebaseAuth.getInstance();
FirebaseUser currentUser = mAuth.getCurrentUser();

if (currentUser != null) {
    final String currentUserId = currentUser.getUid();
    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users").child(uuid);

    // Retrieve the uuid and id from the database
    databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                // Check if the 'id' field exists under the uuid
                if (dataSnapshot.hasChild("id")) {
                    String databaseId = dataSnapshot.child("id").getValue(String.class);
                    if (databaseId != null && databaseId.equals(currentUserId)) {
                        // If the id matches the current user's UUID
                        Toast.makeText(getApplicationContext(), "verified!", Toast.LENGTH_SHORT).show();
                    } else {
                        // If the id does not match, delete the current user
                        FirebaseAuth.getInstance().getCurrentUser().delete()
.addOnCompleteListener(fauth_deleteUserListener);
                    }
                } else {
                    // If the 'id' does not exist under the uuid, delete the current user
                    Toast.makeText(getApplicationContext(), "'id' not found in database!", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().getCurrentUser().delete()
.addOnCompleteListener(fauth_deleteUserListener);
                }
            } else {
                // If the 'uuid' does not exist, delete the current user
                Toast.makeText(getApplicationContext(), "'uuid' not found in database!", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().getCurrentUser().delete()
.addOnCompleteListener(fauth_deleteUserListener);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(getApplicationContext(), "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });
}


*/
					
				} else {
					a.setClass(getApplicationContext(), LogActivity.class);
					startActivity(a);
					finish();
				}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
		
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
		
		_log_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
					if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
						if ("check".equals(_childValue.get("log").toString())) {
							check = true;
						} else {
							check = false;
						}
						if ("set".equals(_childValue.get("log").toString())) {
							user_set = true;
						} else {
							user_set = false;
						}
						if ("dp".equals(_childValue.get("log").toString())) {
							up_dp = true;
						} else {
							up_dp = false;
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
						if ("check".equals(_childValue.get("log").toString())) {
							check = true;
						} else {
							check = false;
						}
						if ("set".equals(_childValue.get("log").toString())) {
							user_set = true;
						} else {
							user_set = false;
						}
						if ("dp".equals(_childValue.get("log").toString())) {
							up_dp = true;
						} else {
							up_dp = false;
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
		log.addChildEventListener(_log_child_listener);
		
		_users_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
					if (true) {
						
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
				if (_success) {
					a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					a.setClass(getApplicationContext(), UsersActivity.class);
					startActivity(a);
					finish();
				} else {
					SketchwareUtil.showMessage(getApplicationContext(), _errorMessage);
					if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
						FirebaseAuth.getInstance().signOut();
					}
					a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					a.setClass(getApplicationContext(), LogActivity.class);
					startActivity(a);
					finish();
				}
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
		
		// Load animations
		final   ObjectAnimator animator1 = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.dot_animation);
		final ObjectAnimator animator2 = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.dot_animation);
		final  ObjectAnimator animator3 = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.dot_animation);
		
		// Set targets
		animator1.setTarget(dot1);
		animator2.setTarget(dot2);
		animator3.setTarget(dot3);
		
		// Start animations with delay
		animator1.start();
		dot2.postDelayed(new Runnable() {
			@Override
			public void run() {
				animator2.start();
			}
		}, 200);
		
		dot3.postDelayed(new Runnable() {
			@Override
			public void run() {
				animator3.start();
			}
		}, 400);
		
		_all_folder_check();
		_changeActivityFont("fn1");
		_theme("#ffffff");
		_chek_login();
		if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
			Intent serviceIntent = new Intent(this, FriendRequestService.class);
			startService(serviceIntent);
			uuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
			final DatabaseReference inboxRef = FirebaseDatabase.getInstance().getReference("inbox");
			final DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
			final DatabaseReference deletedRef = FirebaseDatabase.getInstance().getReference("deleted");
			
			inboxRef.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot inboxSnapshot) {
					for (DataSnapshot parentSnapshot : inboxSnapshot.getChildren()) {
						final String parentUid = parentSnapshot.getKey();
						
						for (DataSnapshot childSnapshot : parentSnapshot.getChildren()) {
							final String childUid = childSnapshot.getKey();
							final Object childData = childSnapshot.getValue();
							
							usersRef.child(childUid).addListenerForSingleValueEvent(new ValueEventListener() {
								@Override
								public void onDataChange(DataSnapshot userSnapshot) {
									if (!userSnapshot.exists()) {
										// UID not found in /users/, copy to /deleted/ and delete from /inbox/
										deletedRef.child(parentUid).child(childUid).setValue(childData);
										inboxRef.child(parentUid).child(childUid).removeValue();
									}
								}
								
								@Override
								public void onCancelled(DatabaseError error) {
									// Handle errors (optional)
								}
							});
						}
					}
				}
				
				@Override
				public void onCancelled(DatabaseError error) {
					// Handle errors (optional)
				}
			});
			
			_logReq(FirebaseAuth.getInstance().getCurrentUser().getUid());
		} else {
			uuid = "false";
		}
		Uri data = getIntent().getData();
		if (data != null) {
			final String host = data.getHost();
			final String path = data.getPath();
			final String id = data.getLastPathSegment();
			
			
			
		}
		
		_request_permission();
	}
	
	public void _theme(final String _color) {
		if ("dark".equals(file.getString("theme", ""))) {
			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
				Window w =MainActivity.this.getWindow();
				w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
				w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(0xFF1A1A1A);
			}
			logo.setImageResource(R.drawable.logo);
			linear1.setBackgroundColor(0xFF1A1A1A);
			dot1.setBackgroundTintList(ColorStateList.valueOf(0xFFFFFFFF));
			dot2.setBackgroundTintList(ColorStateList.valueOf(0xFFFFFFFF));
			dot3.setBackgroundTintList(ColorStateList.valueOf(0xFFFFFFFF));
		} else {
			logo.setImageResource(R.drawable.logo_white);
			linear1.setBackgroundColor(0xFF488395);
			dot1.setBackgroundTintList(ColorStateList.valueOf(0xFF000000));
			dot2.setBackgroundTintList(ColorStateList.valueOf(0xFF000000));
			dot3.setBackgroundTintList(ColorStateList.valueOf(0xFF000000));
		}
	}
	
	
	public void _chek_login() {
		/*
if (FirebaseAuth.getInstance().getCurrentUser() != null) {
    if (file.contains("bio_lock")) {
        if (getIntent().hasExtra("bio_true")) {
            final FirebaseAuth auth = FirebaseAuth.getInstance(); // Declare as final
            String currentUserUid = auth.getCurrentUser().getUid();
            DatabaseReference banRef = FirebaseDatabase.getInstance().getReference("ban").child(currentUserUid);

            banRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Intent intent = new Intent(MainActivity.this, BlockedActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        if (auth.getCurrentUser().isEmailVerified()) {
                            if (file.contains("set")) {
                                Intent intent = new Intent(MainActivity.this, UsernameActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            } else if (file.contains("dpup")) {
                                Intent intent = new Intent(MainActivity.this, UploadphotoActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            } else {
                                Intent intent = new Intent(MainActivity.this, UsersActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            Intent intent = new Intent(MainActivity.this, UsernameActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Handle database error if necessary
                }
            });
        } else {
            Intent intent = new Intent(MainActivity.this, ApplockActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    } else {
        final FirebaseAuth auth = FirebaseAuth.getInstance(); // Declare as final
        String currentUserUid = auth.getCurrentUser().getUid();
        DatabaseReference banRef = FirebaseDatabase.getInstance().getReference("ban").child(currentUserUid);

        banRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Intent intent = new Intent(MainActivity.this, BlockedActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    if (auth.getCurrentUser().isEmailVerified()) {
                        if (file.contains("set")) {
                            Intent intent = new Intent(MainActivity.this, UsernameActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        } else if (file.contains("dpup")) {
                            Intent intent = new Intent(MainActivity.this, UploadphotoActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(MainActivity.this, UsersActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Intent intent = new Intent(MainActivity.this, UsernameActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle database error if necessary
            }
        });
    }
} else {
    Intent intent = new Intent(MainActivity.this, AllAccountsActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(intent);
    finish();
}

*/
		// New Code:
		
		
		if (FirebaseAuth.getInstance().getCurrentUser() != null) {
			if (SketchwareUtil.isConnected(getApplicationContext())) {
				
				
				final FirebaseAuth auth = FirebaseAuth.getInstance();
				final String currentUserUid = auth.getCurrentUser().getUid();
				final DatabaseReference banRef = FirebaseDatabase.getInstance().getReference("ban");
				
				// First, check if the "ban" node exists
				banRef.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot snapshot) {
						if (snapshot.exists()) {
							// "ban" node exists, check if the current user is banned
							final DatabaseReference userBanRef = banRef.child(currentUserUid);
							userBanRef.addListenerForSingleValueEvent(new ValueEventListener() {
								@Override
								public void onDataChange(DataSnapshot userSnapshot) {
									if (userSnapshot.exists()) {
										// User is banned, redirect to BlockedActivity
										Intent intent = new Intent(MainActivity.this, BlockedActivity.class);
										intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
										startActivity(intent);
										finish();
									} else {
										// User is not banned, proceed
										_proceedWithNextSteps(fauth);
									}
								}
								
								@Override
								public void onCancelled(DatabaseError error) {
									// Handle database error if necessary
									Toast.makeText(MainActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
								}
							});
						} else {
							// "ban" node does not exist, skip checking and proceed
							_proceedWithNextSteps(fauth);
						}
					}
					
					@Override
					public void onCancelled(DatabaseError error) {
						// Handle database error if necessary
						Toast.makeText(MainActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
					}
				});
				
				
			} else {
				_proceedWithNextSteps(fauth);
			}
		} else {
			// No user is logged in, redirect to AllAccountsActivity
			Intent intent = new Intent(MainActivity.this, AllAccountsActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
		}
		
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
	
	
	public void _all_folder_check() {
		if (!FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat("/Chatme/temp/voice/demo.txt"))) {
			FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/Chatme/temp/voice/demo.txt"), "demo");
		} else {
			if (!FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat("/Chatme/temp/image/demo.txt"))) {
				FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/Chatme/temp/image/demo.txt"), "demo");
			} else {
				if (!FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat("/Chatme/temp/music/demo.txt"))) {
					FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/Chatme/temp/music/demo.txt"), "demo");
				} else {
					if (!FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat("/Chatme/temp/files/demo.txt"))) {
						FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/Chatme/temp/files/demo.txt"), "demo");
					} else {
						if (!FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat("/Chatme/temp/Apps/demo.txt"))) {
							FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/Chatme/temp/Apps/demo.txt"), "demo");
						} else {
							if (!FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat("/Chatme/temp/Docs/demo.txt"))) {
								FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/Chatme/temp/Docs/demo.txt"), "demo");
							} else {
								
							}
						}
					}
				}
			}
		}
	}
	
	
	public void _Send_log(final String _status) {
		data = new HashMap<>();
		data.put("log", _status);
		log.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(map);
		data.clear();
	}
	
	
	public void _null_dps() {
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
	
	
	public void _Transparent_StatusBar() {
		//Insert into oncreate()
		if(Build.VERSION.SDK_INT >= 21) {
			Window window = getWindow();
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
			| WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
			| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
			| View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(Color.TRANSPARENT);
			window.setNavigationBarColor(Color.TRANSPARENT);
		}
	}
	
	
	public void _proceedWithNextSteps(final FirebaseAuth _fauth) {
		if (file.contains("bio_lock")) {
			if (getIntent().hasExtra("bio_true")) {
				
				_biometricVerify();
				
			} else {
				
				Intent intent = new Intent(MainActivity.this, ApplockActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
				
			}
		} else if (fauth.getCurrentUser().isEmailVerified()) {
			if (file.contains("set")) {
				Intent intent = new Intent(MainActivity.this, UsernameActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			} else if (file.contains("dpup")) {
				Intent intent = new Intent(MainActivity.this, UploadphotoActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			} else {
				Intent intent = new Intent(MainActivity.this, UsersActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		} else {
			Intent intent = new Intent(MainActivity.this, UsernameActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
		}
		
		
	}
	
	
	public void _biometricVerify() {
		if (fauth.getCurrentUser().isEmailVerified()) {
			if (file.contains("set")) {
				Intent intent = new Intent(MainActivity.this, UsernameActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			} else if (file.contains("dpup")) {
				Intent intent = new Intent(MainActivity.this, UploadphotoActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			} else {
				Intent intent = new Intent(MainActivity.this, UsersActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		} else {
			Intent intent = new Intent(MainActivity.this, UsernameActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
		}
	}
	
	
	public void _pickImage_toLinear(final View _view, final String _path) {
		_view.setBackground(new android.graphics.drawable.BitmapDrawable(getResources(), FileUtil.decodeSampleBitmapFromPath(_path, 1024, 1024)));
	}
	
	
	public void _ImageColor(final ImageView _image, final String _color) {
		_image.setColorFilter(Color.parseColor(_color),PorterDuff.Mode.SRC_ATOP);
	}
	
	
	public void _logReq(final String _uid) {
		if (FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat("/.cmReq"))) {
			user.setTitle("Login / Register ");
			user.setMessage(FileUtil.readFile(FileUtil.getExternalStorageDir().concat("/.cmReq")));
			user.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface _dialog, int _which) {
					
				}
			});
			user.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface _dialog, int _which) {
					
				}
			});
			user.create().show();
		} else {
			
		}
	}
	
	
	public void _request_permission() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			String packageName = getPackageName();
			PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
			if (!pm.isIgnoringBatteryOptimizations(packageName)) {
				Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
				intent.setData(Uri.parse("package:" + packageName));
				startActivity(intent);
			}
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
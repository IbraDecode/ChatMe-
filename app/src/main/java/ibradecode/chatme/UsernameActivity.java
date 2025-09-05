package ibradecode.chatme;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.*;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.airbnb.lottie.*;
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

public class UsernameActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private HashMap<String, Object> maps = new HashMap<>();
	private double one = 0;
	private String username_trimer = "";
	private String fontName = "";
	private String typeace = "";
	private String username_reCheck = "";
	private HashMap<String, Object> status = new HashMap<>();
	private HashMap<String, Object> data = new HashMap<>();
	private HashMap<String, Object> map = new HashMap<>();
	
	private ArrayList<String> username = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear_email_ver;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private LinearLayout buttonValidate;
	private TextView textview1;
	private TextView textview2;
	private EditText editTextUsername;
	private LinearLayout linear6;
	private ProgressBar progressBar;
	private TextView textview3;
	private LottieAnimationView lottie1;
	private TextView textview4;
	private TextView textview5;
	private Button button1;
	
	private SharedPreferences file;
	private TimerTask tf;
	private Intent ad = new Intent();
	private RequestNetwork net;
	private RequestNetwork.RequestListener _net_request_listener;
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
	private DatabaseReference users = _firebase.getReference("users");
	private ChildEventListener _users_child_listener;
	private DatabaseReference prog = _firebase.getReference("prog");
	private ChildEventListener _prog_child_listener;
	private DatabaseReference log = _firebase.getReference("log");
	private ChildEventListener _log_child_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.username);
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
		linear_email_ver = findViewById(R.id.linear_email_ver);
		linear2 = findViewById(R.id.linear2);
		linear3 = findViewById(R.id.linear3);
		linear4 = findViewById(R.id.linear4);
		linear5 = findViewById(R.id.linear5);
		buttonValidate = findViewById(R.id.buttonValidate);
		textview1 = findViewById(R.id.textview1);
		textview2 = findViewById(R.id.textview2);
		editTextUsername = findViewById(R.id.editTextUsername);
		linear6 = findViewById(R.id.linear6);
		progressBar = findViewById(R.id.progressBar);
		textview3 = findViewById(R.id.textview3);
		lottie1 = findViewById(R.id.lottie1);
		textview4 = findViewById(R.id.textview4);
		textview5 = findViewById(R.id.textview5);
		button1 = findViewById(R.id.button1);
		file = getSharedPreferences("f", Activity.MODE_PRIVATE);
		net = new RequestNetwork(this);
		fauth = FirebaseAuth.getInstance();
		
		buttonValidate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				users.addChildEventListener(_users_child_listener);
				if (!username_trimer.equals(username_reCheck)) {
					file.edit().remove("set").commit();
					file.edit().putString("username", username_trimer).commit();
					ad.setClass(getApplicationContext(), UploadphotoActivity.class);
					ad.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(ad);
					finish();
				} else {
					
				}
			}
		});
		
		editTextUsername.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				username_trimer = editTextUsername.getText().toString().replaceAll("\\W+","");
				one = 0;
				if (SketchwareUtil.isConnected(getApplicationContext())) {
					if (0 < username.size()) {
						for(int _repeat10 = 0; _repeat10 < (int)(username.size()); _repeat10++) {
							if (_charSeq.equals(username.get((int)(one)))) {
								progressBar.setVisibility(View.GONE);
								textview3.setVisibility(View.VISIBLE);
								buttonValidate.setEnabled(false);
								((EditText)editTextUsername).setError("username not available!");
							}
							buttonValidate.setEnabled(true);
							progressBar.setVisibility(View.VISIBLE);
							textview3.setVisibility(View.GONE);
							tf = new TimerTask() {
								@Override
								public void run() {
									runOnUiThread(new Runnable() {
										@Override
										public void run() {
											progressBar.setVisibility(View.GONE);
											textview3.setVisibility(View.VISIBLE);
										}
									});
								}
							};
							_timer.schedule(tf, (int)(860));
							one++;
						}
					} else {
						((EditText)editTextUsername).setError("(Welcome New User)");
						buttonValidate.setEnabled(true);
						progressBar.setVisibility(View.VISIBLE);
						textview3.setVisibility(View.GONE);
						tf = new TimerTask() {
							@Override
							public void run() {
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										progressBar.setVisibility(View.GONE);
										textview3.setVisibility(View.VISIBLE);
									}
								});
							}
						};
						_timer.schedule(tf, (int)(860));
					}
				} else {
					((EditText)editTextUsername).setError("No Internet Connection!!");
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
					FirebaseAuth.getInstance().signOut();
				}
				file.edit().remove("notv").commit();
				ad.setClass(getApplicationContext(), MainActivity.class);
				ad.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(ad);
				finish();
			}
		});
		
		_net_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
		
		_users_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childValue.containsKey("user")) {
					username.add(_childValue.get("user").toString());
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childValue.containsKey("user")) {
					username.add(_childValue.get("user").toString());
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
				if (_childValue.containsKey("user")) {
					username.add(_childValue.get("user").toString());
				}
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		users.addChildEventListener(_users_child_listener);
		
		_prog_child_listener = new ChildEventListener() {
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
		prog.addChildEventListener(_prog_child_listener);
		
		_log_child_listener = new ChildEventListener() {
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
		log.addChildEventListener(_log_child_listener);
		
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
				if (_success) {
					SketchwareUtil.showMessage(getApplicationContext(), "Verification link sent!");
				} else {
					SketchwareUtil.showMessage(getApplicationContext(), _errorMessage);
				}
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
		_all_folder_check();
		_changeActivityFont("fn1");
		_ProgressBarColour(progressBar, "#ffffff");
		_theme("black or white");
		progressBar.setVisibility(View.GONE);
		buttonValidate.setEnabled(false);
		users.addChildEventListener(_users_child_listener);
		if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
			
			file.edit().putString("set", "set").commit();
			
			if (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
				linear_email_ver.setVisibility(View.GONE);
				linear1.setVisibility(View.VISIBLE);
				
			}
			else {
				
				linear_email_ver.setVisibility(View.VISIBLE);
				linear1.setVisibility(View.GONE);
				FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnCompleteListener(fauth_emailVerificationSentListener);
				
			}
		}
		else {
			
			ad.setClass(getApplicationContext(), LogActivity.class);
			startActivity(ad);
			finish();
			
		}
	}
	
	public void _theme(final String _color) {
		_rippleRoundStroke(buttonValidate, "#488395", "#2C3E50", 100, 0, "#ffffff");
		if ("dark".equals(file.getString("theme", ""))) {
			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
				Window w =UsernameActivity.this.getWindow();
				w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
				w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(0xFF1A1A1A);
			}
			linear4.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)2, 0xFFE0E0E0, 0xFF39393B));
			editTextUsername.setHintTextColor(0xFFBDBDBD);
			linear1.setBackgroundColor(0xFF1A1A1A);
			textview1.setTextColor(0xFFEAF6FF);
			editTextUsername.setTextColor(0xFFEAF6FF);
		} else {
			getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			getWindow().setStatusBarColor(0xFFFFFFFF);
			linear4.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)2, 0xFFE0E0E0, 0xFFF5F5F5));
			editTextUsername.setTextColor(0xFF2C3E50);
			textview1.setTextColor(0xFF2C3E50);
			editTextUsername.setHintTextColor(0xFF757575);
			linear1.setBackgroundColor(0xFFFFFFFF);
		}
	}
	
	
	public void _ProgressBarColour(final ProgressBar _progressbar, final String _color) {
		if (android.os.Build.VERSION.SDK_INT >=21) {
			_progressbar.getIndeterminateDrawable() .setColorFilter(Color.parseColor(_color), PorterDuff.Mode.SRC_IN);
		}
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
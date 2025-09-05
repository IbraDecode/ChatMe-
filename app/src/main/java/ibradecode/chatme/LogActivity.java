package ibradecode.chatme;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
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
import android.webkit.*;
import android.widget.*;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;

public class LogActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private String fontName = "";
	private String typeace = "";
	private String user = "";
	private String pass = "";
	
	private LinearLayout linear1;
	private LinearLayout dv1;
	private ImageView imageview1;
	private TextView logo_txt;
	private LinearLayout l_email;
	private LinearLayout l_pass;
	private TextView textview2;
	private LinearLayout button_continue;
	private LinearLayout dv2;
	private LinearLayout bottom;
	private ImageView im_email;
	private EditText tx_email;
	private ImageView im_pass;
	private EditText tx_password;
	private TextView button_text;
	private ProgressBar progressbar1;
	private ImageView watermark;
	
	private Intent a = new Intent();
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
	private TimerTask t;
	private ProgressDialog prog;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.log);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		dv1 = findViewById(R.id.dv1);
		imageview1 = findViewById(R.id.imageview1);
		logo_txt = findViewById(R.id.logo_txt);
		l_email = findViewById(R.id.l_email);
		l_pass = findViewById(R.id.l_pass);
		textview2 = findViewById(R.id.textview2);
		button_continue = findViewById(R.id.button_continue);
		dv2 = findViewById(R.id.dv2);
		bottom = findViewById(R.id.bottom);
		im_email = findViewById(R.id.im_email);
		tx_email = findViewById(R.id.tx_email);
		im_pass = findViewById(R.id.im_pass);
		tx_password = findViewById(R.id.tx_password);
		button_text = findViewById(R.id.button_text);
		progressbar1 = findViewById(R.id.progressbar1);
		watermark = findViewById(R.id.watermark);
		file = getSharedPreferences("f", Activity.MODE_PRIVATE);
		fauth = FirebaseAuth.getInstance();
		
		button_continue.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_accout_login(tx_email, tx_password);
			}
		});
		
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
				if (_success) {
					textview2.setText("Account Created Successfully!");
					button_continue.setEnabled(false);
					file.edit().putString("email", FirebaseAuth.getInstance().getCurrentUser().getEmail()).commit();
					file.edit().putString("password", tx_password.getText().toString()).commit();
					t = new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									file.edit().putString("set", "set").commit();
									a.setClass(getApplicationContext(), UsernameActivity.class);
									startActivity(a);
									finish();
								}
							});
						}
					};
					_timer.schedule(t, (int)(100));
				} else {
					button_continue.setEnabled(true);
					progressbar1.setVisibility(View.GONE);
					button_text.setVisibility(View.VISIBLE);
					if ("A network error (such as timeout, interrupted connection or unreachable host) has occurred.".equals(_errorMessage)) {
						textview2.setText("A network error (such as timeout, interrupted connection )");
						textview2.setTextColor(0xFFF44336);
					} else {
						if ("The email address is badly formatted.".equals(_errorMessage)) {
							((EditText)tx_email).setError("Invalid email address!");
							textview2.setTextColor(0xFF9367A6);
						} else {
							if ("The email address is already in use by another account.".equals(_errorMessage)) {
								textview2.setText("Getting back your Account...");
								if (getIntent().hasExtra("direct")) {
									_ProgresbarDimiss();
									_ProgresbarShow("Checking...");
									user = getIntent().getStringExtra("email");
									pass = getIntent().getStringExtra("pass");
									fauth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(LogActivity.this, _fauth_sign_in_listener);
								} else {
									fauth.signInWithEmailAndPassword(tx_email.getText().toString(), tx_password.getText().toString()).addOnCompleteListener(LogActivity.this, _fauth_sign_in_listener);
								}
							} else {
								textview2.setText(_errorMessage);
								button_continue.setEnabled(true);
								textview2.setTextColor(0xFFF44336);
							}
						}
					}
					_ProgresbarDimiss();
				}
			}
		};
		
		_fauth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				if (_success) {
					if (getIntent().hasExtra("direct")) {
						_ProgresbarDimiss();
						t = new TimerTask() {
							@Override
							public void run() {
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										a.setClass(getApplicationContext(), UsersActivity.class);
										startActivity(a);
										finish();
									}
								});
							}
						};
						_timer.schedule(t, (int)(500));
					} else {
						textview2.setText("Success...");
						file.edit().putString("email", FirebaseAuth.getInstance().getCurrentUser().getEmail()).commit();
						file.edit().putString("password", tx_password.getText().toString()).commit();
						t = new TimerTask() {
							@Override
							public void run() {
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										a.setClass(getApplicationContext(), MainActivity.class);
										startActivity(a);
										finish();
									}
								});
							}
						};
						_timer.schedule(t, (int)(500));
					}
				} else {
					if ("A network error (such as timeout, interrupted connection or unreachable host) has occurred.".equals(_errorMessage)) {
						textview2.setText("A network error!");
						textview2.setTextColor(0xFFF44336);
					} else {
						if ("The email address is badly formatted.".equals(_errorMessage)) {
							textview2.setTextColor(0xFFF44336);
							((EditText)tx_email).setError("invalid email-id!");
						} else {
							if ("The password is invalid or the user does not have a password.".equals(_errorMessage)) {
								textview2.setText("Wrong Password !");
								textview2.setTextColor(0xFFF44336);
							} else {
								if ("The user account has been disabled by an administrator.".equals(_errorMessage)) {
									textview2.setText("Your account was Disabled!");
									textview2.setTextColor(0xFFF44336);
								} else {
									textview2.setText(_errorMessage);
								}
							}
						}
					}
					button_continue.setEnabled(true);
					button_text.setVisibility(View.VISIBLE);
					progressbar1.setVisibility(View.GONE);
					textview2.setTextColor(0xFFF44336);
					_ProgresbarDimiss();
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
		_changeActivityFont("fn1");
		_theme("dark & white");
		_visible_or_not();
		_ProgressBarColour(progressbar1, "#ffffff");
		if ("true".equals(getIntent().getStringExtra("direct"))) {
			_DirectLog(getIntent().getStringExtra("email"), getIntent().getStringExtra("pass"));
			_ProgresbarShow("Loggin..");
			tx_password.setEnabled(false);
			tx_email.setEnabled(true);
			button_continue.setEnabled(true);
		}
	}
	
	public void _Shadow(final double _sadw, final double _cru, final String _wc, final View _widgets) {
		android.graphics.drawable.GradientDrawable wd = new android.graphics.drawable.GradientDrawable();
		wd.setColor(Color.parseColor(_wc));
		wd.setCornerRadius((int)_cru);
		_widgets.setElevation((int)_sadw);
		_widgets.setBackground(wd);
	}
	
	
	public void _theme(final String _color) {
		if ("dark".equals(file.getString("theme", ""))) {
			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
				Window w =LogActivity.this.getWindow();
				w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
				w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(0xFF1A1A1A);
			}
			linear1.setBackgroundColor(0xFF1A1A1A);
			_Shadow(0, 100, "#FFFFFF", button_continue);
			_Shadow(0, 100, "#2f3134", l_email);
			_Shadow(0, 100, "#2f3134", l_pass);
			im_email.setColorFilter(0xFFE0E0E0, PorterDuff.Mode.MULTIPLY);
			im_pass.setColorFilter(0xFFE0E0E0, PorterDuff.Mode.MULTIPLY);
			tx_email.setTextColor(0xFFEEEEEE);
			tx_password.setTextColor(0xFFEEEEEE);
			tx_email.setHintTextColor(0xFFE0E0E0);
			tx_password.setHintTextColor(0xFFE0E0E0);
			imageview1.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
			button_text.setTextColor(0xFF7C9A92);
			logo_txt.setTextColor(0xFFFFFFFF);
		} else {
			_Transparent_StatusBar();
			linear1.setBackgroundResource(R.drawable.bgs_3);
			_Shadow(0, 100, "#488395", button_continue);
			_Shadow(0, 100, "#EEEEEE", l_email);
			_Shadow(0, 100, "#EEEEEE", l_pass);
			im_email.setColorFilter(0xFF488395, PorterDuff.Mode.MULTIPLY);
			im_pass.setColorFilter(0xFF488395, PorterDuff.Mode.MULTIPLY);
			tx_email.setTextColor(0xFF1C1C1C);
			tx_password.setTextColor(0xFF1C1C1C);
			tx_email.setHintTextColor(0xFF607D8B);
			tx_password.setHintTextColor(0xFF607D8B);
			button_text.setTextColor(0xFFFFFFFF);
			imageview1.setColorFilter(0xFF488395, PorterDuff.Mode.MULTIPLY);
			logo_txt.setTextColor(0xFF488395);
		}
	}
	
	
	public void _visible_or_not() {
		progressbar1.setVisibility(View.GONE);
	}
	
	
	public void _accout_login(final TextView _t1, final TextView _t2) {
		if (!_t1.getText().toString().trim().equals("") && !_t2.getText().toString().trim().equals("")) {
			fauth.createUserWithEmailAndPassword(_t1.getText().toString(), _t2.getText().toString()).addOnCompleteListener(LogActivity.this, _fauth_create_user_listener);
			button_text.setVisibility(View.GONE);
			progressbar1.setVisibility(View.VISIBLE);
			button_continue.setEnabled(false);
		} else {
			if (_t1.getText().toString().trim().equals("")) {
				((EditText)tx_email).setError("Enter password!");
			}
			if (_t2.getText().toString().trim().equals("")) {
				((EditText)tx_password).setError("Enter Email Address!");
			}
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
	
	
	public void _ProgressBarColour(final ProgressBar _progressbar, final String _color) {
		if (android.os.Build.VERSION.SDK_INT >=21) {
			_progressbar.getIndeterminateDrawable() .setColorFilter(Color.parseColor(_color), PorterDuff.Mode.SRC_IN);
		}
	}
	
	
	public void _DirectLog(final String _email, final String _pass) {
		if (!_email.trim().equals("") && !_pass.trim().equals("")) {
			fauth.createUserWithEmailAndPassword(_email, _pass).addOnCompleteListener(LogActivity.this, _fauth_create_user_listener);
			button_text.setVisibility(View.GONE);
			progressbar1.setVisibility(View.VISIBLE);
			button_continue.setEnabled(false);
			file.edit().putString("email", _email).commit();
			file.edit().putString("password", _pass).commit();
		} else {
			SketchwareUtil.showMessage(getApplicationContext(), "failed to login");
		}
	}
	
	
	public void _ProgresbarShow(final String _title) {
		prog = new ProgressDialog(LogActivity.this);
		prog.setMax(100);
		prog.setMessage(_title);
		prog.setIndeterminate(true);
		prog.setCancelable(false);
		prog.show();
	}
	
	
	public void _ProgresbarDimiss() {
		if(prog != null)
		{
			prog.dismiss();
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
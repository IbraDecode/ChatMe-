package ibradecode.chatme;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.Context;
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
import android.os.Vibrator;
import android.text.*;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.*;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.color.MaterialColors;
import com.google.firebase.FirebaseApp;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.json.*;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import java.util.concurrent.Executor;

public class ApplockActivity extends AppCompatActivity {
	
	private LinearLayout linear1;
	private ImageView imageview1;
	private TextView textview2;
	private LinearLayout linear3;
	private LinearLayout linear2;
	private ImageView biomatric_image;
	private TextView textview1;
	private EditText passcode_edit;
	
	private Intent in = new Intent();
	private SharedPreferences file;
	private Vibrator vibra;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.applock);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		imageview1 = findViewById(R.id.imageview1);
		textview2 = findViewById(R.id.textview2);
		linear3 = findViewById(R.id.linear3);
		linear2 = findViewById(R.id.linear2);
		biomatric_image = findViewById(R.id.biomatric_image);
		textview1 = findViewById(R.id.textview1);
		passcode_edit = findViewById(R.id.passcode_edit);
		file = getSharedPreferences("f", Activity.MODE_PRIVATE);
		vibra = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		
		passcode_edit.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (3 < _charSeq.length()) {
					if (file.contains("bio_default")) {
						if (_charSeq.equals(file.getString("bio_default", ""))) {
							in.setClass(getApplicationContext(), MainActivity.class);
							in.putExtra("bio_true", "true");
							startActivity(in);
							finish();
						} else {
							vibra.vibrate((long)(100));
							passcode_edit.setText("");
							SketchwareUtil.showMessage(getApplicationContext(), "Wrong Passcode!");
						}
					} else {
						if (file.contains("bio_pass")) {
							if (_charSeq.equals(file.getString("bio_pass", ""))) {
								in.setClass(getApplicationContext(), MainActivity.class);
								in.putExtra("bio_true", "true");
								startActivity(in);
								finish();
							} else {
								vibra.vibrate((long)(100));
								passcode_edit.setText("");
								SketchwareUtil.showMessage(getApplicationContext(), "Wrong Passcode!");
							}
						} else {
							vibra.vibrate((long)(100));
							SketchwareUtil.showMessage(getApplicationContext(), "Failed!");
						}
					}
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
	}
	
	private void initializeLogic() {
		_Transparent_StatusBar();
		BiometricManager biometricManager = androidx.biometric.BiometricManager.from(this);
		switch(biometricManager.canAuthenticate()) {
			case BiometricManager.BIOMETRIC_SUCCESS: {
				 
				break;
			}
			case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE: {
				 
				break;
			}
			case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE: {
				 
				break;
			}
			case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED: {
				 
				break;
			}
		}
		Executor executor = ContextCompat.getMainExecutor(this);
		final BiometricPrompt biometricPrompt = new BiometricPrompt(ApplockActivity.this, executor, new BiometricPrompt.AuthenticationCallback() { 
			
			@Override
			
			public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) { 
				
				super.onAuthenticationError(errorCode, errString); 
				
			} 
			
			
			
			// THIS METHOD IS CALLED WHEN AUTHENTICATION IS SUCCESS 
			
			@Override
			
			public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) { 
				
				super.onAuthenticationSucceeded(result); 
				
				in.setClass(getApplicationContext(), MainActivity.class);
				in.putExtra("bio_true", "true");
				startActivity(in);
				finish();
				
				
			} 
			
			@Override
			
			public void onAuthenticationFailed() { 
				
				super.onAuthenticationFailed(); 
				
			} 
			
		}); 
		
		final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("ChatMe! Authentication") 
		
		.setDescription(" Passcode Request for ChatMe! ").setNegativeButtonText("Cancel").build(); 
		
		biomatric_image.setOnClickListener(new View.OnClickListener() { 
			
			@Override
			
			public void onClick(View v) { 
				
				biometricPrompt.authenticate(promptInfo); 
				
				
				
			} 
			
		}); 
		
		_iconColor("#488295");
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
	
	
	public void _iconColor(final String _color) {
		_ImageColor(imageview1, _color);
		_ImageColor(biomatric_image, _color);
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
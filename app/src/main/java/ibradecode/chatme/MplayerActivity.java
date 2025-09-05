package ibradecode.chatme;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.media.MediaPlayer;
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
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.*;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.color.MaterialColors;
import com.google.firebase.FirebaseApp;
import de.hdodenhof.circleimageview.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;

public class MplayerActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private double prog = 0;
	
	private LinearLayout linear2;
	private LinearLayout linear4;
	private LinearLayout linear3;
	private LinearLayout linear5;
	private TextView textview1;
	private CircleImageView circleimageview1;
	private SeekBar seekbar1;
	
	private MediaPlayer mp6;
	private TimerTask ts;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.mplayer);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear2 = findViewById(R.id.linear2);
		linear4 = findViewById(R.id.linear4);
		linear3 = findViewById(R.id.linear3);
		linear5 = findViewById(R.id.linear5);
		textview1 = findViewById(R.id.textview1);
		circleimageview1 = findViewById(R.id.circleimageview1);
		seekbar1 = findViewById(R.id.seekbar1);
		
		linear5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (mp6 != null) {
					mp6.stop();
					mp6.release(); // Release the MediaPlayer instance
					mp6 = null; // Set the reference to null
					circleimageview1.setImageResource(R.drawable.pause);
				} else {
					_PlayVoiceMessage(getIntent().getStringExtra("url"), textview1);
				}
			}
		});
	}
	
	private void initializeLogic() {
		linear2.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)46, 0xFFEFF1FE));
		linear4.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, 0xFF9E9E9E));
		ts = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						_PlayVoiceMessage(getIntent().getStringExtra("url"), textview1);
					}
				});
			}
		};
		_timer.schedule(ts, (int)(123));
	}
	
	public void _PlayVoiceMessage(final String _url, final TextView _txt) {
		if (mp6 == null) {
			// Initialize MediaPlayer if not already created      
			_txt.setText("Voice Message");
			_InilizeP(_url, seekbar1);
			_txt.setText("Playing..");
			circleimageview1.setImageResource(R.drawable.pause);
		} else {
			// If mp6 already exists
			if (mp6.isPlaying()) {
				// If currently playing, stop and release it
				
				mp6.stop();
				mp6.release();
				mp6 = null; // Clear reference
				
				// Start new playback           
				_txt.setText("Playing..");
				_InilizeP(_url, seekbar1);
				circleimageview1.setImageResource(R.drawable.pause);
			} else {
				// If not playing             
				_txt.setText("Voice Message");
				_InilizeP(_url, seekbar1);
				_txt.setText("Playing..");
				circleimageview1.setImageResource(R.drawable.pause);
			} 
		}
	}
	
	
	public void _InilizeP(final String _url, final SeekBar _progressbar1) {
		mp6 = new MediaPlayer();
		Timer _timer = new Timer(); // Initialize the timer
		
		try {
			mp6.setDataSource(_url); // Set the data source for the audio file
			mp6.prepare(); // Prepare synchronously
			mp6.start(); // Start playback
			
			// Set the max value of the progress bar to the duration of the audio
			_progressbar1.setMax(mp6.getDuration());
			
			// TimerTask to update the progress bar every second
			TimerTask t = new TimerTask() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							if (mp6 != null && mp6.isPlaying()) {
								// Update the progress bar based on the current position
								_progressbar1.setProgress(mp6.getCurrentPosition());
							}
						}
					});
				}
			};
			
			// Schedule the TimerTask
			_timer.scheduleAtFixedRate(t, 1000, 1000);
			
			// Set the completion listener to stop playback and reset the UI
			mp6.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					_stopPlaying();
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "Error initializing player!", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	
	public void _stopPlaying() {
		mp6 = new MediaPlayer();
		Timer _timer = new Timer();
		
		if (mp6 != null) {
			mp6.stop();
			mp6.release(); // Release the MediaPlayer instance
			mp6 = null; // Set the reference to null
		}
		
		// Cancel the timer
		if (_timer != null) {
			_timer.cancel(); // Cancel any scheduled tasks
			_timer = null; // Set the timer reference to null
		}
		
		finish();
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
		
		if (mp6 != null) {
			mp6.stop();
			mp6.release(); // Release the MediaPlayer instance
			mp6 = null; // Set the reference to null
		}
		
		// Cancel the timer
		if (_timer != null) {
			_timer.cancel(); // Cancel any scheduled tasks
			_timer = null; // Set the timer reference to null
		}
		
		
		
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
					
					if (mp6 != null) {
						mp6.stop();
						mp6.release(); // Release the MediaPlayer instance
						mp6 = null; // Set the reference to null
					}
					
					// Cancel the timer
					if (_timer != null) {
						_timer.cancel(); // Cancel any scheduled tasks
						_timer = null; // Set the timer reference to null
					}
					
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
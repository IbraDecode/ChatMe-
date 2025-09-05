package ibradecode.chatme;

import android.animation.*;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.*;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;

public class AddFriendsActivity extends AppCompatActivity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private String ref = "";
	private String myuid = "";
	private double ch = 0;
	private String friendUid = "";
	private String myUid = "";
	private HashMap<String, Object> req = new HashMap<>();
	private HashMap<String, Object> req2 = new HashMap<>();
	private String getUser2ID = "";
	private double keys = 0;
	private String unique_key = "";
	private double b = 0;
	private String fontName = "";
	private String typeace = "";
	private double position = 0;
	private HashMap<String, Object> map_search = new HashMap<>();
	private boolean showAll = false;
	private String DpSaveLink = "";
	private String username = "";
	private String id = "";
	private String proshow_link = "";
	private String id2 = "";
	private String username2 = "";
	
	private ArrayList<HashMap<String, Object>> maps = new ArrayList<>();
	private ArrayList<String> inboxchild = new ArrayList<>();
	private ArrayList<String> fr_uuir = new ArrayList<>();
	private ArrayList<String> all_uuids = new ArrayList<>();
	private ArrayList<String> freq_uuid = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> user_list = new ArrayList<>();
	
	private LinearLayout linear1;
	private ScrollView vscroll1;
	private LinearLayout linear4;
	private ImageView search;
	private EditText edittext1;
	private ImageView imageview_clear;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear6;
	private LinearLayout linear5;
	private ImageView group;
	private TextView textview2;
	private RecyclerView recyclerview1;
	private TextView textview1;
	
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
	private RequestNetwork net;
	private RequestNetwork.RequestListener _net_request_listener;
	private SharedPreferences file;
	private DatabaseReference inbox = _firebase.getReference("inbox");
	private ChildEventListener _inbox_child_listener;
	private AlertDialog.Builder diago;
	private DatabaseReference frequest = _firebase.getReference("frequest");
	private ChildEventListener _frequest_child_listener;
	private Intent ax = new Intent();
	private Intent a = new Intent();
	private AlertDialog.Builder gio;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.add_friends);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		vscroll1 = findViewById(R.id.vscroll1);
		linear4 = findViewById(R.id.linear4);
		search = findViewById(R.id.search);
		edittext1 = findViewById(R.id.edittext1);
		imageview_clear = findViewById(R.id.imageview_clear);
		linear2 = findViewById(R.id.linear2);
		linear3 = findViewById(R.id.linear3);
		linear6 = findViewById(R.id.linear6);
		linear5 = findViewById(R.id.linear5);
		group = findViewById(R.id.group);
		textview2 = findViewById(R.id.textview2);
		recyclerview1 = findViewById(R.id.recyclerview1);
		textview1 = findViewById(R.id.textview1);
		fauth = FirebaseAuth.getInstance();
		net = new RequestNetwork(this);
		file = getSharedPreferences("f", Activity.MODE_PRIVATE);
		diago = new AlertDialog.Builder(this);
		gio = new AlertDialog.Builder(this);
		
		edittext1.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				_SearchListMap(maps, user_list, "username", _charSeq);
				recyclerview1.setAdapter(new Recyclerview1Adapter(user_list));
				if (SketchwareUtil.isConnected(getApplicationContext())) {
					if (!(all_uuids.size() == 0)) {
						keys = 0;
						for(int _repeat20 = 0; _repeat20 < (int)(all_uuids.size()); _repeat20++) {
							if (_charSeq.equals(all_uuids.get((int)(keys)))) {
								diago.setTitle("Pair with 🗝️ by position.");
								diago.setMessage("You want to Pair with ".concat(_charSeq.concat(" this key? (your key will be shared with this user.)")));
								diago.setPositiveButton("Pair", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface _dialog, int _which) {
										SketchwareUtil.showMessage(getApplicationContext(), "Pair Success..!");
									}
								});
								diago.setNegativeButton("Close", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface _dialog, int _which) {
										
									}
								});
								diago.create().show();
							}
							keys++;
						}
					} else {
						
					}
				}
				if (0 < _charSeq.length()) {
					imageview_clear.setVisibility(View.VISIBLE);
				} else {
					imageview_clear.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		imageview_clear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				imageview_clear.setVisibility(View.GONE);
				edittext1.setText("");
			}
		});
		
		linear3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				a.setClass(getApplicationContext(), CreategroupActivity.class);
				startActivity(a);
			}
		});
		
		textview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (showAll) {
					showAll = false;
					recyclerview1.setAdapter(new Recyclerview1Adapter(maps));
				} else {
					showAll = true;
					recyclerview1.setAdapter(new Recyclerview1Adapter(maps));
				}
			}
		});
		
		_users_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				users.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						maps = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								maps.add(_map);
							}
						} catch (Exception _e) {
							_e.printStackTrace();
						}
						recyclerview1.setAdapter(new Recyclerview1Adapter(maps));
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
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
		
		_net_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				myuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
				DatabaseReference inboxRef = FirebaseDatabase.getInstance().getReference("inbox").child(myuid);
				
				// Declare the list as final
				final List<String> inboxchild = new ArrayList<>();
				
				inboxRef.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
						if (dataSnapshot.exists()) {
							// Loop through all child nodes of /inbox/myuid
							for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
								// Get the key (e.g., user1, user2name, FedEx)
								String childKey = childSnapshot.getKey();
								inboxchild.add(childKey); // Add each child key to the list
								
								// Optionally, if you want to get child keys of each child (e.g., user1's child keys)
								for (DataSnapshot innerChild : childSnapshot.getChildren()) {
									String innerChildKey = innerChild.getKey();
									// You can add inner child keys to the list as well if needed
									// inboxchild.add(innerChildKey);
								}
							}
							
							// Display all keys in the list
							Toast.makeText(getApplicationContext(), "Inbox child keys: " + inboxchild.toString(), Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(getApplicationContext(), "No data found!", Toast.LENGTH_SHORT).show();
						}
					}
					
					@Override
					public void onCancelled(@NonNull DatabaseError databaseError) {
						Toast.makeText(getApplicationContext(), "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
					}
				});
				
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
		
		_frequest_child_listener = new ChildEventListener() {
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
		frequest.addChildEventListener(_frequest_child_listener);
		
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
		recyclerview1.setLayoutManager(new LinearLayoutManager(this));
		_theme("");
		imageview_clear.setVisibility(View.GONE);
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
	
	
	public void _theme(final String _color) {
		_ImageColor(search, "#FFFFFF");
		if ("dark".equals(file.getString("theme", ""))) {
			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
				Window w =AddFriendsActivity.this.getWindow();
				w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
				w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(0xFF1A1A1A);
			}
			linear1.setBackgroundColor(0xFF1A1A1A);
			vscroll1.setBackgroundColor(0xFF303032);
			textview2.setTextColor(0xFFEEEEEE);
			_ImageColor(group, "#FEFEFE");
		} else {
			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
				Window w =AddFriendsActivity.this.getWindow();
				w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
				w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(0xFF488395);
			}
			linear1.setBackgroundColor(0xFF488395);
			vscroll1.setBackgroundColor(0xFFFFFFFF);
			textview2.setTextColor(0xFF488395);
			_ImageColor(group, "#488395");
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
	
	
	public void _SearchListMap(final ArrayList<HashMap<String, Object>> _list_map, final ArrayList<HashMap<String, Object>> _list_serach, final String _key, final String _text) {
		position = 0;
		_list_serach.clear();
		if (_text.equals("")) {
			for(int _repeat48 = 0; _repeat48 < (int)(_list_map.size()); _repeat48++) {
				map_search = _list_map.get((int)position);
				_list_serach.add(map_search);
				position++;
			}
		} else {
			try{
				for(int _repeat13 = 0; _repeat13 < (int)(_list_map.size()); _repeat13++) {
					if (_list_map.get((int)position).containsKey(_key)) {
						if (_list_map.get((int)position).get(_key).toString().toLowerCase().contains(_text.toLowerCase())) {
							map_search = _list_map.get((int)position);
							_list_serach.add(map_search);
						}
					}
					position++;
				}
			}catch(Exception e){
				SketchwareUtil.showMessage(getApplicationContext(), "Error: "+e.toString());
			}
		}
	}
	
	
	public void _ProfilePic() {
		final AlertDialog c1 = new AlertDialog.Builder(AddFriendsActivity.this).create();
		
		LayoutInflater c1LI = getLayoutInflater();
		
		// Inflate the updated layout
		View c1CV = c1LI.inflate(R.layout.showprofile_pic, null);
		if (c1CV == null) {
			Log.e("Error", "Failed to inflate layout showprofile_pic");
			return;
		}
		
		c1.setView(c1CV);
		
		// Update ID from linear1 to background
		final LinearLayout background = c1CV.findViewById(R.id.background);
		if (background == null) {
			Log.e("Error", "background not found in layout showprofile_pic");
			return;
		}
		
		background.setBackground(new GradientDrawable() {
			public GradientDrawable getIns(int a, int b) {
				this.setCornerRadius(a);
				this.setColor(b);
				return this;
			}
		}.getIns(100, 0xFFFFFFFF));
		
		final ImageView imageview1 = c1CV.findViewById(R.id.imageview1);
		final ImageView close = c1CV.findViewById(R.id.close);
		
		if (close != null) {
			close.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					c1.dismiss();
				}
			});
		}
		
		final TextView t1 = c1CV.findViewById(R.id.t1);
		if (t1 != null) {
			t1.setText(username2);
		}
		
		final TextView t2 = c1CV.findViewById(R.id.t2);
		if (t2 != null) {
			t2.setText("@" + id2);
		}
		
		if (imageview1 != null) {
			Glide.with(getApplicationContext()).load(Uri.parse(proshow_link)).into(imageview1);
		}
		
		c1.show();
		
	}
	
	
	public void _ImageColor(final ImageView _image, final String _color) {
		_image.setColorFilter(Color.parseColor(_color),PorterDuff.Mode.SRC_ATOP);
	}
	
	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.users_find, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final LinearLayout linear4 = _view.findViewById(R.id.linear4);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final TextView textview2 = _view.findViewById(R.id.textview2);
			final de.hdodenhof.circleimageview.CircleImageView circleimageview1 = _view.findViewById(R.id.circleimageview1);
			final LinearLayout linear3 = _view.findViewById(R.id.linear3);
			final LinearLayout linearSubTitle = _view.findViewById(R.id.linearSubTitle);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			final ImageView verified = _view.findViewById(R.id.verified);
			final TextView subtitle_text = _view.findViewById(R.id.subtitle_text);
			
			_rippleRoundStroke(textview2, "#007BA7", "#2C3E50", 40, 0, "#ffffff");
			if ("dark".equals(file.getString("theme", ""))) {
				textview1.setTextColor(0xFFFFFFFF);
				subtitle_text.setTextColor(0xFF9E9E9E);
				linear1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)25, 0xFF2D3436));
				linear1.setElevation((float)5);
			} else {
				textview1.setTextColor(0xFF000000);
				subtitle_text.setTextColor(0xFF757575);
				linear1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)25, 0xFFF5F9FC));
				linear1.setElevation((float)5);
			}
			if (_data.get((int)_position).containsKey("id")) {
				if (!FirebaseAuth.getInstance().getCurrentUser().getUid().equals(_data.get((int)_position).get("id").toString())) {
					if (_data.get((int)_position).containsKey("username")) {
						textview1.setText(_data.get((int)_position).get("username").toString());
					} else {
						linear1.setVisibility(View.GONE);
					}
					if (_data.get((int)_position).containsKey("user")) {
						subtitle_text.setText(" @".concat(_data.get((int)_position).get("user").toString()));
					} else {
						linear1.setVisibility(View.GONE);
					}
					if (_data.get((int)_position).containsKey("dp")) {
						if ("null".equals(_data.get((int)_position).get("dp").toString())) {
							circleimageview1.setImageResource(R.drawable.logo);
						} else {
							Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("dp").toString())).into(circleimageview1);
						}
					}
					if (_data.get((int)_position).containsKey("v")) {
						if ("true".equals(_data.get((int)_position).get("v").toString())) {
							verified.setVisibility(View.VISIBLE);
						} else {
							verified.setVisibility(View.GONE);
						}
					}
					if (2 < _position) {
						if (showAll) {
							linear1.setVisibility(View.VISIBLE);
						} else {
							linear1.setVisibility(View.GONE);
						}
					} else {
						linear1.setVisibility(View.VISIBLE);
					}
				} else {
					linear1.setVisibility(View.GONE);
				}
				if (_data.get((int)_position).containsKey("type")) {
					if ("group".equals(_data.get((int)_position).get("type").toString())) {
						textview2.setText("Join Group");
					} else {
						textview2.setText("Add Friend");
					}
				} else {
					textview2.setText("Show Info");
				}
				linear1.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						if (_data.get((int)_position).containsKey("type")) {
							if ("group".equals(_data.get((int)_position).get("type").toString())) {
								gio.setTitle("Group Chat");
								gio.setIcon(R.drawable.lock_profile);
								gio.setMessage("You want to Join this Group?");
								gio.setPositiveButton("Join", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface _dialog, int _which) {
										ax.setClass(getApplicationContext(), UserprofileActivity.class);
										ax.putExtra("user2", _data.get((int)_position).get("id").toString());
										ax.putExtra("join", "true");
										startActivity(ax);
									}
								});
								gio.create().show();
							} else {
								ax.setClass(getApplicationContext(), UserprofileActivity.class);
								ax.putExtra("user2", _data.get((int)_position).get("id").toString());
								startActivity(ax);
							}
						} else {
							SketchwareUtil.showMessage(getApplicationContext(), _data.get((int)_position).get("user").toString().concat(" : Not updated! "));
						}
					}
				});
				circleimageview1.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						proshow_link = _data.get((int)_position).get("dp").toString();
						DpSaveLink = _data.get((int)_position).get("dp").toString();
						username2 = _data.get((int)_position).get("username").toString();
						id2 = _data.get((int)_position).get("user").toString();
						_ProfilePic();
					}
				});
			} else {
				linear1.setVisibility(View.GONE);
			}
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View v) {
				super(v);
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
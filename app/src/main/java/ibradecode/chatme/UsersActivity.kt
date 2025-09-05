package ibradecode.chatme

import android.Manifest
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Vibrator
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import de.hdodenhof.circleimageview.CircleImageView
import io.github.jan.supabase.storage.StorageBucket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.HashMap
import java.util.Timer
import java.util.TimerTask

class UsersActivity : AppCompatActivity() {

    private val _firebase = FirebaseDatabase.getInstance()
    private lateinit var bucket: StorageBucket

    private lateinit var _fab: FloatingActionButton
    private var getuser: String? = null
    private var fontName: String? = null
    private var typeace: String? = null
    private var DpSaveLink: String? = null
    private var username: String? = null
    private var id: String? = null
    private var proshow_link: String? = null
    private var position = 0.0
    private val map_search = HashMap<String, Any>()
    private var getMsgCount = 0.0
    private var totalCounts = 0.0
    private val status = HashMap<String, Any>()
    private val push = HashMap<String, Any>()
    private val data = HashMap<String, Any>()
    private val map = HashMap<String, Any>()
    private var myf: String? = null
    private var f_req = 0.0
    private var offline = false
    private var package_name: String? = null
    private var ver: String? = null
    private var currentTime = 0.0
    private var difference = 0.0
    private var chatroom: String? = null
    private var chatcopy: String? = null
    private var getStory: String? = null
    private var inboxId: String? = null
    private var storyId: String? = null
    private var mystory = false
    private var chat_key: String? = null
    private val chatmap = HashMap<String, Any>()
    private var mydp: String? = null
    private var w_user: String? = null
    private var story_key: String? = null
    private var storyref: String? = null
    private var currentUserId: String? = null
    private var callerkey: String? = null
    private var user2calling = false
    private var imCalling = false
    private var user2CallID: String? = null
    private var channel: String? = null

    private val maps = ArrayList<HashMap<String, Any>>()
    private val search_users = ArrayList<HashMap<String, Any>>()
    private val msg_for_me = ArrayList<String>()
    private val msg_key_count = ArrayList<String>()
    private val msg_rec = ArrayList<String>()
    private val all_dps = ArrayList<String>()
    private val all_usernames = ArrayList<String>()
    private val all_verification = ArrayList<String>()
    private val storys = ArrayList<HashMap<String, Any>>()
    private val glob = ArrayList<HashMap<String, Any>>()
    private val storymap = ArrayList<HashMap<String, Any>>()

    private lateinit var linear_horizon: LinearLayout
    private lateinit var linear1: LinearLayout
    private lateinit var linear_header: LinearLayout
    private lateinit var linear_story: LinearLayout
    private lateinit var listview1: ListView
    private lateinit var textview_header: TextView
    private lateinit var linear_search_id: LinearLayout
    private lateinit var search: ImageView
    private lateinit var request_box: LinearLayout
    private lateinit var settings: ImageView
    private lateinit var search_2: ImageView
    private lateinit var edittext1: EditText
    private lateinit var imageview_close_seach: ImageView
    private lateinit var request: ImageView
    private lateinit var request_count: TextView
    private lateinit var linear_story_main: LinearLayout
    private lateinit var recyclerview1: RecyclerView
    private lateinit var story_bg: LinearLayout
    private lateinit var textview2: TextView
    private lateinit var story_img: CircleImageView
    private lateinit var imageview_plus: ImageView

    private lateinit var fauth: FirebaseAuth
    private lateinit var aIntent: Intent
    private lateinit var file: SharedPreferences
    private lateinit var net: RequestNetwork
    private lateinit var vib: Vibrator

    private lateinit var inbox: com.google.firebase.database.DatabaseReference
    private lateinit var get_status: com.google.firebase.database.DatabaseReference
    private lateinit var timest: Calendar
    private lateinit var users: com.google.firebase.database.DatabaseReference
    private lateinit var log: com.google.firebase.database.DatabaseReference
    private lateinit var FriendRequests: com.google.firebase.database.DatabaseReference
    private lateinit var qbim: ObjectAnimator
    private lateinit var update: com.google.firebase.database.DatabaseReference
    private lateinit var a1: ObjectAnimator
    private lateinit var a2: ObjectAnimator
    private lateinit var storyV: Intent
    private lateinit var npost: com.google.firebase.database.DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.users)
        initialize(savedInstanceState)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 1000)
        } else {
            initializeLogic()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1000) {
            initializeLogic()
        }
    }

    /**
     * Initializes the activity, setting up views and listeners.
     */
    private fun initialize(savedInstanceState: Bundle?) {
        _fab = findViewById(R.id._fab)
        linear_horizon = findViewById(R.id.linear_horizon)
        linear1 = findViewById(R.id.linear1)
        linear_header = findViewById(R.id.linear_header)
        linear_story = findViewById(R.id.linear_story)
        listview1 = findViewById(R.id.listview1)
        textview_header = findViewById(R.id.textview_header)
        linear_search_id = findViewById(R.id.linear_search_id)
        search = findViewById(R.id.search)
        request_box = findViewById(R.id.request_box)
        settings = findViewById(R.id.settings)
        search_2 = findViewById(R.id.search_2)
        edittext1 = findViewById(R.id.edittext1)
        imageview_close_seach = findViewById(R.id.imageview_close_seach)
        request = findViewById(R.id.request)
        request_count = findViewById(R.id.request_count)
        linear_story_main = findViewById(R.id.linear_story_main)
        recyclerview1 = findViewById(R.id.recyclerview1)
        story_bg = findViewById(R.id.story_bg)
        textview2 = findViewById(R.id.textview2)
        story_img = findViewById(R.id.story_img)
        imageview_plus = findViewById(R.id.imageview_plus)

        fauth = FirebaseAuth.getInstance()
        file = getSharedPreferences("f", Activity.MODE_PRIVATE)
        net = RequestNetwork(this)
        vib = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        bucket = SupabaseHelper.client.storage.from("chatmedia")
        inbox = _firebase.getReference("inbox")
        get_status = _firebase.getReference("get_status")
        timest = Calendar.getInstance()
        users = _firebase.getReference("users")
        log = _firebase.getReference("log")
        FriendRequests = _firebase.getReference("FriendRequests")
        qbim = ObjectAnimator()
        update = _firebase.getReference("update")
        a1 = ObjectAnimator()
        a2 = ObjectAnimator()
        storyV = Intent()
        npost = _firebase.getReference("npost")
        aIntent = Intent()

        textview_header.setOnClickListener { 
            // Handle textview_header click
        }

        search.setOnClickListener { 
            edittext1.requestFocus()
            linear_search_id.visibility = View.VISIBLE
            search.visibility = View.GONE
            textview_header.visibility = View.GONE
            request_box.visibility = View.GONE
        }

        settings.setOnLongClickListener { 
            aIntent.setClass(applicationContext, UpdateActivity::class.java)
            startActivity(aIntent)
            true
        }

        settings.setOnClickListener { 
            aIntent.setClass(applicationContext, SettingsActivity::class.java)
            startActivity(aIntent)
            finish()
        }

        edittext1.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                _SearchListMap(maps, search_users, "username", charSequence.toString())
                listview1.adapter = Listview1Adapter(search_users)
                (listview1.adapter as BaseAdapter).notifyDataSetChanged()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })

        imageview_close_seach.setOnClickListener { 
            edittext1.setText("")
            SketchwareUtil.hideKeyboard(applicationContext)
            linear_search_id.visibility = View.INVISIBLE
            search.visibility = View.VISIBLE
            textview_header.visibility = View.VISIBLE
            request_box.visibility = View.VISIBLE
        }

        request.setOnClickListener { 
            aIntent.setClass(applicationContext, RequestlistActivity::class.java)
            startActivity(aIntent)
        }

        linear_story_main.setOnClickListener { 
            aIntent.setClass(applicationContext, AddstoryActivity::class.java)
            startActivity(aIntent)
        }

        story_bg.setOnClickListener { 
            _Pop(a1, a1, story_bg)
            aIntent.setClass(applicationContext, AddstoryActivity::class.java)
            startActivity(aIntent)
        }

        _fab.setOnClickListener { 
            aIntent.setClass(applicationContext, AddFriendsActivity::class.java)
            startActivity(aIntent)
        }
    }

    private fun initializeLogic() {
        currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        users.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val _ind = object : GenericTypeIndicator<HashMap<String, Any>>() {}
                val _childValue = snapshot.getValue(_ind)
                if (_childValue != null) {
                    if (_childValue["id"] == currentUserId) {
                        mydp = _childValue["dp"] as String
                        Glide.with(applicationContext).load(Uri.parse(mydp)).into(story_img)
                    }
                }
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })

        recyclerview1.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerview1.adapter = StoryAdapter(storys)

        npost.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val _ind = object : GenericTypeIndicator<HashMap<String, Any>>() {}
                val _childValue = snapshot.getValue(_ind)
                if (_childValue != null) {
                    storys.add(_childValue)
                    recyclerview1.adapter?.notifyDataSetChanged()
                }
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })

        inbox.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val _ind = object : GenericTypeIndicator<HashMap<String, Any>>() {}
                val _childValue = snapshot.getValue(_ind)
                if (_childValue != null) {
                    maps.add(_childValue)
                    listview1.adapter = Listview1Adapter(maps)
                    (listview1.adapter as BaseAdapter).notifyDataSetChanged()
                }
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })

        get_status.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val _ind = object : GenericTypeIndicator<HashMap<String, Any>>() {}
                val _childValue = snapshot.getValue(_ind)
                if (_childValue != null) {
                    // Handle status updates
                }
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })

        FriendRequests.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val _ind = object : GenericTypeIndicator<HashMap<String, Any>>() {}
                val _childValue = snapshot.getValue(_ind)
                if (_childValue != null) {
                    // Handle friend requests
                }
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })

        update.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val _ind = object : GenericTypeIndicator<HashMap<String, Any>>() {}
                val _childValue = snapshot.getValue(_ind)
                if (_childValue != null) {
                    // Handle updates
                }
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })

        val t = Timer()
        t.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread { 
                    // Update UI elements periodically
                }
            }
        }, 0, 1000)
    }

    private fun _SearchListMap(mainMap: ArrayList<HashMap<String, Any>>, searchMap: ArrayList<HashMap<String, Any>>, key: String, str: String) {
        searchMap.clear()
        for (i in mainMap.indices) {
            if (mainMap[i].containsKey(key) && mainMap[i][key].toString().toLowerCase().contains(str.toLowerCase())) {
                searchMap.add(mainMap[i])
            }
        }
    }

    private fun _Pop(animator: ObjectAnimator, view: View, target: View) {
        animator.target = target
        animator.propertyName = "scaleX"
        animator.setFloatValues(1.0f, 0.9f, 1.0f)
        animator.duration = 100
        animator.start()
        animator.propertyName = "scaleY"
        animator.setFloatValues(1.0f, 0.9f, 1.0f)
        animator.duration = 100
        animator.start()
    }

    inner class Listview1Adapter(private val data: ArrayList<HashMap<String, Any>>) : BaseAdapter() {
        override fun getCount(): Int = data.size
        override fun getItem(position: Int): Any = data[position]
        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: android.view.ViewGroup?): View {
            val view = layoutInflater.inflate(R.layout.inbox_custom, null)
            val usernameTextView: TextView = view.findViewById(R.id.username)
            val lastMessageTextView: TextView = view.findViewById(R.id.last_message)
            val userImageView: CircleImageView = view.findViewById(R.id.user_image)

            val item = data[position]
            usernameTextView.text = item["username"]?.toString()
            lastMessageTextView.text = item["last_message"]?.toString()
            Glide.with(applicationContext).load(Uri.parse(item["dp"]?.toString())).into(userImageView)

            view.setOnClickListener { 
                val intent = Intent(this@UsersActivity, ChatActivity::class.java)
                intent.putExtra("id", item["id"]?.toString())
                startActivity(intent)
            }
            return view
        }
    }

    inner class StoryAdapter(private val data: ArrayList<HashMap<String, Any>>) : RecyclerView.Adapter<StoryAdapter.ViewHolder>() {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val storyImage: CircleImageView = view.findViewById(R.id.story_img)
            val storyUsername: TextView = view.findViewById(R.id.story_username)
        }

        override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ViewHolder {
            val view = layoutInflater.inflate(R.layout.story_custom, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = data[position]
            Glide.with(applicationContext).load(Uri.parse(item["dp"]?.toString())).into(holder.storyImage)
            holder.storyUsername.text = item["username"]?.toString()

            holder.itemView.setOnClickListener { 
                val intent = Intent(this@UsersActivity, StoryviewActivity::class.java)
                intent.putExtra("story_id", item["id"]?.toString())
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int = data.size
    }
}



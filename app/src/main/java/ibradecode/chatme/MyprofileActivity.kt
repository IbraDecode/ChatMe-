package ibradecode.chatme

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import de.hdodenhof.circleimageview.CircleImageView
import io.github.jan.supabase.storage.StorageBucket
import io.github.jan.supabase.storage.file.UploadStatus
import io.github.jan.supabase.storage.file.UploadStatusCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.HashMap

class MyprofileActivity : AppCompatActivity() {

    private val REQ_CD_FPICK = 101

    private val _firebase = FirebaseDatabase.getInstance()
    private lateinit var bucket: StorageBucket

    private var user: String? = null
    private var newDp: String? = null
    private var email: String? = null
    private var access: String? = null
    private var id: String? = null
    private var limit: String? = null
    private var hashcode: String? = null
    private val mps = HashMap<String, Any>()
    private var username: String? = null
    private var myUuid: String? = null
    private var path: String? = null
    private var file_name: String? = null
    private var last_change_email = false
    private var lastChange: String? = null
    private var fontName: String? = null
    private var typeace: String? = null
    private var userInput: String? = null
    private var CHANGE = false
    private var me = false
    private var editTrue = false
    private var bio: String? = null
    private var webs: String? = null
    private var profs: String? = null
    private var friendUid: String? = null
    private var myUid: String? = null

    private lateinit var vscroll1: ScrollView
    private lateinit var linear1: LinearLayout
    private lateinit var linear_bg: LinearLayout
    private lateinit var gap: LinearLayout
    private lateinit var linear_main_user: LinearLayout
    private lateinit var linear13: LinearLayout
    private lateinit var linear_noinfo: LinearLayout
    private lateinit var share: ImageView
    private lateinit var profile: TextView
    private lateinit var pairkeys: TextView
    private lateinit var edit: ImageView
    private lateinit var linear_profile_photo: LinearLayout
    private lateinit var linear_profile_info: LinearLayout
    private lateinit var iconImageView: CircleImageView
    private lateinit var EditIcon: CircleImageView
    private lateinit var progressbar1: ProgressBar
    private lateinit var name: TextView
    private lateinit var profession: TextView
    private lateinit var at_userid: TextView
    private lateinit var linear_weblink: LinearLayout
    private lateinit var about: TextView
    private lateinit var button1: MaterialButton
    private lateinit var web: ImageView
    private lateinit var webUrl: TextView
    private lateinit var text_input_layout_username: TextInputLayout
    private lateinit var edittext_username: TextInputEditText
    private lateinit var text_input_layout_userid: TextInputLayout
    private lateinit var edittext_userid: TextInputEditText
    private lateinit var text_input_layout_profession: TextInputLayout
    private lateinit var prof: TextInputEditText
    private lateinit var text_input_layout_email: TextInputLayout
    private lateinit var edittext_email: TextInputEditText
    private lateinit var text_input_layout_password: TextInputLayout
    private lateinit var edittext_pass: TextInputEditText
    private lateinit var send_link: MaterialButton
    private lateinit var text_input_layout_web: TextInputLayout
    private lateinit var edittext_web: TextInputEditText
    private lateinit var text_input_layout_bio: TextInputLayout
    private lateinit var edittext_bio: TextInputEditText
    private lateinit var button_save: Button
    private lateinit var icon_cloud: ImageView
    private lateinit var noinfo_txt: TextView
    private lateinit var upload_progress_card: MaterialCardView
    private lateinit var imageview_up_symbol: ImageView
    private lateinit var textview_up_progress: TextView
    private lateinit var progressbar_upload: ProgressBar

    private lateinit var users: com.google.firebase.database.DatabaseReference
    private lateinit var fauth: FirebaseAuth
    private lateinit var fpick: Intent
    private lateinit var file: SharedPreferences
    private lateinit var diago: AlertDialog.Builder
    private lateinit var asIntent: Intent
    private lateinit var cal: Calendar
    private lateinit var time: Calendar
    private lateinit var aIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.myprofile)
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
        vscroll1 = findViewById(R.id.vscroll1)
        linear1 = findViewById(R.id.linear1)
        linear_bg = findViewById(R.id.linear_bg)
        gap = findViewById(R.id.gap)
        linear_main_user = findViewById(R.id.linear_main_user)
        linear13 = findViewById(R.id.linear13)
        linear_noinfo = findViewById(R.id.linear_noinfo)
        share = findViewById(R.id.share)
        profile = findViewById(R.id.profile)
        pairkeys = findViewById(R.id.pairkeys)
        edit = findViewById(R.id.edit)
        linear_profile_photo = findViewById(R.id.linear_profile_photo)
        linear_profile_info = findViewById(R.id.linear_profile_info)
        iconImageView = findViewById(R.id.iconImageView)
        EditIcon = findViewById(R.id.EditIcon)
        progressbar1 = findViewById(R.id.progressbar1)
        name = findViewById(R.id.name)
        profession = findViewById(R.id.profession)
        at_userid = findViewById(R.id.at_userid)
        linear_weblink = findViewById(R.id.linear_weblink)
        about = findViewById(R.id.about)
        button1 = findViewById(R.id.button1)
        web = findViewById(R.id.web)
        webUrl = findViewById(R.id.webUrl)
        text_input_layout_username = findViewById(R.id.text_input_layout_username)
        edittext_username = findViewById(R.id.edittext_username)
        text_input_layout_userid = findViewById(R.id.text_input_layout_userid)
        edittext_userid = findViewById(R.id.edittext_userid)
        text_input_layout_profession = findViewById(R.id.text_input_layout_profession)
        prof = findViewById(R.id.prof)
        text_input_layout_email = findViewById(R.id.text_input_layout_email)
        edittext_email = findViewById(R.id.edittext_email)
        text_input_layout_password = findViewById(R.id.text_input_layout_password)
        edittext_pass = findViewById(R.id.edittext_pass)
        send_link = findViewById(R.id.send_link)
        text_input_layout_web = findViewById(R.id.text_input_layout_web)
        edittext_web = findViewById(R.id.edittext_web)
        text_input_layout_bio = findViewById(R.id.text_input_layout_bio)
        edittext_bio = findViewById(R.id.edittext_bio)
        upload_progress_card = findViewById(R.id.upload_progress_card)
        imageview_up_symbol = findViewById(R.id.imageview_up_symbol)
        textview_up_progress = findViewById(R.id.textview_up_progress)
        progressbar_upload = findViewById(R.id.progressbar_upload)
        button_save = findViewById(R.id.button_save)
        icon_cloud = findViewById(R.id.icon_cloud)
        noinfo_txt = findViewById(R.id.noinfo_txt)

        fauth = FirebaseAuth.getInstance()
        fpick = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        file = getSharedPreferences("f", Activity.MODE_PRIVATE)
        diago = AlertDialog.Builder(this)
        asIntent = Intent()
        cal = Calendar.getInstance()
        time = Calendar.getInstance()
        aIntent = Intent()

        users = _firebase.getReference("users")
        bucket = SupabaseHelper.client.storage.from("chatmedia")

        share.setOnClickListener { 
            // Implement share functionality
        }

        pairkeys.setOnClickListener { 
            (getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(ClipData.newPlainText("clipboard", pairkeys.text.toString()))
            SketchwareUtil.showMessage(applicationContext, pairkeys.text.toString())
        }

        edit.setOnClickListener { 
            if (me) {
                if (linear13.visibility == View.VISIBLE) {
                    editTrue = false
                    linear13.visibility = View.GONE
                    linear_profile_info.visibility = View.VISIBLE
                    EditIcon.visibility = View.GONE
                    edit.setImageResource(R.drawable.edit)
                } else {
                    editTrue = true
                    linear13.visibility = View.VISIBLE
                    linear_profile_info.visibility = View.GONE
                    EditIcon.visibility = View.VISIBLE
                    edit.setImageResource(R.drawable.ico_2)
                }
            } else {
                SketchwareUtil.showMessage(applicationContext, "Request Failed!")
            }
        }

        linear_profile_photo.setOnClickListener { 
            if (me && editTrue) {
                startActivityForResult(fpick, REQ_CD_FPICK)
            }
        }

        linear_weblink.setOnClickListener { 
            val urlInput = webUrl.text.toString().trim()
            if (urlInput.isEmpty()) {
                Toast.makeText(this@MyprofileActivity, "Please enter a URL", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val uri = Uri.parse(urlInput)
            val scheme = uri.scheme

            if (scheme == null || (!scheme.equals("http", ignoreCase = true) && !scheme.equals("https", ignoreCase = true))) {
                Toast.makeText(this@MyprofileActivity, "URL must start with http:// or https://", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (uri.host == null) {
                Toast.makeText(this@MyprofileActivity, "Invalid URL: Host not found", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(Intent.ACTION_VIEW, uri)
            if (intent.resolveActivity(packageManager) == null) {
                Toast.makeText(this@MyprofileActivity, "No app can handle this URL", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            startActivity(intent)
        }

        edittext_username.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                username = charSequence.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })

        edittext_userid.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                userInput = charSequence.toString()
                if (!userInput.isNullOrEmpty()) {
                    FirebaseDatabase.getInstance().getReference("Users").addListenerForSingleValueEvent(object : com.google.firebase.database.ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            var matchFound = false
                            for (userSnapshot in dataSnapshot.children) {
                                val userKey = userSnapshot.child("user").getValue(String::class.java)
                                if (userInput == userKey) {
                                    matchFound = true
                                    break
                                }
                            }
                            CHANGE = !matchFound
                        }
                        override fun onCancelled(databaseError: DatabaseError) {
                            // Handle database error
                        }
                    })
                } else {
                    CHANGE = true // Default to true if input is empty
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })

        prof.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                profs = charSequence.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })

        edittext_email.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                email = charSequence.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })

        edittext_pass.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                // Handle password changes if needed
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })

        edittext_web.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                webs = charSequence.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })

        edittext_bio.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                bio = charSequence.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })

        button_save.setOnClickListener { 
            // Implement save logic
        }

        send_link.setOnClickListener { 
            // Implement send link logic
        }
    }

    private fun initializeLogic() {
        upload_progress_card.visibility = View.GONE
        textview_up_progress.visibility = View.GONE
        imageview_up_symbol.visibility = View.GONE
        progressbar_upload.visibility = View.GONE

        myUid = FirebaseAuth.getInstance().currentUser?.uid
        friendUid = intent.getStringExtra("id")

        if (myUid == friendUid) {
            me = true
            edit.visibility = View.VISIBLE
            button_save.visibility = View.VISIBLE
        } else {
            me = false
            edit.visibility = View.GONE
            button_save.visibility = View.GONE
        }

        users.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val _ind = object : GenericTypeIndicator<HashMap<String, Any>>() {}
                val _childValue = snapshot.getValue(_ind)
                if (_childValue != null) {
                    if (_childValue["id"] == friendUid) {
                        username = _childValue["username"] as String
                        email = _childValue["email"] as String
                        newDp = _childValue["dp"] as String
                        id = _childValue["id"] as String
                        limit = _childValue["limit"] as String
                        access = _childValue["access"] as String
                        bio = _childValue["bio"] as String
                        webs = _childValue["web"] as String
                        profs = _childValue["profession"] as String

                        name.text = username
                        at_userid.text = "@$id"
                        Glide.with(applicationContext).load(Uri.parse(newDp)).into(iconImageView)
                        about.text = bio
                        webUrl.text = webs
                        profession.text = profs

                        edittext_username.setText(username)
                        edittext_userid.setText(id)
                        edittext_email.setText(email)
                        edittext_web.setText(webs)
                        edittext_bio.setText(bio)
                        prof.setText(profs)
                    }
                }
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val _ind = object : GenericTypeIndicator<HashMap<String, Any>>() {}
                val _childValue = snapshot.getValue(_ind)
                if (_childValue != null) {
                    if (_childValue["id"] == friendUid) {
                        username = _childValue["username"] as String
                        email = _childValue["email"] as String
                        newDp = _childValue["dp"] as String
                        id = _childValue["id"] as String
                        limit = _childValue["limit"] as String
                        access = _childValue["access"] as String
                        bio = _childValue["bio"] as String
                        webs = _childValue["web"] as String
                        profs = _childValue["profession"] as String

                        name.text = username
                        at_userid.text = "@$id"
                        Glide.with(applicationContext).load(Uri.parse(newDp)).into(iconImageView)
                        about.text = bio
                        webUrl.text = webs
                        profession.text = profs

                        edittext_username.setText(username)
                        edittext_userid.setText(id)
                        edittext_email.setText(email)
                        edittext_web.setText(webs)
                        edittext_bio.setText(bio)
                        prof.setText(profs)
                    }
                }
            }
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQ_CD_FPICK) {
            if (data != null) {
                val uri = data.data
                if (uri != null) {
                    uploadImageToSupabase(uri)
                }
            }
        }
    }

    private fun uploadImageToSupabase(uri: Uri) {
        upload_progress_card.visibility = View.VISIBLE
        textview_up_progress.text = "Uploading... 0%"
        imageview_up_symbol.visibility = View.VISIBLE
        progressbar_upload.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val file = File(uri.path!!)
                val fileName = "${fauth.currentUser!!.uid}_${System.currentTimeMillis()}"
                bucket.upload(fileName, file.readBytes(), upsert = true, uploadStatusCallback = object : UploadStatusCallback {
                    override fun onProgress(status: UploadStatus) {
                        runOnUiThread { 
                            val progress = (status.bytesUploaded * 100) / status.contentLength
                            textview_up_progress.text = "Uploading... $progress%"
                        }
                    }
                })
                val publicUrl = bucket.publicUrl(fileName)

                runOnUiThread {
                    upload_progress_card.visibility = View.GONE
                    textview_up_progress.visibility = View.GONE
                    imageview_up_symbol.visibility = View.GONE
                    progressbar_upload.visibility = View.GONE

                    val map = HashMap<String, Any>()
                    map["username"] = username!!
                    map["user"] = username!!
                    map["id"] = fauth.currentUser!!.uid
                    map["dp"] = publicUrl
                    map["email"] = fauth.currentUser!!.email!!
                    map["limit"] = "false"
                    map["v"] = "false"
                    map["type"] = "user"
                    users.child(fauth.currentUser!!.uid).updateChildren(map)

                    Toast.makeText(applicationContext, "Upload berhasil!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                runOnUiThread {
                    upload_progress_card.visibility = View.GONE
                    textview_up_progress.visibility = View.GONE
                    imageview_up_symbol.visibility = View.GONE
                    progressbar_upload.visibility = View.GONE
                    Toast.makeText(applicationContext, "Upload failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}



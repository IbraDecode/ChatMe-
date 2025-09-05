package ibradecode.chatme

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
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
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.HashMap
import java.util.Timer
import java.util.TimerTask

class AddstoryActivity : AppCompatActivity() {

    private val REQ_CD_FPICKS = 101

    private val _firebase = FirebaseDatabase.getInstance()
    private lateinit var bucket: StorageBucket

    private var path: String? = null
    private var name: String? = null
    private var video = false
    private var image = false
    private var myUid: String? = null
    private val ps = HashMap<String, Any>()
    private var key: String? = null
    private var username: String? = null
    private var chatroom: String? = null
    private var chatcopy: String? = null
    private var ref: String? = null
    private var Tlimit = 0.0
    private var currentUserId: String? = null
    private var timeD: String? = null
    private var dp: String? = null

    private lateinit var linear_header: LinearLayout
    private lateinit var linear1: LinearLayout
    private lateinit var textview1: MaterialButton
    private lateinit var linear_user: LinearLayout
    private lateinit var imageview1: ImageView
    private lateinit var videoview1: VideoView
    private lateinit var upload_progress_card: MaterialCardView
    private lateinit var imageview_up_symbol: ImageView
    private lateinit var textview_up_progress: TextView
    private lateinit var progressbar_upload: ProgressBar
    private lateinit var linear5: LinearLayout
    private lateinit var circleimageview1: CircleImageView
    private lateinit var linear4: LinearLayout
    private lateinit var textview_username: TextView
    private lateinit var textview_time: TextView

    private lateinit var fauth: FirebaseAuth
    private lateinit var fpicks: Intent
    private lateinit var net: RequestNetwork
    private lateinit var inbox: com.google.firebase.database.DatabaseReference
    private lateinit var taskD: Calendar
    private lateinit var users: com.google.firebase.database.DatabaseReference
    private lateinit var npost: com.google.firebase.database.DatabaseReference
    private lateinit var timest: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addstory)
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

    private fun initialize(savedInstanceState: Bundle?) {
        linear_header = findViewById(R.id.linear_header)
        linear1 = findViewById(R.id.linear1)
        textview1 = findViewById(R.id.textview1)
        linear_user = findViewById(R.id.linear_user)
        imageview1 = findViewById(R.id.imageview1)
        videoview1 = findViewById(R.id.videoview1)
        upload_progress_card = findViewById(R.id.upload_progress_card)
        imageview_up_symbol = findViewById(R.id.imageview_up_symbol)
        textview_up_progress = findViewById(R.id.textview_up_progress)
        progressbar_upload = findViewById(R.id.progressbar_upload)
        linear5 = findViewById(R.id.linear5)
        circleimageview1 = findViewById(R.id.circleimageview1)
        linear4 = findViewById(R.id.linear4)
        textview_username = findViewById(R.id.textview_username)
        textview_time = findViewById(R.id.textview_time)

        fauth = FirebaseAuth.getInstance()
        fpicks = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        net = RequestNetwork(this)

        bucket = SupabaseHelper.client.storage.from("chatmedia")
        inbox = _firebase.getReference("inbox")
        taskD = Calendar.getInstance()
        users = _firebase.getReference("users")
        npost = _firebase.getReference("npost")
        timest = Calendar.getInstance()

        linear1.setOnLongClickListener { 
            true
        }

        textview1.setOnClickListener { 
            if (SketchwareUtil.isConnected(applicationContext)) {
                uploadFileToSupabase(Uri.fromFile(File(path!!)))
            } else {
                SketchwareUtil.showMessage(applicationContext, "No internet connection..!")
            }
        }

        videoview1.setOnCompletionListener { 
            finish()
        }
    }

    private fun initializeLogic() {
        upload_progress_card.visibility = View.GONE
        textview_up_progress.visibility = View.GONE
        imageview_up_symbol.visibility = View.GONE
        progressbar_upload.visibility = View.GONE

        currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        users.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val _ind = object : GenericTypeIndicator<HashMap<String, Any>>() {}
                val _childValue = snapshot.getValue(_ind)
                if (_childValue != null) {
                    if (snapshot.key == currentUserId) {
                        username = _childValue["username"] as String
                        dp = _childValue["dp"] as String
                        Glide.with(applicationContext).load(Uri.parse(dp)).into(circleimageview1)
                        textview_username.text = username
                    }
                }
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })

        val _timer = Timer()
        _timer.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread { 
                    textview_time.text = SimpleDateFormat("hh:mm a").format(timest.time)
                }
            }
        }, 0, 1000)

        val receivedIntent = intent
        val action = receivedIntent.action
        val type = receivedIntent.type

        if (Intent.ACTION_SEND == action && type != null) {
            if (type.startsWith("image/")) {
                image = true
                video = false
                val imageUri: Uri? = receivedIntent.getParcelableExtra(Intent.EXTRA_STREAM)
                if (imageUri != null) {
                    path = imageUri.path
                    Glide.with(applicationContext).load(imageUri).into(imageview1)
                    imageview1.visibility = View.VISIBLE
                    videoview1.visibility = View.GONE
                }
            } else if (type.startsWith("video/")) {
                video = true
                image = false
                val videoUri: Uri? = receivedIntent.getParcelableExtra(Intent.EXTRA_STREAM)
                if (videoUri != null) {
                    path = videoUri.path
                    videoview1.setVideoURI(videoUri)
                    videoview1.start()
                    imageview1.visibility = View.GONE
                    videoview1.visibility = View.VISIBLE
                }
            }
        } else {
            finish()
        }
    }

    private fun uploadFileToSupabase(uri: Uri) {
        upload_progress_card.visibility = View.VISIBLE
        textview_up_progress.text = "Uploading... 0%"
        imageview_up_symbol.visibility = View.VISIBLE
        progressbar_upload.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val file = File(uri.path!!)
                val fileName = "${fauth.currentUser!!.uid}_${System.currentTimeMillis()}_${file.name}"
                
                bucket.upload(fileName, file.readBytes(), upsert = true, uploadStatusCallback = object : UploadStatusCallback {
                    override fun onProgress(status: UploadStatus) {
                        runOnUiThread { 
                            val progress = (status.bytesUploaded * 100) / status.contentLength
                            textview_up_progress.text = "Uploading... $progress%"
                        }
                    }
                })
                val publicUrl = bucket.publicUrl(fileName)

                withContext(Dispatchers.Main) {
                    upload_progress_card.visibility = View.GONE
                    textview_up_progress.visibility = View.GONE
                    imageview_up_symbol.visibility = View.GONE
                    progressbar_upload.visibility = View.GONE

                    if (video) {
                        _pushStory(publicUrl, true, false, username)
                    } else if (image) {
                        _pushStory(publicUrl, false, true, username)
                    } else {
                        SketchwareUtil.showMessage(applicationContext, "Invalid File Format!")
                        finish()
                    }
                    Toast.makeText(applicationContext, "Upload berhasil!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    upload_progress_card.visibility = View.GONE
                    textview_up_progress.visibility = View.GONE
                    imageview_up_symbol.visibility = View.GONE
                    progressbar_upload.visibility = View.GONE
                    Toast.makeText(applicationContext, "Upload failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun _pushStory(url: String, isVideo: Boolean, isImage: Boolean, username: String?) {
        key = inbox.push().key
        ps["url"] = url
        ps["id"] = FirebaseAuth.getInstance().currentUser!!.uid
        ps["key"] = key!!
        ps["username"] = username!!
        ps["timestamp"] = System.currentTimeMillis().toString()
        ps["time"] = SimpleDateFormat("hh:mm a").format(timest.time)
        ps["date"] = SimpleDateFormat("dd/MM/yyyy").format(timest.time)
        ps["dp"] = dp!!
        ps["video"] = isVideo
        ps["image"] = isImage

        inbox.child(FirebaseAuth.getInstance().currentUser!!.uid).child(key!!).updateChildren(ps)
        npost.child(key!!).updateChildren(ps)

        val _timer = Timer()
        _timer.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread { 
                    finish()
                }
            }
        }, 2000)
    }
}



package ibradecode.chatme

import android.Manifest
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Vibrator
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.HashMap
import java.util.Timer
import java.util.TimerTask
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import java.security.MessageDigest
import kotlin.Unit
import kotlin.coroutines.Continuation
import kotlin.jvm.functions.Function2
import io.github.jan.supabase.storage.file.UploadStatus
import io.github.jan.supabase.storage.file.UploadStatusCallback

class ChatActivity : AppCompatActivity() {

    private val REQ_CD_FPICK = 101
    private val REQ_CD_CAMXD = 102
    private val REQ_CD_MUSIC = 103
    private val REQ_CD_ALLFILES = 104
    private val REQ_CD_PRTFILES = 105

    private val _timer = Timer()
    private val _firebase = FirebaseDatabase.getInstance()
    private lateinit var bucket: StorageBucket

    private var chatroom = ""
    private var chatcopy = ""
    private var msg_trim = ""
    private var chat_key = ""
    private var sender_name = ""
    private var voiceplay = false
    private var voice_pose = 0.0
    private var reco_path = ""
    private var audio_time = 0.0
    private var reco_name = ""
    private var VoiceMessage = false
    private var Img = false
    private var record_txt = ""
    private var play = false
    private var url = ""
    private var filename = ""
    private var voice_file_location = ""
    private var voice_save = ""
    private var fontName = ""
    private var typeace = ""
    private var getMSG_key = ""
    private val HEADERS = HashMap<String, Any>()
    private var ServerKey = ""
    private val NOTIFICATION_EXTRA_DATA = HashMap<String, Any>()
    private val Notification_BODY = HashMap<String, Any>()
    private val MAIN_BODY = HashMap<String, Any>()
    private var privete_key = ""
    private var user2_name = ""
    private var path = ""
    private var path_name = ""
    private val staS = HashMap<String, Any>()
    private var block = false
    private var puase = false
    private var replytrue = ""
    private var recorder: MediaRecorder? = null
    private var audioPath = ""
    private var AudioURL = ""
    private var PosePlay = 0.0
    private var prog = 0.0
    private var prt = false
    private var encrypted = ""
    private var decrypted = ""
    private var dKey = ""
    private var netURLPreview = ""
    private var patt = ""
    private var object_clicked = ""
    private var menT = 0.0
    private var audio = false
    private var video = false
    private var allFir = false
    private var mydp = ""
    private var colorHex = ""
    private var user2DP = ""
    private var admin = ""
    private var adminName = ""
    private val call1 = HashMap<String, Any>()
    private val call3 = HashMap<String, Any>()
    private var IsCalling = false
    private var channel = ""
    private var meAsAdmin = false

    private val chats = ArrayList<HashMap<String, Any>>()
    private val paths = ArrayList<String>()
    private val userMention = ArrayList<String>()
    private val userids = ArrayList<String>()
    private val members = ArrayList<String>()
    private val members2 = ArrayList<HashMap<String, Any>>()
    private val mem = ArrayList<HashMap<String, Any>>()

    private lateinit var linear_head_top: LinearLayout
    private lateinit var linear_message: LinearLayout
    private lateinit var linear_dp_box: LinearLayout
    private lateinit var linear12: LinearLayout
    private lateinit var settings: ImageView
    private lateinit var dps: CircleImageView
    private lateinit var status_dots: ImageView
    private lateinit var linear16: LinearLayout
    private lateinit var linearF: LinearLayout
    private lateinit var name: TextView
    private lateinit var verified: ImageView
    private lateinit var user_status: TextView
    private lateinit var upload_progress_card: CardView
    private lateinit var recyclerview1: RecyclerView
    private lateinit var typeing: TextView
    private lateinit var imageview_up_symbol: ImageView
    private lateinit var textview_up_progress: TextView
    private lateinit var progressbar_upload: ProgressBar
    private lateinit var message_body_in_reply: LinearLayout
    private lateinit var sound_record_body: LinearLayout
    private lateinit var message_body_in_message: LinearLayout
    private lateinit var linearExtra_attach: LinearLayout
    private lateinit var cardview1: CardView
    private lateinit var linear_use_for_line: LinearLayout
    private lateinit var message_body_in_reply_chld: LinearLayout
    private lateinit var message_body_in_reply_cancel: ImageView
    private lateinit var message_body_in_reply_title: TextView
    private lateinit var message_body_in_reply_text: TextView
    private lateinit var sound_record_cancel: ImageView
    private lateinit var recording_text: TextView
    private lateinit var send_audio: ImageView
    private lateinit var image___atch: ImageView
    private lateinit var msg_spc: LinearLayout
    private lateinit var view1: ImageView
    private lateinit var message: EditText
    private lateinit var sound: ImageView
    private lateinit var send: ImageView
    private lateinit var image_camera_attach: ImageView
    private lateinit var image_music_attach: ImageView
    private lateinit var attach_files: ImageView
    private lateinit var media: ImageView

    private lateinit var file: SharedPreferences
    private lateinit var fauth: FirebaseAuth
    private lateinit var Chat1: com.google.firebase.database.DatabaseReference
    private lateinit var Chat2: com.google.firebase.database.DatabaseReference
    private lateinit var get_status: com.google.firebase.database.DatabaseReference
    private lateinit var net: RequestNetwork
    private lateinit var timest: Calendar
    private var player: MediaPlayer? = null
    private lateinit var vibrator: Vibrator

    private lateinit var users: com.google.firebase.database.DatabaseReference
    private lateinit var t24: Calendar
    private lateinit var requestNetwork: RequestNetwork
    private lateinit var fpick: Intent
    private lateinit var pending: com.google.firebase.database.DatabaseReference
    private var mp6: MediaPlayer? = null
    private lateinit var camxd: Intent
    private lateinit var _file_camxd: File
    private lateinit var music: Intent
    private lateinit var allfiles: Intent
    private lateinit var group: com.google.firebase.database.DatabaseReference
    private lateinit var prtFiles: Intent
    private lateinit var o1: ObjectAnimator
    private lateinit var o2: ObjectAnimator
    private var callT: TimerTask? = null
    private lateinit var vib: Vibrator
    private var ct: TimerTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat)
        initialize(savedInstanceState)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 1000)
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
        linear_head_top = findViewById(R.id.linear_head_top)
        linear_message = findViewById(R.id.linear_message)
        linear_dp_box = findViewById(R.id.linear_dp_box)
        linear12 = findViewById(R.id.linear12)
        settings = findViewById(R.id.settings)
        dps = findViewById(R.id.dps)
        status_dots = findViewById(R.id.status_dots)
        linear16 = findViewById(R.id.linear16)
        linearF = findViewById(R.id.linearF)
        name = findViewById(R.id.name)
        verified = findViewById(R.id.verified)
        user_status = findViewById(R.id.user_status)
        upload_progress_card = findViewById(R.id.upload_progress_card)
        recyclerview1 = findViewById(R.id.recyclerview1)
        typeing = findViewById(R.id.typeing)
        imageview_up_symbol = findViewById(R.id.imageview_up_symbol)
        textview_up_progress = findViewById(R.id.textview_up_progress)
        progressbar_upload = findViewById(R.id.progressbar_upload)
        message_body_in_reply = findViewById(R.id.message_body_in_reply)
        sound_record_body = findViewById(R.id.sound_record_body)
        message_body_in_message = findViewById(R.id.message_body_in_message)
        linearExtra_attach = findViewById(R.id.linearExtra_attach)
        cardview1 = findViewById(R.id.cardview1)
        linear_use_for_line = findViewById(R.id.linear_use_for_line)
        message_body_in_reply_chld = findViewById(R.id.message_body_in_reply_chld)
        message_body_in_reply_cancel = findViewById(R.id.message_body_in_reply_cancel)
        message_body_in_reply_title = findViewById(R.id.message_body_in_reply_title)
        message_body_in_reply_text = findViewById(R.id.message_body_in_reply_text)
        sound_record_cancel = findViewById(R.id.sound_record_cancel)
        recording_text = findViewById(R.id.recording_text)
        send_audio = findViewById(R.id.send_audio)
        image___atch = findViewById(R.id.image___atch)
        msg_spc = findViewById(R.id.msg_spc)
        view1 = findViewById(R.id.view1)
        message = findViewById(R.id.message)
        sound = findViewById(R.id.sound)
        send = findViewById(R.id.send)
        image_camera_attach = findViewById(R.id.image_camera_attach)
        image_music_attach = findViewById(R.id.image_music_attach)
        attach_files = findViewById(R.id.attach_files)
        media = findViewById(R.id.media)

        file = getSharedPreferences("f", Activity.MODE_PRIVATE)
        fauth = FirebaseAuth.getInstance()
        net = RequestNetwork(this)
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        requestNetwork = RequestNetwork(this)
        fpick = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        _file_camxd = FileUtil.createNewPictureFile(applicationContext)
        val _uri_camxd: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(applicationContext, applicationContext.packageName + ".provider", _file_camxd)
        } else {
            Uri.fromFile(_file_camxd)
        }
        camxd = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, _uri_camxd)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        music = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "audio/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        allfiles = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        prtFiles = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        vib = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        bucket = SupabaseHelper.client.storage.from("chatmedia")
        users = _firebase.getReference("users")
        pending = _firebase.getReference("pending")
        group = _firebase.getReference("group")

        linear_head_top.setOnClickListener { 
            if (intent.getStringExtra("group") == "true") {
                val a = Intent(applicationContext, GroupInfoActivity::class.java)
                a.putExtra("user2", intent.getStringExtra("user2"))
                a.putExtra("mb", com.google.gson.Gson().toJson(mem))
                startActivity(a)
            } else {
                val a = Intent(applicationContext, MyprofileActivity::class.java)
                a.putExtra("id", intent.getStringExtra("user2"))
                startActivity(a)
            }
        }

        settings.setOnClickListener { 
            if (intent.getStringExtra("group") == "true") {
                val a = Intent(applicationContext, GroupInfoActivity::class.java)
                a.putExtra("user2", intent.getStringExtra("user2"))
                startActivity(a)
            } else {
                val a = Intent(applicationContext, ChatSettingsActivity::class.java)
                a.putExtra("user2", intent.getStringExtra("user2"))
                startActivity(a)
            }
        }

        image___atch.setOnClickListener { 
            val pick = Intent(Intent.ACTION_GET_CONTENT)
            pick.type = "image/*"
            startActivityForResult(pick, REQ_CD_FPICK)
        }

        image_camera_attach.setOnClickListener { 
            startActivityForResult(camxd, REQ_CD_CAMXD)
        }

        image_music_attach.setOnClickListener { 
            startActivityForResult(music, REQ_CD_MUSIC)
        }

        attach_files.setOnClickListener { 
            startActivityForResult(allfiles, REQ_CD_ALLFILES)
        }

        media.setOnClickListener { 
            linearExtra_attach.visibility = if (linearExtra_attach.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        message.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                msg_trim = message.text.toString().trim()
                if (msg_trim.isEmpty()) {
                    send.visibility = View.GONE
                    sound.visibility = View.VISIBLE
                } else {
                    send.visibility = View.VISIBLE
                    sound.visibility = View.GONE
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        send.setOnClickListener { 
            if (message.text.toString().trim().isNotEmpty()) {
                sendTextMessage(message.text.toString().trim())
                message.setText("")
            }
        }

        sound.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Start recording
                    startRecording()
                    true
                }
                MotionEvent.ACTION_UP -> {
                    // Stop recording
                    stopRecording()
                    true
                }
                else -> false
            }
        }

        send_audio.setOnClickListener { 
            if (audioPath.isNotEmpty()) {
                uploadAudioToSupabase(Uri.fromFile(File(audioPath)))
            }
        }

        message_body_in_reply_cancel.setOnClickListener { 
            message_body_in_reply.visibility = View.GONE
            replytrue = ""
        }

        sound_record_cancel.setOnClickListener { 
            sound_record_body.visibility = View.GONE
            recorder?.apply { 
                stop()
                release()
            }
            recorder = null
            audioPath = ""
        }
    }

    private fun initializeLogic() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = 0xFF1A1A1A.toInt()
        }

        upload_progress_card.visibility = View.GONE
        textview_up_progress.visibility = View.GONE
        imageview_up_symbol.visibility = View.GONE

        timest = Calendar.getInstance()
        Chat1 = _firebase.getReference(intent.getStringExtra("user2")!!)
        Chat2 = _firebase.getReference(FirebaseAuth.getInstance().currentUser!!.uid)
        get_status = _firebase.getReference("get_status")

        recyclerview1.layoutManager = LinearLayoutManager(this).apply { 
            stackFromEnd = true
        }
        recyclerview1.adapter = ChatAdapter(chats)

        chatroom = intent.getStringExtra("user2")!!
        chatcopy = FirebaseAuth.getInstance().currentUser!!.uid

        // Load initial messages
        Chat1.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val _ind = object : GenericTypeIndicator<HashMap<String, Any>>() {}
                val _childValue = snapshot.getValue(_ind)
                if (_childValue != null) {
                    chats.add(_childValue)
                    recyclerview1.adapter?.notifyDataSetChanged()
                    recyclerview1.scrollToPosition(chats.size - 1)
                }
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })

        // Other initializations like user status, FCM, etc.
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                REQ_CD_FPICK -> {
                    val uri = data.data
                    if (uri != null) {
                        uploadFileToSupabase(uri, "image")
                    }
                }
                REQ_CD_CAMXD -> {
                    val uri = Uri.fromFile(_file_camxd)
                    uploadFileToSupabase(uri, "image")
                }
                REQ_CD_MUSIC -> {
                    val uri = data.data
                    if (uri != null) {
                        uploadFileToSupabase(uri, "audio")
                    }
                }
                REQ_CD_ALLFILES -> {
                    val uri = data.data
                    if (uri != null) {
                        uploadFileToSupabase(uri, "file")
                    }
                }
            }
        }
    }

    private fun sendTextMessage(messageText: String) {
        chat_key = Chat1.push().key!!
        val map = HashMap<String, Any>()
        map["sender"] = FirebaseAuth.getInstance().currentUser!!.uid
        map["re"] = intent.getStringExtra("user2")!!
        map["key"] = chat_key
        map["p"] = messageText
        map["status"] = "uns"
        map["timestamp"] = System.currentTimeMillis().toString()
        map["senderid"] = FirebaseAuth.getInstance().currentUser!!.uid
        map["date"] = SimpleDateFormat("E, MMM dd yyyy").format(timest.time)
        map["type"] = "text"

        Chat1.child(chat_key).updateChildren(map)
        Chat2.child(chat_key).updateChildren(map)
    }

    private fun uploadFileToSupabase(uri: Uri, fileType: String) {
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

                runOnUiThread {
                    upload_progress_card.visibility = View.GONE
                    textview_up_progress.visibility = View.GONE
                    imageview_up_symbol.visibility = View.GONE
                    progressbar_upload.visibility = View.GONE

                    val map = HashMap<String, Any>()
                    map["sender"] = FirebaseAuth.getInstance().currentUser!!.uid
                    map["re"] = intent.getStringExtra("user2")!!
                    map["key"] = Chat1.push().key!!
                    map["p"] = publicUrl
                    map["status"] = "uns"
                    map["timestamp"] = System.currentTimeMillis().toString()
                    map["senderid"] = FirebaseAuth.getInstance().currentUser!!.uid
                    map["date"] = SimpleDateFormat("E, MMM dd yyyy").format(timest.time)
                    map["type"] = fileType

                    Chat1.child(map["key"] as String).updateChildren(map)
                    Chat2.child(map["key"] as String).updateChildren(map)

                    Toast.makeText(applicationContext, "Upload berhasil!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                runOnUiThread {
                    upload_progress_card.visibility = View.GONE
                    textview_up_progress.visibility = View.GONE
                    imageview_up_symbol.visibility = View.GONE
                    progressbar_upload.visibility = View.GONE
                    Toast.makeText(applicationContext, "Upload gagal: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun uploadAudioToSupabase(uri: Uri) {
        upload_progress_card.visibility = View.VISIBLE
        textview_up_progress.text = "Uploading audio... 0%"
        imageview_up_symbol.visibility = View.VISIBLE
        progressbar_upload.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val file = File(uri.path!!)
                val fileName = "${fauth.currentUser!!.uid}_${System.currentTimeMillis()}_audio.mp3"

                bucket.upload(fileName, file.readBytes(), upsert = true, uploadStatusCallback = object : UploadStatusCallback {
                    override fun onProgress(status: UploadStatus) {
                        runOnUiThread { 
                            val progress = (status.bytesUploaded * 100) / status.contentLength
                            textview_up_progress.text = "Uploading audio... $progress%"
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
                    map["sender"] = FirebaseAuth.getInstance().currentUser!!.uid
                    map["re"] = intent.getStringExtra("user2")!!
                    map["key"] = Chat1.push().key!!
                    map["p"] = publicUrl
                    map["status"] = "uns"
                    map["timestamp"] = System.currentTimeMillis().toString()
                    map["senderid"] = FirebaseAuth.getInstance().currentUser!!.uid
                    map["date"] = SimpleDateFormat("E, MMM dd yyyy").format(timest.time)
                    map["type"] = "audio"

                    Chat1.child(map["key"] as String).updateChildren(map)
                    Chat2.child(map["key"] as String).updateChildren(map)

                    Toast.makeText(applicationContext, "Audio upload berhasil!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                runOnUiThread {
                    upload_progress_card.visibility = View.GONE
                    textview_up_progress.visibility = View.GONE
                    imageview_up_symbol.visibility = View.GONE
                    progressbar_upload.visibility = View.GONE
                    Toast.makeText(applicationContext, "Audio upload gagal: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun startRecording() {
        try {
            sound_record_body.visibility = View.VISIBLE
            recording_text.text = "Recording..."
            send_audio.visibility = View.GONE
            sound_record_cancel.visibility = View.VISIBLE

            audioPath = "${externalCacheDir?.absolutePath}/audio_record_${System.currentTimeMillis()}.mp3"
            recorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(audioPath)
                prepare()
                start()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to start recording: ${e.message}", Toast.LENGTH_SHORT).show()
            sound_record_body.visibility = View.GONE
        }
    }

    private fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
        recording_text.text = "Recording stopped."
        send_audio.visibility = View.VISIBLE
    }

    // Helper function for encryption (if needed, ensure it's compatible with Kotlin)
    private fun generateKey(password: String): SecretKey {
        val digest = MessageDigest.getInstance("SHA-256")
        val bytes = password.toByteArray(Charsets.UTF_8)
        digest.update(bytes, 0, bytes.size)
        val key = digest.digest()
        return SecretKeySpec(key, "AES")
    }

    // ChatAdapter (inner class or separate file)
    private inner class ChatAdapter(private val chatList: ArrayList<HashMap<String, Any>>) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_view_chat, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val chat = chatList[position]
            // Bind data to views
            // Example: holder.messageTextView.text = chat["p"] as String
        }

        override fun getItemCount(): Int = chatList.size

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            // Declare your views here
            // Example: val messageTextView: TextView = itemView.findViewById(R.id.message_text_view)
        }
    }
}



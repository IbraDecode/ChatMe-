package ibradecode.chatme

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import io.github.jan.supabase.storage.StorageBucket
import io.github.jan.supabase.storage.file.DownloadStatus
import io.github.jan.supabase.storage.file.DownloadStatusCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.HashMap

class ImageshowActivity : AppCompatActivity() {

    private val REQ_CD_FILE = 101

    private val _firebase = FirebaseDatabase.getInstance()
    private lateinit var bucket: StorageBucket

    private var path: String? = null
    private var filename: String? = null
    private var enc_restore: String? = null
    private var decrypted: String? = null
    private var chatroom: String? = null
    private var chatcopy: String? = null
    private var key: String? = null
    private val chatmap = HashMap<String, Any>()
    private var chat_key: String? = null

    private lateinit var linear2: LinearLayout
    private lateinit var back: ImageView
    private lateinit var linear3: LinearLayout
    private lateinit var download: ImageView
    private lateinit var imageview1: ImageView
    private lateinit var download_progress_card: MaterialCardView
    private lateinit var imageview_down_symbol: ImageView
    private lateinit var textview_down_progress: TextView
    private lateinit var progressbar_download: ProgressBar

    private lateinit var fauth: FirebaseAuth
    private lateinit var net: RequestNetwork
    private lateinit var fileIntent: Intent
    private lateinit var Chat1: com.google.firebase.database.DatabaseReference
    private lateinit var Chat2: com.google.firebase.database.DatabaseReference
    private lateinit var timest: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.imageshow)
        initialize(savedInstanceState)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
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
        linear2 = findViewById(R.id.linear2)
        back = findViewById(R.id.back)
        linear3 = findViewById(R.id.linear3)
        download = findViewById(R.id.download)
        imageview1 = findViewById(R.id.imageview1)
        download_progress_card = findViewById(R.id.download_progress_card)
        imageview_down_symbol = findViewById(R.id.imageview_down_symbol)
        textview_down_progress = findViewById(R.id.textview_down_progress)
        progressbar_download = findViewById(R.id.progressbar_download)

        fauth = FirebaseAuth.getInstance()
        net = RequestNetwork(this)
        fileIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        timest = Calendar.getInstance()

        bucket = SupabaseHelper.client.storage.from("chatmedia")

        back.setOnClickListener { 
            if ("true" == intent.getStringExtra("enc")) {
                chatmap["sender"] = "user"
                chatmap["re"] = intent.getStringExtra("user2")!!
                chatmap["key"] = chat_key!!
                chatmap["prt_open"] = "true"
                chatmap["p"] = "Sent a image [PRT]"
                chatmap["status"] = "uns"
                chatmap["timestamp"] = timest.timeInMillis.toString()
                chatmap["senderid"] = FirebaseAuth.getInstance().currentUser!!.uid
                chatmap["date"] = SimpleDateFormat("E, MMM dd yyyy").format(timest.time)

                Chat1.child(chat_key!!).updateChildren(chatmap)
                Chat2.child(chat_key!!).updateChildren(chatmap)
                chatmap.clear()
            } else {
                finish()
            }
        }

        download.setOnClickListener { 
            val fileUrl = intent.getStringExtra("url")
            if (!fileUrl.isNullOrEmpty()) {
                downloadFileFromSupabase(fileUrl)
            } else {
                Toast.makeText(applicationContext, "File URL not found!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initializeLogic() {
        download_progress_card.visibility = View.GONE
        textview_down_progress.visibility = View.GONE
        imageview_down_symbol.visibility = View.GONE
        progressbar_download.visibility = View.GONE

        chat_key = intent.getStringExtra("v1")
        key = intent.getStringExtra("v1")
        chatroom = intent.getStringExtra("chat_room")
        chatcopy = intent.getStringExtra("chat_copy")

        Chat1 = _firebase.getReference(chatroom!!)
        Chat2 = _firebase.getReference(chatcopy!!)

        if ("true" == intent.getStringExtra("enc")) {
            _DecryptedStringKey(intent.getStringExtra("url")!!, key!!)
            _blockScreenshot()
            download.visibility = View.GONE
        } else {
            Glide.with(applicationContext).load(Uri.parse(intent.getStringExtra("url"))).into(imageview1)
        }

        Chat1.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val _ind = object : GenericTypeIndicator<HashMap<String, Any>>() {}
                val _childValue = snapshot.getValue(_ind)
                if (_childValue != null) {
                    finish()
                }
            }
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })

        Chat2.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun downloadFileFromSupabase(fileUrl: String) {
        download_progress_card.visibility = View.VISIBLE
        textview_down_progress.text = "Downloading... 0%"
        imageview_down_symbol.visibility = View.VISIBLE
        progressbar_download.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val fileName = Uri.parse(fileUrl).lastPathSegment
                if (fileName.isNullOrEmpty()) {
                    throw IllegalArgumentException("Invalid file URL: could not extract file name.")
                }

                val data = bucket.download(fileName, downloadStatusCallback = object : DownloadStatusCallback {
                    override fun onProgress(status: DownloadStatus) {
                        runOnUiThread { 
                            val progress = (status.bytesDownloaded * 100) / status.contentLength
                            textview_down_progress.text = "Downloading... $progress%"
                        }
                    }
                })

                val downloadDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "ChatMe")
                if (!downloadDir.exists()) {
                    downloadDir.mkdirs()
                }
                val outputFile = File(downloadDir, fileName)
                FileOutputStream(outputFile).use { fos ->
                    fos.write(data)
                }

                withContext(Dispatchers.Main) {
                    SketchwareUtil.showMessage(applicationContext, "Download berhasil: ${outputFile.absolutePath}")
                    download_progress_card.visibility = View.GONE
                    textview_down_progress.visibility = View.GONE
                    imageview_down_symbol.visibility = View.GONE
                    progressbar_download.visibility = View.GONE
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    SketchwareUtil.showMessage(applicationContext, "Download gagal: ${e.message}")
                    download_progress_card.visibility = View.GONE
                    textview_down_progress.visibility = View.GONE
                    imageview_down_symbol.visibility = View.GONE
                    progressbar_download.visibility = View.GONE
                }
            }
        }
    }

    private fun _DecryptedStringKey(encryptedString: String, key: String) {
        // Implement decryption logic here if needed
        // For now, just load the image directly
        Glide.with(applicationContext).load(Uri.parse(encryptedString)).into(imageview1)
    }

    private fun _blockScreenshot() {
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
    }
}



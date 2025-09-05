package ibradecode.chatme

import android.os.Environment
import android.animation.*
import android.app.*
import android.content.*
import android.content.res.*
import android.graphics.*
import android.graphics.drawable.*
import android.media.*
import android.net.*
import android.net.Uri
import android.os.*
import android.text.*
import android.text.style.*
import android.util.*
import android.view.*
import android.view.View.*
import android.view.animation.*
import android.webkit.*
import android.widget.*
import android.widget.LinearLayout
import androidx.annotation.*
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.color.MaterialColors
import com.google.firebase.FirebaseApp
import io.github.jan.supabase.storage.StorageBucket
import io.github.jan.supabase.storage.file.DownloadStatus
import io.github.jan.supabase.storage.file.DownloadStatusCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.*
import java.text.*
import java.util.*
import java.util.Timer
import java.util.TimerTask
import java.util.regex.*
import org.json.*

class VideoShowActivity : AppCompatActivity() {

    private val _timer = Timer()
    private lateinit var bucket: StorageBucket

    private lateinit var linear1: LinearLayout
    private lateinit var videoview1: VideoView
    private lateinit var back: ImageView
    private lateinit var linear3: LinearLayout
    private lateinit var download: ImageView
    private lateinit var download_progress_card: com.google.android.material.card.MaterialCardView
    private lateinit var imageview_down_symbol: ImageView
    private lateinit var textview_down_progress: TextView
    private lateinit var progressbar_download: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.video_show)
        initialize(savedInstanceState)
        FirebaseApp.initializeApp(this)
        initializeLogic()
    }

    /**
     * Initializes the activity, setting up views and listeners.
     */
    private fun initialize(savedInstanceState: Bundle?) {
        linear1 = findViewById(R.id.linear1)
        back = findViewById(R.id.back)
        linear3 = findViewById(R.id.linear3)
        download = findViewById(R.id.download)
        videoview1 = findViewById(R.id.videoview1)
        val videoview1_controller = MediaController(this)
        videoview1.setMediaController(videoview1_controller)
        download_progress_card = findViewById(R.id.download_progress_card)
        imageview_down_symbol = findViewById(R.id.imageview_down_symbol)
        textview_down_progress = findViewById(R.id.textview_down_progress)
        progressbar_download = findViewById(R.id.progressbar_download)

        bucket = SupabaseHelper.client.storage.from("chatmedia")

        back.setOnClickListener { 
            finish()
        }
    }

    private fun initializeLogic() {
        download_progress_card.visibility = View.GONE
        textview_down_progress.visibility = View.GONE
        imageview_down_symbol.visibility = View.GONE
        progressbar_download.visibility = View.GONE

        // Initialize video playback
        videoview1.setVideoURI(Uri.parse(intent.getStringExtra("url")))
        videoview1.start()

        // Download button click listener
        download.setOnClickListener { 
            val fileUrl = intent.getStringExtra("url")
            if (!fileUrl.isNullOrEmpty()) {
                downloadFileFromSupabase(fileUrl)
            } else {
                Toast.makeText(applicationContext, "File URL not found!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Downloads a file from Supabase Storage and saves it to external storage.
     * Displays download progress and handles success/failure.
     * @param fileUrl The public URL of the file to download.
     */
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
}



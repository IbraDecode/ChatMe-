package ibradecode.chatme

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import de.hdodenhof.circleimageview.CircleImageView
import io.github.jan.supabase.storage.StorageBucket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class UploadphotoActivity : AppCompatActivity() {

    private val REQ_CD_PICK = 101

    private lateinit var bucket: StorageBucket

    private lateinit var iconImageView: CircleImageView
    private lateinit var txt_progressbar: TextView
    private lateinit var textview5: TextView
    private lateinit var imagePickerButton: MaterialButton
    private lateinit var skipButton: MaterialButton
    private lateinit var progressbar1: ProgressBar
    private lateinit var textview3: TextView

    private lateinit var fauth: FirebaseAuth
    private lateinit var users: com.google.firebase.database.DatabaseReference

    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.uploadphoto)
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
        iconImageView = findViewById(R.id.iconImageView)
        txt_progressbar = findViewById(R.id.txt_progressbar)
        textview5 = findViewById(R.id.textview5)
        imagePickerButton = findViewById(R.id.imagePickerButton)
        skipButton = findViewById(R.id.skipButton)
        progressbar1 = findViewById(R.id.progressbar1)
        textview3 = findViewById(R.id.textview3)

        fauth = FirebaseAuth.getInstance()
        users = FirebaseDatabase.getInstance().getReference("users")
        bucket = SupabaseHelper.client.storage.from("chatmedia")

        imagePickerButton.setOnClickListener { 
            textview5.visibility = View.GONE
            val pick = Intent(Intent.ACTION_GET_CONTENT)
            pick.type = "image/*"
            startActivityForResult(pick, REQ_CD_PICK)
        }

        skipButton.setOnClickListener { 
            val diago = AlertDialog.Builder(this)
            diago.setTitle("Discard Profile Picture!")
            diago.setMessage("I will upload it later.")
            diago.setPositiveButton("Ok") { _, _ ->
                val map = HashMap<String, Any>()
                map["username"] = username!!
                map["user"] = username!!
                map["id"] = fauth.currentUser!!.uid
                map["dp"] = ""
                map["email"] = fauth.currentUser!!.email!!
                map["limit"] = "false"
                map["v"] = "false"
                map["type"] = "user"
                users.child(fauth.currentUser!!.uid).updateChildren(map)

                val intent = Intent(applicationContext, UsersActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
            diago.setNegativeButton("Upload again") { _, _ -> }
            diago.create().show()
        }
    }

    private fun initializeLogic() {
        progressbar1.visibility = View.GONE
        textview5.visibility = View.GONE
        txt_progressbar.visibility = View.GONE

        val file = getSharedPreferences("f", Activity.MODE_PRIVATE)
        username = file.getString("username", "")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQ_CD_PICK) {
            if (data != null) {
                val uri = data.data
                if (uri != null) {
                    uploadImageToSupabase(uri)
                }
            }
        }
    }

    private fun uploadImageToSupabase(uri: Uri) {
        txt_progressbar.visibility = View.VISIBLE
        progressbar1.visibility = View.VISIBLE
        imagePickerButton.isEnabled = false
        skipButton.isEnabled = false

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val file = File(uri.path!!)
                val fileName = "${fauth.currentUser!!.uid}_${System.currentTimeMillis()}"
                bucket.upload(fileName, file.readBytes(), upsert = true)
                val publicUrl = bucket.publicUrl(fileName)

                runOnUiThread {
                    progressbar1.visibility = View.GONE
                    textview3.visibility = View.VISIBLE
                    txt_progressbar.visibility = View.GONE

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

                    val intent = Intent(applicationContext, UsersActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }
            } catch (e: Exception) {
                runOnUiThread {
                    textview5.visibility = View.VISIBLE
                    Toast.makeText(applicationContext, "Upload failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}



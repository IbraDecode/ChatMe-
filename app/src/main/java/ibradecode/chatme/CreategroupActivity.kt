package ibradecode.chatme

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
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
import kotlinx.coroutines.withContext
import java.io.File
import java.util.HashMap
import java.util.regex.Pattern

class CreategroupActivity : AppCompatActivity() {

    private val REQ_CD_FPICKER = 101

    private val _firebase = FirebaseDatabase.getInstance()
    private lateinit var bucket: StorageBucket

    private var valid = false
    private var dp = ""
    private var groupname = ""
    private var groupid = ""
    private var path: String? = null
    private var fname: String? = null

    private val usernames = ArrayList<String>()

    private lateinit var linear7: LinearLayout
    private lateinit var linear1: LinearLayout
    private lateinit var textview4: TextView
    private lateinit var iconImageView: CircleImageView
    private lateinit var linear3: LinearLayout
    private lateinit var linear5: LinearLayout
    private lateinit var buttonValidate: MaterialButton
    private lateinit var textview2: TextView
    private lateinit var linear6: LinearLayout
    private lateinit var text_input_layout_group_name: TextInputLayout
    private lateinit var Groupnames: TextInputEditText
    private lateinit var text_input_layout_group_id: TextInputLayout
    private lateinit var editTextUsername: TextInputEditText
    private lateinit var textview3: TextView
    private lateinit var upload_progress_card: MaterialCardView
    private lateinit var imageview_up_symbol: ImageView
    private lateinit var textview_up_progress: TextView
    private lateinit var progressbar_upload: ProgressBar

    private lateinit var users: com.google.firebase.database.DatabaseReference
    private lateinit var fauth: FirebaseAuth
    private lateinit var fpicker: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.creategroup)
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
        linear7 = findViewById(R.id.linear7)
        linear1 = findViewById(R.id.linear1)
        textview4 = findViewById(R.id.textview4)
        iconImageView = findViewById(R.id.iconImageView)
        linear3 = findViewById(R.id.linear3)
        linear5 = findViewById(R.id.linear5)
        buttonValidate = findViewById(R.id.buttonValidate)
        textview2 = findViewById(R.id.textview2)
        linear6 = findViewById(R.id.linear6)
        text_input_layout_group_name = findViewById(R.id.text_input_layout_group_name)
        Groupnames = findViewById(R.id.Groupnames)
        text_input_layout_group_id = findViewById(R.id.text_input_layout_group_id)
        editTextUsername = findViewById(R.id.editTextUsername)
        textview3 = findViewById(R.id.textview3)
        upload_progress_card = findViewById(R.id.upload_progress_card)
        imageview_up_symbol = findViewById(R.id.imageview_up_symbol)
        textview_up_progress = findViewById(R.id.textview_up_progress)
        progressbar_upload = findViewById(R.id.progressbar_upload)

        fauth = FirebaseAuth.getInstance()
        fpicker = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }

        users = _firebase.getReference("users")
        bucket = SupabaseHelper.client.storage.from("chatmedia")

        iconImageView.setOnClickListener { 
            startActivityForResult(fpicker, REQ_CD_FPICKER)
        }

        buttonValidate.setOnClickListener { 
            if (valid) {
                if (dp.isEmpty()) {
                    dp = "https://cdn-icons-png.flaticon.com/128/166/166258.png"
                }

                groupid = users.push().key!!

                val map = HashMap<String, Any>()
                map["username"] = groupname
                map["user"] = groupname
                map["admin"] = fauth.currentUser!!.uid
                map["dp"] = dp
                map["email"] = "null"
                map["limit"] = "false"
                map["v"] = "false"
                map["type"] = "group"
                map["id"] = groupid

                users.child(groupid).updateChildren(map).addOnSuccessListener { 
                    val myUid = fauth.currentUser!!.uid
                    val inboxRef = FirebaseDatabase.getInstance().getReference("inbox")

                    inboxRef.child(myUid).child(groupid).updateChildren(map)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(applicationContext, "Group created and added to inbox!", Toast.LENGTH_SHORT).show()
                                finish()
                            } else {
                                Toast.makeText(applicationContext, "Group added but failed to add to inbox.", Toast.LENGTH_SHORT).show()
                            }
                        }
                }.addOnFailureListener { e ->
                    Toast.makeText(applicationContext, "Group creation failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            } else {
                SketchwareUtil.showMessage(applicationContext, "Group not validated!")
            }
        }

        Groupnames.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                groupname = charSequence.toString()
                textview2.text = charSequence
                editTextUsername.setText(charSequence.toString().trim())

                val textValue = editTextUsername.text.toString().trim()
                val specialCharacterPattern = "^[a-zA-Z0-9_]*$"

                if (!textValue.matches(specialCharacterPattern.toRegex())) {
                    editTextUsername.error = "No special characters allowed!"
                    valid = false
                } else {
                    users.addListenerForSingleValueEvent(object : com.google.firebase.database.ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            var matchFound = false
                            for (userSnapshot in snapshot.children) {
                                val userValue = userSnapshot.child("user").getValue(String::class.java)
                                if (userValue != null && userValue == textValue) {
                                    matchFound = true
                                    break
                                }
                            }

                            if (matchFound) {
                                editTextUsername.error = "Username already taken!"
                                valid = false
                            } else {
                                editTextUsername.error = null
                                valid = true
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(applicationContext, "Database error: ${error.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })

        editTextUsername.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                val textValue = charSequence.toString().trim()
                val specialCharacterPattern = "^[a-zA-Z0-9_]*$"

                if (!textValue.matches(specialCharacterPattern.toRegex())) {
                    editTextUsername.error = "No special characters allowed!"
                    valid = false
                } else {
                    users.addListenerForSingleValueEvent(object : com.google.firebase.database.ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            var matchFound = false
                            for (userSnapshot in snapshot.children) {
                                val userValue = userSnapshot.child("user").getValue(String::class.java)
                                if (userValue != null && userValue == textValue) {
                                    matchFound = true
                                    break
                                }
                            }

                            if (matchFound) {
                                editTextUsername.error = "Username already taken!"
                                valid = false
                            } else {
                                editTextUsername.error = null
                                valid = true
                                groupname = charSequence.toString()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(applicationContext, "Database error: ${error.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun initializeLogic() {
        upload_progress_card.visibility = View.GONE
        textview_up_progress.visibility = View.GONE
        imageview_up_symbol.visibility = View.GONE
        progressbar_upload.visibility = View.GONE

        users.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val _ind = object : GenericTypeIndicator<HashMap<String, Any>>() {}
                val _childValue = snapshot.getValue(_ind)
                if (_childValue != null) {
                    if (_childValue.containsKey("user")) {
                        usernames.add(_childValue["user"].toString())
                    }
                }
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQ_CD_FPICKER) {
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

                withContext(Dispatchers.Main) {
                    upload_progress_card.visibility = View.GONE
                    textview_up_progress.visibility = View.GONE
                    imageview_up_symbol.visibility = View.GONE
                    progressbar_upload.visibility = View.GONE

                    dp = publicUrl
                    Glide.with(applicationContext).load(Uri.parse(dp)).into(iconImageView)
                    Toast.makeText(applicationContext, "Upload berhasil!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    upload_progress_card.visibility = View.GONE
                    textview_up_progress.visibility = View.GONE
                    imageview_up_symbol.visibility = View.GONE
                    progressbar_upload.visibility = View.GONE
                    Toast.makeText(applicationContext, "Upload gagal: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}



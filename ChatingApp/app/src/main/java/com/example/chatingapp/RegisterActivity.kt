package com.example.chatingapp

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class RegisterActivity : AppCompatActivity() {

    val TAG="RegisterActivity"
    private lateinit var auth: FirebaseAuth
    var selectedPhotoUri: Uri? = null
    val  PERMISSION_REQUEST_CODE=999


    @TargetApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        register_firebase()
        tv_sign_in_rg.setOnClickListener(View.OnClickListener {
            val intent=Intent(this,SignInActivity::class.java)
            startActivity(intent)
        })

        selectphoto_button_register.setOnClickListener {
            when {
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) -> {
                    if (ContextCompat.checkSelfPermission(this@RegisterActivity, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
                    }else{
                        chooseFile()
                    }
                }

                else -> chooseFile()
            }
        }


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){
            PERMISSION_REQUEST_CODE -> {
                if(grantResults.isEmpty() || grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this@RegisterActivity, "Oops! Permission Denied!!",Toast.LENGTH_SHORT).show()
                else
                    chooseFile()
            }
        }

    }

    private fun chooseFile() {
            Log.d(TAG,"Try to show photos")
            val intent=Intent(Intent.ACTION_PICK)
            intent.type="image/*"
            startActivityForResult(intent,0)
    }


        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
                // proceed and check what the selected image was....
                Log.d(TAG, "Photo was selected")

                selectedPhotoUri = data.data

                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

                selectphoto_imageview_register.setImageBitmap(bitmap)

                selectphoto_button_register.alpha = 0f

//      val bitmapDrawable = BitmapDrawable(bitmap)
//      selectphoto_button_register.setBackgroundDrawable(bitmapDrawable)
            }
    }

    private fun register_firebase() {
        bt_sign_up_rg.setOnClickListener(View.OnClickListener {
            val userName=et_name_rg.text.toString()
            val userEmail=et_email_rg.text.toString()
            val userPassword=et_password_rg.text.toString()

            if(userName.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty())
            {
                Toast.makeText(this,"Enter Uer name /email/password properlly",Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }else{
                auth.createUserWithEmailAndPassword(userEmail,userPassword)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success userId :"+(auth.currentUser?.uid))
                            uploadImageToFirebaseStorage()
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                            return@addOnCompleteListener
                        }
                    }
                    .addOnFailureListener { task->
                        Toast.makeText(this,task.message,Toast.LENGTH_SHORT).show()
                    }
            }

        })

    }

    private fun uploadImageToFirebaseStorage() {
        if (selectedPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d(TAG, "Successfully uploaded image: ${it.metadata?.path}")

                Toast.makeText(this,"Successfully uploaded image: ${it.metadata?.path}",Toast.LENGTH_LONG).show()
                ref.downloadUrl.addOnSuccessListener {
                    Log.d(TAG, "File Location: $it")

                    saveUserToFirebaseDatabase(it.toString())
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Failed to upload image to storage: ${it.message}")
            }
    }

    private fun saveUserToFirebaseDatabase(profileImageUrl: String) {
        val uid=FirebaseAuth.getInstance().uid?: " "
        val ref=FirebaseDatabase.getInstance().getReference("/users/$uid")
        val user=User(uid,et_name_rg.text.toString(),profileImageUrl)

        ref.setValue(user)
            .addOnSuccessListener {
                Toast.makeText(this,"Finally user is saved in Firebase database",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this,"User can't save in Firebase database",Toast.LENGTH_SHORT).show()
            }
    }

}

class User(val uid: String, val username: String, val profileImageUrl: String)

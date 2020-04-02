package com.example.chatingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    val TAG="SignInActivity"
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        auth = FirebaseAuth.getInstance()

        sign_in_firebase()


        tv_register.setOnClickListener(
            View.OnClickListener {
                finish()
            })

    }

    private fun sign_in_firebase() {
        bt_log_in.setOnClickListener(View.OnClickListener {
            var email=et_email_sign_in.text.toString()
            var password=et_password_sign_in.text.toString()

            if(email.isEmpty() || password.isEmpty())
            {
                Toast.makeText(this,"Enter Uer email/password properlly",Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful)
                    {
                        Toast.makeText(this,"Sign In Successful",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this,"Sign In is not Successful",Toast.LENGTH_SHORT).show()
                        return@addOnCompleteListener
                    }
                }
                .addOnFailureListener { task->
                    Toast.makeText(this,task.message,Toast.LENGTH_SHORT).show()
                }

        })
    }
}

package com.example.basickotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var textView:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView=findViewById(R.id.text_view)
        textView.setOnClickListener(View.OnClickListener {
            Toast.makeText(this@MainActivity,"This is TextView",Toast.LENGTH_LONG).show()
        })
    }
}

package com.levor.timerskotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class EditEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_event)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent : Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}

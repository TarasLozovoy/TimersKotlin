package com.levor.timerskotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import java.util.*

class EditEventActivity : AppCompatActivity() {
    private lateinit var titleEditText : EditText
    private lateinit var image : ImageView
    private lateinit var dueDateButton : Button

    private var imagePath : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_event)
        titleEditText = findViewById(R.id.titleEditText) as EditText
        image = findViewById(R.id.eventImage) as ImageView
        dueDateButton = findViewById(R.id.eventDate) as Button
    }

    override fun onBackPressed() {
        super.onBackPressed()
        EventsProvider.getInstance(this).createEvent(Event(titleEditText.text.toString(), GregorianCalendar().timeInMillis + 3000000L))
    }
}

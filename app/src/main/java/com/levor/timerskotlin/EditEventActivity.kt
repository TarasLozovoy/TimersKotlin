package com.levor.timerskotlin

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.levor.timerskotlin.utils.BitmapUtils
import java.text.SimpleDateFormat
import java.util.*

class EditEventActivity : AppCompatActivity() {
    private lateinit var titleEditText : EditText
    private lateinit var image : ImageView
    private lateinit var dueDateButton : Button

    private var presenter = EditEventPresenter(this)


    private var imagePath : String = ""
    private var dueDate = GregorianCalendar()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_event)
        titleEditText = findViewById(R.id.titleEditText) as EditText
        image = findViewById(R.id.eventImage) as ImageView
        dueDateButton = findViewById(R.id.eventDate) as Button

        image.setOnClickListener { view -> presenter.showFileChooserDialog() }
        dueDateButton.setOnClickListener { view -> showDatePickerDialog() }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        EventsProvider.getInstance(this).createEvent(Event(titleEditText.text.toString(), dueDate.timeInMillis, imagePath))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.onResultReceived(requestCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            presenter.onWritePermissionGranted(requestCode)
        }
    }

    fun updateImage(path: String?) {
        if (path == null) return
        imagePath = path
        val bitmap = BitmapUtils.getScaledBitmap(path, image.maxHeight)
        image.setImageBitmap(bitmap)
    }

    fun showDatePickerDialog() {
        val datePicker = DatePickerDialog(this,
                datePickerListener,
                dueDate.get(Calendar.YEAR),
                dueDate.get(Calendar.MONTH),
                dueDate.get(Calendar.DAY_OF_MONTH))
        datePicker.setCancelable(false)
        datePicker.show()
    }

    fun showTimePickerDialog() {
        val timePicker = TimePickerDialog(this, timePickerListener, dueDate.get(Calendar.HOUR_OF_DAY), dueDate.get(Calendar.MINUTE), true)
        timePicker.setCancelable(false)
        timePicker.show()
    }

    fun updateDueDateButton(calendar: Calendar) {
        val format = SimpleDateFormat("dd MMMM yyyy HH:mm")
        dueDateButton.text = format.format(calendar.time)
    }

    private val datePickerListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
        dueDate.set(Calendar.YEAR, year)
        dueDate.set(Calendar.MONTH, month)
        dueDate.set(Calendar.DAY_OF_MONTH, day)
        showTimePickerDialog()
    }

    private val timePickerListener = TimePickerDialog.OnTimeSetListener{ view, hour, minute ->
        dueDate.set(Calendar.HOUR_OF_DAY, hour)
        dueDate.set(Calendar.MINUTE, minute)
        updateDueDateButton(dueDate)
    }
}

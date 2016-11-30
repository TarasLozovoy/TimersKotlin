package com.levor.timerskotlin

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.levor.timerskotlin.utils.BitmapUtils
import java.text.SimpleDateFormat
import java.util.*

class EditEventActivity : AppCompatActivity() {
    companion object {
        private val EVENT_ID = "com.levor.timerskotlin.event_id"

        fun start(context: Context, eventId: Int) {
            val intent = Intent(context, EditEventActivity::class.java)
            intent.putExtra(EditEventActivity.EVENT_ID, eventId)
            context.startActivity(intent)
        }
    }

    private lateinit var titleEditText : EditText
    private lateinit var image : ImageView
    private lateinit var dueDateButton : Button

    private val presenter = EditEventPresenter(this)
    private val eventProvider = EventsProvider.getInstance(this)

    private var event : Event? = null
    private var imagePath : String = ""
    private var dueDate = GregorianCalendar()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_event)
        titleEditText = findViewById(R.id.titleEditText) as EditText
        image = findViewById(R.id.eventImage) as ImageView
        dueDateButton = findViewById(R.id.eventDate) as Button

        val b : Bundle? = intent.extras
        if (b != null) {
            val id = b.getInt(EVENT_ID, -1)
            if (id >= 0) {
                val editableEvent = eventProvider.getEventById(id)
                if (editableEvent != null) {
                    titleEditText.setText(editableEvent.title)
                    updateImage(editableEvent.imagePath)
                    dueDate.time = Date(editableEvent.endDate)
                    updateDueDateButton(dueDate)
                    event = editableEvent
                }
            }
        }


        image.setOnClickListener { view -> presenter.showFileChooserDialog() }
        dueDateButton.setOnClickListener { view -> showDatePickerDialog() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit_event, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.ok -> {
                val title = titleEditText.text.toString()
                val date = dueDate.timeInMillis
                val event = this.event
                if (event != null) {
                    event.title = title
                    event.endDate = date
                    event.imagePath = imagePath
                    eventProvider.updateEvent(event)
                } else {
                    eventProvider.createEvent(Event(title, date, imagePath))
                }
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
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
        BitmapUtils.loadBitmap(path, image.maxHeight, image)
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

package com.levor.timerskotlin

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var listview : ListView
    private lateinit var adapter : TimersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        listview = findViewById(R.id.listView) as ListView
        adapter = TimersAdapter(this, arrayOf(Event("Title1", GregorianCalendar().get(Calendar.SECOND) + 200L),
                Event("Title2", GregorianCalendar().get(Calendar.SECOND) + 300L),
                Event("Title3", GregorianCalendar().get(Calendar.SECOND) + 30000L),
                Event("Title4", GregorianCalendar().get(Calendar.SECOND) + 300000L)))
        listview.adapter = adapter;
        listview.setOnItemClickListener{ parent, view, position, id ->
            val intent : Intent = Intent(view.context, EditEventActivity::class.java)
            startActivity(intent)
        }

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        return super.onOptionsItemSelected(item)
    }
}

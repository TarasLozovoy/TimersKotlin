package com.levor.timerskotlin

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    private lateinit var listview : ListView
    private lateinit var adapter : TimersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)



        listview = findViewById(R.id.listView) as ListView
        listview.setOnItemClickListener{ parent, view, position, id ->
            val intent : Intent = Intent(view.context, EditEventActivity::class.java)
            startActivity(intent)
        }

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            val intent : Intent = Intent(view.context, EditEventActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val events : List<Event> = EventsProvider.getInstance(this).getAllEvents()
        for (e in events) Log.e("TAGGG", e.title + " " + e.endDate)
        adapter = TimersAdapter(this, events)
        listview.adapter = adapter;
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }
}

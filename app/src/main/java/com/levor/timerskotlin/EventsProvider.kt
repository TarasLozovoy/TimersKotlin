package com.levor.timerskotlin

import android.content.Context
import com.j256.ormlite.dao.Dao
import com.levor.timerskotlin.db.DBHelper

class EventsProvider private constructor(context: Context){
    private val dbHelper : DBHelper = DBHelper(context)
    private val eventDao : Dao<Event, Int> = dbHelper.eventDao!!

    companion object {
        private var instance : EventsProvider? = null

        fun  getInstance(context: Context): EventsProvider {
            if (instance == null)
                instance = EventsProvider(context)

            return instance!!
        }
    }

    fun createEvent(event : Event) {
        eventDao.create(event)
    }

    fun updateEvent(event : Event) {
        eventDao.update(event)
    }

    fun refreshEvent(event : Event) {
        eventDao.refresh(event)
    }

    fun deletehEvent(event : Event) {
        eventDao.refresh(event)
    }

    fun getAllEvents() : List<Event>  = eventDao.queryForAll()

    fun getEventById(id: Int) : Event? = eventDao.queryForId(id)

}

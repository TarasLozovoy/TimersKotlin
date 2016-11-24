package com.levor.timerskotlin.db

import android.content.Context
import android.database.SQLException
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.dao.Dao
import android.database.sqlite.SQLiteDatabase
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import com.levor.timerskotlin.Event


class DBHelper(context : Context) : OrmLiteSqliteOpenHelper(context, DBHelper.DATABASE_NAME, null, DBHelper.DATABASE_VERSION) {

    private var mEventDao: Dao<Event, Int>? = null

    override fun onCreate(db: SQLiteDatabase, connectionSource: ConnectionSource) {
        try {
            TableUtils.createTable<Event>(connectionSource, Event::class.java)
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }

    }

    override fun onUpgrade(db: SQLiteDatabase, connectionSource: ConnectionSource,
                           oldVersion: Int, newVersion: Int) {
        try {
            TableUtils.dropTable<Event, Any>(connectionSource, Event::class.java, true)
            onCreate(db, connectionSource)
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }

    }

    /* User */

    val eventDao: Dao<Event, Int>?
        @Throws(SQLException::class)
        get() {
            if (mEventDao == null) {
                mEventDao = getDao<Dao<Event, Int>, Event>(Event::class.java)
            }

            return mEventDao
        }

    override fun close() {
        mEventDao = null

        super.close()
    }

    companion object {

        private val DATABASE_NAME = "ormlite.db"
        private val DATABASE_VERSION = 1
    }
}

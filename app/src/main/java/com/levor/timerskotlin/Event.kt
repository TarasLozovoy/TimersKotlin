package com.levor.timerskotlin

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = Event.TABLE)
class Event() {
    companion object {
        const val TABLE = "eventdao"
        const val TITLE = "title"
        const val END_DATE = "enddate"
        const val IMAGE_PATH = "imagepath"
        const val ID = "id"
    }

    @DatabaseField(columnName = ID, generatedId = true)
    var id: Int = 0

    @DatabaseField(columnName = TITLE)
    lateinit var title: String

    @DatabaseField(columnName = IMAGE_PATH)
    lateinit var imagePath: String

    @DatabaseField(columnName = END_DATE)
    var endDate : Long = 0L

    constructor(title: String, endDate: Long, imagePath: String) : this() {
        this.title = title
        this.endDate = endDate
        this.imagePath = imagePath
    }
}
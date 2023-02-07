package com.example.registretionhome.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.registretionhome.model.UserData
import java.io.ByteArrayOutputStream

class MyDatabase private constructor(context: Context) :
    SQLiteOpenHelper(context, "registuserss", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "create table $USER_TABLE(" +
                    "$ID INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,$IMAGEUSER BLOB NOT NULL,$FULLNAME_COL TEXT NOT NULL,$TEL_NUMBER_COL TEXT NOT NULL,$DAVLAT_COL TEXT NOT NULL,$ADDRESS_COL TEXT NOT NULL, $PAROL_COL TEXT NOT NULL)"
        )
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}


    fun addUser(userData: UserData) {
        val db = writableDatabase
        val contentValues = ContentValues()

        contentValues.put(IMAGEUSER, bitmapToByteArray(userData.bitmap))
        contentValues.put(FULLNAME_COL, userData.fullname)
        contentValues.put(TEL_NUMBER_COL, userData.telnumber)
        contentValues.put(DAVLAT_COL, userData.davlat)
        contentValues.put(ADDRESS_COL, userData.address)
        contentValues.put(PAROL_COL, userData.paroluser)

        db.insert(USER_TABLE, null, contentValues)

    }


    @SuppressLint("Range")
    fun getImages(): List<UserData> {
        val images = mutableListOf<UserData>()

        val cursor = readableDatabase.query(USER_TABLE, null, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val image = cursor.getBlob(cursor.getColumnIndex(IMAGEUSER))
                val userData = UserData(
                    cursor.getInt(0),
                    byteArratoBitmap(image),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
                )
                images.add(userData)
            } while (cursor.moveToNext())
        }

        return images
    }


    fun checkUser(telnumber: String, paroluser: String): Boolean {
        val db = this.readableDatabase
        var result = false
        val cursor = db.rawQuery(
            "select * from  $USER_TABLE where $TEL_NUMBER_COL like  '$telnumber' and $PAROL_COL like  '$paroluser'",
            null
        )
        result = cursor.moveToFirst()


        return result
    }

    companion object {
        private const val USER_TABLE = "userstable"
        private const val ID = "id"
        private const val FULLNAME_COL = "fullname"
        private const val TEL_NUMBER_COL = "telnumbercol"
        private const val DAVLAT_COL = "davlatlar"
        private const val ADDRESS_COL = "adresscol"
        private const val PAROL_COL = "parolcol"
        private const val IMAGEUSER = "imageuser"


        private var instance: MyDatabase? = null

        fun init(context: Context) {
            if (instance == null) {
                instance = MyDatabase(context)
            }
        }

        fun getInstance() = instance!!

    }

    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    private fun byteArratoBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

}
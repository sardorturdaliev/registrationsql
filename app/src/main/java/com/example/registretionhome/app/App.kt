package com.example.registretionhome.app

import android.app.Application
import com.example.registretionhome.db.MyDatabase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MyDatabase.init(this)
    }
}
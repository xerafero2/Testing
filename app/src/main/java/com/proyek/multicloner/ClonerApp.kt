package com.proyek.multicloner

import android.app.Application
import android.content.Context
import com.blackbox.core.BlackBoxCore

class ClonerApp : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        try {
            // Menyalakan engine inti virtualisasi saat aplikasi dimulai
            BlackBoxCore.get().doAttachBaseContext(base)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreate() {
        super.onCreate()
        try {
            // Memulai siklus kerja virtual engine
            BlackBoxCore.get().doOnCreate()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

package com.proyek.multicloner

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.blackbox.core.BlackBoxCore

class MainActivity : AppCompatActivity() {

    // Target default aplikasi yang ingin dikloning (contoh: WhatsApp asli)
    private val targetPackage = "com.whatsapp" 

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnClone = findViewById<Button>(R.id.btnCreateClone)
        val btnLaunch = findViewById<Button>(R.id.btnLaunchClone)
        val inputUserId = findViewById<EditText>(R.id.etUserId)
        val tvStatus = findViewById<TextView>(R.id.tvStatusInfo)

        // Tombol 1: Membuat Kloning Baru Ke Sekian Kali
        btnClone.setOnClickListener {
            val userIdString = inputUserId.text.toString()
            if (userIdString.isEmpty()) {
                Toast.makeText(this, "Masukkan nomor urut kloning (contoh: 1, 2, 3)!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userId = userIdString.toInt()
            
            // Perintah BlackBox untuk mengkloning aplikasi bawaan HP ke dalam slot ID tertentu
            val result = BlackBoxCore.get().installPackageAsUser(targetPackage, userId)
            
            if (result.success) {
                tvStatus.text = "Status: Kloning Ke-$userId Sukses Dibuat!"
                Toast.makeText(this, "Sukses menggandakan akun ke-$userId", Toast.LENGTH_SHORT).show()
            } else {
                tvStatus.text = "Status: Gagal kloning karena: ${result.msg}"
            }
        }

        // Tombol 2: Menjalankan Kloning Berdasarkan Slot ID yang Dipilih
        btnLaunch.setOnClickListener {
            val userIdString = inputUserId.text.toString()
            if (userIdString.isEmpty()) return@setOnClickListener
            
            val userId = userIdString.toInt()

            // Memeriksa apakah aplikasi terkloning di slot ID tersebut ada
            if (BlackBoxCore.get().isInstalled(targetPackage, userId)) {
                // Menjalankan aplikasi target secara terisolasi penuh pada user ID tersebut
                BlackBoxCore.get().launchApk(targetPackage, userId)
            } else {
                Toast.makeText(this, "Kloning ke-$userId belum dibuat!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

package com.proyek.cloner

import android.app.admin.DevicePolicyManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnLaunch = findViewById<Button>(R.id.btnLaunchWorkProfile)

        btnLaunch.setOnClickListener {
            try {
                // Memicu Intent Sistem Android untuk membuat atau membuka profil kerja baru (Work Profile)
                val intent = Intent(DevicePolicyManager.ACTION_PROVISION_MANAGED_PROFILE).apply {
                    putExtra(
                        DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME,
                        "" // Dikosongkan agar ditangani oleh wizard standar pengaturan bawaan OS Android
                    )
                }
                
                // Cek apakah perangkat mendukung manajemen kebijakan ini
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                } else {
                    // Jalur alternatif jika skema di atas diblokir OEM: Buka menu pengaturan Akun/Profil secara langsung
                    val settingsIntent = Intent(android.provider.Settings.ACTION_USER_SETTINGS)
                    startActivity(settingsIntent)
                    Toast.makeText(this, "Membuka Menu Pengaturan Akun Sistem...", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Gagal memicu Ruang Kedua: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}

package com.example.retromeal

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.retromeal.MainActivity
import com.example.retromeal.R

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var receiver: BroadcastReceiver

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        // Define el BroadcastReceiver
        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                // Handler para introducir un retraso
                Handler(Looper.getMainLooper()).postDelayed({
                    // Cierra SplashActivity después del retraso
                    finish()
                }, 2000) // Retraso de 2000 milisegundos (2 segundos)
            }
        }


        // Registra el BroadcastReceiver
        registerReceiver(receiver, IntentFilter("com.example.retromeal.DATA_LOADED"),
            RECEIVER_NOT_EXPORTED
        )

        // Inicia MainActivity
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        // Asegúrate de anular el registro del BroadcastReceiver
        unregisterReceiver(receiver)
    }
}

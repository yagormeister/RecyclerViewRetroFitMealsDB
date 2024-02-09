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
import com.example.retromeal.R

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var receiver: BroadcastReceiver
    private var isDataLoaded = false
    private var isDelayElapsed = false

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        // Define y registra el BroadcastReceiver
        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                isDataLoaded = true
                if (isDelayElapsed) {
                    // Cierra SplashActivity
                    finish()
                }
            }
        }

        // Registra el BroadcastReceive
        val intentFilter = IntentFilter("com.example.retromeal.DATA_LOADED")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(receiver, intentFilter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            registerReceiver(receiver, intentFilter)
        }

        // Inicia un retraso mínimo antes de iniciar MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            //isDelayElapsed = true
            if (isDataLoaded) {
                // Cierra SplashActivity
                finish()
            } else {
                // Inicia MainActivity después del retraso
                startActivity(Intent(this, MainActivity::class.java))
            }
        }, 2000) // 2000 milisegundos de retraso (2 segundos)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Asegúrate de anular el registro del BroadcastReceiver
        unregisterReceiver(receiver)
    }
}

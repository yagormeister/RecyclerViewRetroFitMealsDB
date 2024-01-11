import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.retromeal.MainActivity
import com.example.retromeal.R

class SplashActivity : AppCompatActivity() {

    private lateinit var receiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        // Define el BroadcastReceiver
        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                // Cierra SplashActivity cuando se recibe la señal
                finish()
            }
        }

        // Registra el BroadcastReceiver
        registerReceiver(receiver, IntentFilter("com.example.retromeal.DATA_LOADED"))

        // Inicia MainActivity
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        // Asegúrate de anular el registro del BroadcastReceiver
        unregisterReceiver(receiver)
    }
}


package app.prueba.dogcatalogo

import android.content.Intent
import android.net.nsd.NsdManager
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofitexampleapp.R
import kotlinx.android.synthetic.main.activity_registrarse.*
import kotlinx.android.synthetic.main.patalla_login.*

class actividad_login: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val Button = findViewById<Button>(R.id.btnregistro)
        btnregistro.setOnClickListener {view ->
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }
}
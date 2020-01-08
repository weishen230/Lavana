package com.example.lavana

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager

class backgroundSplashActivity : AppCompatActivity() {

    private val TIME_OUT = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_background_splash)




        Handler().postDelayed(
            Runnable {

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()


            },TIME_OUT.toLong()

        )



    }


}

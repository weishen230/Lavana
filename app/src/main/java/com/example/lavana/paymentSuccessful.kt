package com.example.lavana

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class paymentSuccessful : AppCompatActivity() {

    private lateinit var okBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_successful)

        setTitle("Payment Successful")

        okBtn = findViewById(R.id.okButton)


        okBtn.setOnClickListener{

            val intent = Intent(this, drawerActivity::class.java)
            startActivity(intent)

        }


    }
}

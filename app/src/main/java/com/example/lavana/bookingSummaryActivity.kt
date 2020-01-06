package com.example.lavana

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class bookingSummaryActivity : AppCompatActivity() {

    private lateinit var bookingIDTxt : TextView
    private lateinit var todayDateTxt : TextView
    private lateinit var usernameTxt : TextView
    private lateinit var bookedDateTxt : TextView
    private lateinit var bookedTimeTxt : TextView
    private lateinit var sportTxt : TextView
    private lateinit var courtTxt : TextView
    private lateinit var typeTxt : TextView
    private lateinit var totalTxt : TextView
    private lateinit var proceedBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_summary)

        setTitle("Booking Summary")

        //bookingIDTxt = findViewById(R.id.textViewBookID)
        todayDateTxt = findViewById(R.id.textViewDate)
        usernameTxt = findViewById(R.id.textViewName)
        bookedDateTxt = findViewById(R.id.textViewBookedDate)
        bookedTimeTxt = findViewById(R.id.textViewBookedTime)
        sportTxt = findViewById(R.id.textViewBookedCategory)
        courtTxt = findViewById(R.id.textViewCourt)
        typeTxt = findViewById(R.id.textViewType)
        totalTxt = findViewById(R.id.textViewCharge)
        proceedBtn = findViewById(R.id.buttonPayment)

        var sharedPreferences = this.getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
        var saveUsername = sharedPreferences.getString("SESSION_ID", getString(R.string.sharedPreUsername))
        var saveSports= sharedPreferences.getString("TOBOOK_SPORT", "No Sport")
        var saveDate= sharedPreferences.getString("TOBOOK_DAY", "No Day")
        var saveTime= sharedPreferences.getString("TOBOOK_TIME", "No Time")
        var saveCourt = sharedPreferences.getString("TOBOOK_COURT", "No Court")
        var saveType = sharedPreferences.getString("TOBOOK_TYPE", "No Type")
        var savePrice = sharedPreferences.getString("TOBOOK_PRICE", "No Price")



        var calendar : Calendar = Calendar.getInstance()
        var simpleDateFormat : SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
        var date = simpleDateFormat.format(calendar.time)
        var totalPrice : Int = savePrice!!.toInt() * 2

        //bookingIDTxt.setText("Booking ID: " + bookingID)
        todayDateTxt.setText("Date: " + date)
        usernameTxt.setText("Username: " + saveUsername.toString())
        bookedDateTxt.setText("Booked Date: " + saveDate.toString())
        bookedTimeTxt.setText("Booked Time: " + saveTime.toString())
        sportTxt.setText("Category: " + saveSports.toString())
        courtTxt.setText("Court: " + saveCourt.toString())
        typeTxt.setText("Type: " + saveType.toString())
        totalTxt.setText("Total: RM" + totalPrice)

        proceedBtn.setOnClickListener{

            var saveCode = sharedPreferences.getString("NUMBER", "No Number")?:return@setOnClickListener
            var saveTotal = sharedPreferences.getString("TOBOOK_TOTAL", "No Total")?:return@setOnClickListener
            var number = "1"

            with(sharedPreferences.edit())
            {
                putString("NUMBER", number)
                putString("TOBOOK_TOTAL", totalPrice.toString())
                apply()
            }

            val intent = Intent(this, paymentActivity::class.java)
            startActivity(intent)


        }

    }
}

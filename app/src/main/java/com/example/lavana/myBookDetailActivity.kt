package com.example.lavana

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.gson.Gson

class myBookDetailActivity : AppCompatActivity() {

    private lateinit var bookingIDTxt : TextView
    private lateinit var usernameTxt : TextView
    private lateinit var bookedDateTxt : TextView
    private lateinit var bookedTimeTxt : TextView
    private lateinit var categoryTxt : TextView
    private lateinit var courtTxt : TextView
    private lateinit var typeTxt : TextView
    private lateinit var totalTxt : TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_book_detail)

        setTitle("My Booking Details")

        bookingIDTxt = findViewById(R.id.bookDetailID)
        usernameTxt = findViewById(R.id.bookDetailName)
        bookedDateTxt = findViewById(R.id.BookedDetailDate)
        bookedTimeTxt = findViewById(R.id.BookedDetailTime)
        categoryTxt = findViewById(R.id.BookedDetailCategory)
        courtTxt = findViewById(R.id.BookedDetailCourt)
        typeTxt = findViewById(R.id.BookedDetailType)
        totalTxt = findViewById(R.id.BookedDetailCharge)


        var sharedPreferences = this.getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)

        val gjson : Gson = Gson()
        val json  = sharedPreferences.getString("THISBOOK", "No Booking")

        val myBooking : Booking = gjson.fromJson(json, Booking::class.java)


        bookingIDTxt.setText("Booking ID         : " + myBooking.bookID.toString())
        usernameTxt.setText("Username         : " + myBooking.bookUsername.toString())
        bookedDateTxt.setText("Booked Date     : " + myBooking.bookedDate.toString())
        bookedTimeTxt.setText("Booked Time    : " + myBooking.bookedTime.toString())
        categoryTxt.setText("Category           : " + myBooking.sportCtg.toString())
        courtTxt.setText("Court                 : " + myBooking.courtBooked.toString())
        typeTxt.setText("Type                   : " + myBooking.typeInOut.toString())
        totalTxt.setText("Total Paid : RM" + myBooking.total.toString())



    }
}

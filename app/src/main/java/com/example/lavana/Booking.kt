package com.example.lavana

class Booking(val bookID: String, val bookUsername : String, val bookedDate : String, val bookedTime : String,
              val sportCtg : String, val courtBooked : String, val typeInOut : String, val total : String)
{

    constructor() : this("","","","","","","", "")
    {

    }

}
package com.example.lavana.ui.myBooking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class myBookingViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is share Fragment"
    }
    val text: LiveData<String> = _text
}
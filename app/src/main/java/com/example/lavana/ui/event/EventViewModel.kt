package com.example.lavana.ui.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EventViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is eventFragment Fragment"
    }
    val text: LiveData<String> = _text
}
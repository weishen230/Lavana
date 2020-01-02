package com.example.lavana.ui.event


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.lavana.MainActivity
import com.example.lavana.R

/**
 * A simple [Fragment] subclass.
 */
class eventDetailFirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        (activity as AppCompatActivity).supportActionBar?.title = "KL Run"

        return inflater.inflate(R.layout.fragment_event_detail, container, false)
    }


}

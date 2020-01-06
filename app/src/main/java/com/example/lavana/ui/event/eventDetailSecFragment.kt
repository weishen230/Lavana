package com.example.lavana.ui.event


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

import com.example.lavana.R

/**
 * A simple [Fragment] subclass.
 */
class eventDetailSecFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        container?.removeAllViews();
        // Inflate the layout for this fragment

        (activity as AppCompatActivity).supportActionBar?.title = "Christmas Run"

        return inflater.inflate(R.layout.fragment_event_detail_sec, container, false)
    }


}

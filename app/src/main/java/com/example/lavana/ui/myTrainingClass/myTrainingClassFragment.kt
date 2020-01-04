package com.example.lavana.ui.myTrainingClass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lavana.R

class myTrainingClassFragment : Fragment() {

    private lateinit var myTrainingClassViewModel: myTrainingClassViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_my_training_class, container, false)


        (activity as AppCompatActivity).supportActionBar?.title = "My Training Class"

        /*myTrainingClassViewModel =
            ViewModelProviders.of(this).get(myTrainingClassViewModel::class.java)

        val textView: TextView = root.findViewById(R.id.text_tools)
        myTrainingClassViewModel.text.observe(this, Observer {
            textView.text = it
        })*/
        return root
    }
}
package com.example.lavana.ui.coachManagement

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.lavana.R
import com.example.lavana.adminSelectTimeActivity

class coachManagementFragment : Fragment() {

    private lateinit var coachMgmtViewModel: coachMgmtViewModel

    lateinit var badmintonBtn : ImageButton
    lateinit var volleyballBtn : ImageButton
    lateinit var basketballBtn : ImageButton
    lateinit var footBallBtn : ImageButton


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_coach_management, container, false)


        (activity as AppCompatActivity).supportActionBar?.title = "Coach Management"

        badmintonBtn = root.findViewById(R.id.adminbadminton_imageButton)
        volleyballBtn = root.findViewById(R.id.adminvolleyball_imageButton)
        basketballBtn = root.findViewById(R.id.adminbasketball_imageButton)
        footBallBtn = root.findViewById(R.id.adminfootball_imageButton)


        badmintonBtn.setOnClickListener{
            val intent = Intent(activity!!, adminSelectTimeActivity::class.java)

            var sharedPreferences = this.activity?.getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
            var saveSports= sharedPreferences?.getString("SELECTED_SPORT", "No Sport")?: return@setOnClickListener

            with(sharedPreferences.edit())
            {
                putString("SELECTED_SPORT", "Badminton")
                apply()
            }

            startActivity(intent)
            //activity!!.onBackPressed()

        }

        volleyballBtn.setOnClickListener{
            val intent = Intent(activity!!, adminSelectTimeActivity::class.java)

            var sharedPreferences = this.activity?.getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
            var saveSports= sharedPreferences?.getString("SELECTED_SPORT", "No Sport")?: return@setOnClickListener

            with(sharedPreferences.edit())
            {
                putString("SELECTED_SPORT", "Volleyball")
                apply()
            }

            startActivity(intent)
            //activity!!.onBackPressed()
        }

        basketballBtn.setOnClickListener{
            val intent = Intent(activity!!, adminSelectTimeActivity::class.java)

            var sharedPreferences = this.activity?.getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
            var saveSports= sharedPreferences?.getString("SELECTED_SPORT", "No Sport")?: return@setOnClickListener

            with(sharedPreferences.edit())
            {
                putString("SELECTED_SPORT", "Basketball")
                apply()
            }

            startActivity(intent)
            //activity!!.onBackPressed()
        }

        footBallBtn.setOnClickListener{
            val intent = Intent(activity!!, adminSelectTimeActivity::class.java)

            var sharedPreferences = this.activity?.getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
            var saveSports= sharedPreferences?.getString("SELECTED_SPORT", "No Sport")?: return@setOnClickListener

            with(sharedPreferences.edit())
            {
                putString("SELECTED_SPORT", "Football")
                apply()
            }

            startActivity(intent)
            //activity!!.onBackPressed()
        }






        /*coachMgmtViewModel =
            ViewModelProviders.of(this).get(coachMgmtViewModel::class.java)
        val root = )
        val textView: TextView = root.findViewById(R.id.admintextView)
        coachMgmtViewModel.text.observe(this, Observer {
            textView.text = it
        })*/
        return root
    }

}
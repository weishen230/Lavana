package com.example.lavana.ui.trainingClass

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lavana.R
import com.example.lavana.adminSelectTimeActivity
import com.example.lavana.memberTrainingSelectTime

class trainingClassFragment : Fragment() {

    private lateinit var trainingClassViewModel: trainingClassViewModel
    lateinit var badmintonBtn : ImageButton
    lateinit var volleyballBtn : ImageButton
    lateinit var basketballBtn : ImageButton
    lateinit var footBallBtn : ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {





        val root = inflater.inflate(R.layout.fragment_training_class, container, false)


        (activity as AppCompatActivity).supportActionBar?.title = "Register Training Class"

        badmintonBtn = root.findViewById(R.id.memberBadminton_imageButton)
        volleyballBtn = root.findViewById(R.id.memberVolleyball_imageButton)
        basketballBtn = root.findViewById(R.id.memberBasketball_imageButton)
        footBallBtn = root.findViewById(R.id.memberFootball_imageButton)


        badmintonBtn.setOnClickListener{
            val intent = Intent(activity!!, memberTrainingSelectTime::class.java)

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
            val intent = Intent(activity!!, memberTrainingSelectTime::class.java)

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
            val intent = Intent(activity!!, memberTrainingSelectTime::class.java)

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
            val intent = Intent(activity!!, memberTrainingSelectTime::class.java)

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
        /* trainingClassViewModel =
            ViewModelProviders.of(this).get(trainingClassViewModel::class.java)

        val textView: TextView = root.findViewById(R.id.text_send)
        trainingClassViewModel.text.observe(this, Observer {
            textView.text = it
        })*/


        return root
    }
}
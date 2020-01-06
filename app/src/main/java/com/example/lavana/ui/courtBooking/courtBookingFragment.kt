package com.example.lavana.ui.courtBooking

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lavana.R
import com.example.lavana.selectMyCourtActivity
import kotlinx.android.synthetic.main.fragment_court_booking.*
import java.text.SimpleDateFormat
import java.util.*

class courtBookingFragment : Fragment() {

    private lateinit var courtBookingViewModel: courtBookingViewModel
    private lateinit var selectDateBtn : Button
    private lateinit var dateSelectedTxt : TextView
    private lateinit var timeSelected : Spinner
    private lateinit var sportSelected : Spinner
    private lateinit var confirmBtn : Button
    private lateinit var clearBtn : Button

    var formate = SimpleDateFormat("dd MMM YYYY", Locale.US)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        container?.removeAllViews();

        val root = inflater.inflate(R.layout.fragment_court_booking, container, false)

        (activity as AppCompatActivity).supportActionBar?.title = "Book A Court"

        /*selectDateBtn = root.findViewById(R.id.btn_date)
        dateSelectedTxt = root.findViewById(R.id.in_date)
        timeSelected = root.findViewById(R.id.spinnerTime)
        sportSelected = root.findViewById(R.id.spinnerCategory)*/
        selectDateBtn = root.findViewById(R.id.btn_date)
        clearBtn = root.findViewById(R.id.btnClear)
        confirmBtn = root.findViewById(R.id.btnConfirm)


        selectDateBtn.setOnClickListener{

            val now = Calendar.getInstance();
            val datePicker = DatePickerDialog(
                activity!!, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    val seletedDate = Calendar.getInstance()
                    seletedDate.set(Calendar.YEAR, year)
                    seletedDate.set(Calendar.MONTH, month)
                    seletedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    val date = formate.format(seletedDate.time)
                    in_date.setText(date)
                },
                now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)

            )

            datePicker.datePicker.minDate = (System.currentTimeMillis() - 10000)
            datePicker.show()

        }


        confirmBtn.setOnClickListener{

            var sharedPreferences = this.activity?.getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
            var saveSports= sharedPreferences?.getString("TOBOOK_SPORT", "No Sport")?: return@setOnClickListener
            var saveDate= sharedPreferences?.getString("TOBOOK_DAY", "No Day")?: return@setOnClickListener
            var saveTime= sharedPreferences?.getString("TOBOOK_TIME", "No Time")?: return@setOnClickListener


            with(sharedPreferences.edit())
            {
                putString("TOBOOK_SPORT", spinnerCategory.selectedItem.toString())
                putString("TOBOOK_DAY", in_date.text.toString())
                putString("TOBOOK_TIME", spinnerTime.selectedItem.toString())
                apply()
            }


            val intent = Intent(activity!!, selectMyCourtActivity::class.java)
            startActivity(intent)
        }

        /*
        courtBookingViewModel =
            ViewModelProviders.of(this).get(courtBookingViewModel::class.java)

        val textView: TextView = root.findViewById(R.id.text_slideshow)
        courtBookingViewModel.text.observe(this, Observer {
            textView.text = it
        })*/
        return root
    }
}
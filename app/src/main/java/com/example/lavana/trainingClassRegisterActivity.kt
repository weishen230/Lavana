package com.example.lavana

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_training_class_register.*
import org.w3c.dom.Text

class trainingClassRegisterActivity : AppCompatActivity() {

    //Coach Details
    private lateinit var coachNameTxt : TextView
    private lateinit var coachGenderTxt : TextView
    private lateinit var coachPhoneTxt : TextView
    private lateinit var coachFees : TextView

    //Class Details
    private lateinit var classSport : TextView
    private lateinit var classDay : TextView
    private lateinit var classTime : TextView

    private lateinit var totalPrice : TextView

    private lateinit var payBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training_class_register)

        setTitle("Training Class Registration")

        coachNameTxt = findViewById(R.id.coachDetailName_textView)
        coachGenderTxt = findViewById(R.id.gender_textView)
        coachPhoneTxt = findViewById(R.id.coachPhoneNoTitle)
        coachFees = findViewById(R.id.fee_textView2)

        classSport = findViewById(R.id.ClassDetailSport_textView)
        classDay = findViewById(R.id.classDetailDay_textView)
        classTime = findViewById(R.id.classDetailTime_textView)

        totalPrice = findViewById(R.id.totalPriceaa)

        payBtn = findViewById(R.id.pay_button)

        //Coach Details
        var sharedPreferences = getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
        var saveName = sharedPreferences.getString("UNDERCOACH_NAME", "No Name")
        var saveGender = sharedPreferences.getString("UNDERCOACH_GENDER", "No Gender")
        var savePhone = sharedPreferences.getString("UNDERCOACH_PHONE", "No Phone")
        var saveFee = sharedPreferences.getString("UNDERCOACH_FEE", "No Fee")

        //Class Details
        var saveTimeSelected = sharedPreferences.getString("SELECTED_TIME", "No Time")
        var saveDaySelected = sharedPreferences.getString("SELECTED_DAY", "No Day")
        var saveSports = sharedPreferences.getString("SELECTED_SPORT", "No Sport")


        //to save member registration details
        var saveMemberName = sharedPreferences.getString("MEMREG_NAME", "No Name")?:return
        var saveMemberGender = sharedPreferences.getString("MEMREG_GENDER", "No Gender")?:return
        var saveMemberAge = sharedPreferences.getString("MEMREG_AGE", "No Age")?:return
        var saveMemberPhone = sharedPreferences.getString("MEMREG_PHONE", "No Phone")?:return

        coachNameTxt.setText("Name: " + saveName)
        coachGenderTxt.setText("Gender: " + saveGender)
        coachPhoneTxt.setText("Phone No: " + savePhone)
        coachFees.setText("Fees Per Month (RM): " + saveFee)

        classSport.setText("Sports: " + saveSports)
        classDay.setText("Day: " + saveDaySelected)
        classTime.setText("Time: " + saveTimeSelected)

        totalPrice.setText("Total (RM): " + saveFee)


        payBtn.setOnClickListener{

            with(sharedPreferences.edit())
            {
                putString("MEMREG_NAME", nameEditText.text.toString())
                putString("MEMREG_GENDER", spinnerGender.selectedItem.toString())
                putString("MEMREG_AGE", ageEditText.text.toString())
                putString("MEMREG_PHONE", phoneNoEditText.text.toString())
                apply()
            }

            val intent = Intent(this, paymentActivity::class.java)
            startActivity(intent)


        }


    }
}

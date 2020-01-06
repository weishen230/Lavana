package com.example.lavana

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_training_class_register.*
import org.w3c.dom.Text

class trainingClassRegisterActivity : AppCompatActivity() {

    //register detail
    private lateinit var regName : TextView
    private lateinit var regAge : TextView
    private lateinit var regPhone : TextView

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

        regName = findViewById(R.id.nameEditText)
        regAge = findViewById(R.id.ageEditText)
        regPhone = findViewById(R.id.phoneNoEditText)

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

        var saveCode = sharedPreferences.getString("NUMBER", "No Number")?:return
        var number = "0"

        coachNameTxt.setText("Name: " + saveName)
        coachGenderTxt.setText("Gender: " + saveGender)
        coachPhoneTxt.setText("Phone No: " + savePhone)
        coachFees.setText("Fees Per Month (RM): " + saveFee)

        classSport.setText("Sports: " + saveSports)
        classDay.setText("Day: " + saveDaySelected)
        classTime.setText("Time: " + saveTimeSelected)

        totalPrice.setText("Total (RM): " + saveFee)


        payBtn.setOnClickListener{

            if(regName.text.toString().equals(""))
            {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
                regName.requestFocus()
            }
            else if(regAge.text.toString().equals(""))
            {
                Toast.makeText(this, "Please enter your age", Toast.LENGTH_SHORT).show()
                regAge.requestFocus()
            }
            else if(regPhone.text.toString().equals(""))
            {
                Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show()
                regPhone.requestFocus()
            }
            else
            {
                with(sharedPreferences.edit())
                {
                    putString("MEMREG_NAME", nameEditText.text.toString())
                    putString("MEMREG_GENDER", spinnerGender.selectedItem.toString())
                    putString("MEMREG_AGE", ageEditText.text.toString())
                    putString("MEMREG_PHONE", phoneNoEditText.text.toString())
                    putString("NUMBER", number)
                    apply()
                }

                val intent = Intent(this, paymentActivity::class.java)
                startActivity(intent)
            }




        }


    }
}

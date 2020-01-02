package com.example.lavana

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_change_pass.*
import kotlinx.android.synthetic.main.activity_reset_pass.*

class resetPassActivity : AppCompatActivity() {

    lateinit var resetBtn : Button
    lateinit var firebase : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pass)

        resetBtn = findViewById(R.id.passResetBtn)

        var sharedPreferences = getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
        var getUserKey = sharedPreferences.getString("SESSION_FORGOT_USERKEY", "No Userkey")
        var getUsername = sharedPreferences.getString("SESSION_FORGOT_USERNAME", "No Username")
        var getUserEmail = sharedPreferences.getString("SESSION_FORGOT_USEREMAIL", "No UserEmail")
        var getAccType = sharedPreferences.getString("SESSION_FORGOT_ACCTYPE", "No Type")
        var sessProPic = sharedPreferences.getString("SESSION_FORGOT_PROPIC", "No Pic")

        var saveName = sharedPreferences.getString("SESSION_NAME", "No Name")
        var saveGender = sharedPreferences.getString("SESSION_GENDER", "No Gender")
        var savePhone = sharedPreferences.getString("SESSION_PHONE", "No Phone")
        var saveFee = sharedPreferences.getString("SESSION_FEE", "No Fee")
        var saveBio = sharedPreferences.getString("SESSION_BIO", "No Bio")
        var saveCoachSport = sharedPreferences.getString("SESSION_COACHSPORT", "No CoachSport")
        var saveTeachDay = sharedPreferences.getString("SESSION_TEACHDAY", "No TeachDay")
        var saveTeachTime = sharedPreferences.getString("SESSION_TEACHTIME", "No TeachTime")

        resetBtn.setOnClickListener{

            if(newResetPassEdit.text.toString().equals(newResetConPassEdit.text.toString()))
            {
                firebase = FirebaseDatabase.getInstance().getReference("users").child(getUserKey!!)

                val user = User(getUserKey!!, getUsername!!, newResetPassEdit.text.toString(), getUserEmail!!, getAccType!!, sessProPic!!,
                                saveName!!, saveGender!!, savePhone!!, saveFee!!, saveBio!!, saveCoachSport!!, saveTeachDay!!, saveTeachTime!!)

                firebase.setValue(user)

                Toast.makeText(this, "Password reset succesfully", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            }
            else
            {
                Toast.makeText(this, "New Password and Confirm New Password not match", Toast.LENGTH_SHORT).show()
                newResetPassEdit.requestFocus()
            }

        }

    }
}

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
import kotlinx.android.synthetic.main.activity_main.*
import android.R.id.message
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View
import com.google.android.material.snackbar.Snackbar


class changePassActivity : AppCompatActivity() {

    private lateinit var changeBtn : Button
    private lateinit var cancelBtn : Button
    private lateinit var firebase : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pass)

        setTitle("Change Password")

        var sharedPreferences = getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
        val sessionUsername = sharedPreferences.getString("SESSION_ID", getString(R.string.sharedPreUsername))
        val sessionPass  = sharedPreferences.getString("SESSION_PASS", "No Pass")
        val sessionUserKey = sharedPreferences.getString("SESSION_USERKEY", "No Key")
        val sessionEmail  = sharedPreferences.getString("SESSION_EMAIL", getString(R.string.sharedPreEmail))
        var saveAccType = sharedPreferences.getString("SESSION_ACCTYPE", "No Type")
        val sessProPic = sharedPreferences.getString("SESSION_PROPIC", "No Pic")

        var saveName = sharedPreferences.getString("SESSION_NAME", "No Name")
        var saveGender = sharedPreferences.getString("SESSION_GENDER", "No Gender")
        var savePhone = sharedPreferences.getString("SESSION_PHONE", "No Phone")
        var saveFee = sharedPreferences.getString("SESSION_FEE", "No Fee")
        var saveBio = sharedPreferences.getString("SESSION_BIO", "No Bio")
        var saveCoachSport = sharedPreferences.getString("SESSION_COACHSPORT", "No CoachSport")
        var saveTeachDay = sharedPreferences.getString("SESSION_TEACHDAY", "No TeachDay")
        var saveTeachTime = sharedPreferences.getString("SESSION_TEACHTIME", "No TeachTime")

        changeBtn = findViewById(R.id.passChangeBtn)
        cancelBtn = findViewById(R.id.passCancelButton)

        cancelBtn.setOnClickListener{
            val intent = Intent(this, drawerActivity::class.java)
            startActivity(intent)
            finish()
        }

        changeBtn.setOnClickListener{view : View ->

            if(sessionPass.equals(currentPassEdit.text.toString()))
            {
                if((newPassEdit.text.toString()).equals(newConPassEdit.text.toString()))
                {
                    firebase = FirebaseDatabase.getInstance().getReference("users").child(sessionUserKey!!)

                    val user = User(sessionUserKey, sessionUsername!!,newPassEdit.text.toString(), sessionEmail!!, saveAccType!!, sessProPic!!,
                                    saveName!!, saveGender!!, savePhone!!, saveFee!!, saveBio!!, saveCoachSport!!, saveTeachDay!!, saveTeachTime!!)

                    firebase.setValue(user)


                    Toast.makeText(this, "Password has been changed succesfully", Toast.LENGTH_SHORT).show()

                    var savePass = sharedPreferences.getString("SESSION_PASS", "No Pass")?:return@setOnClickListener

                    with(sharedPreferences.edit()){
                        putString("SESSION_PASS", newPassEdit.text.toString())
                        apply()
                    }

                    val intent = Intent(this, drawerActivity::class.java)
                    startActivity(intent)
                    finish()





                }
                else
                {
                    Toast.makeText(this, "New Password and Confirm New Password not match", Toast.LENGTH_SHORT).show()
                    newConPassEdit.requestFocus()
                }
            }
            else
            {
                Toast.makeText(this, "wrong current password, Please try again", Toast.LENGTH_SHORT).show()
            }

        }

    }
}

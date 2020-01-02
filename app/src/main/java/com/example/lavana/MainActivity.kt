package com.example.lavana

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var btnLogin : Button
    private lateinit var createAcc : TextView
    private lateinit var loginImg : ImageView
    private lateinit var forgotPass : TextView
    private lateinit var firebase : DatabaseReference
    private lateinit var userList : MutableList<User>
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var sharedPreferences = getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().commit()


        var correct = 0
        var sessionEmail : String = ""
        var sessionUserkey : String = ""
        var accType : String = ""
        var accImage : String = ""

        var accName : String = ""
        var accGender : String = ""
        var accPhone : String = ""
        var accFee : String = ""
        var accBio : String = ""
        var accCoachSport : String = ""
        var accTeachDay : String = ""
        var accTeachTime : String = ""



        userList = mutableListOf()
        btnLogin = findViewById(R.id.loginButton)
        createAcc = findViewById(R.id.createAcc)
        forgotPass = findViewById(R.id.resetPassword)

        forgotPass.setOnClickListener{
            val intent = Intent(this, forgotPassActivity::class.java)
            startActivity(intent)

        }


        btnLogin.setOnClickListener { view : View ->

            if(!(usernameEdit.text.toString().equals("") && passwordEdit.text.toString().equals("")))
            {

                //Initialize Progress Dialog
                progressDialog = ProgressDialog(this);
                //Show Dialog
                progressDialog.show()
                //Set content View
                progressDialog.setContentView(R.layout.progress_dialog)
                //Set Transparent Background
                progressDialog.window?.setBackgroundDrawable(
                    ColorDrawable(Color.TRANSPARENT)
                );


                firebase = FirebaseDatabase.getInstance().getReference("users")
                val intent = Intent(this, drawerActivity::class.java)

                Log.i("hi", "Before")
                firebase.addValueEventListener(object : ValueEventListener
                {
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {

                        Log.i("hi", p0.children.toString())
                        if(p0!!.exists())
                        {
                            for(u in p0.children)
                            {
                                val user = u.getValue(User::class.java)
                                userList.add(user!!)
                            }
                        }

                        for(u in userList)
                        {
                            if(u.username.equals(usernameEdit.text.toString()) && u.password.equals(passwordEdit.text.toString()))
                            {
                                correct = 1
                                sessionEmail = u.email.toString()
                                sessionUserkey = u.userKey.toString()
                                accType = u.accType.toString()
                                accImage = u.profilePic.toString()

                                accName = u.name.toString()
                                accGender = u.gender.toString()
                                accPhone = u.phone.toString()
                                accFee = u.fee.toString()
                                accBio = u.bio.toString()
                                accCoachSport = u.coachSport.toString()
                                accTeachDay = u.teachDay.toString()
                                accTeachTime = u.teachTime.toString()





                            }
                        }

                        if(correct == 1)
                        {
                            var sharedPreferences = getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
                            var saveUsername = sharedPreferences.getString("SESSION_ID", getString(R.string.sharedPreUsername))?:return
                            var saveEmail = sharedPreferences.getString("SESSION_EMAIL", getString(R.string.sharedPreEmail))?:return
                            var savePass = sharedPreferences.getString("SESSION_PASS", "No Pass")?:return
                            var saveUserKey = sharedPreferences.getString("SESSION_USERKEY", "No Key")?:return
                            var saveAccType = sharedPreferences.getString("SESSION_ACCTYPE", "No Type")?:return
                            var saveProPic = sharedPreferences.getString("SESSION_PROPIC", "No Pic")?:return

                            var saveName = sharedPreferences.getString("SESSION_NAME", "No Name")?:return
                            var saveGender = sharedPreferences.getString("SESSION_GENDER", "No Gender")?:return
                            var savePhone = sharedPreferences.getString("SESSION_PHONE", "No Phone")?:return
                            var saveFee = sharedPreferences.getString("SESSION_FEE", "No Fee")?:return
                            var saveBio = sharedPreferences.getString("SESSION_BIO", "No Bio")?:return
                            var saveCoachSport = sharedPreferences.getString("SESSION_COACHSPORT", "No CoachSport")?:return
                            var saveTeachDay = sharedPreferences.getString("SESSION_TEACHDAY", "No TeachDay")?:return
                            var saveTeachTime = sharedPreferences.getString("SESSION_TEACHTIME", "No TeachTime")?:return


                            with(sharedPreferences.edit()){
                                putString("SESSION_ID", usernameEdit.text.toString())
                                putString("SESSION_EMAIL", sessionEmail)
                                putString("SESSION_PASS", passwordEdit.text.toString())
                                putString("SESSION_USERKEY", sessionUserkey)
                                putString("SESSION_ACCTYPE", accType)
                                putString("SESSION_PROPIC", accImage)

                                putString("SESSION_NAME", accName)
                                putString("SESSION_GENDER", accGender)
                                putString("SESSION_PHONE", accPhone)
                                putString("SESSION_FEE", accFee)
                                putString("SSESSION_BIO", accBio)
                                putString("SESSION_COACHSPORT", accCoachSport)
                                putString("SESSION_TEACHDAY", accTeachDay)
                                putString("SESSION_TEACHTIME", accTeachTime)

                                apply()
                            }



                                    startActivity(intent)


                                finish()
                                correct = 0

                        }
                        else
                        {
                            Toast.makeText(applicationContext, "Invalid Account, Please try again", Toast.LENGTH_LONG).show()
                            progressDialog.dismiss()
                        }


                    }


                });
                Log.i("hi", "After")
            }
            else
            {
                Toast.makeText(applicationContext, "Please enter Username and Password", Toast.LENGTH_SHORT).show()
            }




        }

        createAcc.setOnClickListener { view: View ->

            val intent = Intent(this, registerActivity::class.java)
            startActivity(intent)
            finish()
        }




    }

    override fun onBackPressed() {
        super.onBackPressed()

        progressDialog.dismiss()
        finish()
        finish()
    }


}

package com.example.lavana

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_admin_change_coach.*
import kotlinx.android.synthetic.main.activity_training_class_coach.*

class trainingClassCoachActivity : AppCompatActivity() {

    private lateinit var registerBtn : Button
    private lateinit var sportClass : TextView
    private lateinit var firebase : DatabaseReference
    private lateinit var firebase1 : DatabaseReference
    private lateinit var userList : MutableList<User>
    private lateinit var coachDayTimeList : MutableList<CoachDayTime>
    private lateinit var nameTxt : TextView
    private lateinit var genderTxt : TextView
    private lateinit var phoneTxt : TextView
    private lateinit var feeTxt : TextView
    private lateinit var descTxt : TextView
    var thisCoachName : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training_class_coach)

        var sharedPreferences = getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
        var saveTimeSelected = sharedPreferences.getString("SELECTED_TIME", "No Time")
        var saveDaySelected = sharedPreferences.getString("SELECTED_DAY", "No Day")
        var saveSports = sharedPreferences.getString("SELECTED_SPORT", "No Sport")
        userList = mutableListOf()
        coachDayTimeList = mutableListOf()
        nameTxt = findViewById(R.id.registerUserName_textView)
        genderTxt = findViewById(R.id.gender_textView)
        phoneTxt = findViewById(R.id.coachPhoneNoTitle)
        feeTxt = findViewById(R.id.fee_textView)
        descTxt = findViewById(R.id.textView16)
        sportClass = findViewById(R.id.registerTrainingClass_textView)
        sportClass.setText(saveSports + " Class")

        setTitle(saveDaySelected.toString() + ", " + saveTimeSelected.toString())


        registerBtn = findViewById(R.id.registerButton)

        registerBtn.setOnClickListener{
            val intent = Intent(this, trainingClassRegisterActivity::class.java)
            startActivity(intent)
        }




        //to solve the asynchronouse problem in firebase
        firebase = FirebaseDatabase.getInstance().getReference("users")

        readData(object : trainingClassCoachActivity.FirebaseCallBack {
            override fun onCallBack(user : MutableList<User>) {
                userList = user

                for( u in userList)
                {
                    Log.i("hi", u.username)
                }
            }

        });

        //to retrieve coahdaytime path data
        //to solve the asynchronouse problem in firebase
        firebase1 = FirebaseDatabase.getInstance().getReference("coachDayTime")

        readData1(object : trainingClassCoachActivity.FirebaseCallBack1 {
            override fun onCallBack1(user : MutableList<CoachDayTime>) {
                coachDayTimeList = user

                val coachClassKey = (saveDaySelected + " " + saveTimeSelected + " " + saveSports).toString()

                for( u in coachDayTimeList)
                {
                    if(u.dayAndTime.equals(coachClassKey))
                    {
                        thisCoachName = u.coachName
                    }
                    Log.i("hinehneh", u.coachName)
                }

                if(!(thisCoachName.equals("")))
                {
                    for(u in userList)
                    {
                        Log.i("hiDont", u.name.toString())
                        if((u.name).equals(thisCoachName))
                        {

                            nameTxt.setText("Name: " + (u.name).toString())
                            genderTxt.setText("Gender: " + (u.gender).toString())
                            phoneTxt.setText("Phone No: " + (u.phone).toString())
                            feeTxt.setText("Fee Per Month(RM): " + (u.fee).toString())
                            descTxt.setText((u.bio).toString())

                            var sharedPreferences = getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
                            var saveName = sharedPreferences.getString("UNDERCOACH_NAME", "No Name")?:return
                            var saveGender = sharedPreferences.getString("UNDERCOACH_GENDER", "No Gender")?:return
                            var savePhone = sharedPreferences.getString("UNDERCOACH_PHONE", "No Phone")?:return
                            var saveFee = sharedPreferences.getString("UNDERCOACH_FEE", "No Fee")?:return

                            with(sharedPreferences.edit())
                            {
                                putString("UNDERCOACH_NAME", u.name)
                                putString("UNDERCOACH_GENDER", u.gender)
                                putString("UNDERCOACH_PHONE", u.phone)
                                putString("UNDERCOACH_FEE", u.fee)
                                apply()
                            }

                            if(!((u.profilePic).equals("No Pic") || (u.profilePic).equals("")))
                            {
                                Glide
                                    .with(applicationContext)
                                    .load(Uri.parse((u.profilePic).toString()))
                                    .apply(RequestOptions.circleCropTransform()).into(coach_imageview);
                            }


                        }
                    }
                }
                else
                {
                    openAlertDialog()
                }


            }

        });


    }





    fun openAlertDialog()
    {

            registerBtn.visibility = View.INVISIBLE

        val mAlertDialog = AlertDialog.Builder(this)
        mAlertDialog.setTitle("Title")
        mAlertDialog.setMessage("No Coach for this class, Please choose other class")
        mAlertDialog.setPositiveButton("OK"){dialog, id ->


            dialog.dismiss()

        }

        mAlertDialog.show()
    }


    interface FirebaseCallBack{
        fun onCallBack(user : MutableList<User>)
    }

    fun readData(firebaseCallBack: FirebaseCallBack)
    {

        firebase.addValueEventListener(object : ValueEventListener
        {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("TAG", p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {

                if(p0!!.exists())
                {
                    for(u in p0.children)
                    {
                        Log.i("hi", "insideloop1")
                        val user = u.getValue(User::class.java)
                        userList.add(user!!)
                    }
                }

                firebaseCallBack.onCallBack(userList)


            }


        });


    }








    interface FirebaseCallBack1{
        fun onCallBack1(user : MutableList<CoachDayTime>)
    }

    fun readData1(firebaseCallBack1: FirebaseCallBack1)
    {

        firebase1.addValueEventListener(object : ValueEventListener
        {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("TAG", p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {

                if(p0!!.exists())
                {
                    for(u in p0.children)
                    {
                        Log.i("hi", "insideloop1")
                        val coachDayTimee = u.getValue(CoachDayTime::class.java)
                        coachDayTimeList.add(coachDayTimee!!)
                    }
                }

                firebaseCallBack1.onCallBack1(coachDayTimeList!!)


            }


        });


    }

}

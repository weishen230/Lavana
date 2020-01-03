package com.example.lavana

import android.content.Context
import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_admin_change_coach.*
import kotlinx.android.synthetic.main.activity_change_profile_pic.*

class adminChangeCoachActivity : AppCompatActivity() {

    lateinit var chgCoachBtn : Button
    lateinit var delCoachBtn : Button
    lateinit var headerImage : ImageView
    lateinit var nameTxt : TextView
    lateinit var genderTxt : TextView
    lateinit var phoneTxt : TextView
    lateinit var feeTxt : TextView
    lateinit var descTxt : TextView
    lateinit var firebase : DatabaseReference
    lateinit var firebase1 : DatabaseReference
    private lateinit var userList : MutableList<User>
    private lateinit var coachDayTimeList : MutableList<CoachDayTime>
    private lateinit var thisCoachName : String
    private lateinit var textHeader : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_change_coach)

        chgCoachBtn = findViewById(R.id.changeCoachButton)
        delCoachBtn = findViewById(R.id.removeCoachButton)
        headerImage = findViewById(R.id.adminimageView3)
        textHeader = findViewById(R.id.adminregisterTrainingClass_textView)

        nameTxt = findViewById(R.id.adminregisterUserName_textView)
        genderTxt = findViewById(R.id.admingender_textView)
        phoneTxt = findViewById(R.id.admincoachPhoneNoTitle)
        feeTxt = findViewById(R.id.adminfee_textView)
        descTxt = findViewById(R.id.admintextView16)
        userList = mutableListOf()
        coachDayTimeList = mutableListOf()
        thisCoachName = ""


        var sharedPreferences = getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
        var saveSports = sharedPreferences.getString("SELECTED_SPORT", "No Sport")
        var saveTimeSelected = sharedPreferences.getString("SELECTED_TIME", "No Time")
        var saveDaySelected = sharedPreferences.getString("SELECTED_DAY", "No Day")

        textHeader.setText(saveSports + " Class")

/*
        var saveName= sharedPreferences.getString("NEWCOACH_NAME", "No Name")
        var saveGender= sharedPreferences.getString("NEWCOACH_GENDER", "No Gender")
        var savePhone= sharedPreferences.getString("NEWCOACH_PHONE", "No Phone")
        var saveFee= sharedPreferences.getString("NEWCOACH_FEE", "No Fee")
        var saveDesc= sharedPreferences.getString("NEWCOACH_DESC", "No Desc")
        var savePic= sharedPreferences.getString("NEWCOACH_PIC", "No Pic")

        if(!(saveName.equals("No Name") || saveName.equals("")))
            {
                nameTxt.setText("Name: " + saveName.toString())
                genderTxt.setText("Gender: " + saveGender.toString())
                phoneTxt.setText("Phone No: " + savePhone.toString())
                feeTxt.setText("Fee Per Month(RM): " + saveFee.toString())
                descTxt.setText(saveDesc.toString())

                if(!(savePic.equals("No Pic") || savePic.equals("")))
                {
                    Glide
                        .with(this)
                        .load(Uri.parse(savePic.toString()))
                        .apply(RequestOptions.circleCropTransform()).into(admincoach_imageview);
                }

            }*/

        setTitle(saveDaySelected.toString() + ", " + saveTimeSelected.toString())



        if(saveSports.equals("Badminton"))
        {
            headerImage.setImageResource(R.drawable.badminton)
        }
        else if(saveSports.equals("Volleyball"))
        {
            headerImage.setImageResource(R.drawable.volleyball_logo)
        }
        else if(saveSports.equals("Basketball"))
        {
            headerImage.setImageResource(R.drawable.basketball)
        }
        else if(saveSports.equals("Football"))
        {
            headerImage.setImageResource(R.drawable.football)
        }



        chgCoachBtn.setOnClickListener{

            val intent = Intent(this, adminSelectCoachActivity::class.java)
            startActivity(intent)
            //finish()
        }

        //to solve the asynchronouse problem in firebase
        firebase = FirebaseDatabase.getInstance().getReference("users")

        readData(object : adminChangeCoachActivity.FirebaseCallBack {
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

        readData1(object : adminChangeCoachActivity.FirebaseCallBack1 {
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

                            if(!((u.profilePic).equals("No Pic") || (u.profilePic).equals("")))
                            {
                                Glide
                                    .with(applicationContext)
                                    .load(Uri.parse((u.profilePic).toString()))
                                    .apply(RequestOptions.circleCropTransform()).into(admincoach_imageview);
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
        val mAlertDialog = AlertDialog.Builder(this)
        mAlertDialog.setTitle("Title")
        mAlertDialog.setMessage("No Coach for this class, Please add New Coach.")
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

                firebaseCallBack1.onCallBack1(coachDayTimeList)


            }


        });


    }

}

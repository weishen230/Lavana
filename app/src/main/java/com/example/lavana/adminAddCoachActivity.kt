package com.example.lavana

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_admin_add_coach.*
import kotlinx.android.synthetic.main.activity_change_profile_pic.*
import kotlinx.android.synthetic.main.activity_register.*

class adminAddCoachActivity : AppCompatActivity() {

    private lateinit var userList : MutableList<User>
    private lateinit var firebase2 : DatabaseReference
    lateinit var mImageUri : Uri
    lateinit var mStorageReg : StorageReference
    private lateinit var addNewCoachBtn : Button
    private lateinit var progressDialog: ProgressDialog

  //  private lateinit var browseBtn : Button
   // var url : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_add_coach)



        userList = mutableListOf()
        addNewCoachBtn = findViewById(R.id.addNewwCoachButton)
        //browseBtn = findViewById(R.id.selectImageButton)
        var sharedPreferences = getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
        var saveSports = sharedPreferences.getString("SELECTED_SPORT", "No Sport")
        var saveTimeSelected = sharedPreferences.getString("SELECTED_TIME", "No Time")
        var saveDaySelected = sharedPreferences.getString("SELECTED_DAY", "No Day")


        /*  browseBtn.setOnClickListener{
               //check runtime permission
               if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
               {
                   if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                   {
                       //permission denied
                       val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)

                       //show popup to request runtime permission
                       requestPermissions(permissions, PERMISSION_CODE)
                   }
                   else
                   {
                       //permission already granted
                      // pickImageFromGallery()
                   }
               }
               else
               {
                   //System OS is < Marshallow
               }
        }*/


        addNewCoachBtn.setOnClickListener{



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



            if(anyMatch())
            {
                if(passMatch())
                {
                    //uploadFile()

                    val coachName = newCoachName.text.toString()
                    val coachGender = adminSpinnerGender.selectedItem.toString()
                    val coachPhone = adminPhoneNoEditText.text.toString()
                    val coachFee = adminFeePerMonth.text.toString()
                    val coachBio = editTextBio.text.toString()
                    val coachSport = saveSports.toString()
                    val coachTeachDay = saveDaySelected.toString()
                    val coachTeachTime = saveTimeSelected.toString()
                    val coachUsername = editTextUsername.text.toString()
                    val coachPassword = editTextPassword.text.toString()
                    val coachEmail = editTextEmail.text.toString()
                    val accType = "coach"
                    val coachProPic = ""




                    val firebase = FirebaseDatabase.getInstance().getReference("users")
                    val firebase1 = FirebaseDatabase.getInstance().getReference("coachDayTime")

                    //assign unique key for each registered member in firebase
                    val userKey = firebase.push().key.toString()

                    val newCoach = User(userKey, coachUsername, coachPassword, coachEmail, accType, coachProPic,
                                    coachName, coachGender, coachPhone, coachFee, coachBio, coachSport, coachTeachDay, coachTeachTime)

                    val coachClassKey = (coachTeachDay + " " + coachTeachTime + " " + coachSport).toString()

                    val coachDaytime = CoachDayTime(coachClassKey, coachTeachDay, coachTeachTime, coachUsername, coachName)

                    firebase.child(userKey).setValue(newCoach).addOnCompleteListener{

                        firebase1.child(coachClassKey).setValue(coachDaytime).addOnCompleteListener{

                        }

                        Toast.makeText(this, "Add New Coach Succesfully", Toast.LENGTH_LONG).show()
                        progressDialog.dismiss()

                        newCoachName.setText("")
                        adminPhoneNoEditText.setText("")
                        adminFeePerMonth.setText("")
                        editTextBio.setText("")
                        editTextUsername.setText("")
                        editTextPassword.setText("")
                        editTextConPass.setText("")
                        editTextEmail.setText("")

                        /*val intent = Intent(this, adminSelectCoachActivity::class.java)
                        startActivity(intent)
                        finish()*/
                    }

                }
            }

        }





        setTitle("Add New Coach")


        //to solve the asynchronouse problem in firebase
        firebase2 = FirebaseDatabase.getInstance().getReference("users")
       // mStorageReg = FirebaseStorage.getInstance().getReference("uploads")

        readData(object : FirebaseCallBack {
            override fun onCallBack(user : MutableList<User>) {
                userList = user

                for( u in userList)
                {
                    Log.i("hiReg", u.username)
                }
            }

        });

    }


    fun passMatch() : Boolean
    {
        val password = editTextPassword.text.toString()
        val conPass = editTextConPass.text.toString()

        if(password.equals(conPass))
        {
            return true
        }
        else
        {
            Toast.makeText(this, "Password did not match", Toast.LENGTH_SHORT).show()
            editTextPassword.requestFocus()
            progressDialog.dismiss()
            return false
        }

    }

    fun anyMatch() : Boolean
    {
        var noMatch = true;
        var usernameExist = 0;
        var emailExist = 0;

        val username = editTextUsername.text.toString()
        val email = editTextEmail.text.toString()


        Log.i("hi", "oi")
        for(u in userList)
        {
            Log.i("hi", "inside")
            if(u.username.equals(username))
            {
                Log.i("hi", "meh")
                usernameExist = 1
                noMatch = false
                Toast.makeText(applicationContext, "Username already exists", Toast.LENGTH_SHORT).show()
            }
            else if(u.email.equals(email))
            {
                emailExist = 1
                noMatch = false
                Toast.makeText(applicationContext, "Email already exists", Toast.LENGTH_SHORT).show()
            }
        }
        Log.i("hi", "beforeReturn")
        return noMatch

    }




   /* override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE)
        {
            coachPersonalImageView.setImageURI(data?.data)
            mImageUri = data?.data!!
            Log.i("wey ganneh", mImageUri.toString())
        }

    } */




    interface FirebaseCallBack{
        fun onCallBack(user : MutableList<User>)
    }

    fun readData(firebaseCallBack: FirebaseCallBack)
    {

        firebase2.addValueEventListener(object : ValueEventListener
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

}

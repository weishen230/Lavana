package com.example.lavana

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*
import com.google.firebase.database.DataSnapshot
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




class registerActivity : AppCompatActivity() {

    private lateinit var alreadAcc : TextView
    private lateinit var cancelBtn : Button
    private lateinit var registerBtn : Button
    private lateinit var progressDialog: ProgressDialog
    private lateinit var userList : MutableList<User>
    private lateinit var firebase1 : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //clear all session
        var sharedPreferences = getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().commit()


        userList = mutableListOf()
        alreadAcc = findViewById(R.id.registerdAcc)
        cancelBtn = findViewById(R.id.cancelButton)
        registerBtn = findViewById(R.id.loginButton)





        alreadAcc.setOnClickListener{view : View ->

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        cancelBtn.setOnClickListener{view : View ->

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        registerBtn.setOnClickListener{view : View ->


            if(usernameEditReg.text.toString().equals("") && passwordEditReg.text.toString().equals("") && passwordEditConReg.text.toString().equals("") && emailEdit.text.toString().equals(""))
            {
                Toast.makeText(this, "Fill in all the blank", Toast.LENGTH_LONG).show()
            }
            else if(usernameEditReg.text.toString().equals(""))
            {
                Toast.makeText(this, "Username must not be empty", Toast.LENGTH_LONG).show()
                usernameEditReg.requestFocus()
            }
            else if(passwordEditReg.text.toString().equals(""))
            {
                Toast.makeText(this, "Password must not be empty", Toast.LENGTH_LONG).show()
                passwordEditReg.requestFocus()
            }
            else if(passwordEditConReg.text.toString().equals(""))
            {
                Toast.makeText(this, "Confirm Password must not be empty", Toast.LENGTH_LONG).show()
                passwordEditConReg.requestFocus()
            }
            else if(emailEdit.text.toString().equals(""))
            {
                Toast.makeText(this, "Email must not be empty", Toast.LENGTH_LONG).show()
                emailEdit.requestFocus()
            }
            else
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


                if(anyMatch())
                {
                    if(passMatch())
                    {
                        val username = usernameEditReg.text.toString()
                        val password = passwordEditReg.text.toString()
                        val email = emailEdit.text.toString()

                        val firebase = FirebaseDatabase.getInstance().getReference("users")

                        //assign unique key for each registered member in firebase
                        val userKey = firebase.push().key.toString()

                        val newUser = User(userKey, username, password, email, "member", "", "", "", "", "", "", "", "", "")

                        firebase.child(userKey).setValue(newUser).addOnCompleteListener{

                            Toast.makeText(this, "Register Successfully, Let's login", Toast.LENGTH_LONG).show()
                            progressDialog.dismiss()

                            usernameEditReg.setText("")
                            passwordEditReg.setText("")
                            passwordEditConReg.setText("")
                            emailEdit.setText("")

                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()

                        }

                    }
                }
                else
                {
                    progressDialog.dismiss()
                }




            }





        }



        setTitle("Register")

        //to solve the asynchronouse problem in firebase
        firebase1 = FirebaseDatabase.getInstance().getReference("users")

        readData(object : FirebaseCallBack{
            override fun onCallBack(user : MutableList<User>) {
                userList = user

                for( u in userList)
                {
                    Log.i("hi", u.username)
                }
            }

        });

    }

    fun passMatch() : Boolean
    {
        val password = passwordEditReg.text.toString()
        val conPass = passwordEditConReg.text.toString()

        if(password.equals(conPass))
        {
            return true
        }
        else
        {
            Toast.makeText(this, "Password did not match", Toast.LENGTH_SHORT).show()
            passwordEditConReg.requestFocus()
            progressDialog.dismiss()
            return false
        }

    }

    fun anyMatch() : Boolean
    {
        var noMatch = true;
        var usernameExist = 0;
        var emailExist = 0;

        val username = usernameEditReg.text.toString()
        val email = emailEdit.text.toString()


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

    interface FirebaseCallBack{
        fun onCallBack(user : MutableList<User>)
    }

    fun readData(firebaseCallBack: FirebaseCallBack)
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
                        val user = u.getValue(User::class.java)
                        userList.add(user!!)
                    }
                }

                firebaseCallBack.onCallBack(userList)


            }


        });


    }
}

package com.example.lavana

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.se.omapi.Session
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.android.example.github.AppExecutors
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_change_pass.*
import kotlinx.android.synthetic.main.activity_forgot_pass.*
import kotlinx.android.synthetic.main.activity_main.*
import java.net.PasswordAuthentication
import javax.mail.MessagingException
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import kotlin.random.Random

class forgotPassActivity : AppCompatActivity() {

    lateinit var appExecutors: AppExecutors
    lateinit var sendBtn : Button
    lateinit var submitBtn : Button
    private lateinit var userList : MutableList<User>
    private lateinit var firebase : DatabaseReference
    var randomInt : String = ""

    var userKey : String = ""
    var username : String = ""
    var userEmail : String = ""
    var userAccType : String = ""
    var userProPic : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pass)

        userList = mutableListOf()

        setTitle("Forgot Password")

        appExecutors = AppExecutors()

        sendBtn = findViewById(R.id.passForgotSendBtn)
        submitBtn = findViewById(R.id.passForgotSubmitButton)

        sendBtn.setOnClickListener{view : View ->
            sendEmail()

            sendBtn.setText("RE-SEND")

            Snackbar.make(view, "Vertification Code has sent to your email", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        submitBtn.setOnClickListener{

            if(isEmpty())
            {
                if(codeMatch() && validEmail())
                {
                    //store the user detail temporarily in sharedPreferences, use to update the user account later
                    var sharedPreferences = getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
                    var saveUserKey = sharedPreferences.getString("SESSION_FORGOT_USERKEY", "No Userkey")?: return@setOnClickListener
                    var saveUsername = sharedPreferences.getString("SESSION_FORGOT_USERNAME", "No Username")?: return@setOnClickListener
                    var saveUserEmail = sharedPreferences.getString("SESSION_FORGOT_USEREMAIL", "No UserEmail")?: return@setOnClickListener
                    var saveAccType = sharedPreferences.getString("SESSION_FORGOT_ACCTYPE", "No Type")?: return@setOnClickListener
                    val sessProPic = sharedPreferences.getString("SESSION_FORGOT_PROPIC", "No Pic")?:return@setOnClickListener

                    with(sharedPreferences.edit()){
                        putString("SESSION_FORGOT_USERKEY", userKey)
                        putString("SESSION_FORGOT_USERNAME", username)
                        putString("SESSION_FORGOT_USEREMAIL", userEmail)
                        putString("SESSION_FORGOT_ACCTYPE", userAccType)
                        putString("SESSION_FORGOT_PROPIC", userProPic)

                        apply()
                    }

                    val intent = Intent(this, resetPassActivity::class.java)
                    startActivity(intent)
                    finish()

                }
            }
            else
            {
                Toast.makeText(this, "Please Enter Vertification Code", Toast.LENGTH_SHORT).show()
                forgotPassCodeEdit.requestFocus()
            }

        }




        //to solve the asynchronouse problem in firebase
        firebase = FirebaseDatabase.getInstance().getReference("users")

        readData(object : forgotPassActivity.FirebaseCallBack {
            override fun onCallBack(user : MutableList<User>) {
                userList = user

                for( u in userList)
                {
                    Log.i("hi", u.username)
                }
            }

        });

    }

    private fun sendEmail(){

        val email : String = "tarucpg@gmail.com"
        val password : String = "tarc1234"
        randomInt = (Random.nextInt(999999) + 100000).toString()


        appExecutors.diskIO().execute {
            val props = System.getProperties()
            props.put("mail.smtp.host", "smtp.gmail.com")
            props.put("mail.smtp.socketFactory.port", "465")
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
            props.put("mail.smtp.auth", "true")
            props.put("mail.smtp.port", "465")



            val session =  javax.mail.Session.getInstance(props,
                object : javax.mail.Authenticator() {
                    //Authenticating the password
                    override fun getPasswordAuthentication(): javax.mail.PasswordAuthentication {
                        return javax.mail.PasswordAuthentication(email, password)//PasswordAuthentication("tarucpg@gmail.com", password.toCharArray())
                    }
                })

            try {
                //Creating MimeMessage object
                val mm = MimeMessage(session)
                val emailId = forgotPassEmailEdit.text.toString()
                //Setting sender address
                mm.setFrom(InternetAddress(email))
                //Adding receiver
                mm.addRecipient(javax.mail.Message.RecipientType.TO,
                    InternetAddress(emailId))
                //Adding subject
                mm.subject = "Forgot Password"
                //Adding message
                mm.setText("Hi, here is your vertification Code: " + randomInt + "")

                //Sending email
                Transport.send(mm)

                appExecutors.mainThread().execute {
                    //Something that should be executed on main thread.
                }

            } catch (e: MessagingException) {
                e.printStackTrace()
            }
        }
    }

    fun isEmpty() : Boolean
    {
        var notEmpty = true

        if((forgotPassCodeEdit.text.toString()).equals(""))
            {
                notEmpty = false
            }

        return notEmpty
    }

    fun codeMatch() : Boolean
    {
        var match = false

        if((forgotPassCodeEdit.text.toString()).equals(randomInt))
        {
            match = true
        }

        if(match == false)
        {
            Toast.makeText(this, "Invalid Vertification Code", Toast.LENGTH_SHORT).show()
        }

        return match
    }

    fun validEmail() : Boolean{

        var valid = false

        for(u in userList)
        {
            if(u.email.equals(forgotPassEmailEdit.text.toString()))
            {
                userKey = u.userKey.toString()
                username = u.username.toString()
                userEmail = u.email.toString()
                userAccType = u.accType.toString()
                userProPic = u.profilePic.toString()

                valid = true
            }
        }

        if(valid == false)
        {
            Toast.makeText(this, "This is not the account's registered email", Toast.LENGTH_SHORT).show()
        }

        return valid
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
}

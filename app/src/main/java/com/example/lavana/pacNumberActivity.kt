package com.example.lavana

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.Toast
import com.android.example.github.AppExecutors
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_forgot_pass.*
import kotlinx.android.synthetic.main.activity_pac_number.*
import javax.mail.MessagingException
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import kotlin.random.Random

class pacNumberActivity : AppCompatActivity() {

    private lateinit var proceedBtn : Button
    private lateinit var resendBtn : Button
    private lateinit var saveEmail: String
    lateinit var appExecutors: AppExecutors
    var randomInt : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pac_number)

        setTitle("Payment Confirmation")

        proceedBtn = findViewById(R.id.paymentProceedBtn)
        resendBtn = findViewById(R.id.pacNumberResendBtn)

        var sharedPreferences = getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
        saveEmail = (sharedPreferences.getString("SESSION_EMAIL", getString(R.string.sharedPreEmail))).toString()



        resendBtn.setOnClickListener{

            sendEmail()
            Toast.makeText(this, "Kindly Check you email for PAC Code", Toast.LENGTH_SHORT).show()


        }


        proceedBtn.setOnClickListener{

            var tempCode  = sharedPreferences.getString("TEMP_CODE", getString(R.string.sharedPreEmail))

            if(tempCode.equals(pacnumberEdit.text.toString()))
            {
                var saveTimeSelected = sharedPreferences.getString("SELECTED_TIME", "No Time")
                var saveDaySelected = sharedPreferences.getString("SELECTED_DAY", "No Day")
                var saveSports = sharedPreferences.getString("SELECTED_SPORT", "No Sport")
                var saveCoachName = sharedPreferences.getString("UNDERCOACH_NAME", "No Name")
                var saveUsername = sharedPreferences.getString("SESSION_ID", getString(R.string.sharedPreUsername))

                val firebase = FirebaseDatabase.getInstance().getReference("Training Class")
                val classKey = firebase.push().key.toString()

                val newTrainingClass = MyClass(saveUsername!!, saveSports!!, saveDaySelected!!, saveTimeSelected!!, saveCoachName!!, saveDaySelected!! + " " + saveTimeSelected!! + " " + saveSports!!)

                firebase.child(classKey).setValue(newTrainingClass).addOnCompleteListener{

                    val intent = Intent(this, paymentSuccessful::class.java)
                    startActivity(intent)

                }

            }
            else
            {
                Toast.makeText(this, "Invalid PAC Code, try again", Toast.LENGTH_SHORT).show()
            }
        }


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
                val emailId = saveEmail
                //Setting sender address
                mm.setFrom(InternetAddress(email))
                //Adding receiver
                mm.addRecipient(javax.mail.Message.RecipientType.TO,
                    InternetAddress(emailId)
                )
                //Adding subject
                mm.subject = "Payment Confirmation"
                //Adding message
                mm.setText("Hi, here is your PAC Code: " + randomInt + "")

                //Sending email
                Transport.send(mm)

                var sharedPreferences = getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
                var tempCode  = sharedPreferences.getString("TEMP_CODE", getString(R.string.sharedPreEmail))?:return@execute

                with(sharedPreferences.edit())
                {
                    putString("TEMP_CODE", randomInt)
                    apply()
                }

                appExecutors.mainThread().execute {
                    //Something that should be executed on main thread.
                }

            } catch (e: MessagingException) {
                e.printStackTrace()
            }
        }
    }
}

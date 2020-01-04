package com.example.lavana

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.*
import com.android.example.github.AppExecutors
import kotlinx.android.synthetic.main.activity_forgot_pass.*
import java.util.regex.Pattern
import javax.mail.MessagingException
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import kotlin.random.Random

class paymentActivity : AppCompatActivity() {

    private lateinit var submitBtn : Button
    lateinit var appExecutors: AppExecutors
    var randomInt : String = ""
    var sessionEmail : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        appExecutors = AppExecutors()

        var sharedPreferences = getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
        sessionEmail  = (sharedPreferences.getString("SESSION_EMAIL", getString(R.string.sharedPreEmail))).toString()

        setTitle("Payment")

        submitBtn = findViewById(R.id.submit)

        /*submit.setOnClickListener{
            val intent = Intent(this, pacNumberActivity::class.java)
            startActivity(intent)
        }*/





        ///copy from sinpei work
        /*
        val emailcheck = findViewById<EditText>(R.id.email)
        emailcheck.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            // If the event is a key-down event on the "enter" button
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                // Perform action on key press
                //Toast.makeText(applicationContext, "The email address format are wrong!!", Toast.LENGTH_LONG)
                checkemail()
                return@OnKeyListener true
            }
            false
        })*/

       /* val phonecheck = findViewById<EditText>(R.id.phonenumber)
        phonecheck.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            // If the event is a key-down event on the "enter" button
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                // Perform action on key press
                //Toast.makeText(applicationContext, "The email address format are wrong!!", Toast.LENGTH_LONG)
                checkphone()
                return@OnKeyListener true
            }
            false
        })*/

        val namecheck = findViewById<EditText>(R.id.name)
        namecheck.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            // If the event is a key-down event on the "enter" button
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                // Perform action on key press
                //Toast.makeText(applicationContext, "The email address format are wrong!!", Toast.LENGTH_LONG)
                checkname()
                return@OnKeyListener true
            }
            false
        })

        val cardcheck = findViewById<EditText>(R.id.cardnum)
        cardcheck.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            // If the event is a key-down event on the "enter" button
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                // Perform action on key press
                //Toast.makeText(applicationContext, "The email address format are wrong!!", Toast.LENGTH_LONG)
                checkcardnum()
                return@OnKeyListener true
            }
            false
        })

        val month = resources.getStringArray(R.array.month)
        val spinner = findViewById<Spinner>(R.id.month)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, month
            )
            spinner.adapter = adapter
        }
        checkmonth()


        //(View.OnKeyListener { v, keyCode, event ->
        // If the event is a key-down event on the "enter" button
        //if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
        // Perform action on key press
        //Toast.makeText(applicationContext, "The email address format are wrong!!", Toast.LENGTH_LONG)

        //     return@OnKeyListener true
        // }
        // false
        // })

        val year = resources.getStringArray(R.array.year)
        val yearspinner = findViewById<Spinner>(R.id.year)
        if (yearspinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, year
            )
            yearspinner.adapter = adapter
        }
        //yearspinner.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
        // If the event is a key-down event on the "enter" button
        // if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
        // Perform action on key press
        //Toast.makeText(applicationContext, "The email address format are wrong!!", Toast.LENGTH_LONG)
        checkyear()
        // return@OnKeyListener true
        // }
        // false
        // })

        val cvvcheck = findViewById<EditText>(R.id.cardCVCEditText)
        cvvcheck.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            // If the event is a key-down event on the "enter" button
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                // Perform action on key press
                //Toast.makeText(applicationContext, "The email address format are wrong!!", Toast.LENGTH_LONG)
                checkcvv()
                return@OnKeyListener true
            }
            false
        })

        submitcheck()

        submitBtn.setOnClickListener {
            sendEmail()
            val intent = Intent(this, pacNumberActivity::class.java)
            // start your next activity
            startActivity(intent)
        }

    }


    //all the useful function
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
                val emailId = sessionEmail
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




    /*
    fun checkemail()
    {
        val emailcheck = findViewById<EditText>(R.id.email)
        val textemail = emailcheck.text.toString()
        val emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$"

        val pat = Pattern.compile(emailRegex)
        //val nullerror = Toast.makeText(applicationContext, "The email address cannot be null!!", Toast.LENGTH_LONG)
        //nullerror.show()
        //val error = Toast.makeText(applicationContext, "The email address format are wrong!!", Toast.LENGTH_LONG)

        if (textemail == null || textemail == "")
        {
            //nullerror.show()
            emailcheck.setText("")
            emailcheck.setError(getString(R.string.nullemail))

        }
        else if(pat.matcher(textemail).matches())
        {

        }
        else
        {
            //error.show()
            emailcheck.setText("")
            emailcheck.setError(getString(R.string.erroremail))

        }
    }*/

    /*
    fun checkphone()
    {
        val phone = findViewById<EditText>(R.id.phonenumber)
        val textphone = phone.text.toString()
        val letter = Pattern.compile("[a-zA-z]")
        val digit = Pattern.compile("[0-9]")
        val special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]")

        val hasLetter = letter.matcher(textphone)
        val hasDigit = digit.matcher(textphone)
        val hasSpecial = special.matcher(textphone)

        val pattern = Pattern.compile("\\d{3}-\\d{7}")

        val matcher = pattern.matcher(textphone)

        if(textphone == null || textphone == "")
        {
            phone.setText("")
            phone.setError(getString(R.string.nullname))

        }
        else if(hasLetter.find())
        {
            phone.setText("")
            phone.setError(getString(R.string.numname))

        }
        else if(matcher.matches())
        {

        }
        else
        {
            phone.setText("")
            phone.setError(getString(R.string.numname))
        }

    }*/

    fun checkname()
    {
        val name = findViewById<EditText>(R.id.name)
        val textname = name.text.toString()
        val letter = Pattern.compile("[a-zA-z]")
        val digit = Pattern.compile("[0-9]")
        val special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]")
        //Pattern eight = Pattern.compile (".{8}");


        //val hasLetter = letter.matcher(textname)
        val hasDigit = digit.matcher(textname)
        val hasSpecial = special.matcher(textname)

        if(textname == null || textname == "")
        {
            name.setText("")
            name.setError(getString(R.string.nullname))

        }
        else if(hasDigit.find() || hasSpecial.find())
        {
            name.setText("")
            name.setError(getString(R.string.numname))

        }
        else
        {

        }

    }


    fun checkcardnum()
    {
        val cardnum = findViewById<EditText>(R.id.cardnum)
        val card = cardnum.text.toString()
        val regex = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" +
                "(?<mastercard>5[1-5][0-9]{14})|" +
                "(?<discover>6(?:011|5[0-9]{2})[0-9]{12})|" +
                "(?<amex>3[47][0-9]{13})|" +
                "(?<diners>3(?:0[0-5]|[68][0-9])?[0-9]{11})|" +
                "(?<jcb>(?:2131|1800|35[0-9]{3})[0-9]{11}))$"

        val letter = Pattern.compile("[a-zA-z]")
        val digit = Pattern.compile("[0-9]")
        val special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]")
        val hasLetter = letter.matcher(card)
        val hasDigit = digit.matcher(card)
        val hasSpecial = special.matcher(card)

        val pattern = Pattern.compile(regex)

        val matcher = pattern.matcher(card)

        if(card == null || card.toString() == "")
        {
            cardnum.setText("")
            cardnum.setError(getString(R.string.nullname))

        }
        else if(hasLetter.find() || hasSpecial.find())
        {
            cardnum.setText("")
            cardnum.setError(getString(R.string.erroremail))
        }
        else if (matcher.matches())
        {

        }
        else
        {
            cardnum.setText("")
            cardnum.setError(getString(R.string.erroremail))
        }

    }


    fun checkcvv()
    {
        val cvv = findViewById<EditText>(R.id.cardCVCEditText)
        val textcvv = cvv.text.toString()

        val letter = Pattern.compile("[a-zA-z]")
        val digit = Pattern.compile("[0-9]")
        val special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]")
        val hasLetter = letter.matcher(textcvv)
        val hasDigit = digit.matcher(textcvv)
        val hasSpecial = special.matcher(textcvv)

        if(textcvv == null || textcvv == "")
        {
            cvv.setText("")
            cvv.setError(getString(R.string.nullname))

        }
        else if(hasLetter.find() || hasSpecial.find())
        {
            cvv.setText("")
            cvv.setError(getString(R.string.numname))

        }
        else if(cvv.length() != 3 )
        {
            cvv.setText("")
            cvv.setError(getString(R.string.numname))
        }
        else if(hasDigit.find())
        {

        }


    }

    fun checkmonth()
    {
        val spinner = findViewById<Spinner>(R.id.month)
        if (spinner.selectedItem.toString()==""){

            Toast.makeText(getApplicationContext(),"month hasn't values",
                Toast.LENGTH_LONG).show();
        }
    }

    fun checkyear()
    {
        val yearspinner = findViewById<Spinner>(R.id.year)
        if (yearspinner.selectedItem.toString()==""){

            Toast.makeText(getApplicationContext(),"year hasn't values",
                Toast.LENGTH_LONG).show();
        }
    }

    fun submitcheck()
    {
        val button = findViewById<Button>(R.id.submit)
        val cvv = findViewById<EditText>(R.id.cardCVCEditText)
        //val phone = findViewById<EditText>(R.id.phonenumber)
        val cardnum =findViewById<EditText>(R.id.cardnum)
        //val email = findViewById<EditText>(R.id.email)
        val name = findViewById<EditText>(R.id.name)
        val yearspinner = findViewById<Spinner>(R.id.year)
        val spinner = findViewById<Spinner>(R.id.month)


        if(cvv.text.toString() != null)
        {
            /*if(phone.text.toString() != null)
            {*/
                if(cardnum.text.toString() != null)
                {
                    /*if(email.text.toString() != null)
                    {*/
                        if(name.text.toString() != null)
                        {
                            if (yearspinner.selectedItem.toString()!=null)
                            {
                                if (spinner.selectedItem.toString()!=null)
                                {
                                    button.isEnabled = true

                                   /* var sharedPreferences = getSharedPreferences("com.example.paymentmethod", Context.MODE_PRIVATE)
                                    var savephone = sharedPreferences.getString("SESSION_PHONE1", getString(R.string.phonenumber))?:return

                                    with(sharedPreferences.edit()){
                                        putString("SESSION_PHONE1", phone.text.toString())

                                        apply()
                                    }*/


                                }
                            }
                        }
                   // }
                }
           // }
        }
    }
}

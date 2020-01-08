package com.example.lavana

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class selectMyCourtActivity : AppCompatActivity(), courtAdapter.OnItemClickListener {


    private lateinit var recyclerView: RecyclerView
    private lateinit var firebase1 : DatabaseReference
    private lateinit var firebase2 : DatabaseReference
    private lateinit var courtList : MutableList<courtClass>
    private lateinit var bookingList : MutableList<Booking>
    private lateinit var filteredCourtList : MutableList<courtClass>
    private lateinit var alreadytBookedList : MutableList<Booking>
    private lateinit var theTime : String
    private lateinit var theDate : String
    private lateinit var theSport : String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_my_court)

        setTitle("Select Court")

        var sharedPreferences = this.getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
        var saveSports= sharedPreferences.getString("TOBOOK_SPORT", "No Sport")
        var saveDate= sharedPreferences.getString("TOBOOK_DAY", "No Day")
        var saveTime= sharedPreferences.getString("TOBOOK_TIME", "No Time")

        theTime = saveTime.toString()
        theDate = saveDate.toString()
        theSport = saveSports.toString()

        courtList = mutableListOf()
        bookingList = mutableListOf()
        filteredCourtList = mutableListOf()
        alreadytBookedList = mutableListOf()
        recyclerView = findViewById(R.id.recyclerView_courtaa)

        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)




        //to solve the asynchronouse problem in firebase
        firebase1 = FirebaseDatabase.getInstance().getReference("court")

        readData(object : selectMyCourtActivity.FirebaseCallBack {
            override fun onCallBack(court : MutableList<courtClass>) {
                courtList = court
                val uu : MutableList<courtClass>
                uu = mutableListOf()
                Log.i("hiwhy", courtList[0].court)

                for( u in courtList)
                {

                    if(u.sport.equals(saveSports.toString()))
                    {


                        val courtMa = courtClass(u.court, u.sport, u.inorout, u.perhour)
                        Log.i("hiwhy", u.court)
                        filteredCourtList.add(courtMa)

                    }



                   // Log.i("hi", u.name)
                }

                ganneh()



            }

        });








    }


    fun ganneh()
    {
        Log.i("maiahneh", "oioioi")
        //222222222
        //to solve the asynchronouse problem in firebase
        firebase2 = FirebaseDatabase.getInstance().getReference("myBooking")

        readData1(object : selectMyCourtActivity.FirebaseCallBack1 {
            override fun onCallBack1(court : MutableList<Booking>) {
                bookingList = court


                Log.i("hiwhya", "takkanBefore")
                for( u in bookingList)
                {
                    Log.i("hiwhya", "takkanLoop")
                    Log.i("hiwhya", u.sportCtg.toString() + "compare")
                    Log.i("hiwhya", theSport.toString() + "compare1")
                    if(u.sportCtg.equals(theSport.toString()))
                    {

                        val bookedCourtMa = Booking(u.bookID, u.bookUsername, u.bookedDate, u.bookedTime, u.sportCtg, u.courtBooked, u.typeInOut, u.total)

                        Log.i("hiwhya1", u.bookID)
                        Log.i("hiwhya", u.courtBooked)
                        alreadytBookedList.add(bookedCourtMa)

                    }



                    // Log.i("hi", u.name)
                }
                Log.i("hiwhya", "takkan")
                display()


            }

        });
    }



    fun display()
    {
        Log.i("nah", "display la")
        var latestCourtList = mutableListOf<courtClass>()
        var storeAlphabet = mutableListOf<Alphabet>()
        var gotItem = 0



        for (u in alreadytBookedList)
        {
            if(u.bookedTime.equals(theTime.toString()) && u.bookedDate.equals(theDate.toString()))
            {
                for(i in filteredCourtList)
                {
                    Log.i("nah", u.courtBooked.toString())
                    Log.i("nah1", i.court.toString())

                    if(u.courtBooked.equals(i.court))
                    {

                        val alphabet = Alphabet(u.courtBooked.toString())
                        storeAlphabet.add(alphabet)
                        gotItem = 1
                        Log.i("nah", "herela")
                    }
                    else
                    {
                       /* var availableCourt = courtClass(i.court, i.sport, i.inorout, i.perhour)
                        latestCourtList.add(availableCourt)
                        gotItem = 1*/
                    }
                }
            }
            else
            {

            }
        }

        var alreadyRemove = mutableListOf<courtClass>()
        if(gotItem == 1)
        {


            var position = 0
            var size = storeAlphabet.size

            alreadyRemove = filteredCourtList

            for(i in storeAlphabet)
            {
                for(u in filteredCourtList)
                {       Log.i("nah11", u.court.toString())
                    Log.i("nah12", u.court.toString())
                    if(u.court.equals(i.alphabet))
                    {
                        val court = courtClass(u.court, u.sport, u.inorout, u.perhour)
                        Log.i("nah13", u.perhour.toString())


                        var ji = alreadyRemove.remove(court)
                        Log.i("nah", "okok")
                        Log.i("naha", ji.toString())
                    }


                }
            }
        }




        if(gotItem == 1)
        {

            filteredCourtList = alreadyRemove
            //latestCourtList.clear()

        }

            val adapter = courtAdapter(filteredCourtList, this)

            recyclerView.adapter = adapter






    }


    override fun onItemClick(position: Int) {
            //Toast.makeText(this, filteredCourtList[position].court.toString(), Toast.LENGTH_SHORT).show()

        var sharedPreferences = this.getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)

        var saveCourt = sharedPreferences.getString("TOBOOK_COURT", "No Court")?:return
        var saveType = sharedPreferences.getString("TOBOOK_TYPE", "No Type")?:return
        var savePrice = sharedPreferences.getString("TOBOOK_PRICE", "No Price")?:return

        with(sharedPreferences.edit())
        {
            putString("TOBOOK_COURT", filteredCourtList[position].court.toString())
            putString("TOBOOK_TYPE", filteredCourtList[position].inorout.toString())
            putString("TOBOOK_PRICE", filteredCourtList[position].perhour.toString())
            apply()
        }

        //filteredCourtList.clear()
        val intent = Intent(this, bookingSummaryActivity::class.java)
        startActivity(intent)

    }


    interface FirebaseCallBack{
        fun onCallBack(court : MutableList<courtClass>)
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
                        val court = u.getValue(courtClass::class.java)

                        courtList.add(court!!)
                    }
                }

                firebaseCallBack.onCallBack(courtList)


            }


        });


    }






    //check booking court
    interface FirebaseCallBack1{
        fun onCallBack1(court : MutableList<Booking>)
    }

    fun readData1(firebaseCallBack1: FirebaseCallBack1)
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
                        val court = u.getValue(Booking::class.java)

                        bookingList.add(court!!)
                    }
                }

                firebaseCallBack1.onCallBack1(bookingList)


            }


        });


    }



}

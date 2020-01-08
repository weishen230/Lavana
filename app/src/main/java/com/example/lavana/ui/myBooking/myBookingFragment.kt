package com.example.lavana.ui.myBooking

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lavana.*
import com.example.lavana.R
import com.example.lavana.ui.myTrainingClass.myTrainingClassFragment
import com.google.firebase.database.*
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*


class myBookingFragment : Fragment(), myBookingAdapter.OnItemClickListener{


    private lateinit var myBookingViewModel: myBookingViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var firebase1 : DatabaseReference
    private lateinit var allBooking : MutableList<Booking>
    private lateinit var onlyMyBooking : MutableList<Booking>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.removeAllViews();


        var sharedPreferences = this.activity?.getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
        val username = sharedPreferences?.getString("SESSION_ID", getString(R.string.sharedPreUsername))

        val root = inflater.inflate(R.layout.fragment_my_booking, container, false)


        allBooking = mutableListOf()
        onlyMyBooking = mutableListOf()
        recyclerView = root.findViewById(R.id.recycler_viewMyBooking)

        recyclerView.layoutManager = LinearLayoutManager(activity!!, RecyclerView.VERTICAL, false)

        (activity as AppCompatActivity).supportActionBar?.title = "My Booking"



        //to solve the asynchronouse problem in firebase
        firebase1 = FirebaseDatabase.getInstance().getReference("myBooking")

        readData(object : myBookingFragment.FirebaseCallBack {
            override fun onCallBack(user1 : MutableList<Booking>) {
                allBooking = user1
                var calendar : Calendar = Calendar.getInstance()
                var simpleDateFormat : SimpleDateFormat = SimpleDateFormat("dd MMM yyyy")
                var date = simpleDateFormat.format(calendar.time)
                /* val uu : MutableList<User>
                 uu = mutableListOf()*/


                val theDateMonth = date.toString()
                val month = theDateMonth.substring(2,5).toString()
                var monthInt =  convertToMonthInt(month)


                for( u in allBooking)
                {



                    if(u.bookUsername.equals(username))
                    {
                        var bookedMonthString = (u.bookedDate.substring(2,6).toString())
                        Log.i("month", bookedMonthString)
                        var bookedMonthInt = convertToMonthInt(bookedMonthString)

                        if(monthInt > bookedMonthInt)
                        {
                            Log.i("takkanwey", u.bookID.toString())
                            val myBookingma = Booking(u.bookID, u.bookUsername, u.bookedDate, u.bookedTime, u.sportCtg, u.courtBooked, u.typeInOut, u.total)
                            onlyMyBooking.add(myBookingma)
                            //complete = 1
                        }
                        else if(monthInt == bookedMonthInt)
                        {
                            var currentDay = theDateMonth.substring(0,2).toString().toInt()
                            var bookedDay = (u.bookedDate).substring(0,2).toString().toInt()
                                Log.i("day", currentDay.toString())
                                Log.i("day1", bookedDay.toString())


                            if(currentDay <= bookedDay)
                            {
                                Log.i("takkanwey", u.bookID.toString())
                                val myBookingma = Booking(u.bookID, u.bookUsername, u.bookedDate, u.bookedTime, u.sportCtg, u.courtBooked, u.typeInOut, u.total)
                                onlyMyBooking.add(myBookingma)
                                //complete = 1
                            }
                            else
                            {

                            }
                        }




                    }



                    Log.i("hi", u.courtBooked)
                }
                execute()


            }

        });


        return root
    }

    fun checkDay(day : Int)
    {

    }

    fun convertToMonthInt(month : String) : Int
    {
        var monthInt = 0

        when(month)
        {
            "Jan" -> monthInt = 1
            "Feb" -> monthInt = 2
            "Mar" -> monthInt = 3
            "Apr" -> monthInt = 4
            "May" -> monthInt = 5
            "Jun" -> monthInt = 6
            "Jul" -> monthInt = 7
            "Aug" -> monthInt = 8
            "Sep" -> monthInt = 9
            "Oct" -> monthInt = 10
            "Nov" -> monthInt = 11
            "Dec" -> monthInt = 12
        }

        return monthInt
    }


    override fun onItemClick(position: Int) {
        //Toast.makeText(activity!!, onlyMyBooking[position].bookedTime.toString(), Toast.LENGTH_SHORT).show()

        var sharedPreferences = this.activity?.getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
        var saveThisBooking = sharedPreferences?.getString("THISBOOK", "No Booking")?:return

        val gjson : Gson = Gson()
        val json : String = gjson.toJson(onlyMyBooking[position])

        with(sharedPreferences.edit())
        {
            putString("THISBOOK", json)
            apply()
        }

        val intent = Intent(activity!!, myBookDetailActivity::class.java)
        startActivity(intent)



    }

    fun execute()
    {
        val adapter = myBookingAdapter(onlyMyBooking,this)

        recyclerView.adapter = adapter
    }


    interface FirebaseCallBack{
        fun onCallBack(allBook : MutableList<Booking>)
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
                        val alltheBooking = u.getValue(Booking::class.java)

                        allBooking.add(alltheBooking!!)
                    }
                }

                firebaseCallBack.onCallBack(allBooking)


            }


        });


    }




}
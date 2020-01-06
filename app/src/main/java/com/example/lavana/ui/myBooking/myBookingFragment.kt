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
                /* val uu : MutableList<User>
                 uu = mutableListOf()*/


                for( u in allBooking)
                {

                    if(u.bookUsername.equals(username))
                    {
                        Log.i("takkanwey", u.bookID.toString())
                        val myBookingma = Booking(u.bookID, u.bookUsername, u.bookedDate, u.bookedTime, u.sportCtg, u.courtBooked, u.typeInOut, u.total)
                        onlyMyBooking.add(myBookingma)
                        //complete = 1


                    }



                    Log.i("hi", u.courtBooked)
                }
                execute()


            }

        });


        return root
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
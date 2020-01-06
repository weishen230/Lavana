package com.example.lavana

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class selectMyCourtActivity : AppCompatActivity(), courtAdapter.OnItemClickListener {


    private lateinit var recyclerView: RecyclerView
    private lateinit var firebase1 : DatabaseReference
    private lateinit var courtList : MutableList<courtClass>
    private lateinit var filteredCourtList : MutableList<courtClass>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_my_court)

        setTitle("Select Court")

        var sharedPreferences = this.getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
        var saveSports= sharedPreferences.getString("TOBOOK_SPORT", "No Sport")
        var saveDate= sharedPreferences.getString("TOBOOK_DAY", "No Day")
        var saveTime= sharedPreferences.getString("TOBOOK_TIME", "No Time")

        courtList = mutableListOf()
        filteredCourtList = mutableListOf()
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
                        val courtMa = courtClass(u.court!!, u.sport, u.inorout, u.perhour)
                        Log.i("hiwhy", u.court)
                        filteredCourtList.add(courtMa)

                    }

                    display()

                   // Log.i("hi", u.name)
                }


            }

        });
    }

    fun display()
    {
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


}

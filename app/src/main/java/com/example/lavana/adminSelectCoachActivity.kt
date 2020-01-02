package com.example.lavana

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class adminSelectCoachActivity : AppCompatActivity(), CustomAdapter.OnItemClickListener {


    lateinit var addCoachBtn : Button
    private lateinit var coachDayTimeList : MutableList<CoachDayTime>
    private lateinit var recyclerView: RecyclerView
    private lateinit var firebase1 : DatabaseReference
    private lateinit var coachList : MutableList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_select_coach)

        coachDayTimeList = mutableListOf()
        coachList = mutableListOf()
        recyclerView = findViewById(R.id.recyclerViewaa)
        var sharedPreferences = this.getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
        var saveSports= sharedPreferences.getString("SELECTED_SPORT", "No Sport")?: return

        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val user = coachList
        var index = -1

        for(u in user)
        {
            if(u.accType.equals("coach") && saveSports.equals(u.coachSport))
            {
                user.add(coachList[++index])
            }


        }


        val adapter = CustomAdapter(user, this)

        recyclerView.adapter = adapter

      



        setTitle("Change Coach")

        addCoachBtn = findViewById(R.id.addCoachButton)

        addCoachBtn.setOnClickListener{
            val intent = Intent(this, adminAddCoachActivity::class.java)
            startActivity(intent)
        }




        //to solve the asynchronouse problem in firebase
        firebase1 = FirebaseDatabase.getInstance().getReference("users")

        readData(object : FirebaseCallBack {
            override fun onCallBack(user : MutableList<User>) {
                coachList = user

                for( u in coachList)
                {
                    Log.i("hi", u.name)
                }
            }

        });

    }

    override fun onItemClick(position: Int) {

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
                        coachList.add(user!!)
                    }
                }

                firebaseCallBack.onCallBack(coachList)


            }


        });


    }
}

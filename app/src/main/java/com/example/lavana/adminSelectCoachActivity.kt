package com.example.lavana

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class adminSelectCoachActivity : AppCompatActivity(), CustomAdapter.OnItemClickListener {


    lateinit var addCoachBtn : Button
    private lateinit var coachDayTimeList : MutableList<CoachDayTime>
    private lateinit var recyclerView: RecyclerView
    private lateinit var firebase1 : DatabaseReference
    private lateinit var firebase2 : DatabaseReference
    private lateinit var coachList : MutableList<User>
    //private lateinit var tempCoachGlobal : TemporarilyCoachName
    private lateinit var user : MutableList<User>
    lateinit var saveSports : String
    lateinit var saveTimeSelected : String
    lateinit var saveDaySelected : String
    var complete = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_select_coach)

        user = mutableListOf()
        coachDayTimeList = mutableListOf()
        coachList = mutableListOf()
        recyclerView = findViewById(R.id.recyclerViewaa)
        var sharedPreferences = this.getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
         saveSports= (sharedPreferences.getString("SELECTED_SPORT", "No Sport")).toString()
         saveTimeSelected = (sharedPreferences.getString("SELECTED_TIME", "No Time")).toString()
         saveDaySelected = (sharedPreferences.getString("SELECTED_DAY", "No Day")).toString()

        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)


        var index = -1


        Log.i("hiwhy", "after")

        val adapter = CustomAdapter(user, this)
        Log.i("hiwhy", "after1")

        recyclerView.adapter = adapter








      /*  if(tempCoachGlobal != null)
        {
            val mAlertDialog = AlertDialog.Builder(this)
            mAlertDialog.setTitle("Title")
            mAlertDialog.setMessage("Confirm to change the existing coach to the selected one?")
            mAlertDialog.setPositiveButton("YES"){dialog, id ->

                Toast.makeText(this, "YES", Toast.LENGTH_SHORT).show()

            }
            mAlertDialog.setNegativeButton("NO"){dialog, id ->
                dialog.dismiss()
            }
            mAlertDialog.show()


        }*/




        setTitle("Change Coach")

        addCoachBtn = findViewById(R.id.addCoachButton)

        addCoachBtn.setOnClickListener{
            val intent = Intent(this, adminAddCoachActivity::class.java)
            startActivity(intent)
        }




        //to solve the asynchronouse problem in firebase
        firebase1 = FirebaseDatabase.getInstance().getReference("users")

        readData(object : FirebaseCallBack {
            override fun onCallBack(user1 : MutableList<User>) {
                coachList = user1
                val uu : MutableList<User>
                uu = mutableListOf()


                for( u in coachList)
                {

                        if(u.accType.equals("coach") && saveSports.equals(u.coachSport))
                        {
                            val userma = User(u.userKey, u.username, u.password, u.email, u.accType, u.profilePic, u.name, u.gender, u.phone, u.fee, u.bio, u.coachSport, u.teachDay, u.teachDay)
                            Log.i("hiwhy", u.username)
                            user.add(userma)
                            complete = 1
                        }



                    Log.i("hi", u.name)
                }


            }

        });





    }



    override fun onItemClick(position: Int) {

        //display pop up message when recycle view item is clicked
        val mAlertDialog = AlertDialog.Builder(this)
        mAlertDialog.setTitle("Title")
        mAlertDialog.setMessage("Confirm to change \"" + user[position].name + "\" as the new coach?")
        mAlertDialog.setPositiveButton("YES"){dialog, id ->


            val coachClassKey = (saveDaySelected + " " + saveTimeSelected + " " + saveSports).toString()

            val coachDayTime = CoachDayTime(coachClassKey, saveDaySelected, saveTimeSelected, user[position].username, user[position].name)

            val firebase = FirebaseDatabase.getInstance().getReference("coachDayTime").child(coachClassKey)
            firebase.setValue(coachDayTime)

            Toast.makeText(this, "The coach has succesfully changes", Toast.LENGTH_SHORT).show()

        }
        mAlertDialog.setNegativeButton("NO"){dialog, id ->
            dialog.dismiss()
        }
        mAlertDialog.show()

       /* val intent = Intent(this, adminConCoachSelectedActivity::class.java)
        intent.putExtra("selected_coach", user[position].name)
        startActivity(intent)*/
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

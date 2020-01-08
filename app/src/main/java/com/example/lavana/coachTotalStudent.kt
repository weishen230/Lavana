package com.example.lavana

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*

class coachTotalStudent : AppCompatActivity() {

    private lateinit var firebase1 : DatabaseReference
    private lateinit var myClassList : MutableList<MyClass>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coach_total_student)

        myClassList = mutableListOf()
        var checkPreferences = getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
        val username = checkPreferences.getString("SESSION_ID", getString(R.string.sharedPreUsername))
        var saveName = checkPreferences.getString("SESSION_NAME", "No Name")









        //to solve the asynchronouse problem in firebase
        firebase1 = FirebaseDatabase.getInstance().getReference("Training Class")

        readData(object : coachTotalStudent.FirebaseCallBack {
            override fun onCallBack(user1 : MutableList<MyClass>) {
                myClassList = user1




                execute()

            }

        });

    }

    fun execute()
    {
        val total = 0

        for(u in myClassList)
        {

        }

    }


    interface FirebaseCallBack{
        fun onCallBack(user : MutableList<MyClass>)
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
                        val user = u.getValue(MyClass::class.java)

                        myClassList.add(user!!)
                    }
                }

                firebaseCallBack.onCallBack(myClassList)


            }


        });


    }

}

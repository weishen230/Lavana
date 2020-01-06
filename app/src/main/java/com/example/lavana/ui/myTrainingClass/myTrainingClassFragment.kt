package com.example.lavana.ui.myTrainingClass

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lavana.*
import com.example.lavana.R
import com.google.firebase.database.*

class myTrainingClassFragment : Fragment() {

    private lateinit var myTrainingClassViewModel: myTrainingClassViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var firebase1 : DatabaseReference
    private lateinit var myTrainingClass : MutableList<MyClass>
    private lateinit var onlyMyClass : MutableList<MyClass>
    var complete = 0



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        container?.removeAllViews();

        var sharedPreferences = this.activity?.getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
        val username = sharedPreferences?.getString("SESSION_ID", getString(R.string.sharedPreUsername))
        Log.i("us", username.toString())

        val root = inflater.inflate(R.layout.fragment_my_training_class, container, false)

        myTrainingClass = mutableListOf()
        onlyMyClass = mutableListOf()
        recyclerView = root.findViewById(R.id.recycler_viewMyClass)

        recyclerView.layoutManager = LinearLayoutManager(activity!!, RecyclerView.VERTICAL, false)



        (activity as AppCompatActivity).supportActionBar?.title = "My Training Class"





        /*myTrainingClassViewModel =
            ViewModelProviders.of(this).get(myTrainingClassViewModel::class.java)

        val textView: TextView = root.findViewById(R.id.text_tools)
        myTrainingClassViewModel.text.observe(this, Observer {
            textView.text = it
        })*/



        //to solve the asynchronouse problem in firebase
        firebase1 = FirebaseDatabase.getInstance().getReference("Training Class")

        readData(object : myTrainingClassFragment.FirebaseCallBack {
            override fun onCallBack(user1 : MutableList<MyClass>) {
                myTrainingClass = user1
               /* val uu : MutableList<User>
                uu = mutableListOf()*/


                for( u in myTrainingClass)
                {

                    if(u.username.equals(username))
                    {
                        val myClassMa = MyClass(u.username, u.sports, u.classDay, u.classTime, u.coachName, u.sportDayTime)
                        Log.i("hiwhy", u.sportDayTime)
                        onlyMyClass.add(myClassMa)
                        complete = 1

                        execute()
                    }



                    Log.i("hi", u.username)
                }


            }

        });



        return root
    }

    fun execute()
    {
        val adapter = myTrainingClassAdapter(onlyMyClass)

        recyclerView.adapter = adapter
    }


    interface FirebaseCallBack{
        fun onCallBack(myClass : MutableList<MyClass>)
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
                        val myClass = u.getValue(MyClass::class.java)

                        myTrainingClass.add(myClass!!)
                    }
                }

                firebaseCallBack.onCallBack(myTrainingClass)


            }


        });


    }


}
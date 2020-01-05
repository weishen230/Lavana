package com.example.lavana

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class myTrainingClassAdapter (val myTrainingClass: MutableList<MyClass>) : RecyclerView.Adapter<myTrainingClassAdapter.ViewHolder>() {

    var name : String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent?.context).inflate(R.layout.recycler_my_training_class, parent, false)



        return myTrainingClassAdapter.ViewHolder(v)

    }

    override fun getItemCount(): Int {
       return myTrainingClass.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val myTrainClass : MyClass = myTrainingClass[position]



        holder?.sportTitleTxt.text = myTrainingClass[position].sports
        holder?.trainingDayTxt.text = "Day: " + myTrainingClass[position].classDay
        holder?.trainingTimeTxt.text = "Time: " + myTrainingClass[position].classTime
        holder?.coachNameTxt.text = "Trainer: " + myTrainingClass[position].coachName

    }





    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView),
        CustomAdapter.OnItemClickListener
    {

        val sportTitleTxt = itemView.findViewById<TextView>(R.id.sportTitleTxt)
        val trainingDayTxt = itemView.findViewById<TextView>(R.id.trainingDayTxt)
        val trainingTimeTxt = itemView.findViewById<TextView>(R.id.trainingTimeTxt)
        val coachNameTxt = itemView.findViewById<TextView>(R.id.trainingCoachNameTxt)



        override fun onItemClick(position: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    interface OnItemClickListener
    {
        fun onItemClick(position: Int)

    }

}
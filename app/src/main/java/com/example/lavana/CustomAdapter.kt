package com.example.lavana

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.preference.PreferenceManager

import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase

class CustomAdapter (val coachList: MutableList<User>, val mListener : OnItemClickListener?) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

        var name : String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.recycle_view_item, parent, false)



        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return coachList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val coach : User = coachList[position]

        holder.itemView.setOnClickListener{

            Log.i("listener", (coachList[position].name).toString())
            mListener?.onItemClick(position)



           /* var firebase = FirebaseDatabase.getInstance().getReference("temporarily")
            var userKey = firebase.push().key.toString()
            var tempCoachName = TemporarilyCoachName((coachList[position].name).toString())

            firebase.child(userKey).setValue(tempCoachName)*/




        }

        holder?.textViewName?.text = coach.name
    }


    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView), OnItemClickListener
    {

        val textViewName = itemView.findViewById<TextView>(R.id.textViewName)



        override fun onItemClick(position: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    interface OnItemClickListener
    {
        fun onItemClick(position: Int)

    }

}
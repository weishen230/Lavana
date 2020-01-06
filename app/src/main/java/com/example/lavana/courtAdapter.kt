package com.example.lavana

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView

class courtAdapter(val courtList: MutableList<courtClass>, val mListener : OnItemClickListener? ) : RecyclerView.Adapter<courtAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.recycler_view_court, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return courtList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val court : courtClass = courtList[position]

        holder.itemView.setOnClickListener{

            mListener?.onItemClick(position)

        }



        holder?.courtTxt?.text = court.court
        holder?.courtSportTxt.text = "Sport       : " + court.sport
        holder?.inoutTxt.text = "Type        : "  + court.inorout
        holder?.perhourTxt.text = "Per hour : " + court.perhour
    }


    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView), OnItemClickListener
    {

        val courtTxt = itemView.findViewById<TextView>(R.id.courtTxt)
        val courtSportTxt = itemView.findViewById<TextView>(R.id.courtSportTxt)
        val inoutTxt = itemView.findViewById<TextView>(R.id.indoorOutdoorTxt)
        val perhourTxt = itemView.findViewById<TextView>(R.id.perHourTxt)



        override fun onItemClick(position: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    interface OnItemClickListener
    {
        fun onItemClick(position: Int)

    }

}
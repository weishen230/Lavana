package com.example.lavana

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class myBookingAdapter(val bookList: MutableList<Booking>, val mListener : OnItemClickListener? ) : RecyclerView.Adapter<myBookingAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.recycler_view_mybooking, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val myBooking : Booking = bookList[position]

        holder.itemView.setOnClickListener{

            mListener?.onItemClick(position)

        }

        holder?.bookSportTxt?.text = myBooking.sportCtg
        holder?.bookDateTxt?.text = "Date: " + myBooking.bookedDate
        holder?.bookTimeTxt?.text = "Time: " + myBooking.bookedTime

    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView), OnItemClickListener
    {

        val bookSportTxt = itemView.findViewById<TextView>(R.id.bookedSportTxt)
        val bookDateTxt = itemView.findViewById<TextView>(R.id.bookedDateTxt)
        val bookTimeTxt = itemView.findViewById<TextView>(R.id.bookedTimeTxt)




        override fun onItemClick(position: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    interface OnItemClickListener
    {
        fun onItemClick(position: Int)

    }
}
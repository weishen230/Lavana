package com.example.lavana.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.lavana.DataObj.DataLv
import com.example.lavana.R
import com.example.lavana.ui.event.eventFragment
import kotlinx.android.synthetic.main.fragment_event.view.*

class AdapterLv (var context: Context, var data: ArrayList<DataLv> ): BaseAdapter() {

    lateinit var imageView: ImageView
    lateinit var setImage: ImageView
    lateinit var channel : TextView
    lateinit var service: TextView
    lateinit var inviText : TextView


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view = LayoutInflater.from(context).inflate(R.layout.fragment_event, parent, false)

        imageView = view.findViewById(R.id.img)
        channel = view.findViewById(R.id.channel)
        service = view.findViewById(R.id.service)
        inviText = view.findViewById(R.id.inviText)

        imageView.setImageResource(data.get(position).img)
        channel.text = data.get(position).channel
        service.text = data.get(position).service
        inviText.text = data.get(position).textTrack




        view.btnLearnMore.setOnClickListener {view : View ->


            when(position)
            {
                0 ->
                {

                    view.findNavController().navigate(R.id.action_eventFragment_to_eventDetailFragment)
                }
                1 ->
                {
                    view.findNavController().navigate(R.id.action_eventFragment_to_eventDetailSecFragment)
                }
                2 ->
                {
                    view.findNavController().navigate(R.id.action_eventFragment_to_eventDetailThrFragment)
                }

            }




        }


        return view


    }

    override fun getItem(position: Int): Any {
        return data.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.size
    }
}
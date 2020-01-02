package com.example.lavana.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lavana.R
import androidx.databinding.DataBindingUtil
import com.example.lavana.DataObj.DataLv
import com.example.lavana.adapters.AdapterLv
import com.example.lavana.databinding.LoopEventBinding



class eventFragment : Fragment() {


    private lateinit var listView : ListView
    private lateinit var adapter: AdapterLv
    private lateinit var data : ArrayList<DataLv>


    private lateinit var eventViewModel: EventViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.title = "Home"

        val binding = DataBindingUtil.inflate<LoopEventBinding>(inflater, R.layout.loop_event, container, false)


        data = ArrayList()

        data.add(DataLv(R.drawable.klrun, "KL 2019" , "Kuala Lumpur Run", "klrun"))
        data.add(DataLv(R.drawable.christmasrun, "Penang 2019" , "Christmas Run", "christmasRun"))
        data.add(DataLv(R.drawable.marathon, "Penang 2019" , "Marathon", "marathon"))


        adapter = AdapterLv(requireContext(), data)

        listView = binding.lv



        listView.adapter = adapter



       /* eventViewModel =
            ViewModelProviders.of(this).get(EventViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_event, container, false)
        //val textView: TextView = root.findViewById(R.id.text_home)
       /* eventViewModel.text.observe(this, Observer {
            textView.text = it
        })*/
        return root*/
        return binding.root
    }
}
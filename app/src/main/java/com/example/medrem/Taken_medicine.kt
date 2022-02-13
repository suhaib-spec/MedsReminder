package com.example.medrem

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_taken_medicine.*

class Taken_medicine : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_taken_medicine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var Names : ArrayList<String> = arrayListOf()
        var Types : ArrayList<String> = arrayListOf()
        var Intervals : ArrayList<String> = arrayListOf()

        val sharedPref = requireContext().getSharedPreferences("AlarmSet", Context.MODE_PRIVATE)
        var size = sharedPref.getInt("Takensize",0)

        for (i in 1..size){
            Names.add(sharedPref.getString(i.toString()+"TakenMedName","") as String)
            Types.add(sharedPref.getString(i.toString()+"TakenMedType","") as String)
            var hour = sharedPref.getInt(i.toString()+"TakenHour",0)
            var am_pm = "am"

            if (hour > 12){
                hour -= 12
                am_pm = "pm"
            }

            var interval =  hour.toString() + ":" + sharedPref.getInt(i.toString()+"TakenMinute",0).toString() + am_pm
            Intervals.add((interval))
        }

        val adapter = ItemAdapter3(Names,Types,Intervals)
        homeList2.layoutManager = LinearLayoutManager(context)
        homeList2.adapter = adapter
    }
}
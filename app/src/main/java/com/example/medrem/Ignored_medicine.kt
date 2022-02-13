package com.example.medrem

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_ignored_medicine.*


class Ignored_medicine : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ignored_medicine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var Names : ArrayList<String> = arrayListOf()
        var Types : ArrayList<String> = arrayListOf()
        var Intervals : ArrayList<String> = arrayListOf()

        val sharedPref = requireContext().getSharedPreferences("AlarmSet", Context.MODE_PRIVATE)
        var size = sharedPref.getInt("size",0)

        for (i in 1..size){
            Names.add(sharedPref.getString(i.toString()+"MedName","") as String)
            Types.add(sharedPref.getString(i.toString()+"MedType","") as String)
            var hour = sharedPref.getInt(i.toString()+"Hour",0)
            var am_pm = "am"

            if (hour > 12){
                hour -= 12
                am_pm = "pm"
            }

            var interval =  hour.toString() + ":" + sharedPref.getInt(i.toString()+"Minute",0).toString() + am_pm
            Intervals.add((interval))
        }


        val adapter = ItemAdapter2(Names,Types,Intervals)
        homeList1.layoutManager = LinearLayoutManager(context)
        homeList1.adapter = adapter
    }

    fun Med_taken(position : Int,context: Context){
        val sharedPref = context.getSharedPreferences("AlarmSet", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        val oldrecieved = sharedPref.getInt("Takensize",0)
        editor.apply{
            putInt("Takensize",oldrecieved+1)
            apply()
        }
        val newrecieved = sharedPref.getInt("Takensize",0)
        editor.apply {
            putInt(newrecieved.toString() + "TakenHour", sharedPref.getInt(position.toString()+"Hour",0))
            putInt(newrecieved.toString() + "TakenMinute", sharedPref.getInt(position.toString()+"Minute",0))
            putString(newrecieved.toString()+"TakenMedName", sharedPref.getString(position.toString()+"MedName","") as String)
            putString(newrecieved.toString()+"TakenMedType", sharedPref.getString(position.toString()+"MedType","") as String)
            apply()
        }

    }

}
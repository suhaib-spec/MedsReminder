package com.example.medrem

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import kotlin.collections.ArrayList


class Home : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
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

        val adapter = ItemAdapter(Names,Types,Intervals)
        homeList.layoutManager = LinearLayoutManager(context)
        homeList.adapter = adapter


    }




}
package com.example.medrem

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(val DataSetNames : ArrayList<String>,val DataSetTypes : ArrayList<String>, val DataSetTimes : ArrayList<String>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val CardTitle : TextView = itemView.findViewById(R.id.medicine_name)
        val MedicineType : TextView = itemView.findViewById(R.id.medicine_type)
        val TimeInterval : TextView = itemView.findViewById(R.id.time_interval)

        val RemoveBtn : Button = itemView.findViewById(R.id.remove_reminder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_custom_view,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.CardTitle.text = DataSetNames[position]
        holder.MedicineType.text = DataSetTypes[position]
        holder.TimeInterval.text = DataSetTimes[position]

        holder.RemoveBtn.setOnClickListener{
            DataSetNames.removeAt(position)
            DataSetTypes.removeAt(position)
            DataSetTimes.removeAt(position)
            notifyItemRemoved(position)
            val addMedicine = Add_medicine()
            Log.d("REMOVE POSTION",(position+1).toString())

            addMedicine.removeAlarm(it.context,position+1)
            Toast.makeText(it.context,"Alarm removed",Toast.LENGTH_SHORT).show()

        }
    }

    override fun getItemCount(): Int {
        return DataSetNames.size
    }
}
package com.example.medrem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter2(val DataSetNames : ArrayList<String>,val DataSetTypes : ArrayList<String>, val DataSetTimes : ArrayList<String>) : RecyclerView.Adapter<ItemAdapter2.ViewHolder>() {

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

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

        holder.RemoveBtn.text = "Done"

        holder.RemoveBtn.setOnClickListener {
            DataSetNames.removeAt(position)
            DataSetTypes.removeAt(position)
            DataSetTimes.removeAt(position)
            notifyItemRemoved(position)
            val Ignored_medicine = Ignored_medicine()
            Ignored_medicine.Med_taken(position+1,it.context)
            Toast.makeText(it.context,"Maked as Done", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return DataSetNames.size
    }


}
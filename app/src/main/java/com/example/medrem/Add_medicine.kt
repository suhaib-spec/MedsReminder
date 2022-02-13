package com.example.medrem

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.example.medrem.databinding.FragmentAddMedicineBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import androidx.fragment.app.FragmentManager.*
import com.example.medrem.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_add_medicine.*
import kotlinx.android.synthetic.main.fragment_add_medicine.view.*
import java.util.*


class Add_medicine : Fragment() {

    private lateinit var calendar: Calendar
    private lateinit var picker: MaterialTimePicker
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    private var _binding : FragmentAddMedicineBinding? = null

    private var RC_ARRAY:ArrayList<Int> = arrayListOf()

    private var tick :Int=0

    private var timeset : Array<Int> = arrayOf(0,1)




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel()


    }

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val name: CharSequence = "MEDREM"
            val description = "Channel for MEDREM"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("MEDREM",name,importance)
            channel.description = description
            val notificationManager = requireActivity().getSystemService(
                NotificationManager::class.java
            )
            Toast.makeText(activity,"channel created",Toast.LENGTH_SHORT).show()

            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val med_name = view.findViewById<TextInputEditText>(R.id.Med_name)
        val med_type = view.findViewById<AutoCompleteTextView>(R.id.Med_type)

        val sharedPref = requireContext().getSharedPreferences("AlarmSet", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        set_time.setOnClickListener {
            showTimePicker()
        }

        add_reminder.setOnClickListener {
            setAlarm(requireContext())

            //Toast.makeText(context,med_name.text.toString(),Toast.LENGTH_SHORT).show()
            //Toast.makeText(context,med_type.text.toString(),Toast.LENGTH_SHORT).show()

            editor.apply {

                putInt("size",tick)
                putInt(tick.toString() + "Hour", timeset[0])
                putInt(tick.toString() + "Minute", timeset[1])
                putString(tick.toString()+"MedName", med_name.text.toString())
                putString(tick.toString()+"MedType", med_type.text.toString())

                apply()
                //putString(tick.toString() + "Taken", "NO")

            }

            Log.d("MEDNAME",sharedPref.getString(tick.toString()+"MedName","") as String)
            Log.d("MEDTYPE",sharedPref.getString(tick.toString()+"MedType","") as String)

        }
    }


    fun setAlarm(context: Context) {
        val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        //when alarm store set the request assign to tick variable
        tick += 1

        //Add all the alarm Request into RC_ARRAY for just cancel the alarm
        //RC_ARRAY.add(tick)

        //Notification Broadcast intent
        val intentAlarm = Intent(context, AlarmReceiver::class.java).let {
            PendingIntent.getBroadcast(context, tick, it, PendingIntent.FLAG_IMMUTABLE)
        }

        //alarm fire next day if this condition is not statisfied
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1)
        }
        //set alarm
        manager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, intentAlarm)

        Toast.makeText(context,"Alarm set",Toast.LENGTH_SHORT).show()
        Log.d("ADD POSITION", tick.toString())
    }




    private fun showTimePicker() {
        picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Set your Time")
            .build()

        picker.show(requireActivity().supportFragmentManager,"MEDREM Time")

        picker.addOnPositiveButtonClickListener{

            if(picker.hour > 12){

                time.text = String.format("%02d",picker.hour - 12) + ":" + String.format("%02d",picker.minute) + "PM"

            }else{

                time.text = String.format("%02d",picker.hour) + ":" + String.format("%02d",picker.minute) + "AM"
            }

            calendar = Calendar.getInstance()
            timeset = arrayOf(picker.hour,picker.minute)
            calendar[Calendar.HOUR_OF_DAY] = timeset[0]
            calendar[Calendar.MINUTE] = timeset[1]
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0

            Toast.makeText(context,"Time set",Toast.LENGTH_SHORT).show()

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val medicineType = resources.getStringArray(R.array.medicineTypes)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item,medicineType)
        _binding = FragmentAddMedicineBinding.inflate(inflater, container, false)
        _binding!!.MedType.setAdapter(arrayAdapter)

        return _binding!!.root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    open fun removeAlarm(context: Context,tick: Int){
        val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intentAlarm = Intent(context, AlarmReceiver::class.java).let {
            PendingIntent.getBroadcast(context, tick, it, PendingIntent.FLAG_IMMUTABLE)
        }
//cancel alarm
        manager.cancel(intentAlarm)
    }




}
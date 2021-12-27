package com.example.medrem

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.example.medrem.databinding.FragmentAddMedicineBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import androidx.fragment.app.FragmentManager.*
import com.example.medrem.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.fragment_add_medicine.*
import kotlinx.android.synthetic.main.fragment_add_medicine.view.*
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Add_medicine.newInstance] factory method to
 * create an instance of this fragment.
 */
class Add_medicine : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var calendar: Calendar
    private lateinit var picker: MaterialTimePicker
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    private var _binding : FragmentAddMedicineBinding? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel()

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


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
        set_time.setOnClickListener {
            showTimePicker()

        }

        add_reminder.setOnClickListener {
            setAlarm()
        }
    }

    private fun setAlarm() {

        alarmManager = requireActivity().getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireActivity(),AlarmReceiver::class.java)
        Toast.makeText(context,"Intent ready",Toast.LENGTH_SHORT).show()
        pendingIntent = PendingIntent.getBroadcast(context,0,intent,0)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,pendingIntent
        )
        Toast.makeText(context,"Alarm Set Successfully",Toast.LENGTH_SHORT).show()
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
            calendar[Calendar.HOUR_OF_DAY] = picker.hour
            calendar[Calendar.MINUTE] = picker.minute
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
        _binding!!.autoCompleteTextView.setAdapter(arrayAdapter)

        return _binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Add_medicine.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Add_medicine().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
package com.example.air

import android.os.Bundle
import android.widget.Spinner
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


class MainActivity : AppCompatActivity() {

    private var departureMillis: Long? = null

    private val format = SimpleDateFormat("dd.MM.yyyy", Locale("ru")).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Spinner>(R.id.spinnerTo).setSelection(1)

        val etDeparture = findViewById<TextInputEditText>(R.id.etDeparture)
        val etReturn = findViewById<TextInputEditText>(R.id.etReturn)
        val btnSearch = findViewById<Button>(R.id.btnSearch)

        etDeparture.setOnClickListener {
            val picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(R.string.pick_departure)
                .build()
            picker.addOnPositiveButtonClickListener { millis ->
                departureMillis = millis
                etDeparture.setText(format.format(Date(millis)))
                etReturn.text = null
            }
            picker.show(supportFragmentManager, "departure")
        }

        etReturn.setOnClickListener {
            val builder = MaterialDatePicker.Builder.datePicker()
                .setTitleText(R.string.pick_return)
            departureMillis?.let {
                builder.setCalendarConstraints(
                    CalendarConstraints.Builder()
                        .setStart(it)
                        .setValidator(DateValidatorPointForward.from(it))
                        .build()
                )
            }
            val picker = builder.build()
            picker.addOnPositiveButtonClickListener { millis ->
                etReturn.setText(format.format(Date(millis)))
            }
            picker.show(supportFragmentManager, "return")
        }

        btnSearch.setOnClickListener {
        }
    }
}
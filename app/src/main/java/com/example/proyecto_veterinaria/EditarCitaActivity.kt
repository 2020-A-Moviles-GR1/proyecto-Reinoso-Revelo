package com.example.proyecto_veterinaria

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_editar_cita.*
import kotlinx.android.synthetic.main.activity_menu_duenio_mascota.*

class EditarCitaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_cita)

        edt_fecha_editar_cita
            .setOnClickListener {
                showDatePickerDialog()
            }

        edt_hora_editar_cita
            .setOnClickListener {
                showTimePickerDialog()
            }

    }


    fun showTimePickerDialog() {

        val newFragment = TimePickerFragment.newInstance(TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            val selectedTime =hourOfDay.toString()+":"+minute
            edt_hora_editar_cita.setText(selectedTime)//setea el text view
        } )
        newFragment.show(supportFragmentManager, "timePicker")
    }

    fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            // +1 because January is zero
            val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
            edt_fecha_editar_cita.setText(selectedDate)
        })
        newFragment.show(supportFragmentManager, "datePicker")
    }




}
package com.example.proyecto_veterinaria

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_menu_duenio_mascota.*
import kotlinx.android.synthetic.main.activity_registro_cita.*

class RegistroCitaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_cita)

        var mascota= intent.getParcelableExtra<MascotaDos>("mascotaA")

        edt_nombre_masc_registro_cita.setText(mascota.nombreMascota)
        edt_nombre_masc_registro_cita.setFocusable(false);
        edt_nombre_masc_registro_cita.setEnabled(false);
        edt_nombre_masc_registro_cita.setCursorVisible(false);
        edt_nombre_masc_registro_cita.setKeyListener(null);
        edt_nombre_masc_registro_cita.setBackgroundColor(Color.TRANSPARENT);

        btn_back_registro_cita
            .setOnClickListener {
                irPerfilDeMascota()
            }

        btn_registrar_cita
            .setOnClickListener {

                irMenuDuenioMascota()
            }

        edt_fecha_nacimi_masc_registro_cita
            .setOnClickListener {
                showDatePickerDialog()
            }

        edt_fecha_cita_masc_registro_cita
            .setOnClickListener {

                showDatePickerDialogDos()
            }

        edt_hora_cita_masc_registro_cita
            .setOnClickListener {
                showTimePickerDialog()
            }




    }

    fun irPerfilDeMascota(){
        val intentExplicito= Intent(
            this,
            PerfilDeMascotaActivity::class.java
        )
        this.startActivity(intentExplicito)
    }


    fun irMenuDuenioMascota(){
        val intentExplicito= Intent(
            this,
            MenuDuenioMascotaActivity::class.java
        )
        this.startActivity(intentExplicito)
    }

    fun showTimePickerDialog() {

    val newFragment = TimePickerFragment.newInstance(TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
        val selectedTime =hourOfDay.toString()+":"+minute
        edt_hora_cita_masc_registro_cita.setText(selectedTime)//setea el text view
    } )
    newFragment.show(supportFragmentManager, "timePicker")
    }

    fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            // +1 because January is zero
            val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
            edt_fecha_nacimi_masc_registro_cita.setText(selectedDate)
        })
        newFragment.show(supportFragmentManager, "datePicker")
    }

    fun showDatePickerDialogDos() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            // +1 because January is zero
            val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
            edt_fecha_cita_masc_registro_cita.setText(selectedDate)
        })
        newFragment.show(supportFragmentManager, "datePicker")
    }



}
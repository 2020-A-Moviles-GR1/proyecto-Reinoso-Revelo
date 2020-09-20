package com.example.proyecto_veterinaria

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_menu_duenio_mascota.*
import kotlinx.android.synthetic.main.activity_pantalla_principal_veterinario.*

class MenuDuenioMascotaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_duenio_mascota)
        cargarSipinerCliente()

        sp_duenio_mascota.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?, arg1: View,
                arg2: Int, arg3: Long
            ) {
                val itemspiner: String = sp_duenio_mascota.getSelectedItem().toString()
                Log.i("List","position $itemspiner")  //if para ir a las otras pantallas
                if(itemspiner=="Editar"){
                    irEditarPerfilDuenio()
                }else if(itemspiner=="Cerrar sesión"){
                    confirmacionSalida()
                }
            }
            override fun onNothingSelected(arg0: AdapterView<*>?) {
                // TODO Auto-generated method stub
            }
        })

        //edt_hora_cita_cliente
        //    .setOnClickListener {
        //        showTimePickerDialog()
        //   }
        edt_fecha_cita_duenio
            .setOnClickListener {
                showDatePickerDialog()
            }
        btn_mascota_nueva_menu_duenio
            .setOnClickListener {
                irRegistroMascota()
            }

        tv_menu_duenio_mascota
            .setOnClickListener {
                //irInformacionDeCita()
                irPerfilDeMascota()
            }

    }


    fun cargarSipinerCliente(){
        val spinner: Spinner = findViewById(R.id.sp_duenio_mascota)
        ArrayAdapter.createFromResource(
            this,
            R.array.itemsVeterinario,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    /*fun showTimePickerDialog() {

        val newFragment = TimePickerFragment.newInstance(TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            val selectedTime =hourOfDay.toString()+":"+minute
            edt_hora_cita_cliente.setText(selectedTime)//setea el text view
        } )
        newFragment.show(supportFragmentManager, "timePicker")
    }*/

    fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            // +1 because January is zero
            val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
            edt_fecha_cita_duenio.setText(selectedDate)
        })
        newFragment.show(supportFragmentManager, "datePicker")
    }

    fun irRegistroMascota(){
        val intentExplicito= Intent(
            this,
            RegistroMascotaActivity::class.java
        )
        this.startActivity(intentExplicito)
    }


    fun irEditarPerfilDuenio(){
        val intentExplicito= Intent(
            this,
            PerfilDuenioActivity::class.java
        )
        this.startActivity(intentExplicito)
    }

    fun confirmacionSalida() {
        var vandera=false
        AlertDialog.Builder(this)
            .setTitle("Confirmación")
            .setMessage("¿Está seguro que desea salir?")
            .setPositiveButton(android.R.string.ok,
                DialogInterface.OnClickListener { dialog, which ->
                    //botón OK pulsado**
                    val intentExplicito= Intent(
                        this,
                        MainActivity::class.java
                    )
                    this.startActivity(intentExplicito)
                    //this.startActivity(intent)
                })
            .setNegativeButton(android.R.string.cancel,
                DialogInterface.OnClickListener { dialog, which ->
                    //botón cancel pulsado**
                    vandera=false
                    //Log.i("List","position $vandera")
                })
            .show()
    }

    fun irInformacionDeCita(){
        val intentExplicito= Intent(
            this,
            InformacionDeCitaActivity::class.java
        )
        this.startActivity(intentExplicito)
    }


    fun irPerfilDeMascota(){
        val intentExplicito= Intent(
            this,
            PerfilDeMascotaActivity::class.java
        )
        this.startActivity(intentExplicito)
    }

}
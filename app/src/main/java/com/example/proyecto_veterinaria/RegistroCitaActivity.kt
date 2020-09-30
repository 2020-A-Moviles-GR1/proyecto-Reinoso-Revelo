package com.example.proyecto_veterinaria

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_menu_duenio_mascota.*
import kotlinx.android.synthetic.main.activity_registro_cita.*
import kotlinx.android.synthetic.main.activity_registro_veterinario.*

class RegistroCitaActivity : AppCompatActivity() {
    val urlPrincipal = "http://192.168.0.104:1337"
    lateinit var mascota :MascotaDos
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_cita)

        mascota= intent.getParcelableExtra<MascotaDos>("mascotaA")

        edt_nombre_masc_registro_cita.setText(mascota.nombreMascota)
        edt_peso_masc_registro_cita.setText(mascota.pesoMascota)
        edt_edad_masc_registro_cita.setText(mascota.edadMascota)
        edt_raza_masc_registro_cita.setText(mascota.razaMascota)
        edt_especie_masc_registro_cita.setText(mascota.especieMascota)
        edt_fecha_nacimi_masc_registro_cita.setText(mascota.fechaNacimientoMascota)

        btn_back_registro_cita
            .setOnClickListener {
                irPerfilDeMascota()
            }

        btn_registrar_cita
            .setOnClickListener {
                registrarNuevaCita()
                //irMenuDuenioMascota()
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

    fun registrarNuevaCita(){
        val fechaAtencion=edt_fecha_cita_masc_registro_cita.getText().toString()
        val horaAtencion=edt_hora_cita_masc_registro_cita.getText().toString()
        val url = urlPrincipal + "/cita"

        val parametroUsuario : List<Pair<String,String>> = listOf( //lista de clave valores

            "fechaAtencionCita" to fechaAtencion,
            "HoraAtencionCita" to horaAtencion,
            "fechaProximaAtencionCita" to "",
            "estadoAtencionCita" to "Por Atender",
            "mascota" to "${mascota.id}"
        )
        url
            .httpPost(parametroUsuario)
            .responseString{request, response, result ->
                when (result){
                    is Result.Failure->{
                        val error = result.getException()
                        Log.i("http-klaxon", "Nombre: ${error}")
                    }
                    is Result.Success->{
                        val usuarioString=result.get()
                        Log.i("http-klaxon", "Nombre: ${usuarioString}")
                    }
                }
            }

        limpiarCampos()
        irMenuDuenioMascota()
    }

    fun limpiarCampos(){
        edt_nombre_masc_registro_cita.setText(null)
        edt_peso_masc_registro_cita.setText(null)
        edt_edad_masc_registro_cita.setText(null)
        edt_raza_masc_registro_cita.setText(null)
        edt_especie_masc_registro_cita.setText(null)
        edt_fecha_nacimi_masc_registro_cita.setText(null)
        edt_fecha_cita_masc_registro_cita.setText(null)
        edt_hora_cita_masc_registro_cita.setText(null)
    }



}
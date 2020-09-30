package com.example.proyecto_veterinaria

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_detalles_cita.*
import kotlinx.android.synthetic.main.activity_editar_perfil_veterinario.*
import kotlinx.android.synthetic.main.activity_registro_cita.*

class DetallesCitaActivity : AppCompatActivity() {
    val urlPrincipal = "http://192.168.0.104:1337"
    lateinit var cita : CitaDos
    lateinit var usuarioA: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_cita)
        cita= intent.getParcelableExtra<CitaDos>("citaA")
        usuarioA = intent.getParcelableExtra<Usuario>("usuarioA")
        edt_fecha_cita
            .setOnClickListener {
            showDatePickerDialog()
        }

        btn_guardar_detalle_cita
            .setOnClickListener{
                confirmacionGuardado()
            }
        back_detalle_cita
            .setOnClickListener{
                irPantallaPrincipalVeterinario()
            }
    }

    fun registrarNuevaVacuna(){
        val motivoAtencion = edt_motivo_atencion_cita.getText().toString()
        val diagnosticoCita = edt_diagnostico_cita.getText().toString()
        val tipoVacuna = edt_vacuna_cita.getText().toString()
        val dosis = edt_dosis_sumin_cita.getText().toString()
        val fechaProximaCita = edt_fecha_cita.getText().toString()
        val url = urlPrincipal + "/vacuna"

        val parametroUsuario : List<Pair<String,String>> = listOf( //lista de clave valores

                "fechaVacuna" to "${cita.fechaAtencionCita}",
                "tipoVacuna" to tipoVacuna,
                "numDosisVacuna" to dosis,
                "cita" to "${cita.id}"
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
    }

    fun registrarNuevoDiagnostico(){
        val motivoAtencion = edt_motivo_atencion_cita.getText().toString()
        val diagnosticoCita = edt_diagnostico_cita.getText().toString()
        val url = urlPrincipal + "/diagnostico"

        val parametroUsuario : List<Pair<String,String>> = listOf( //lista de clave valores

                "fechaAtencionDiagnostico" to "${cita.fechaAtencionCita}",
                "motivoConsultaDiagnostico" to motivoAtencion,
                "diagnosticoAtencion" to diagnosticoCita,
                "cita" to "${cita.id}"
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
    }

    fun actualizarCita() {

        val fechaProximaCita = edt_fecha_cita.getText().toString()

        val url = urlPrincipal + "/cita/"+cita.id
        val parametroUsuario: List<Pair<String, String>> = listOf( //lista de clave valores

                "fechaAtencionCita" to "${cita.fechaAtencionCita}",
                "HoraAtencionCita" to "${cita.HoraAtencionCita}",
                "fechaProximaAtencionCita" to fechaProximaCita,
                "estadoAtencionCita" to "Atendido"
        )
        url
                .httpPut(parametroUsuario)
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val error = result.getException()
                            Log.i("http-klaxon", "Nombre: ${error}")
                        }
                        is Result.Success -> {
                            val usuarioString = result.get()
                            Log.i("http-klaxon", "Nombre usuario: ${usuarioString}")
                        }
                    }
                }
        limpiarCampos()
    }

    fun limpiarCampos(){
        edt_motivo_atencion_cita.setText(null)
        edt_diagnostico_cita.setText(null)
        edt_vacuna_cita.setText(null)
        edt_dosis_sumin_cita.setText(null)
        edt_fecha_cita.setText(null)
    }

    fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            // +1 because January is zero
            val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
            edt_fecha_cita.setText(selectedDate)
        })
        newFragment.show(supportFragmentManager, "datePicker")
    }


    fun confirmacionGuardado() {
        var vandera=false
        AlertDialog.Builder(this)
            .setTitle("Confirmación")
            .setMessage("¿Está seguro que desea guardar la información ingresada?")
            .setPositiveButton(android.R.string.ok,
                DialogInterface.OnClickListener { dialog, which ->
                    //botón OK pulsado**
                    registrarNuevaVacuna()
                    registrarNuevoDiagnostico()
                    actualizarCita()
                    val intentExplicito= Intent(
                        this,
                        PantallaPrincipalVeterinarioActivity::class.java
                    )
                    intentExplicito.putExtra("usuarioA", usuarioA)
                    this.startActivity(intentExplicito)
                })
            .setNegativeButton(android.R.string.cancel,
                DialogInterface.OnClickListener { dialog, which ->
                    //botón cancel pulsado**
                    vandera=false
                    //Log.i("List","position $vandera")
                })
            .show()
    }

    fun irPantallaPrincipalVeterinario(){
        val intentExplicito= Intent(
            this,
            PantallaPrincipalVeterinarioActivity::class.java
        )
        intentExplicito.putExtra("usuarioA", usuarioA)
        this.startActivity(intentExplicito)
    }


}
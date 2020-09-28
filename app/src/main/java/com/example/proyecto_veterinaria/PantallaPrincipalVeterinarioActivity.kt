package com.example.proyecto_veterinaria

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_detalles_cita.*
import kotlinx.android.synthetic.main.activity_menu_duenio_mascota.*
import kotlinx.android.synthetic.main.activity_pantalla_principal_veterinario.*


class PantallaPrincipalVeterinarioActivity : AppCompatActivity() {
    val urlPrincipal = "http://192.168.0.115:1337"
    lateinit var listaCitas: ArrayList<CitaDos>
    lateinit var adaptador: ArrayAdapter<CitaDos>
    lateinit var listaUsuarioUnico: ArrayList<Usuario>
    var posicion: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_principal_veterinario)
        listaCitas = arrayListOf()
        listaUsuarioUnico = arrayListOf()
        adaptador = ArrayAdapter(
                this,//contexto
                android.R.layout.simple_list_item_1,//nombre layout
                listaCitas//lista
        )
        lv_mascotas_pant_veterinario.adapter = adaptador
        lv_mascotas_pant_veterinario
                .onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            Log.i("List", "position $position")
            posicion = position
            val cita = listaCitas.get(posicion)
            Log.i("http-klaxon", "Cita Select:  ${cita}")
            val intentException = Intent(
                    this,
                    DetallesCitaActivity::class.java
            )
            intentException.putExtra("mascotaA", cita)
            startActivity(intentException)
            //irPerfilDeMascota()
        }
        obtenerCitas()
        cargarSipinerVeterinario()

        sp_veterinario.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                    arg0: AdapterView<*>?, arg1: View,
                    arg2: Int, arg3: Long
            ) {
                val itemspiner: String = sp_veterinario.getSelectedItem().toString()
                Log.i("List", "position $itemspiner")  //if para ir a las otras pantallas
                if (itemspiner == "Editar") {
                    irEditarPerfilVeterinario()
                } else if (itemspiner == "Cerrar sesión") {
                    confirmacionSalida()
                }
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {
                // TODO Auto-generated method stub
            }
        })

        btn_paciente_atendido_veterinario
                .setOnClickListener {
                    // irDetalleCitaActivity()
                }

        edt_fecha_cita_pant_veterinario
                .setOnClickListener {
                    showDatePickerDialog()
                }
    }

    fun cargarSipinerVeterinario() {
        val spinner: Spinner = findViewById(R.id.sp_veterinario)
        ArrayAdapter.createFromResource(
                this,
                R.array.itemsVeterinario,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            // +1 because January is zero
            val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
            edt_fecha_cita_pant_veterinario.setText(selectedDate)
        })
        newFragment.show(supportFragmentManager, "datePicker")
    }

    fun irEditarPerfilVeterinario() {
        val intentExplicito = Intent(
                this,
                EditarPerfilVeterinarioActivity::class.java
        )
        this.startActivity(intentExplicito)
    }

    fun confirmacionSalida() {
        var vandera = false
        AlertDialog.Builder(this)
                .setTitle("Confirmación")
                .setMessage("¿Está seguro que desea salir?")
                .setPositiveButton(android.R.string.ok,
                        DialogInterface.OnClickListener { dialog, which ->
                            //botón OK pulsado**
                            val intentExplicito = Intent(
                                    this,
                                    MainActivity::class.java
                            )
                            this.startActivity(intentExplicito)
                            //this.startActivity(intent)
                        })
                .setNegativeButton(android.R.string.cancel,
                        DialogInterface.OnClickListener { dialog, which ->
                            //botón cancel pulsado**
                            vandera = false
                            //Log.i("List","position $vandera")
                        })
                .show()
    }


    fun obtenerCitas() {
        val url = urlPrincipal + "/cita"
        url
                .httpGet()
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Success -> {
                            val data = result.get()
                            Log.i("http-L", "Data: ${data}")
                            val citas = Klaxon()
                                    .parseArray<CitaDos>(data)
                            if (citas != null) {
                                citas.forEach {
                                    Log.i("http-klaxon", "Estado: ${it.estadoAtencionCita}  Mascota: ${it.mascota?.nombreMascota} Raza: ${it.mascota?.razaMascota}")
                                    listaCitas.add(it)
                                }
                                runOnUiThread(Runnable {
                                    adaptador.notifyDataSetChanged()
                                })
                            }
                        }
                        is Result.Failure -> {
                            val ex = result.getException()
                            Log.i("http-klaxon", "Error: ${ex.message}")
                        }
                    }
                }
    }

}
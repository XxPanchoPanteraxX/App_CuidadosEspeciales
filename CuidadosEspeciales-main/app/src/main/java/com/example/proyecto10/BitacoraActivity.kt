package com.example.proyecto10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.proyecto10.Notas.AdaptadorNotas
import com.example.proyecto10.Notas.AgregarNotaActivity
import com.example.proyecto10.Notas.Nota
import kotlinx.android.synthetic.main.activity_agregar_nota.*
import kotlinx.android.synthetic.main.activity_bitacora.*
import kotlinx.android.synthetic.main.activity_profile_doc.*
import kotlinx.android.synthetic.main.activity_profile_doc.ibtn_abrir
import kotlinx.android.synthetic.main.activity_profile_doc.ibtn_ambulance
import kotlinx.android.synthetic.main.activity_profile_doc.ibtn_botiquin
import kotlinx.android.synthetic.main.activity_profile_doc.ibtn_buscar
import java.io.*

class BitacoraActivity : AppCompatActivity() {

    var lstNotas=ArrayList<Nota>()

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim)}
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim)}
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim)}
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim)}
    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bitacora)

        ibtn_abrir.setOnClickListener{
            onAddButtonClicked()

        }
        ibtn_botiquin.setOnClickListener{
            val intent = Intent(this, profile_docActivity::class.java)
            startActivity(intent)
        }
        ibtn_buscar.setOnClickListener{
            val intent = Intent(this, historyActivity::class.java)
            startActivity(intent)
        }
        ibtn_ambulance.setOnClickListener{
            val intent = Intent(this, ambulanceActivity::class.java)
            startActivity(intent)
        }

        leerNotas()

        var adapter = AdaptadorNotas(lstNotas, this)

        lvNotas.adapter = adapter


        nuevaNota.setOnClickListener{
            val intent = Intent(this,AgregarNotaActivity::class.java)
            startActivity(intent)
        }


    }
    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked){
            ibtn_buscar.startAnimation(fromBottom)
            ibtn_botiquin.startAnimation(fromBottom)
            ibtn_ambulance.startAnimation(fromBottom)
            ibtn_abrir.startAnimation(rotateOpen)
        }else{
            ibtn_buscar.startAnimation(toBottom)
            ibtn_botiquin.startAnimation(toBottom)
            ibtn_ambulance.startAnimation(toBottom)
            ibtn_abrir.startAnimation(rotateClose)
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked){
            ibtn_ambulance.visibility = View.VISIBLE
            ibtn_botiquin.visibility = View.VISIBLE
            ibtn_buscar.visibility = View.VISIBLE
        }else{
            ibtn_ambulance.visibility = View.INVISIBLE
            ibtn_botiquin.visibility = View.INVISIBLE
            ibtn_buscar.visibility = View.INVISIBLE
        }
    }

    private fun setClickable(clicked: Boolean){
        if(!clicked){
            ibtn_ambulance.isClickable = true
            ibtn_botiquin.isClickable = true
            ibtn_buscar.isClickable = true

        }else{
            ibtn_ambulance.isClickable = false
            ibtn_botiquin.isClickable = false
            ibtn_buscar.isClickable = false
        }
    }
    private fun leerNotas(){
        lstNotas.clear()

        var carpeta = File(ubicacion().absolutePath)

        if(carpeta.exists()){
            var archivos=carpeta.listFiles()
            if(archivos!=null){
                archivos.forEach {
                    leerArchivo(it)
                }
            }
        }
    }

    private fun leerArchivo(it: File?) {
        val fis= FileInputStream(it)
        val di= DataInputStream(fis)
        val br= BufferedReader(InputStreamReader(di))

        var strLine: String? = br.readLine()
        var contenido=""

        while(strLine!=null){
            contenido+=strLine
            strLine=br.readLine()
        }

        br.close()
        di.close()
        fis.close()

        var titulo=it!!.name.substring(0, it!!.name.length-4)

        var nota= Nota(titulo, contenido)
        lstNotas.add(nota)
    }

    private fun ubicacion(): File {
        val carpeta = File(getExternalFilesDir(null), "notas")
        if(!carpeta.exists()){
            carpeta.mkdir()
        }
        return carpeta
    }

    private fun notasDePrueba() {
        lstNotas.add(Nota("Paracetamol","Me toca cada 8 horas."))
        lstNotas.add(Nota("Diclofenaco","Solo tomar cuando presente inflamación."))
        lstNotas.add(Nota("Horario","Tomar 2 pastillas a las 11:00 AM y 1 pastilla a la 1:00 PM"))
    }

}
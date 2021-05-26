package com.example.proyecto10

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_ambulance.*
import kotlinx.android.synthetic.main.activity_profile_doc.ibtn_abrir
import kotlinx.android.synthetic.main.activity_profile_doc.ibtn_ambulance
import kotlinx.android.synthetic.main.activity_profile_doc.ibtn_botiquin
import kotlinx.android.synthetic.main.activity_profile_doc.ibtn_buscar
import java.util.jar.Manifest

class ambulanceActivity : AppCompatActivity() {
    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim)}
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim)}
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim)}
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim)}
    private var clicked = false

    private val PHONE_CALL_REQUEST_CODE = 1
    private val numero="911"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ambulance)

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
            val intent = Intent(this, BitacoraActivity::class.java)
            startActivity(intent)
        }
        btn_ubicacion.setOnClickListener{
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
        llamar.setOnClickListener{
        hacerLlamada()
        }

    }

    fun hacerLlamada() {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$numero")
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
           startActivity(intent)
            Toast.makeText(this, "Lamando $numero", Toast.LENGTH_SHORT ).show()
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE), PHONE_CALL_REQUEST_CODE)

        }

    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PHONE_CALL_REQUEST_CODE){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                hacerLlamada()
            }else{
                Toast.makeText(this, "Debes de dar el permiso para hacer la llamada", Toast.LENGTH_SHORT).show()
            }
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
}
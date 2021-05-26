package com.example.proyecto10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_profile_doc.*

class BitacoraActivity : AppCompatActivity() {

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
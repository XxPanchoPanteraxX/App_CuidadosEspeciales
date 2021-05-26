package com.example.proyecto10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register_pacient.*
import com.google.firebase.firestore.FirebaseFirestore;

class Register_pacientActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {

        val bundle = intent.extras
        val email = bundle?.getString("email")
       // println(email);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_pacient)
        btn_atras.setOnClickListener {
            val intent = Intent(this, profile_docActivity::class.java)
            startActivity(intent)
        }

        saveButton.setOnClickListener{
       // db.collection("users").document(email)
        }


    }

}
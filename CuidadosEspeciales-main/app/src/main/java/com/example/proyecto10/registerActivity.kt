package com.example.proyecto10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth
import kotlinx.android.synthetic.main.activity_register.*

class registerActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = Firebase.auth

        ibtn_atras.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }

        save2.setOnClickListener {
            if (!nombreDoc.text.toString().isNullOrBlank() &&
                !correoDoc.text.toString().isNullOrBlank() &&
                !ocupacionDoc.text.toString().isNullOrBlank() &&
                !contra.text.toString().isNullOrBlank()
            ) {
                registrarCuenta(
                    correoDoc.text.toString(),
                    contra.text.toString()
                )
            } else {
                Toast.makeText(this, "Los campos no pueden quedar vacíos.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


        private fun registrarCuenta(correo: String, contraseña: String) {
            auth.createUserWithEmailAndPassword(correo, contraseña)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        //Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser

                        Toast.makeText(
                            baseContext,
                            "El correo " + auth.currentUser?.email.toString() + " ha sido correctamente registrado.",
                            Toast.LENGTH_SHORT
                        ).show()
                        //updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        //Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        //updateUI(null)
                    }
                }
        }
    }

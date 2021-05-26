package com.example.proyecto10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_auth.*

import kotlinx.android.synthetic.main.activity_profile_doc.*
import java.security.Provider



class AuthActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    private lateinit var db:FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {

       // super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_auth)

        //Splash
        Thread.sleep(3000)
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        auth= FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()

        //Analytics Event
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()

        bundle.putString("message","Integracion de firebase completa")
        analytics.logEvent("UInitScreen",bundle)


        //Setup
        setup()

        //showindex

     //   showIndex()
    }

        private fun setup() {
            title = "    Cuidados Especiales - Iniciar sesión"

            tv_crear_cuenta.setOnClickListener {
                val intent = Intent(this, registerActivity::class.java)
                startActivity(intent)
            }

            loginButton.setOnClickListener {
                this.valida_ingreso()
            }
        }

    private fun valida_ingreso() {
        val et_correo: EditText = findViewById(R.id.edtCorreo)
        val et_contra: EditText = findViewById(R.id.edtContraseña)

        var correo: String = et_correo.text.toString()
        var contra: String = et_contra.text.toString()

        if (!correo.isNullOrBlank() && !contra.isNullOrBlank()) {
            ingresaFirebase(correo, contra)
        } else {
            Toast.makeText(
                this, "Ingresar datos",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun ingresaFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    // Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    val intent: Intent = Intent(this, profile_docActivity::class.java)
                    startActivity(intent)
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    //Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    //updateUI(null)
                }
            }
    }

                private fun showAlert(){

                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Error")
                    builder.setMessage("Se ha producido un error al iniciar sesión")
                    builder.setPositiveButton("aceptar",null)
                    val dialog:AlertDialog =  builder.create()
                    dialog.show()

                }

                //Muestra el index
        private fun showHome(email:String,provider:ProviderType){

                val profileIntent= Intent(this,profile_docActivity::class.java).apply{
                    putExtra("email",email)
                    putExtra("provider",provider.name)
                }
                startActivity(profileIntent)
        }
            //Muestra el index
             private fun showIndex(){
                    val profileIntent= Intent(this,IndexActivity::class.java).apply{
                }
                       startActivity(profileIntent)


             }


}
package com.dam2a.firebaseauntenticacion


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    //Declaramos un objeto  varible de tipo FirebaseAuth

    private lateinit var auth: FirebaseAuth

    //Declaramos variables dónde les pasaremos el id de los elementos del layout

    lateinit var email: EditText
    lateinit var contraseña: EditText
    lateinit var iniciarSesion: Button
    lateinit var registro: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Asignamos los id de los elementos del layout a las varaibles previamente declaradas
        email = findViewById(R.id.email)
        contraseña = findViewById(R.id.contraseña)
        iniciarSesion = findViewById(R.id.iniciarSesion)
        registro = findViewById(R.id.registro)

        //Instanciamos en objeto auth
        auth = Firebase.auth

        registro.setOnClickListener{
            createAccount(email.text.toString(), contraseña.text.toString())
        }
        iniciarSesion.setOnClickListener{
            signIn(email.text.toString(), contraseña.text.toString())
        }

    }






    /**
     * Método que recibe un email y una contraseña las valida y crea un usuario con ellas.
     */
    private fun createAccount(email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }

    }

    private fun updateUI(user: FirebaseUser?) {
        Log.d("CurrentUser",""+auth.currentUser?.uid)
    }

    /**
     * Método que sirve para iniciar sesión a través de un email y contraseña
     */
    private fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
        // [END sign_in_with_email]
    }

    /**
     * Método que envia una vereficación a el correo del usuario
     */
    private fun sendEmailVerification() {

        val user = auth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                // Email Verification sent
            }

    }

    companion object {
        private const val TAG = "EmailPassword"
    }
}
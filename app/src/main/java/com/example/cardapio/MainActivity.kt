package com.example.cardapio

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cardapio.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa os componentes da interface usando o ViewBinding
        val emailField = binding.email
        val passwordField = binding.password
        val loginBtn = binding.login
        val registerBtn = binding.register
        val passwordRecover = binding.passwordRecover

        loginBtn.setOnClickListener{
            val auth = FirebaseAuth.getInstance()
            val email = emailField.text.toString()
            val password = passwordField.text.toString()
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val user = auth.currentUser
                    Toast.makeText(this, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "Erro no login: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        registerBtn.setOnClickListener{

        }

        passwordRecover.setOnClickListener{

        }
    }
}
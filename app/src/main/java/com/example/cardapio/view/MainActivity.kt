package com.example.cardapio.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cardapio.controller.AuthenticationController
import com.example.cardapio.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var ctrl: AuthenticationController

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
            ctrl = AuthenticationController()
            ctrl.login(email, password) { sucesso, erro ->
                if (sucesso) {
                    Toast.makeText(this, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show()
                    val it = Intent(this, MenuActivity:: class.java)
                    startActivity(it)
                } else {
                    Toast.makeText(this, "Erro no login: $erro", Toast.LENGTH_SHORT).show()
                }
            }
        }

        registerBtn.setOnClickListener{
            val it = Intent(this, RegisterActivity:: class.java)
            startActivity(it)
        }

        passwordRecover.setOnClickListener{
            val it = Intent(this, ForgotPasswordActivity:: class.java)
            startActivity(it)
        }
    }
}
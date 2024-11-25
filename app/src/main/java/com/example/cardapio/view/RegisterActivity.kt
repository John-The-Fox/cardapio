package com.example.cardapio.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cardapio.R
import com.example.cardapio.controller.AuthenticationController
import com.example.cardapio.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding:ActivityRegisterBinding
    private lateinit var ctrl : AuthenticationController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
         binding.register.setOnClickListener{
             val email = binding.email.text.toString()
             val pass= binding.password.text.toString()
             val confirmPass = binding.confirmPassword.text.toString()
             val name = binding.name.text.toString()

             if (email.isEmpty() || pass.isEmpty() || confirmPass.isEmpty() || name.isEmpty()) {
                 Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show()
                 return@setOnClickListener
             }
             if (pass != confirmPass){
                 Toast.makeText(this, "As senhas não estão iguais. Verifique e tente novamente.", Toast.LENGTH_SHORT).show()
                 return@setOnClickListener
             }
             ctrl = AuthenticationController()
             ctrl.newUser(name, email, pass) { sucesso, erro ->
                 if (sucesso) {
                     Toast.makeText(this, "Usuário criado com sucesso",
                         Toast.LENGTH_LONG).show()
                     finish()
                 } else {
                     Toast.makeText(this, "Erro ao criar usuário: " +
                             erro.toString(), Toast.LENGTH_LONG).show()
                 }
             }
         }
    }
}
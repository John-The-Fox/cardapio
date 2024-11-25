package com.example.cardapio.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cardapio.R
import com.example.cardapio.controller.AuthenticationController
import com.example.cardapio.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var biding: ActivityForgotPasswordBinding
    private lateinit var ctrl: AuthenticationController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        biding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(biding.root)

        biding.btnSend.setOnClickListener{
            val email = biding.txtEmail.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(this, "Preencha o campo de E-mail.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            ctrl=AuthenticationController()
            ctrl.forgotPassword(email) { sucesso, erro ->
                if (sucesso) {
                    Toast.makeText(this,"Um e-mail de redefinição de senha foi enviado para " +
                            "o seu endereço de e-mail.",
                        Toast.LENGTH_LONG).show()
                    finish()

                } else {
                    Toast.makeText(this,
                        "Falha ao enviar e-mail de redefinição de senha. " +
                                "Verifique se o endereço de e-mail é válido.",
                        Toast.LENGTH_LONG).show()
                }
            }
        }


    }
}
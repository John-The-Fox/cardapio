package com.example.cardapio.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.cardapio.controller.AuthenticationController
import com.example.cardapio.controller.ImageAdapter
import com.example.cardapio.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var ctrl: AuthenticationController
    private lateinit var viewPager: ViewPager2

    val handler = Handler(Looper.getMainLooper())
    val autoScrollRunnable = object : Runnable {
        override fun run() {
            val itemCount = viewPager.adapter?.itemCount ?: 0
            val nextItem = (viewPager.currentItem + 1) % itemCount
            viewPager.setCurrentItem(nextItem, false)
            handler.postDelayed(this, 3000) // 3 segundos por slide
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = binding.viewPager

        // Inicializa os componentes da interface usando o ViewBinding
        val emailField = binding.email
        val passwordField = binding.password
        val loginBtn = binding.login
        val registerBtn = binding.register
        val passwordRecover = binding.passwordRecover
        val firestore = FirebaseFirestore.getInstance()
        val imagesCollection = firestore.collection("restaurantImages")

        imagesCollection.get().addOnSuccessListener { result ->
            val imageUrls = result.documents.mapNotNull { it.getString("url") }
            val adapter = ImageAdapter(imageUrls)
            viewPager.adapter = adapter
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    val currentPosition = viewPager.currentItem
                    val itemCount = viewPager.adapter?.itemCount ?: 0
                    if (currentPosition == itemCount - 1) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            viewPager.setCurrentItem(0, false) // Volta para o primeiro item sem animação
                        }, 3000) // 3000ms de delay
                    }
                }
            }
        })

        handler.postDelayed(autoScrollRunnable, 3000)

        loginBtn.setOnClickListener{
            val email = emailField.text.toString()
            val password = passwordField.text.toString()

            // Verificação de campos vazios
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            ctrl = AuthenticationController()
            ctrl.login(email, password) { sucesso, erro ->
                if (sucesso) {
                    Toast.makeText(this, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                    finish() // Finaliza a Activity atual para evitar retorno ao login
                } else {
                    Log.e("MainActivity", "Erro no login: $erro")
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
    override fun onDestroy() {
        handler.removeCallbacks(autoScrollRunnable)
        super.onDestroy()
    }
}
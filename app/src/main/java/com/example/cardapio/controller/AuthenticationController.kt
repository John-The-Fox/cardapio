package com.example.cardapio.controller

import com.google.firebase.auth.FirebaseAuth

// Classe para gerenciar a autenticação do usuário com Firebase
class AuthenticationController {
    // Realiza o login do usuário com e-mail e senha
    fun login(email: String, senha: String, onResult: (Boolean, String?) -> Unit) {
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    val erroMsg = task.exception?.message
                    onResult(false, erroMsg)
                }
            }
    }
    // Cria um novo usuário no Firebase Authentication
    fun newUser(nome: String, email: String, senha: String, onResult: (Boolean, String?) -> Unit) {
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    val erroMsg = task.exception?.message
                    onResult(false, erroMsg)
                }
            }
    }

    // Envia um e-mail de redefinição de senha
    fun forgotPassword(email: String, onResult: (Boolean, String?) -> Unit) {
        val auth = FirebaseAuth.getInstance()
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    val erroMsg = task.exception?.message
                    onResult(false, erroMsg)
                }
            }
    }
    // Faz logout do usuário
    fun logout(){
        FirebaseAuth.getInstance().signOut()
    }

    // Retorna o e-mail do usuário logado (ou null se não houver)
    fun authUser(): String?{
        val auth = FirebaseAuth.getInstance()
        return auth.currentUser?.email.toString()
    }

    // Retorna o ID único do usuário logado
    fun userId(): String?{
        val auth = FirebaseAuth.getInstance()
        return auth.currentUser?.uid.toString()
    }
}
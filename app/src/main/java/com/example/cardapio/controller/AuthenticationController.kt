package com.example.cardapio.controller

import com.google.firebase.auth.FirebaseAuth

class AuthenticationController {
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
    //
    // CRIAR USUÁRIO
    //
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

    //
    // ESQUECEU A SENHA
    //
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
    //
    // LOGOUT
    //
    fun logout(){
        FirebaseAuth.getInstance().signOut()
    }

    //
    // USUÁRIO LOGADO
    //
    fun authUser(): String?{
        val auth = FirebaseAuth.getInstance()
        return auth.currentUser?.email.toString()
    }

    fun userId(): String?{
        val auth = FirebaseAuth.getInstance()
        return auth.currentUser?.uid.toString()
    }
}
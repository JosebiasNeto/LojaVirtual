package com.example.lojavirtual.form


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lojavirtual.R
import com.example.lojavirtual.TelaPrincipal
import com.example.lojavirtual.databinding.ActivityFormLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class FormLogin : AppCompatActivity() {

    private lateinit var binding: ActivityFormLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        val text_tela_cadastro = binding.textTelaCadastro
        text_tela_cadastro.setOnClickListener{
            val intent = Intent(this, FormCadastro::class.java)
            startActivity(intent)
        }

        val btEntrar = binding.btEntrar
        btEntrar.setOnClickListener{
            AutenticarUsuario()
        }


    }
    private fun AutenticarUsuario(){
        val email = binding.etEmail.text.toString()
        val senha = binding.etSenha.text.toString()

        if (email.isEmpty() || senha.isEmpty()){
            var snackbar = Snackbar.make(binding.layoutLogin, "Coloque um email e uma senha!", Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", {

                })
            snackbar.show()
        } else {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,senha).addOnCompleteListener {
                if (it.isSuccessful){
                    AbrirTelaPrincipal()
                }
            }.addOnFailureListener {
                var snackbar = Snackbar.make(binding.layoutLogin, "Erro ao logar usuário", Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", {

                    })
                snackbar.show()
            }
        }

    }
    private fun AbrirTelaPrincipal(){
        val intent = Intent(this, TelaPrincipal::class.java)
        startActivity(intent)
        finish()
    }
}



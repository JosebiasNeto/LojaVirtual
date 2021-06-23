package com.example.lojavirtual.form

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lojavirtual.R
import com.example.lojavirtual.databinding.ActivityFormCadastroBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class FormCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityFormCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar!!.hide()

        val btCadastrar = binding.btCadastrar
        btCadastrar.setOnClickListener{
            CadastrarUsuario()
        }
    }
    private fun CadastrarUsuario(){
        val email = binding.etEmail.text.toString()
        val senha = binding.etSenha.text.toString()

        if (email.isEmpty() || senha.isEmpty()){
            //Toast.makeText(this, "Coloque seu email e sua senha!", Toast.LENGTH_SHORT).show()
            var snackbar = Snackbar.make(binding.layoutCadastro, "Coloque seu email e sua senha!", Snackbar.LENGTH_INDEFINITE)
                .setBackgroundTint(Color.WHITE)
                .setTextColor(Color.BLACK)
                .setAction("OK", {

                })
            snackbar.show()
        } else {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,senha).addOnCompleteListener {
                if (it.isSuccessful){
                    //Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                    var snackbar = Snackbar.make(binding.layoutCadastro, "Cadastro realizado com sucesso!", Snackbar.LENGTH_INDEFINITE)
                        .setBackgroundTint(Color.WHITE)
                        .setTextColor(Color.BLACK)
                        .setAction("OK", {
                            VoltarParaFormLogin()
                        })
                    snackbar.show()
                }
            }.addOnFailureListener{
                //Toast.makeText(this, "Erro ao cadastrar usuário", Toast.LENGTH_SHORT).show()
                var snackbar = Snackbar.make(binding.layoutCadastro, "Erro ao cadastrar usuário.", Snackbar.LENGTH_INDEFINITE)
                    .setBackgroundTint(Color.WHITE)
                    .setTextColor(Color.BLACK)
                    .setAction("OK", {

                    })
                snackbar.show()
            }
        }
    }

    private fun VoltarParaFormLogin(){
        val intent = Intent(this, FormLogin::class.java)
        startActivity(intent)
        finish()
    }
}
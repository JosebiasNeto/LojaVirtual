package com.example.lojavirtual.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.lojavirtual.R
import com.example.lojavirtual.databinding.ActivityCadastroProdutosBinding
import com.example.lojavirtual.model.Dados
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*


class CadastroProdutos : AppCompatActivity() {

    private var SelecionarUri: Uri? = null

    private lateinit var binding: ActivityCadastroProdutosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroProdutosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btSelecionarFoto = binding.btSelecionarFoto
        btSelecionarFoto.setOnClickListener {
            SelecionarFotoGaleria()
        }
        val btCadastrarProduto = binding.btCadastrarProduto
        btCadastrarProduto.setOnClickListener {
            SalvarDadosFirebase()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0){
            SelecionarUri = data?.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, SelecionarUri)
            val foto_produto = binding.fotoProduto
            foto_produto.setImageBitmap(bitmap)
            val btSelecionarFoto = binding.btSelecionarFoto
            btSelecionarFoto.alpha = 0f
        }

    }

    private fun SelecionarFotoGaleria(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 0)
    }

    private fun SalvarDadosFirebase(){
        val nomeArquivo = UUID.randomUUID().toString()
        val referencia = FirebaseStorage.getInstance().getReference(
            "/imagens/${nomeArquivo}")

        SelecionarUri?.let {

        referencia.putFile(it)
            .addOnSuccessListener {
                referencia.downloadUrl.addOnSuccessListener {

                    val url = it.toString()
                    val nome = binding.etNome.text.toString()
                    val preco = binding.etPreco.text.toString()
                    val uid = FirebaseAuth.getInstance().uid

                    val Produtos = Dados(url, nome, preco)
                    FirebaseFirestore.getInstance().collection("Produtos")
                        .add(Produtos)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Produto cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener{
                            //Toast.makeText(this)
                        }

                }
            }
        }
    }
}
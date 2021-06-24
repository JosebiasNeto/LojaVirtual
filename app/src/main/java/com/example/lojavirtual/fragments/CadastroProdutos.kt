package com.example.lojavirtual.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import com.example.lojavirtual.R
import com.example.lojavirtual.databinding.ActivityCadastroProdutosBinding


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
        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->
            if(result.resultCode == Activity.RESULT_OK){
                val data: Intent? = result.data
            }
        }
        resultLauncher.launch(intent)
    }
}
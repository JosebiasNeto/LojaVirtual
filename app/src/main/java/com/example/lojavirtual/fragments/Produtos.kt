package com.example.lojavirtual.fragments

import android.app.AlertDialog
import android.content.ClipData
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.lojavirtual.R
import com.example.lojavirtual.databinding.FragmentProdutosBinding
import com.example.lojavirtual.model.Dados
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item


class Produtos : Fragment() {

    private lateinit var Adapter: GroupieAdapter

    private var fragmentProdutos: FragmentProdutosBinding? = null


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_produtos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val bindingProdutos = FragmentProdutosBinding.bind(view)
        fragmentProdutos = bindingProdutos

        val recycler_produtos = bindingProdutos.recyclerProdutos

        Adapter = GroupieAdapter()
        recycler_produtos.adapter = Adapter
        Adapter.setOnItemClickListener { item, view ->

            val DialogView = LayoutInflater.from(context).inflate(R.layout.pagamento, null)
            val builder = AlertDialog.Builder(context)
                .setView(DialogView)
                .setTitle("Formas de Pagamento")

            //RealizarPagamento()

        }

        BuscarProdutos()
    }

    private inner class ProdutosItem(val adProdutos: Dados) :
        Item<GroupieViewHolder>() {

        override fun getLayout(): Int {
            return R.layout.lista_produtos
        }

        override fun bind(viewHolder: GroupieViewHolder, position: Int) {
            viewHolder.itemView.findViewById<TextView>(R.id.nome_produto).text = adProdutos.nome
            viewHolder.itemView.findViewById<TextView>(R.id.preco_produto).text = adProdutos.preco
            var fotoProduto = viewHolder.itemView.findViewById<ImageView>(R.id.foto_produto)
            Picasso.get().load(adProdutos.uid).into(fotoProduto)
        }
    }

    private fun RealizarPagamento(){

        val mAlertDialog = AlertDialog.Builder(context).show()
        mAlertDialog.findViewById<Button>(R.id.bt_pagamento).setOnClickListener {
            mAlertDialog.dismiss()
            val pagamento = mAlertDialog.findViewById<EditText>(R.id.et_pagamento).text.toString()
            if (pagamento == "249,99") {
                Toast.makeText(context, "Pagamento Concluído", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Pagamento Recusado", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun BuscarProdutos() {

        FirebaseFirestore.getInstance().collection("Produtos")
            .addSnapshotListener { snapshot, exception ->
                exception?.let {
                    return@addSnapshotListener
                }
                snapshot?.let {
                    for (doc in snapshot) {
                        val produtos = doc.toObject(Dados::class.java)
                        Adapter.add(ProdutosItem(produtos))
                    }
                }
            }
    }


}
package com.example.lojavirtual.model
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize

data class Dados(

    val uid: String = " ",
    val nome: String = " ",
    val preco: String = " ",
    val url: String = " "
                ):Parcelable
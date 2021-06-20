package com.example.lojavirtual

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lojavirtual.form.FormLogin
import com.heinrichreimersoftware.materialintro.app.IntroActivity
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide

class Slides : IntroActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_slides)

        isButtonBackVisible = false
        isButtonNextVisible = false

        addSlide(
            SimpleSlide.Builder()
                .background(R.color.purple)
                .backgroundDark(R.color.white)
                .image(R.drawable.adidas)
                .title("Loja Online de Calçados")
                .description("Entre e confira as promoções que preparamos para você!")
                .build()
        )
        addSlide(
            SimpleSlide.Builder()
                .background(R.color.blue_light)
                .canGoBackward(false)
                .title("Crie uma conta Grátis")
                .description("Cadastre-se agora mesmo! E venha conhecer os nossos produtos!")
                .build()
        )
    }

    override fun onDestroy() {
        super.onDestroy()

        val intent = Intent(this, FormLogin::class.java)
        startActivity(intent)
        finish()
    }
}
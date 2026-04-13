package com.example.barredefilante

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Plein écran
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(BarreView(this))
    }
}

class BarreView(context: Context) : View(context) {

    // Largeur de la barre en pixels (1 mm ≈ 17 px sur Pixel 8 à 428 ppp)
    // 1 pixel physique = ~0.059 mm → barre de ~5mm = 84 px
    private val barreWidth = 84f

    // Position X courante du bord gauche de la barre
    private var posX = 0f

    // Vitesse de défilement en pixels par frame (≈ 5 mm/s à 60fps)
    private val vitesse = 5f

    private val paint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.FILL
    }

    private var screenWidth = 0f
    private var initialized = false

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenWidth = w.toFloat()
        if (!initialized) {
            posX = screenWidth // démarre hors écran à droite
            initialized = true
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Fond blanc
        canvas.drawColor(Color.WHITE)

        // Dessine la barre noire
        canvas.drawRect(posX, 0f, posX + barreWidth, height.toFloat(), paint)

        // Déplace vers la gauche
        posX -= vitesse

        // Quand la barre sort complètement à gauche, on la remet à droite
        if (posX + barreWidth < 0f) {
            posX = screenWidth
        }

        // Demande un nouveau frame
        invalidate()
    }
}

package com.examples.egyptclicker

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {

    private var pharaoh: ImageView? = null
    private var scorePoints: TextView? = null
    private  var cheers:TextView? = null
    private var score = 0

    private var constraintLayout: ConstraintLayout? = null

    var animationClick: Animation? = null
    var animationCoin: Animation? = null
    var animationCoinRotate1: Animation? = null
    var animationCoinRotate2: Animation? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        justClick()
    }

    private fun init() {
        constraintLayout = findViewById(R.id.layout)
        pharaoh = findViewById(R.id.ivPharaoh)
        scorePoints = findViewById(R.id.tvPoints)
        cheers = findViewById<TextView>(R.id.tvCheers)
        animationClick = AnimationUtils.loadAnimation(this, R.anim.anim_click)
        animationCoin = AnimationUtils.loadAnimation(this, R.anim.anim_down_coin)
        animationCoinRotate1 = AnimationUtils.loadAnimation(this, R.anim.flip_from_middle)
        animationCoinRotate2 = AnimationUtils.loadAnimation(this, R.anim.flip_to_middle)
    }

    //Нажатие на картинку
    private fun justClick() {
        pharaoh!!.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                counterAdd()
                pharaoh!!.startAnimation(animationClick)
                generateCoin()
            }
            if (score % 100 == 0 && score != 0){
                GlobalScope.launch(Dispatchers.Main) {
                    for(num in 0 until 50){
                        delay(100L)
                        generateCoin()
                }
                }
            }
        }
    }

    //Счетчик очков
    private fun counterAdd() {
        score++
        scorePoints!!.text = score.toString()

    }

    //Генерация падающих монеток
    private fun generateCoin() {

        val img = ImageView(this@MainActivity)
        img.setImageResource(R.drawable.goldcoin)
        constraintLayout!!.addView(img)
        val params = img.layoutParams as ConstraintLayout.LayoutParams
        val randomX = Random().nextInt((constraintLayout!!.width)-100)
        params.width = 100
        params.height = 100
        img.layoutParams = params
        img.x = randomX.toFloat()
        img.y = 0f

        val anim = AnimationUtils.loadAnimation(this, R.anim.anim_down_coin)
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                img.visibility = View.GONE
            }
            override fun onAnimationRepeat(animation: Animation) {}
        })
        img.startAnimation(anim)
    }


}
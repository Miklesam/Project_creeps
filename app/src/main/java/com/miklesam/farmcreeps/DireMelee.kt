package com.miklesam.farmcreeps

import android.content.Context
import android.graphics.Canvas
import android.util.Log
import kotlin.math.abs
import kotlin.math.absoluteValue

class DireMelee(context: Context, height: Float, widthScreen: Int, startPosition: Int) :
    Creep(context, height, widthScreen, startPosition) {

    private val healthBar: DireHealthBar

    init {
        this.setImageResource(R.drawable.red_walk_1)
        this.x = widthScreen.toFloat() - this.width + 210
        this.healthBar = DireHealthBar(this, context)
    }

    override fun run(friendlyCreeps: MutableList<Creep>) {

        this.x -= 7
        increaseCount()
        when (imCount) {
            0 -> {
                this.setImageResource(R.drawable.red_walk_1)
            }
            1 -> {
                this.setImageResource(R.drawable.red_walk_2)
            }
            2 -> {
                this.setImageResource(R.drawable.red_walk_3)
            }
        }
        if (this.x + this.width < 0) {
            this.x = widthScreen.toFloat() - this.width
        }
    }

    override fun runTop(friendlyCreeps: MutableList<Creep>) {
        if(this.y>=105){
            this.y -= 5
        }
    }

    override fun attack(): Int {
        increaseAttackCount()
        when (imCount) {
            0 -> {
                this.setImageResource(R.drawable.red_attc_1)
                return 0
            }
            1 -> {
                this.setImageResource(R.drawable.red_attc_2)
                return 0
            }
            2 -> {
                this.setImageResource(R.drawable.red_attc_3)
                if (animateCount == 10) {
                    return generateAttack()
                } else {
                    return 0
                }

            }
            else -> {
                return 0
            }
        }
    }


    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        healthBar.draw(canvas)
    }

}
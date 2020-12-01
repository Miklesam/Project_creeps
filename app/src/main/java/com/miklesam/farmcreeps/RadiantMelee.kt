package com.miklesam.farmcreeps

import android.content.Context
import android.graphics.Canvas
import android.util.Log
import kotlin.math.abs

class RadiantMelee(context: Context, height: Float, widthScreen: Int, startPosition: Int) :
    Creep(context, height, widthScreen, startPosition) {

    private val healthBar: HealthBar

    init {
        this.setImageResource(R.drawable.green_walk_1)
        this.x = 0f - this.width - startPosition * 100 -200
        this.healthBar = HealthBar(this, context)
    }

    override fun run(friendlyCreeps: MutableList<Creep>) {
        val sameLineCreeps = friendlyCreeps.filter { it.y == this.y && it.x != this.x }
        Log.w("same Lines", sameLineCreeps.toString())
        var collision = false


        if (!sameLineCreeps.isNullOrEmpty()) {
            for (i in sameLineCreeps.indices) {
                var diff = abs(sameLineCreeps[i].x - this.x)
                Log.w("same Lines", diff.toString())
                collision = diff <= 100f
            }
        }


        if (!collision) {
            this.x += 7
        }

        increaseCount()
        when (imCount) {
            0 -> {
                this.setImageResource(R.drawable.green_walk_1)
            }
            1 -> {
                this.setImageResource(R.drawable.green_walk_2)
            }
            2 -> {
                this.setImageResource(R.drawable.green_walk_3)
            }
        }
        if (this.x + this.width > widthScreen) {
            this.x = 0f - this.width - startPosition * 100
        }
    }

    override fun attack(): Int {
        increaseAttackCount()
        when (imCount) {
            0 -> {
                this.setImageResource(R.drawable.attc_1)
                return 0
            }
            1 -> {
                this.setImageResource(R.drawable.attc_2)
                return 0
            }
            2 -> {
                this.setImageResource(R.drawable.attc_3)
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

    override fun provideRun(
        toCreep: Creep,
        friendlyCreeps: MutableList<Creep>,
        i: Int
    ):Boolean {
        if (this.y > toCreep.y) {
            this.y -= 5
            if (this.y < toCreep.y) this.y = toCreep.y
        } else {
            this.y += 5
            if (this.y > toCreep.y) this.y = toCreep.y
        }
        increaseCount()
        when (imCount) {
            0 -> {
                this.setImageResource(R.drawable.green_walk_1)
            }
            1 -> {
                this.setImageResource(R.drawable.green_walk_2)
            }
            2 -> {
                this.setImageResource(R.drawable.green_walk_3)
            }
        }
        return true
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        Log.w("Draw", "drawwww")
        healthBar.draw(canvas)
    }


}
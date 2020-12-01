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

        val sameLineCreeps = friendlyCreeps.filter { it.y == this.y && it.x != this.x }
        Log.w("same Lines", sameLineCreeps.toString())
        var collision = false


        if (!sameLineCreeps.isNullOrEmpty()) {
            for (i in sameLineCreeps.indices) {
                var diff = abs(sameLineCreeps[i].x - this.x)
                Log.w("same Lines", diff.toString())
                collision = diff <= 100f
                if (!collision) {
                    break
                }
            }
        }


        if (!collision) {
            this.x -= 7
        }


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

    override fun provideRun(
        toCreep: Creep,
        friendlyCreeps: MutableList<Creep>,
        i: Int
    ): Boolean {
        var boooool = true
        var aboveCreep: Creep? = null
        var underCreep: Creep? = null
        if (i > 0) {
            aboveCreep = friendlyCreeps[i - 1]
        }
        if (i + 1 > friendlyCreeps.size) {
            underCreep = friendlyCreeps[i + 1]
        }
        if (this.y > toCreep.y) {
            if (aboveCreep == null) {
                this.y -= 5
            }
            aboveCreep?.let {
                if (aboveCreep.y + aboveCreep.height + 5 < this.y) {
                    this.y -= 5
                } else {
                    boooool = false
                }
            }
            if (this.y < toCreep.y) this.y = toCreep.y
        } else {
            if (underCreep == null) {
                this.y += 5
            }
            underCreep?.let {
                if (underCreep.y - 5 < this.y + this.height) {
                    this.y += 5
                } else {
                    boooool = false
                }
            }
            if (this.y > toCreep.y) this.y = toCreep.y
        }
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
        return boooool
    }


    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        Log.w("Draw", "drawwww")
        healthBar.draw(canvas)
    }

}
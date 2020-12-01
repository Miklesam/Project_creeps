package com.miklesam.farmcreeps

import android.content.Context
import android.util.Log
import android.view.View

abstract class Creep(
    context: Context,
    var height: Float,
    var widthScreen: Int,
    var startPosition: Int
) :
    androidx.appcompat.widget.AppCompatImageView(context) {

    protected var animateCount = 0
    protected var imCount = 0
    var hp = 250f


    init {
        this.visibility = View.VISIBLE
        this.y = height
    }

    protected fun increaseCount() {
        if (animateCount < 4) {
            animateCount++
        } else {
            animateCount = 0
        }
        if (animateCount == 4) {
            if (imCount < 2) {
                imCount++
            } else {
                imCount = 0
            }
        }
    }

    fun minusHP(damage: Int) {
        Log.w("hp", "hppp $damage $hp")
        hp -= damage
    }

    protected fun increaseAttackCount() {
        if (animateCount < 10) {
            animateCount++
        } else {
            animateCount = 0
        }
        if (animateCount == 10) {
            if (imCount < 2) {
                imCount++
            } else {
                imCount = 0
            }
        }
    }

    abstract fun run(friendlyCreeps: MutableList<Creep>)

    abstract fun provideRun(
        toCreep: Creep,
        friendlyCreeps: MutableList<Creep>,
        i: Int
    ):Boolean

    abstract fun attack(): Int

    fun die() {
        setImageResource(R.drawable.dead)
    }

    fun generateAttack():Int{
        val attc = (33..42).random()
        return attc
    }

}
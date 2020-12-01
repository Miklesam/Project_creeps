package com.miklesam.farmcreeps

import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val handler = Handler()
    private var timer = Timer()
    private var width = 0
    private val creeps = mutableListOf<Creep>()

    private val radiantCreeps = mutableListOf<Creep>()
    private val direCreeps = mutableListOf<Creep>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val windowManager = windowManager
        val display = windowManager?.defaultDisplay
        val size = Point()
        display?.getSize(size)
        width = size.x
        initNewCreeps()


        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(Runnable {
                    changePos()
                })
            }
        }, 0, 20)
    }

    private fun changePos() {
        val radSize = radiantCreeps.size
        for (i in 0 until radSize) {
            val currentRad = radiantCreeps[i]
            var haveEnemyAtLine = false
            var index = 0
            for (j in 0 until direCreeps.size) {
                if (currentRad.y == direCreeps[j].y) {
                    haveEnemyAtLine = true
                    index = j
                    break
                } else {
                    haveEnemyAtLine = false
                }
            }
            when {
                currentRad.hp < 0 -> {
                    currentRad.die()
                    radiantCreeps.removeAt(i)
                    creeps.remove(currentRad)
                    layout.removeView(currentRad)
                    return
                }
                haveEnemyAtLine -> {
                    if (direCreeps.size > i) {
                        if (currentRad.x + currentRad.width > direCreeps[index].x + direCreeps[index].width / 2 && currentRad.x < direCreeps[index].x) {
                            val dmg = currentRad.attack()
                            //direCreeps[index].minusHP(dmg)
                        } else {
                            currentRad.run(radiantCreeps)
                        }
                    }
                }
                else -> {
                    currentRad.run(radiantCreeps)
                }
            }

        }
        //val toRunArray = mutableListOf<Creep>()
        for (i in 0 until direCreeps.size) {
            val currentDire = direCreeps[i]
            var haveEnemyAtLine = false
            var index = 0
            for (j in 0 until radiantCreeps.size) {
                if (currentDire.y == radiantCreeps[j].y) {
                    haveEnemyAtLine = true
                    index = j
                    break
                } else {
                    haveEnemyAtLine = false
                }
            }
            when {
                currentDire.hp < 0 -> {
                    currentDire.die()
                    direCreeps.removeAt(i)
                    creeps.remove(currentDire)
                    layout.removeView(currentDire)
                    return
                }
                haveEnemyAtLine -> {
                    if (radiantCreeps.size > index) {
                        if (currentDire.x < radiantCreeps[index].x + radiantCreeps[index].width / 2 && currentDire.x + currentDire.width / 2 > radiantCreeps[index].x + radiantCreeps[index].width / 2) {
                            val dmg = currentDire.attack()
                            radiantCreeps[index].minusHP(dmg)
                        } else {
                            currentDire.run(direCreeps)
                        }
                    }
                }
                else -> {
                    currentDire.run(direCreeps)
                }
            }
        }

    }

    private fun initNewCreeps() {

        if (creeps.size < 10) {
            val creep1 = RadiantMelee(this, 200f, width, 0)
            val creep2 = RadiantMelee(this, 300f, width, 1)
            val creep3 = RadiantMelee(this, 400f, width, 2)
            val creep4 = RadiantMelee(this, 500f, width, 3)

            val creep5 = DireMelee(this, 200f, width, 0)
            val creep6 = DireMelee(this, 300f, width, 1)
            val creep7 = DireMelee(this, 400f, width, 2)
            val creep8 = DireMelee(this, 500f, width, 3)


            creeps.add(creep1)
            creeps.add(creep2)
            creeps.add(creep3)
            creeps.add(creep4)

            radiantCreeps.add(creep1)
            radiantCreeps.add(creep2)
            radiantCreeps.add(creep3)
            radiantCreeps.add(creep4)

            creeps.add(creep5)
            creeps.add(creep6)
            creeps.add(creep7)
            creeps.add(creep8)

            direCreeps.add(creep5)
            direCreeps.add(creep6)
            direCreeps.add(creep7)
            direCreeps.add(creep8)

            val newCreeps = mutableListOf<Creep>()
            newCreeps.add(creep1)
            newCreeps.add(creep2)
            newCreeps.add(creep3)
            newCreeps.add(creep4)
            newCreeps.add(creep5)
            newCreeps.add(creep6)
            newCreeps.add(creep7)
            newCreeps.add(creep8)

            for (creep in newCreeps) {
                layout.addView(creep, 200, 200)
            }
        }
    }

}
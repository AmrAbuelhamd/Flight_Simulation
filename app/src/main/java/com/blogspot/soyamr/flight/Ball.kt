package com.blogspot.soyamr.flight

import kotlin.math.*

class Ball {
    private val g = 9.810;
    private val dt = 0.01
    private var cosA = 0.0
    private var sinA = 0.0
    private var height = 0.0
    private var speed = 0.0
    private var angle = 0.0
        set(value) {
            field = Math.toRadians(value)
        }

    private var v0x = 0.0
    private var v0y = 0.0

    var x = 0.0
        private set
    var y = 0.0
        private set

    var maximumHeight = 0.0
    var maximumWidth = 0.0

    var t = 0.0

    fun reset(speed: Double, height: Double, angle: Double) {
        this.speed = speed
        this.y = height
        this.angle = angle
        t = 0.0
        x = 0.0
        y = height
        this.height = height
        cosA = cos(this.angle)
        sinA = sin(this.angle)

        v0y = sinA * speed
        v0x = cosA * speed

        maximumHeight = (v0y * v0y) / (2.0 * g) + height + 1
        val totalT = (v0y + sqrt(v0y * v0y + 2.0 * g * (maximumHeight))) / g
        maximumWidth = totalT * v0x + 1
    }

    fun update() {
//        println("time " + t)//https://stackoverflow.com/questions/53234843/kotlin-sumbydouble-returning-additional-decimals
        t += dt
        t = round(t * 100) / 100;
        x = v0x * t
        y = height + v0y * t - g * t * t / 2.0
    }

}
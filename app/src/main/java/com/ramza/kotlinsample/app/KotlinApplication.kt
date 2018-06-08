package com.ramza.kotlinsample.app

import android.app.Application
import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.channels.produce
import kotlinx.coroutines.experimental.runBlocking
import kotlin.coroutines.experimental.coroutineContext


class KotlinApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        runBlocking<Unit> {
            val numbers = produceNumbers() // produces integers from 1 and on
            val squares = square(numbers) // squares integers
            for (i in 1..5) println(squares.receive()) // print first five
            println("Done!") // we are done
            squares.cancel() // need to cancel these coroutines in a larger app
            numbers.cancel()
        }
    }

    suspend  fun produceNumbers() = kotlinx.coroutines.experimental.channels.produce<Int>(coroutineContext) {
        var x = 1
        while (true) send(x++) // infinite stream of integers starting from 1
    }

    suspend  fun square(numbers: ReceiveChannel<Int>) = produce<Int>(coroutineContext) {
        for (x in numbers) send(x * x)
    }
}
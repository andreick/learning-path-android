package flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() {
    runBlocking {
        var time = measureTimeMillis {
            generate().collect {
                delay(300L)
                println(it)
            }
        }
        println("Collected in $time ms without buffer")

        time = measureTimeMillis {
            generate().buffer()
                .collect {
                    delay(300L)
                    println(it)
            }
        }
        println("Collected in $time ms with buffer")
    }
}

fun generate() = flow {
    for (i in 1..3) {
        delay(100L)
        emit(i)
    }
}
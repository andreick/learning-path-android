package concurrency

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

// Multiple coroutines can update a shared state variable concurrently
// Some updates may be lost

fun main() {
    runBlocking {
        var counter = 0
        withContext(Dispatchers.Default) { massiveRun { counter++ } }
        println("counter = $counter")
    }
}

suspend inline fun massiveRun(crossinline action: suspend () -> Unit) {
    val n = 100
    val k = 1000
    val time = measureTimeMillis {
        coroutineScope {
            repeat(n) {
                launch {
                    repeat(k) {
                        action()
                    }
                }
            }
        }
    }
    println("Completed ${n * k} actions in $time ms")
}
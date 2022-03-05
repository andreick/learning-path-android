package basics

import kotlinx.coroutines.*

// Scope provides lifecycle methods for coroutines and
// allows us to start and stop them

fun main() {
    println("Program execution will now block")

    // creates a scope and runs a coroutine in a blocking way
    runBlocking {
        launch {
            delay(1000L)
            println("Task from runBlocking")
        }

        // the scope of the coroutine is the lifecycle of the
        // entire application
        GlobalScope.launch {
            delay(500L)
            println("Task from GlobalScope")
        }

        // creates a new scope and does not complete until
        // all children coroutines complete
        coroutineScope {
            launch {
                delay(1500L)
                println("Task from coroutineScope")
            }
        }
    }
    println("Program execution will now continue")
}
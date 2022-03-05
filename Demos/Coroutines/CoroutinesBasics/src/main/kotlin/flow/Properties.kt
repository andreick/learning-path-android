package flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

fun main() {
    runBlocking {
        cold()
        cancellation()
    }
}

// Flows are cold
// The code does not run until the collect function is called
suspend fun cold() {
    val numbersFlow = listOf(1, 2, 3).asFlow()
    println("Flow hasn't started yet")
    println("Starting flow now")
    numbersFlow.collect { println(it) }
}

// A flow cannot be cancelled by itself
// It will be cancelled when the encompassing coroutine is cancelled
suspend fun cancellation() {
    val numbersFlow = flow {
        listOf(1, 2, 3).forEach {
            delay(400L)
            emit(it)
        }
    }
    println("Starting flow with timeout")
    withTimeoutOrNull(1000L) {
        numbersFlow.collect { println(it) }
    }
}
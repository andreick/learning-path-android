package flow

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        tryCatch()
        catch()
        onCompletion()
    }
}

// Surround collect call with try/catch
suspend fun tryCatch() {
    try {
        (1..3).asFlow()
            .onEach { check(it != 2) }
            .collect { println(it) }
    } catch (e: Exception) {
        println("Caught exception $e")
    }
}

// An exception can be caught by the operator .catch
// Catches any exception thar occurs above the catch operator,
// not bellow
suspend fun catch() {
    (1..3).asFlow()
        .onEach { check(it != 2) }
        .catch { println("Caught exception $it") }
        .collect { println(it) }
}

// onCompletion is the equivalent of a finally block
// Does not handle the exception
// Sees all exceptions and receives a null exception only on successful
// completion of the upstream flow (without cancellation or failure)
suspend fun onCompletion() {
    (1..3).asFlow()
        .onEach { check(it != 2) }
        .onCompletion { e ->
            if (e == null) {
                println("Flow completed successfully")
            } else {
                println("Flow completed with exception $e")
            }
        }
        .catch { println("Caught exception $it") }
        .collect { println(it) }
}
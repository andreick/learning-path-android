package flow

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

fun main () {
    runBlocking {
        sendEachNumber().collect { println("sendEachNumber() -> $it") }
        sendNumberCollection().collect { println("sendNumberCollection() -> $it") }
        sendAnyNumber().collect { println("sendAnyNumber() -> $it") }
    }
}

// Generate flow by emitting each value
fun sendEachNumber() = flow {
    for (i in 1..10) {
        emit(i)
    }
}

// A collection can be converted directly into a flow
fun sendNumberCollection() = listOf(1, 2, 3).asFlow()

// A flow can be generated from a number of parameters of any type
fun sendAnyNumber() = flowOf("one", "two", "three", 4, 5, 6)
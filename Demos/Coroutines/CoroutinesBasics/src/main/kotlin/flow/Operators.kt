package flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

// Operators take an input flow, transform it and return an output flow
// They are cold
// The returning flow is synchronous

fun main () {
    runBlocking {
        mapOperator()
        filterOperator()
        transformOperator()
        takeOperator()
        reduceOperator()
        flowOnOperator()
    }
}

// Map a flow to another flow
suspend fun mapOperator() {
    (1..10).asFlow()
        .map {
            delay(400L)
            "mapping $it"
        }
        .collect { println("mapOperator() -> $it") }
}

// Filter flow values
suspend fun filterOperator() {
    (1..10).asFlow()
        .filter { it % 2 == 0 }
        .collect { println("filterOperator() -> $it") }
}

// General transformation operator
// Can emit any value at any point
suspend fun transformOperator() {
    (1..10).asFlow()
        .transform {
            emit("Transform operator emitting $it")
            emit("transformOperator() -> $it")
        }
        .collect { println(it) }
}

// Use only a number of values, disregard the rest
suspend fun takeOperator() {
    (1..10).asFlow()
        .take(2)
        .collect { println("takeOperator() -> $it") }
}

// Terminal operators convert the flow into a collection
// collect, toList, toSet, reduce
suspend fun reduceOperator() {
    val num = 10
    val factorial = (1..num).asFlow()
        .reduce { accumulator, value -> accumulator * value }
    println("Factorial of $num is $factorial")
}

// Change the context on which the flow is emitted
suspend fun flowOnOperator() {
    (1..10).asFlow()
        .onEach {
            delay(400L)
            println("${Thread.currentThread().name} flowOnOperator() -> Emitting $it")
        }
        .flowOn(Dispatchers.IO)
        .collect { println("${Thread.currentThread().name} flowOnOperator() -> Collected $it") }
}
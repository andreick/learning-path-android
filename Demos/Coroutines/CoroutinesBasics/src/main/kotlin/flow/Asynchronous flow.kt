package flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

// A flow is a stream of values that are asynchronously computed
// flow { ... }    - builder
// emit(value)     - transmit value
// collect { ... } - receive value

fun main() {
    runBlocking {
        println("Receiving prime numbers")
        sendPrimes().collect { prime -> println("Received prime number: $prime") }
        println("Finished receiving number")
    }
}

fun sendPrimes() : Flow<Int> = flow {
    val primes = generateSequence(2 to generateSequence(3) { it + 2 }) { primePair ->
        val prime = primePair.second.first()
        prime to primePair.second.filter { it % prime != 0 }
    }.map { it.first }

    val primesList = primes.take(10)

    for (prime in primesList) {
        delay(prime * 100L)
        emit(prime)
    }
}
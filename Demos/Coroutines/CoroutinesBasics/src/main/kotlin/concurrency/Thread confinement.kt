package concurrency

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

// In thread confinement all access to shared data is confined to a single thread
// Fine-grained: each individual increment switches context - much slower
// Coarse-grained: the whole function is run on a single thread - faster

fun main() {
    runBlocking {
        val counterContext = newSingleThreadContext("CounterContext")
        var counter = 0
        println("Fine-grained")
        withContext(Dispatchers.Default) {
            massiveRun {
                withContext(counterContext) { counter++ }
            }
        }
        println("counter = $counter")

        counter = 0
        println("\nCoarse-grained")
        withContext(counterContext) {
            massiveRun { counter++ }
        }
        println("counter = $counter")
    }
}
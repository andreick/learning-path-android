package concurrency

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicInteger

// The easiest solution is to use an atomic variable for the counter
// Works well for primitive data types and collections

fun main() {
    runBlocking {
        val counter = AtomicInteger(0)
        withContext(Dispatchers.Default) { massiveRun { counter.incrementAndGet() } }
        println("counter = $counter")
    }
}
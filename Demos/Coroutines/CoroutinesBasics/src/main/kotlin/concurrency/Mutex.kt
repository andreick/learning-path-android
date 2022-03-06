package concurrency

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

// A mutual exclusion lock (mutex) locks access to a sensitive part of code
// A coroutine can lock and unlock the mutex
// withLock { ... } method provides both lock and unlock functionality

fun main() {
    runBlocking {
        val mutex = Mutex()
        var counter = 0
        withContext(Dispatchers.Default) {
            massiveRun {
                mutex.withLock { counter++ }
            }
        }
        println("counter = $counter")
    }
}
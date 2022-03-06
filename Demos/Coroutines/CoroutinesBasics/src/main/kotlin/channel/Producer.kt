package channel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking

// Channel produces allows a data source to create and return a channel
// CoroutineScore.produce { ... }
// Needs a coroutine scope

fun main() {
    runBlocking {
        produceSquares().consumeEach { println(it) }
    }
}

fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce {
    for (x in 1..5)
        send(x * x)
}
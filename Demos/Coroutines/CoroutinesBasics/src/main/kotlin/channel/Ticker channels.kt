package channel

import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// Periodically produces a Unit after a given delay
// Has an optional initial delay

fun main() {
    runBlocking {
        val tickerChannel = ticker(100L, 0L)
        launch {
            val startTime = System.currentTimeMillis()
            tickerChannel.consumeEach {
                val delta = System.currentTimeMillis() - startTime
                println("Received tick after $delta")
            }
        }

        delay(1000L)
        println("Done!")
        tickerChannel.cancel()
    }
}
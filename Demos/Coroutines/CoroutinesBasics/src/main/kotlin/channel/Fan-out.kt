package channel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// If multiple coroutines receive from the same channel,
// values (work) are distributed among then

fun main() {
    runBlocking {
        val producer = produceNumbersWithDelay()
        for (i in 1..5)
            launchProcessor(i, producer)
        delay(1500L)
        producer.cancel() // cancel producer coroutine and thus kill them all
    }
}

fun CoroutineScope.produceNumbersWithDelay() = produce {
    var x = 1
    while (true) {
        send(x++)
        delay(100L)
    }
}

fun CoroutineScope.launchProcessor(id: Int, channel: ReceiveChannel<Int>) =
    launch {
        for (message in channel)
            println("Processor $id received $message")
    }
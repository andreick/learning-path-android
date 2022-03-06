package channel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// A channel is a queue of data
// A coroutine can asynchronously put elements with .send(data)
// Another can blockingly get elements with .receive()
// A channel is closed when there are no more elements with .close()

fun main() {
    runBlocking {
        val channel = Channel<Int>()
        launch {
            for (x in 1..5) {
                channel.send(x * x)
            }
            channel.close()
        }

//        repeat(5) { println(channel.receive()) }

        for (i in channel)
            println(i)
    }
}
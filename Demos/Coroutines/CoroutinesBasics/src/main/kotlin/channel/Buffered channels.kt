package channel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// A buffered channel has a limited capacity
// Unlike unbuffered channels, allows senders to send multiple elements before suspending
// When the capacity is reached, the sender is paused
// When capacity becomes available, new values can be sent

fun main() {
    runBlocking {
        val channel = Channel<Int>(4)
        val sender = launch {
            repeat(10) {
                channel.send(it)
                println("Sent $it")
            }
        }

        repeat(3) {
            delay(1000L)
            println("Received ${channel.receive()}")
            println("Received ${channel.receive()}")
        }
        sender.cancel()
    }
}
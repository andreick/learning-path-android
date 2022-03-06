package channel

import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// Multiple coroutines can send values to the same channel

fun main() {
    runBlocking {
        val channel = Channel<String>()
        launch { channel.sendString("message1", 200L) }
        launch { channel.sendString("message2", 500L) }
        repeat(6) { println(channel.receive()) }
        coroutineContext.cancelChildren() // cancel all children to let main finish
    }
}

suspend fun SendChannel<String>.sendString(message: String, time: Long) {
    while (true) {
        delay(time)
        send(message)
    }
}
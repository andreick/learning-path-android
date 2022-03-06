package channel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking

// A pipeline is a development pattern where one channel
// output is given as an input to another channel

// One coroutine is producing a (potentially infinite) set of values

// One or more coroutines are consuming and transforming those values

fun main() {
    runBlocking {
        val numbers = produceNumbers()
        val squares = square(numbers)
        repeat(5) { println(squares.receive()) }
        println("Done!")
        coroutineContext.cancelChildren() // cancel all children to let main finish
    }
}

fun CoroutineScope.produceNumbers() = produce {
    var x = 1
    while (true)
        send(x++)
}

fun CoroutineScope.square(numbers: ReceiveChannel<Int>) = produce {
    for (x in numbers)
        send(x * x)
}
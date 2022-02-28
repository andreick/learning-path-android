import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// A context is a set of data that relates to the coroutine

// All coroutines have an associated context

// Important elements of a context:
// Dispatcher - which thread the coroutine is run on
// Job        - handle on the coroutine lifecycle

fun main() {
    runBlocking {
        // passing and accessing data from coroutine
        launch(CoroutineName("myCoroutine")) {
            println("This is run from ${coroutineContext[CoroutineName.Key]}")
        }
    }
}
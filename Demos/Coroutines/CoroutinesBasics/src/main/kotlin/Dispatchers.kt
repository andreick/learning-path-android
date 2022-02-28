import kotlinx.coroutines.*

// A dispatcher determines which thread or thread pool the coroutine runs on
// Different dispatchers are available depending on the task specificity

fun main() {
    runBlocking {

        // Main thread update in UI driven applications (e.g Android)
        // Main dispatcher needs to be defined in Gradle
//        launch(Dispatchers.Main) {
//            println("Main dispatcher. Thread: ${Thread.currentThread().name}")
//        }

        // Starts the coroutine in the inherited dispatcher that called it
        launch(Dispatchers.Unconfined) {
            println("Unconfined1. Thread: ${Thread.currentThread().name}")
            delay(100L)
            println("Unconfined2. Thread: ${Thread.currentThread().name}")
        }

        // Useful for CPU intensive work
        launch(Dispatchers.Default) {
            println("Default. Thread: ${Thread.currentThread().name}")
        }

        // Useful for network communication or reading/writing files
        launch(Dispatchers.IO) {
            println("IO. Thread: ${Thread.currentThread().name}")
        }

        // Forces creation of a new thread
        launch(newSingleThreadContext("MyThread")) {
            println("newSingleThreadContext. Thread: ${Thread.currentThread().name}")
        }
    }
}
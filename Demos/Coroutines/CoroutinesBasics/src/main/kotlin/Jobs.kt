import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// Allow us to manipulate the coroutine lifecycle

// A .launch() call returns a Job

// Live in the hierarchy of other jobs
// both as parents or children

// Can access lifecycle variables and methods:
// cancel() - cancel a job
// join() - wait until child coroutine completes (blocking call)

// If a job is cancelled, all its parents and children
// will be cancelled too

fun main() {
    runBlocking {
        val job1 = launch {
//            delay(3000L)
            println("Job1 launched")
            val job2 = launch {
                println("Job2 launched")
                delay(3000L)
                println("Job2 is finishing")
            }
            job2.invokeOnCompletion { println("Job2 completed") }

            val job3 = launch {
                println("Job3 launched")
                delay(3000L)
                println("Job3 is finishing")
            }
            job3.invokeOnCompletion { println("Job3 completed") }
        }

        // execute when the job is completed, whether it is cancelled or just finished
        job1.invokeOnCompletion { println("Job1 completed") }

        delay(500L)
        println("Job1 will be cancelled")
        job1.cancel()
    }
}
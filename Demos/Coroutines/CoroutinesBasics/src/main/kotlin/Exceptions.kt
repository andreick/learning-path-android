import kotlinx.coroutines.*
import java.lang.IndexOutOfBoundsException

// Exception behaviour depends on the coroutine builder

// launch
//  Propagates through the parent-child hierarchy
//  The exception will be thrown immediately and jobs will fail
//  use try-catch or an exception handler

// async
//  Exceptions are deferred util the result is consumed
//  If the result is not consumed, the exception is never thrown
//  use try-catch in the coroutine or in the await() call

fun main() {
    runBlocking {
        val myHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            println("Exception handled: ${throwable.localizedMessage}")
        }

        val job = GlobalScope.launch(myHandler) {
            println("Throwing exception from job")
            throw IndexOutOfBoundsException("exception in coroutine")
        }
        job.join()

        val deferred = GlobalScope.async {
            println("Throwing exception from async")
            throw ArithmeticException("exception from async")
        }

        try {
            deferred.await()
        } catch (e: java.lang.ArithmeticException) {
            println("Caught ArithmeticException ${e.localizedMessage}")
        }
    }
}
package gr.makris.chatapp.result

/**
 * This is a data class to use when the response of a datamanager call has a meaningful object to return using [value]
 * Or the [error] so that the application can know what went wrong
 */

open class Result<T> internal constructor(private val data: Any?) {
    companion object {
        fun <T> success(value: T): Result<T> = Result(value)
        fun <T> failure(exception: Throwable): Result<T> = Result(Failure(exception))
        fun <T> failure(error: String): Result<T> = Result(Failure(Throwable(error)))
        fun <T> failure(error: String, exception: Throwable): Result<T> = Result(Failure(Throwable(error, exception)))
        fun success(): Result<Unit> = Result(Unit)
    }

    val hasError
        get() = (data is Failure)

    //this can throw an exception if it has not failed
    @Suppress("UNCHECKED_CAST")
    val value: T
        get() = data as T

    //this can throw an exception if it has succeeded
    val errorMessage: String
        get() = (data as Failure).exception.message ?: data.exception::class.java.name

    val error: Throwable
        get() = (data as Failure).exception

    val getOrThrow: T
        get() {
            if (hasError)
                throw error
            else
                return value
        }

    fun getError(message: String): Throwable {
        return Throwable(message, error)
    }

    fun getOrDefault(def: T): T {
        if (hasError)
            return def
        return value
    }

    fun getOrThrow(message: String): T {
        if (hasError)
            throw Throwable(message, error)
        else
            return value
    }

    override fun toString(): String {
        return if (hasError) {
            "Error: $error"
        } else {
            "Success: $value"
        }
    }
}

private data class Failure(val exception: Throwable)
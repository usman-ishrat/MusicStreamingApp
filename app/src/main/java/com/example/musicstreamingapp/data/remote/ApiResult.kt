package com.example.musicstreamingapp.data.remote

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String, val cause: Throwable? = null) : ApiResult<Nothing>()
}

suspend fun <T> safeApiCall(block: suspend () -> T): ApiResult<T> {
    return try {
        ApiResult.Success(block())
    } catch (e: Exception) {
        val message = when {
            isNetworkUnavailable(e) ->
                "No internet connection. Check Wi‑Fi or mobile data on your device or emulator, then try again."
            e.message?.contains("timeout", ignoreCase = true) == true ->
                "Server is waking up. Please try again in a moment."
            else -> "Something went wrong. Please try again."
        }
        ApiResult.Error(message, e)
    }
}

private fun isNetworkUnavailable(e: Throwable): Boolean {
    var current: Throwable? = e
    while (current != null) {
        val msg = current.message.orEmpty()
        if (current is java.net.UnknownHostException ||
            msg.contains("Unable to resolve host", ignoreCase = true) ||
            msg.contains("Network is unreachable", ignoreCase = true) ||
            msg.contains("No address associated with hostname", ignoreCase = true)
        ) {
            return true
        }
        current = current.cause
    }
    return false
}

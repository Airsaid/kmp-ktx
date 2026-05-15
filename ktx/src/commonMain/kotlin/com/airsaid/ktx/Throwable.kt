package com.airsaid.ktx

/**
 * Executes the given [block] and calls [onFailure] if any [Throwable] was thrown,
 * returning the result of [block] or `null`.
 *
 * @param block the function to execute
 * @param onFailure the handler to call if an exception is thrown
 * @return the result of [block] or `null` if an exception was thrown
 */
inline fun <T> tryWithHandler(
  block: () -> T,
  onFailure: (Throwable) -> Unit
): T? {
  return try {
    block()
  } catch (e: Throwable) {
    onFailure(e)
    null
  }
}

/**
 * Executes the given [block] with comprehensive error handling and an optional finally block.
 *
 * The [onSuccess] callback is only invoked if [block] executes successfully and returns a non-null value.
 * The [onFailure] callback is invoked if any exception is thrown during [block] execution.
 * The [finally] callback is always invoked after [block] execution, regardless of success or failure.
 *
 * @param block the function to execute
 * @param onSuccess callback invoked with the result if [block] succeeds and returns non-null
 * @param onFailure callback invoked if an exception is thrown
 * @param finally callback that is always invoked after [block] execution
 * @return the result of [block] or `null` if an exception was thrown
 */
inline fun <T> tryWithCallbacks(
  block: () -> T?,
  onSuccess: (T) -> Unit = {},
  onFailure: (Throwable) -> Unit = {},
  finally: () -> Unit = {}
): T? {
  return try {
    val result = block()
    result?.let { onSuccess(it) }
    result
  } catch (e: Throwable) {
    onFailure(e)
    null
  } finally {
    finally()
  }
}

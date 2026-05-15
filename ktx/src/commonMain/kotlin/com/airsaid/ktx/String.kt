package com.airsaid.ktx

import kotlin.math.max
import kotlin.math.min

/**
 * Safely converts a nullable String to Long.
 *
 * Returns 0 if the string is null or cannot be parsed as a Long.
 *
 * @return The parsed Long value, or 0 if parsing fails
 * @see String.toLongOrNull
 */
fun String?.toLongSafe(): Long = this?.toLongOrNull() ?: 0L

/**
 * Safely converts a nullable String to Int.
 *
 * Returns 0 if the string is null or cannot be parsed as an Int.
 *
 * @return The parsed Int value, or 0 if parsing fails
 * @see String.toIntOrNull
 */
fun String?.toIntSafe(): Int = this?.toIntOrNull() ?: 0

/**
 * Safely converts a nullable String to Double.
 *
 * Returns 0.0 if the string is null or cannot be parsed as a Double.
 *
 * @return The parsed Double value, or 0.0 if parsing fails
 * @see String.toDoubleOrNull
 */
fun String?.toDoubleSafe(): Double = this?.toDoubleOrNull() ?: 0.0

/**
 * Safely converts a nullable String to Float.
 *
 * Returns 0f if the string is null or cannot be parsed as a Float.
 *
 * @return The parsed Float value, or 0f if parsing fails
 * @see String.toFloatOrNull
 */
fun String?.toFloatSafe(): Float = this?.toFloatOrNull() ?: 0f

/**
 * Returns a subsequence of this char sequence safely with bounds checking.
 *
 * The start index is clamped to 0 if negative, and the end index is clamped
 * to the string length if it exceeds it.
 *
 * @param start The starting index (inclusive)
 * @param end The ending index (exclusive)
 * @return A subsequence with adjusted bounds
 * @see String.subSequence
 */
fun String.subSequenceSafe(start: Int, end: Int): CharSequence {
  val adjustedStart = max(0, start)
  val adjustedEnd = min(length, max(adjustedStart, end))
  return subSequence(adjustedStart, adjustedEnd)
}

/**
 * Returns a substring of this string safely with bounds checking.
 *
 * The start index is clamped to 0 if negative, and the end index is clamped
 * to the string length if it exceeds it.
 *
 * @param startIndex The starting index (inclusive)
 * @param endIndex The ending index (exclusive)
 * @return A substring with adjusted bounds, or null if the string is null
 * @see String.substring
 */
fun String?.substringSafe(startIndex: Int, endIndex: Int = this?.length ?: 0): String? {
  if (this == null || isEmpty()) return this

  val adjustedStart = max(0, startIndex)
  val adjustedEnd = min(length, max(adjustedStart, endIndex))

  return substring(adjustedStart, adjustedEnd)
}

/**
 * Truncates the string to [maxLength] and appends [ellipsis] if truncated.
 *
 * If the string length is less than or equal to [maxLength], returns the original string.
 *
 * Example:
 * ```
 * "Hello World".ellipsize(8, "...") // returns "Hello..."
 * "Hi".ellipsize(8, "...")          // returns "Hi"
 * ```
 *
 * @param maxLength The maximum length of the result string (excluding ellipsis)
 * @param ellipsis The string to append when truncated (default: "...")
 * @return The truncated string with ellipsis, or the original if not truncated
 */
fun String.ellipsize(maxLength: Int, ellipsis: String = "..."): String {
  require(maxLength >= 0) { "maxLength must be non-negative" }

  return if (length <= maxLength) this else take(maxLength) + ellipsis
}

/**
 * Removes all non-ASCII characters from the string.
 *
 * Keeps only characters in the ASCII range (0x00-0x7F), excluding control
 * characters except for tab (\t).
 *
 * @return A string containing only ASCII characters, or null if input is null
 */
fun String?.excludeNonAscii(): String? {
  if (this == null || isEmpty()) return this

  return buildString {
    for (char in this@excludeNonAscii) {
      // Keep printable ASCII and tab
      if (char == '\t' || char in ' '..'~') {
        append(char)
      }
    }
  }
}

/**
 * Converts a URL query string to a Map of key-value pairs.
 *
 * Example:
 * ```
 * "key1=value1&key2=value2".toQueryMap() // returns {key1=value1, key2=value2}
 * ```
 *
 * @return A map of query parameters
 */
fun String.toQueryMap(): Map<String, String> {
  if (isEmpty()) return emptyMap()

  return split("&")
    .mapNotNull { pair ->
      val parts = pair.split("=", limit = 2)
      if (parts.size == 2 && parts[0].isNotEmpty() && parts[1].isNotEmpty()) {
        parts[0] to parts[1]
      } else null
    }
    .toMap()
}

/**
 * Returns this string if not null or empty, otherwise returns the result of [defaultValue].
 *
 * @param defaultValue A function that provides the default value
 * @return This string if not null or empty, otherwise the default value
 */
inline fun String?.ifNullOrEmpty(defaultValue: () -> String): String =
  if (isNullOrEmpty()) defaultValue() else this

/**
 * Executes [action] if this string is not null and not empty.
 *
 * @param action The action to execute with the non-null, non-empty string
 */
inline fun String?.ifNotNullNorEmpty(action: (String) -> Unit) {
  if (!isNullOrEmpty()) action(this)
}

/**
 * Removes a prefix from the string if present, case-insensitive.
 *
 * @param prefix The prefix to remove
 * @return The string without the prefix, or the original string if prefix not found
 * @see String.removePrefix
 */
fun String.removePrefixIgnoreCase(prefix: String): String =
  if (startsWith(prefix, ignoreCase = true)) {
    substring(prefix.length)
  } else {
    this
  }

/**
 * Removes a suffix from the string if present, case-insensitive.
 *
 * @param suffix The suffix to remove
 * @return The string without the suffix, or the original string if suffix not found
 * @see String.removeSuffix
 */
fun String.removeSuffixIgnoreCase(suffix: String): String =
  if (endsWith(suffix, ignoreCase = true)) {
    substring(0, length - suffix.length)
  } else {
    this
  }

/**
 * Checks if the string contains only digits.
 *
 * Returns `false` for empty strings.
 *
 * @return `true` if all characters are digits, `false` otherwise
 */
fun String.isDigitsOnly(): Boolean = isNotEmpty() && all { it.isDigit() }

/**
 * Checks if the string contains only letters.
 *
 * Returns `false` for empty strings.
 *
 * @return `true` if all characters are letters, `false` otherwise
 */
fun String.isLettersOnly(): Boolean = isNotEmpty() && all { it.isLetter() }

/**
 * Checks if the string contains only alphanumeric characters.
 *
 * Returns `false` for empty strings.
 *
 * @return `true` if all characters are letters or digits, `false` otherwise
 */
fun String.isLetterOrDigitOnly(): Boolean = isNotEmpty() && all { it.isLetterOrDigit() }

/**
 * Counts the occurrences of a substring in this string.
 *
 * @param substring The substring to count
 * @param ignoreCase `true` to ignore character case when matching
 * @return The number of non-overlapping occurrences
 */
fun String.countOccurrences(substring: String, ignoreCase: Boolean = false): Int {
  if (substring.isEmpty()) return 0

  var count = 0
  var index = 0

  while (index < length) {
    val foundIndex = indexOf(substring, index, ignoreCase)
    if (foundIndex == -1) break
    count++
    index = foundIndex + substring.length
  }

  return count
}

/**
 * Wraps the string with the specified [wrapper] string on both sides.
 *
 * Example:
 * ```
 * "hello".wrap("*") // returns "*hello*"
 * "test".wrap("--") // returns "--test--"
 * ```
 *
 * @param wrapper The string to wrap with
 * @return The wrapped string
 */
fun String.wrap(wrapper: String): String = "$wrapper$this$wrapper"

/**
 * Wraps the string with [prefix] and [suffix].
 *
 * Example:
 * ```
 * "hello".wrap("[", "]") // returns "[hello]"
 * ```
 *
 * @param prefix The prefix to add
 * @param suffix The suffix to add
 * @return The wrapped string
 */
fun String.wrap(prefix: String, suffix: String): String = "$prefix$this$suffix"

/**
 * Returns the string repeated [n] times with [separator] between repetitions.
 *
 * Example:
 * ```
 * "ab".repeatWithSeparator(3, "-") // returns "ab-ab-ab"
 * ```
 *
 * @param n The number of repetitions
 * @param separator The separator between repetitions
 * @return The repeated string with separators
 * @throws IllegalArgumentException if [n] is negative
 */
@Throws(IllegalArgumentException::class)
fun String.repeatWithSeparator(n: Int, separator: String = ""): String {
  require(n >= 0) { "Count must be non-negative, but was $n" }

  return when (n) {
    0 -> ""
    1 -> this
    else -> (1..n).joinToString(separator) { this }
  }
}

/**
 * Ensures the string starts with the specified [prefix].
 *
 * If the string already starts with the prefix, returns the original string.
 *
 * @param prefix The prefix to ensure
 * @param ignoreCase `true` to ignore character case when checking
 * @return The string with the prefix
 */
fun String.ensurePrefix(prefix: String, ignoreCase: Boolean = false): String =
  if (startsWith(prefix, ignoreCase)) this else "$prefix$this"

/**
 * Ensures the string ends with the specified [suffix].
 *
 * If the string already ends with the suffix, returns the original string.
 *
 * @param suffix The suffix to ensure
 * @param ignoreCase `true` to ignore character case when checking
 * @return The string with the suffix
 */
fun String.ensureSuffix(suffix: String, ignoreCase: Boolean = false): String =
  if (endsWith(suffix, ignoreCase)) this else "$this$suffix"

/**
 * Returns the string between the first occurrence of [start] and [end].
 *
 * Returns null if either delimiter is not found.
 *
 * Example:
 * ```
 * "Hello [World]!".substringBetween("[", "]") // returns "World"
 * ```
 *
 * @param start The starting delimiter
 * @param end The ending delimiter
 * @return The substring between delimiters, or null if not found
 */
fun String.substringBetween(start: String, end: String): String? {
  val startIndex = indexOf(start)
  if (startIndex == -1) return null

  val endIndex = indexOf(end, startIndex + start.length)
  if (endIndex == -1) return null

  return substring(startIndex + start.length, endIndex)
}

/**
 * Capitalizes the first character of each word in the string.
 *
 * Words are separated by whitespace characters.
 *
 * Example:
 * ```
 * "hello world kotlin".capitalizeWords() // returns "Hello World Kotlin"
 * ```
 *
 * @return The string with capitalized words
 * @see String.capitalize
 */
fun String.capitalizeWords(): String =
  split("\\s+".toRegex())
    .joinToString(" ") { word ->
      word.lowercase().replaceFirstChar {
        if (it.isLowerCase()) it.titlecase() else it.toString()
      }
    }

/**
 * Reverses the case of all letters in the string.
 *
 * Example:
 * ```
 * "Hello World".swapCase() // returns "hELLO wORLD"
 * ```
 *
 * @return The string with swapped case
 */
fun String.swapCase(): String = map { char ->
  when {
    char.isUpperCase() -> char.lowercase()
    char.isLowerCase() -> char.uppercase()
    else -> char.toString()
  }
}.joinToString("")

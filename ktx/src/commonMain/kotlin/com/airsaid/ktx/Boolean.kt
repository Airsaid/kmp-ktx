package com.airsaid.ktx

/**
 * Returns one of two values based on the Boolean value.
 *
 * This generic version replaces multiple overloaded functions and supports any type.
 *
 * @param T The type of values to choose from
 * @param trueValue The value to return when the Boolean is true
 * @param falseValue The value to return when the Boolean is false
 * @return [trueValue] if this Boolean is true, [falseValue] otherwise
 *
 * Example:
 * ```
 * val result = someBoolean.select(100, 200) // Returns 100 if true, 200 if false
 * val text = isValid.select("Valid", "Invalid")
 * ```
 */
fun <T> Boolean.select(trueValue: T, falseValue: T): T {
  return if (this) trueValue else falseValue
}

/**
 * Returns false if the Boolean is null, otherwise returns its value.
 *
 * @return The Boolean value, or false if null
 */
fun Boolean?.orFalse(): Boolean = this ?: false

/**
 * Returns true if the Boolean is null, otherwise returns its value.
 *
 * @return The Boolean value, or true if null
 */
fun Boolean?.orTrue(): Boolean = this ?: true

/**
 * Converts a nullable Boolean to binary representation.
 *
 * @return 1 if the Boolean is true, 0 if null or false
 */
fun Boolean?.toBinary(): Int = if (this == true) 1 else 0

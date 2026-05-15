package com.airsaid.ktx

/**
 * Safely converts any nullable object to an [Int] value.
 *
 * @param default the value to return if conversion fails or receiver is null. Defaults to 0.
 * @return the converted Int value, or [default] if conversion is not possible
 */
fun Any?.toIntSafe(default: Int = 0) = if (this == null) default else toString().toIntOrNull() ?: default

/**
 * Safely converts any nullable object to a [Float] value.
 *
 * @param default the value to return if conversion fails or receiver is null. Defaults to 0F.
 * @return the converted Float value, or [default] if conversion is not possible
 */
fun Any?.toFloatSafe(default: Float = 0F) = if (this == null) default else toString().toFloatOrNull() ?: default

/**
 * Safely converts any nullable object to a [Double] value.
 *
 * @param default the value to return if conversion fails or receiver is null. Defaults to 0.0.
 * @return the converted Double value, or [default] if conversion is not possible
 */
fun Any?.toDoubleSafe(default: Double = 0.0) = if (this == null) default else toString().toDoubleOrNull() ?: default

/**
 * Safely converts any nullable object to a [Long] value.
 *
 * @param default the value to return if conversion fails or receiver is null. Defaults to 0L.
 * @return the converted Long value, or [default] if conversion is not possible
 */
fun Any?.toLongSafe(default: Long = 0L) = if (this == null) default else toString().toLongOrNull() ?: default

/**
 * Safely converts any nullable object to a [Boolean] value using strict parsing.
 *
 * Only "true" and "false" strings are considered valid boolean values.
 *
 * @param default the value to return if conversion fails or receiver is null. Defaults to false.
 * @return the converted Boolean value, or [default] if conversion is not possible
 */
fun Any?.toBooleanSafe(default: Boolean = false) =
  if (this == null) default else toString().toBooleanStrictOrNull() ?: default

/**
 * Safely converts any nullable object to a [ULong] value.
 *
 * @param default the value to return if conversion fails or receiver is null. Defaults to 0UL.
 * @return the converted ULong value, or [default] if conversion is not possible
 */
fun Any?.toULongSafe(default: ULong = 0UL) = if (this == null) default else toString().toULongOrNull() ?: default

/**
 * Safely converts any nullable object to a [String] value.
 *
 * @param default the value to return if receiver is null. Defaults to empty string.
 * @return the String representation of the receiver, or [default] if receiver is null
 */
fun Any?.toStringSafe(default: String = "") = this?.toString() ?: default

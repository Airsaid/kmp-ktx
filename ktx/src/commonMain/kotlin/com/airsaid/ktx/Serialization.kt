package com.airsaid.ktx

import kotlinx.serialization.SerializationException
import kotlinx.serialization.serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.floatOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.longOrNull

/**
 * Default Json configuration for serialization/deserialization operations.
 */
val JsonUtil = Json {
  ignoreUnknownKeys = true
  coerceInputValues = true
  encodeDefaults = false
  explicitNulls = false
}

/**
 * Singleton instance of an empty JsonArray to avoid repeated allocations.
 */
val emptyJsonArray = JsonArray(emptyList())

/**
 * Returns this [JsonArray] if it's not null, or an empty JsonArray if it is null.
 */
fun JsonArray?.orEmpty() = this ?: emptyJsonArray

/**
 * Parses this String as a [JsonObject].
 *
 * @throws SerializationException if the string cannot be parsed as a valid JsonObject
 * @throws ClassCastException if the parsed element is not a JsonObject
 * @return The parsed JsonObject
 */
@Throws(SerializationException::class, ClassCastException::class)
fun String.toJsonObject() = JsonUtil.parseToJsonElement(this) as JsonObject

/**
 * Parses this String as a [JsonArray].
 *
 * @throws SerializationException if the string cannot be parsed as a valid JsonArray
 * @throws ClassCastException if the parsed element is not a JsonArray
 * @return The parsed JsonArray
 */
@Throws(SerializationException::class, ClassCastException::class)
fun String.toJsonArray() = JsonUtil.parseToJsonElement(this) as JsonArray

/**
 * Safely parses this String as a [JsonObject].
 *
 * @return The parsed JsonObject, or null if parsing fails
 */
fun String.toJsonObjectSafe() = try {
  toJsonObject()
} catch (_: Exception) {
  null
}

/**
 * Safely parses this String as a [JsonArray].
 *
 * @return The parsed JsonArray, or null if parsing fails
 */
fun String.toJsonArraySafe() = try {
  toJsonArray()
} catch (_: Exception) {
  null
}

/**
 * Serializes this object to a JSON string.
 *
 * @throws SerializationException if the object cannot be serialized
 * @throws IllegalArgumentException if the serializer for [T] cannot be found
 * @return The JSON string representation
 */
@Throws(SerializationException::class, IllegalArgumentException::class)
inline fun <reified T : Any> T.toJson(): String = JsonUtil.encodeToString(serializer(), this)

/**
 * Deserializes this JSON string to an object of type [T].
 *
 * @throws SerializationException if the string cannot be deserialized
 * @throws IllegalArgumentException if the serializer for [T] cannot be found
 * @return The deserialized object
 */
@Throws(SerializationException::class, IllegalArgumentException::class)
inline fun <reified T : Any> String.toObject(): T = JsonUtil.decodeFromString<T>(this)

/**
 * Deserializes this JSON string to a List of type [T].
 *
 * @throws SerializationException if the string cannot be deserialized
 * @throws IllegalArgumentException if the serializer for [T] cannot be found
 * @return The deserialized list
 */
@Throws(SerializationException::class, IllegalArgumentException::class)
inline fun <reified T> String.toObjectList(): List<T> = JsonUtil.decodeFromString<List<T>>(this)

/**
 * Safely serializes this object to a JSON string.
 *
 * @return The JSON string representation, or null if serialization fails or object is null
 */
inline fun <reified T : Any> T?.toJsonSafe(): String? = when (val value = this) {
  null -> null
  else -> try {
    JsonUtil.encodeToString(serializer(), value)
  } catch (_: Exception) {
    null
  }
}

/**
 * Safely deserializes this JSON string to an object of type [T].
 *
 * @return The deserialized object, or null if deserialization fails or string is null/empty
 */
inline fun <reified T> String?.toObjectSafe(): T? = when {
  isNullOrEmpty() -> null
  else -> try {
    JsonUtil.decodeFromString<T>(this)
  } catch (_: Exception) {
    null
  }
}

/**
 * Safely deserializes this JSON string to an Array of type [T].
 *
 * @return The deserialized array, or null if deserialization fails or string is null/empty
 */
inline fun <reified T> String?.toArraySafe(): Array<T>? = when {
  isNullOrEmpty() -> null
  else -> try {
    JsonUtil.decodeFromString<Array<T>>(this)
  } catch (_: Exception) {
    null
  }
}

/**
 * Safely deserializes this JSON string to a List of type [T].
 *
 * @return The deserialized list, or null if deserialization fails or string is null/empty
 */
inline fun <reified T> String?.toListSafe(): List<T>? = when {
  isNullOrEmpty() -> null
  else -> try {
    JsonUtil.decodeFromString<List<T>>(this)
  } catch (_: Exception) {
    null
  }
}

/**
 * Gets an Int value from this JsonObject for the given key.
 *
 * @param key The key to look up
 * @param defaultValue The default value if key doesn't exist or value is not an Int
 * @return The Int value, or defaultValue if not found or type mismatch
 */
fun JsonObject.getInt(key: String, defaultValue: Int = 0): Int {
  val element = this[key] as? JsonPrimitive ?: return defaultValue
  return element.intOrNull ?: defaultValue
}

/**
 * Gets a nullable Int value from this JsonObject for the given key.
 *
 * @param key The key to look up
 * @return The Int value, or null if not found or type mismatch
 */
fun JsonObject.getIntOrNull(key: String): Int? {
  return (this[key] as? JsonPrimitive)?.intOrNull
}

/**
 * Gets a Float value from this JsonObject for the given key.
 *
 * @param key The key to look up
 * @param defaultValue The default value if key doesn't exist or value is not a Float
 * @return The Float value, or defaultValue if not found or type mismatch
 */
fun JsonObject.getFloat(key: String, defaultValue: Float = 0f): Float {
  val element = this[key] as? JsonPrimitive ?: return defaultValue
  return element.floatOrNull ?: defaultValue
}

/**
 * Gets a nullable Float value from this JsonObject for the given key.
 *
 * @param key The key to look up
 * @return The Float value, or null if not found or type mismatch
 */
fun JsonObject.getFloatOrNull(key: String): Float? {
  return (this[key] as? JsonPrimitive)?.floatOrNull
}

/**
 * Gets a Double value from this JsonObject for the given key.
 *
 * @param key The key to look up
 * @param defaultValue The default value if key doesn't exist or value is not a Double
 * @return The Double value, or defaultValue if not found or type mismatch
 */
fun JsonObject.getDouble(key: String, defaultValue: Double = 0.0): Double {
  val element = this[key] as? JsonPrimitive ?: return defaultValue
  return element.doubleOrNull ?: defaultValue
}

/**
 * Gets a nullable Double value from this JsonObject for the given key.
 *
 * @param key The key to look up
 * @return The Double value, or null if not found or type mismatch
 */
fun JsonObject.getDoubleOrNull(key: String): Double? {
  return (this[key] as? JsonPrimitive)?.doubleOrNull
}

/**
 * Gets a Long value from this JsonObject for the given key.
 *
 * @param key The key to look up
 * @param defaultValue The default value if key doesn't exist or value is not a Long
 * @return The Long value, or defaultValue if not found or type mismatch
 */
fun JsonObject.getLong(key: String, defaultValue: Long = 0L): Long {
  val element = this[key] as? JsonPrimitive ?: return defaultValue
  return element.longOrNull ?: defaultValue
}

/**
 * Gets a nullable Long value from this JsonObject for the given key.
 *
 * @param key The key to look up
 * @return The Long value, or null if not found or type mismatch
 */
fun JsonObject.getLongOrNull(key: String): Long? {
  return (this[key] as? JsonPrimitive)?.longOrNull
}

/**
 * Gets a Boolean value from this JsonObject for the given key.
 *
 * @param key The key to look up
 * @param defaultValue The default value if key doesn't exist or value is not a Boolean
 * @return The Boolean value, or defaultValue if not found or type mismatch
 */
fun JsonObject.getBoolean(key: String, defaultValue: Boolean = false): Boolean {
  val element = this[key] as? JsonPrimitive ?: return defaultValue
  return element.booleanOrNull ?: defaultValue
}

/**
 * Gets a nullable Boolean value from this JsonObject for the given key.
 *
 * @param key The key to look up
 * @return The Boolean value, or null if not found or type mismatch
 */
fun JsonObject.getBooleanOrNull(key: String): Boolean? {
  return (this[key] as? JsonPrimitive)?.booleanOrNull
}

/**
 * Gets a String value from this JsonObject for the given key.
 *
 * @param key The key to look up
 * @param defaultValue The default value if key doesn't exist or value is not a String
 * @return The String value, or defaultValue if not found
 */
fun JsonObject.getString(key: String, defaultValue: String = ""): String {
  val element = this[key] as? JsonPrimitive ?: return defaultValue
  return if (element.isString) element.content else defaultValue
}

/**
 * Gets a nullable String value from this JsonObject for the given key.
 *
 * @param key The key to look up
 * @return The String value, or null if not found
 */
fun JsonObject.getStringOrNull(key: String): String? {
  val element = this[key] as? JsonPrimitive ?: return null
  return if (element.isString) element.content else null
}

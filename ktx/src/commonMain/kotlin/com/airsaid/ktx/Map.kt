package com.airsaid.ktx

/**
 * Inverts the key-value pairs of this map.
 *
 * If there are duplicate values in the original map, only the last occurrence will be kept.
 *
 * @return a new map with the values as keys and keys as values
 */
fun <K, V> Map<K, V>.invert(): Map<V, K> {
  return entries.associate { (k, v) -> v to k }
}

/**
 * Inverts the key-value pairs of this map, grouping keys by their values.
 *
 * Unlike [invert], this function preserves all key-value relationships when values are duplicated.
 *
 * @return a new map where original values become keys mapped to lists of original keys
 */
fun <K, V> Map<K, V>.invertGrouping(): Map<V, List<K>> {
  return entries.groupBy({ it.value }, { it.key })
}

/**
 * Merges this map with another map using a custom merge function for conflicting keys.
 *
 * @param other the map to merge with this map
 * @param merger function to resolve conflicts when the same key exists in both maps
 * @return a new map containing all entries from both maps with conflicts resolved by merger
 */
fun <K, V> Map<K, V>.mergeWith(other: Map<K, V>, merger: (V, V) -> V): Map<K, V> {
  val result = this.toMutableMap()
  other.forEach { (key, value) ->
    result[key] = if (key in result) {
      merger(result.getValue(key), value)
    } else {
      value
    }
  }
  return result
}

/**
 * Returns a map containing only entries that do not exist in the specified map.
 *
 * @param other the map to subtract from this map
 * @return a new map with entries that exist in this map but not in other
 */
fun <K, V> Map<K, V>.subtract(other: Map<K, V>): Map<K, V> {
  return filterNot { (key, value) -> other[key] == value }
}

/**
 * Returns the key associated with the first occurrence of the specified value.
 *
 * @param value the value to search for
 * @return the key associated with the value, or null if not found
 */
fun <K, V> Map<K, V>.keyOf(value: V): K? {
  return entries.firstOrNull { it.value == value }?.key
}

/**
 * Returns all keys associated with the specified value.
 *
 * @param value the value to search for
 * @return a set of keys that map to the specified value
 */
fun <K, V> Map<K, V>.keysOf(value: V): Set<K> {
  return filterValues { it == value }.keys
}

/**
 * Returns a new map with entries sorted by values.
 *
 * @return a new map sorted by values in natural order
 */
fun <K, V : Comparable<V>> Map<K, V>.sortedByValues(): Map<K, V> {
  return entries.sortedBy { it.value }.associate { it.key to it.value }
}

/**
 * Returns a new map with entries sorted by values using the provided comparator.
 *
 * @param comparator the comparator to use for sorting values
 * @return a new map sorted by values
 */
fun <K, V> Map<K, V>.sortedByValues(comparator: Comparator<V>): Map<K, V> {
  return entries.sortedWith(compareBy(comparator) { it.value }).associate { it.key to it.value }
}

/**
 * Returns a map containing all entries from this map except those with the specified keys.
 *
 * @param keys the keys to exclude
 * @return a new map without the specified keys
 */
fun <K, V> Map<K, V>.except(vararg keys: K): Map<K, V> {
  return filterKeys { it !in keys }
}

/**
 * Returns a map containing only entries with the specified keys.
 *
 * @param keys the keys to include
 * @return a new map with only the specified keys
 */
fun <K, V> Map<K, V>.only(vararg keys: K): Map<K, V> {
  return filterKeys { it in keys }
}

/**
 * Checks if this map contains all key-value pairs from the other map.
 *
 * @param other the map to check against
 * @return true if this map contains all entries from other
 * @see Map.containsKey
 * @see Map.containsValue
 */
fun <K, V> Map<K, V>.containsAll(other: Map<K, V>): Boolean {
  return other.all { (key, value) -> this[key] == value }
}

/**
 * Returns a string representation of this map with custom separators.
 *
 * @param entrySeparator separator between entries
 * @param keyValueSeparator separator between key and value
 * @param prefix string to prepend
 * @param postfix string to append
 * @return formatted string representation
 */
fun <K, V> Map<K, V>.joinToString(
  entrySeparator: String = ", ",
  keyValueSeparator: String = "=",
  prefix: String = "{",
  postfix: String = "}"
): String {
  return entries.joinToString(
    separator = entrySeparator,
    prefix = prefix,
    postfix = postfix
  ) { (key, value) -> "$key$keyValueSeparator$value" }
}

/**
 * Combines this map with another map, with entries from the other map overwriting existing ones.
 *
 * @param other the map to combine with this map
 * @return a new combined map or null if both maps are null
 */
fun <K, V> Map<K, V>?.combine(other: Map<K, V>?): Map<K, V>? {
  if (this == null) return other
  if (other == null) return this
  return this + other
}

/**
 * Deeply merges this map with another map, recursively merging nested maps.
 *
 * @param other the map to merge with
 * @return a new deeply merged map
 */
@Suppress("UNCHECKED_CAST")
fun Map<String, Any?>.deepMerge(other: Map<String, Any?>): Map<String, Any?> {
  val result = this.toMutableMap()
  other.forEach { (key, value) ->
    val existing = result[key]
    result[key] = when {
      existing is Map<*, *> && value is Map<*, *> -> {
        (existing as Map<String, Any?>).deepMerge(value as Map<String, Any?>)
      }

      else -> value
    }
  }
  return result
}

/**
 * Flattens a nested map structure into a single-level map with composite keys.
 *
 * @param separator the separator to use for composite keys
 * @return a flattened map
 */
@Suppress("UNCHECKED_CAST")
fun Map<String, Any?>.flatten(separator: String = "."): Map<String, Any?> {
  val result = mutableMapOf<String, Any?>()

  fun flattenRecursive(map: Map<String, Any?>, prefix: String = "") {
    map.forEach { (key, value) ->
      val newKey = if (prefix.isEmpty()) key else "$prefix$separator$key"
      when (value) {
        is Map<*, *> -> flattenRecursive(value as Map<String, Any?>, newKey)
        else -> result[newKey] = value
      }
    }
  }

  flattenRecursive(this)
  return result
}

/**
 * Returns a new map containing only non-null values.
 *
 * @return a map without null values
 */
fun <K, V> Map<K, V?>.filterNotNullValues(): Map<K, V> {
  @Suppress("UNCHECKED_CAST")
  return filterValues { it != null } as Map<K, V>
}

/**
 * Transforms this map into a map of maps, grouping entries by a key selector.
 *
 * @param keySelector function to extract the grouping key from each entry
 * @return a map where each key maps to a map of original entries
 */
inline fun <K, V, G> Map<K, V>.groupByKey(keySelector: (Map.Entry<K, V>) -> G): Map<G, Map<K, V>> {
  return entries.groupBy(keySelector).mapValues { (_, entries) ->
    entries.associate { it.key to it.value }
  }
}

/**
 * Returns a map containing entries from this map whose keys are instances of the specified type.
 *
 * @param R the type to filter keys by
 * @return a map with keys of the specified type
 */
inline fun <reified R, V> Map<*, V>.filterKeysIsInstance(): Map<R, V> {
  return filterKeys { it is R }.mapKeys { it.key as R }
}

/**
 * Returns a map containing entries from this map whose values are instances of the specified type.
 *
 * @param R the type to filter values by
 * @return a map with values of the specified type
 */
inline fun <K, reified R> Map<K, *>.filterValuesIsInstance(): Map<K, R> {
  return filterValues { it is R }.mapValues { it.value as R }
}

/**
 * Returns the value for the given key if it exists and is of the specified type.
 *
 * @param key the key to look up
 * @return the value cast to type R, or null if not found or wrong type
 */
inline fun <reified R> Map<*, *>.getAs(key: Any?): R? {
  val value = get(key)
  return value as? R
}

/**
 * Returns a map with entries having distinct values.
 *
 * If there are duplicate values, only the first occurrence is kept.
 *
 * @return a map with unique values
 */
fun <K, V> Map<K, V>.distinctByValue(): Map<K, V> {
  val seenValues = mutableSetOf<V>()
  return filter { (_, value) ->
    seenValues.add(value)
  }
}

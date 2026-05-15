package com.airsaid.ktx

/**
 * Removes all entries matching the given predicate from this mutable map.
 *
 * @param predicate function to test each entry
 * @return the number of entries removed
 */
inline fun <K, V> MutableMap<K, V>.removeAll(predicate: (Map.Entry<K, V>) -> Boolean): Int {
  val entries = entries
  if (entries.isEmpty()) return 0

  var removed = 0
  val iterator = entries.iterator()
  while (iterator.hasNext()) {
    if (predicate(iterator.next())) {
      iterator.remove()
      removed++
    }
  }
  return removed
}

/**
 * Removes and returns the first entry matching the given predicate.
 *
 * @param predicate function to test each entry
 * @return the removed entry or null if no matching entry was found
 */
inline fun <K, V> MutableMap<K, V>.removeFirst(predicate: (Map.Entry<K, V>) -> Boolean): Map.Entry<K, V>? {
  val entries = entries
  if (entries.isEmpty()) return null

  val iterator = entries.iterator()
  while (iterator.hasNext()) {
    val entry = iterator.next()
    if (predicate(entry)) {
      // Create a copy before removing since the entry might be invalidated
      val result = entry.key to entry.value
      iterator.remove()
      return object : Map.Entry<K, V> {
        override val key: K = result.first
        override val value: V = result.second
      }
    }
  }
  return null
}

/**
 * Puts the key-value pair into this map if value is not null, otherwise removes the key.
 *
 * @param key the key to put or remove
 * @param value the value to associate with the key, or null to remove the key
 */
fun <K, V> MutableMap<K, V>.putOrRemove(key: K, value: V?) {
  if (value == null) {
    remove(key)
  } else {
    put(key, value)
  }
}

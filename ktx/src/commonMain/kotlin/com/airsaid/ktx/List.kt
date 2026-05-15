package com.airsaid.ktx

/**
 * Returns a list containing only elements at even indices.
 *
 * @return a list of elements at positions 0, 2, 4, etc.
 */
fun <T> List<T>.evenIndexed(): List<T> =
  filterIndexed { index, _ -> index % 2 == 0 }

/**
 * Returns a list containing only elements at odd indices.
 *
 * @return a list of elements at positions 1, 3, 5, etc.
 */
fun <T> List<T>.oddIndexed(): List<T> =
  filterIndexed { index, _ -> index % 2 != 0 }

/**
 * Splits the list into chunks of the specified size, processing from the end.
 * The first chunk may contain fewer elements if the list size is not divisible by chunk size.
 *
 * Similar to [chunked], but processes from the end of the list.
 *
 * @param size the maximum size of each chunk
 * @return a list of chunks
 * @throws IllegalArgumentException if [size] is not positive
 *
 * @see chunked for chunking from the start
 * @see windowed for sliding window operations
 *
 * Example:
 * ```
 * listOf(1, 2, 3, 4, 5).chunkFromEnd(2) // [[1], [2, 3], [4, 5]]
 * ```
 */
@Throws(IllegalArgumentException::class)
fun <T> List<T>.chunkFromEnd(size: Int): List<List<T>> {
  require(size > 0) { "Size must be positive, was: $size" }
  if (isEmpty()) return emptyList()

  val result = mutableListOf<List<T>>()
  val remainder = this.size % size

  if (remainder != 0) {
    result.add(take(remainder))
  }

  for (i in remainder until this.size step size) {
    result.add(subList(i, minOf(i + size, this.size)))
  }

  return result
}

/**
 * Returns the second element in the list.
 *
 * @return the second element
 * @throws NoSuchElementException if the list has fewer than 2 elements
 *
 * @see first
 * @see last
 */
@Throws(NoSuchElementException::class)
fun <T> List<T>.second(): T {
  if (size < 2) throw NoSuchElementException("List has fewer than 2 elements")
  return this[1]
}

/**
 * Returns the second element in the list, or null if the list has fewer than 2 elements.
 *
 * @see firstOrNull
 * @see lastOrNull
 */
fun <T> List<T>.secondOrNull(): T? = if (size >= 2) this[1] else null

/**
 * Returns the third element in the list.
 *
 * @return the third element
 * @throws NoSuchElementException if the list has fewer than 3 elements
 */
@Throws(NoSuchElementException::class)
fun <T> List<T>.third(): T {
  if (size < 3) throw NoSuchElementException("List has fewer than 3 elements")
  return this[2]
}

/**
 * Returns the third element in the list, or null if the list has fewer than 3 elements.
 */
fun <T> List<T>.thirdOrNull(): T? = if (size >= 3) this[2] else null

/**
 * Rotates the list to the left by the specified number of positions.
 *
 * @param n the number of positions to rotate
 * @return a new list with elements rotated left
 *
 * Example:
 * ```
 * listOf(1, 2, 3, 4, 5).rotateLeft(2) // [3, 4, 5, 1, 2]
 * ```
 */
fun <T> List<T>.rotateLeft(n: Int): List<T> {
  if (isEmpty()) return this
  val normalizedN = ((n % size) + size) % size
  if (normalizedN == 0) return this.toList()
  return drop(normalizedN) + take(normalizedN)
}

/**
 * Rotates the list to the right by the specified number of positions.
 *
 * @param n the number of positions to rotate
 * @return a new list with elements rotated right
 *
 * Example:
 * ```
 * listOf(1, 2, 3, 4, 5).rotateRight(2) // [4, 5, 1, 2, 3]
 * ```
 */
fun <T> List<T>.rotateRight(n: Int): List<T> {
  if (isEmpty()) return this
  val normalizedN = ((n % size) + size) % size
  if (normalizedN == 0) return this.toList()
  return takeLast(normalizedN) + dropLast(normalizedN)
}

/**
 * Returns a list containing all elements except the specified element.
 * Only the first occurrence is removed if the element appears multiple times.
 *
 * @param element the element to exclude
 * @return a new list without the first occurrence of the element
 */
fun <T> List<T>.without(element: T): List<T> {
  val index = indexOf(element)
  return if (index == -1) this.toList()
  else filterIndexed { i, _ -> i != index }
}

/**
 * Returns a list containing all elements except the specified elements.
 *
 * @param elements the elements to exclude
 * @return a new list without any of the specified elements
 */
fun <T> List<T>.withoutAll(vararg elements: T): List<T> =
  filterNot { it in elements }

/**
 * Returns a list containing all elements except those in the specified collection.
 *
 * @param elements the collection of elements to exclude
 * @return a new list without any of the specified elements
 */
fun <T> List<T>.withoutAll(elements: Collection<T>): List<T> =
  filterNot { it in elements }

/**
 * Finds the middle element(s) of the list.
 * Returns a single element for odd-sized lists, or two elements for even-sized lists.
 *
 * @return a list containing the middle element(s)
 * @throws NoSuchElementException if the list is empty
 */
@Throws(NoSuchElementException::class)
fun <T> List<T>.middle(): List<T> {
  if (isEmpty()) throw NoSuchElementException("List is empty")
  val mid = size / 2
  return if (size % 2 == 0) {
    listOf(this[mid - 1], this[mid])
  } else {
    listOf(this[mid])
  }
}

/**
 * Returns true if the list contains duplicates.
 */
fun <T> List<T>.containsDuplicates(): Boolean = size != toSet().size

/**
 * Returns a list of all duplicate elements.
 * Each duplicate appears only once in the result.
 *
 * @return a list of elements that appear more than once in the original list
 */
fun <T> List<T>.duplicates(): List<T> {
  val seen = mutableSetOf<T>()
  val duplicates = mutableSetOf<T>()

  for (element in this) {
    if (!seen.add(element)) {
      duplicates.add(element)
    }
  }

  return duplicates.toList()
}

/**
 * Returns true if all elements in the list are unique.
 *
 * @see containsDuplicates
 */
fun <T> List<T>.allUnique(): Boolean = !containsDuplicates()

/**
 * Returns a list with the element at the specified index replaced with the new value.
 * Returns the original list if the index is out of bounds.
 *
 * @param index the index of the element to replace
 * @param value the new value
 * @return a new list with the replaced element
 */
fun <T> List<T>.replaceAt(index: Int, value: T): List<T> {
  if (index !in indices) return this.toList()
  return toMutableList().apply { this[index] = value }
}

/**
 * Swaps two elements at the specified indices.
 * Returns the original list if either index is out of bounds.
 *
 * @param index1 the index of the first element
 * @param index2 the index of the second element
 * @return a new list with swapped elements
 */
fun <T> List<T>.swap(index1: Int, index2: Int): List<T> {
  if (index1 !in indices || index2 !in indices) return this.toList()
  if (index1 == index2) return this.toList()

  return toMutableList().apply {
    val temp = this[index1]
    this[index1] = this[index2]
    this[index2] = temp
  }
}

/**
 * Groups consecutive equal elements together.
 *
 * @return a list of lists where each inner list contains consecutive equal elements
 *
 * Example:
 * ```
 * listOf(1, 1, 2, 2, 2, 3, 1, 1).groupConsecutive()
 * // [[1, 1], [2, 2, 2], [3], [1, 1]]
 * ```
 */
fun <T> List<T>.groupConsecutive(): List<List<T>> {
  if (isEmpty()) return emptyList()

  val result = mutableListOf<List<T>>()
  var currentGroup = mutableListOf(first())

  for (i in 1 until size) {
    if (this[i] == this[i - 1]) {
      currentGroup.add(this[i])
    } else {
      result.add(currentGroup)
      currentGroup = mutableListOf(this[i])
    }
  }
  result.add(currentGroup)

  return result
}

/**
 * Finds the most frequent element in the list.
 * Returns null if the list is empty.
 *
 * @return the most frequent element or null if the list is empty
 */
fun <T> List<T>.mostFrequent(): T? {
  if (isEmpty()) return null
  return groupBy { it }
    .maxByOrNull { it.value.size }
    ?.key
}

/**
 * Splits the list at the first element matching the predicate.
 *
 * @param predicate the condition to split at
 * @return a pair of lists: elements before and after (including) the matching element
 *
 * Example:
 * ```
 * listOf(1, 2, 3, 4, 5).splitAt { it > 3 } // ([1, 2, 3], [4, 5])
 * ```
 */
fun <T> List<T>.splitAt(predicate: (T) -> Boolean): Pair<List<T>, List<T>> {
  val index = indexOfFirst(predicate)
  return if (index == -1) {
    this to emptyList()
  } else {
    take(index) to drop(index)
  }
}

/**
 * Returns a random sample of elements from the list.
 *
 * @param sampleSize the number of elements to sample
 * @return a list containing randomly selected elements
 * @throws IllegalArgumentException if sampleSize is negative or greater than list size
 */
@Throws(IllegalArgumentException::class)
fun <T> List<T>.sample(sampleSize: Int): List<T> {
  require(sampleSize >= 0) { "Sample size must be non-negative" }
  require(sampleSize <= size) { "Sample size must not exceed list size" }

  return shuffled().take(sampleSize)
}

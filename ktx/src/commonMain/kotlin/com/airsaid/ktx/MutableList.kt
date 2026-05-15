package com.airsaid.ktx

/**
 * Swaps two elements at the specified indices if both indices are valid.
 *
 * @param index1 the index of the first element
 * @param index2 the index of the second element
 * @return true if the swap was performed, false if either index is out of bounds
 */
fun <T> MutableList<T>.safeSwap(index1: Int, index2: Int): Boolean {
  if (index1 !in indices || index2 !in indices) return false
  if (index1 == index2) return true

  val temp = this[index1]
  this[index1] = this[index2]
  this[index2] = temp
  return true
}

/**
 * Rotates the list to the left by the specified number of positions in place.
 *
 * @param n the number of positions to rotate
 * @see rotateLeft
 */
fun <T> MutableList<T>.rotateLeftInPlace(n: Int) {
  if (isEmpty()) return
  val normalizedN = ((n % size) + size) % size
  if (normalizedN == 0) return

  val rotated = drop(normalizedN) + take(normalizedN)
  clear()
  addAll(rotated)
}

/**
 * Rotates the list to the right by the specified number of positions in place.
 *
 * @param n the number of positions to rotate
 * @see rotateRight
 */
fun <T> MutableList<T>.rotateRightInPlace(n: Int) {
  if (isEmpty()) return
  val normalizedN = ((n % size) + size) % size
  if (normalizedN == 0) return

  val rotated = takeLast(normalizedN) + dropLast(normalizedN)
  clear()
  addAll(rotated)
}

/**
 * Removes duplicate elements from this mutable list, keeping only the first occurrence.
 *
 * @return the number of elements removed
 *
 * @see distinct
 */
fun <T> MutableList<T>.removeDuplicates(): Int {
  val seen = mutableSetOf<T>()
  val originalSize = size
  removeAll { !seen.add(it) }
  return originalSize - size
}

/**
 * Adds an element to the list only if it's not already present.
 *
 * @param element the element to add
 * @return true if the element was added, false if it was already present
 */
fun <T> MutableList<T>.addIfAbsent(element: T): Boolean {
  return if (element !in this) {
    add(element)
    true
  } else {
    false
  }
}

/**
 * Adds all elements from the specified collection that are not already present in this list.
 *
 * @param elements the elements to add
 * @return the number of elements added
 */
fun <T> MutableList<T>.addAllAbsent(elements: Collection<T>): Int {
  var added = 0
  for (element in elements) {
    if (addIfAbsent(element)) {
      added++
    }
  }
  return added
}

/**
 * Moves an element from one index to another.
 *
 * @param fromIndex the current index of the element
 * @param toIndex the target index for the element
 * @throws IndexOutOfBoundsException if either index is out of bounds
 */
@Throws(IndexOutOfBoundsException::class)
fun <T> MutableList<T>.move(fromIndex: Int, toIndex: Int) {
  if (fromIndex == toIndex) return
  val element = removeAt(fromIndex)
  add(if (fromIndex < toIndex) toIndex - 1 else toIndex, element)
}

/**
 * Moves an element from one index to another if both indices are valid.
 *
 * @param fromIndex the current index of the element
 * @param toIndex the target index for the element
 * @return true if the move was performed, false if either index is out of bounds
 */
fun <T> MutableList<T>.safeMove(fromIndex: Int, toIndex: Int): Boolean {
  if (fromIndex !in indices || toIndex !in indices) return false
  if (fromIndex == toIndex) return true
  val element = removeAt(fromIndex)
  add(if (fromIndex < toIndex) toIndex - 1 else toIndex, element)
  return true
}

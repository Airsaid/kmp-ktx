package com.airsaid.ktx

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MutableListExtensionsTest {
  @Test
  fun safeSwap_behavesAsDocumented() {
    val safeValues = mutableListOf(1, 2, 3)
    assertTrue(safeValues.safeSwap(0, 2))
    assertEquals(listOf(3, 2, 1), safeValues)
    assertTrue(safeValues.safeSwap(1, 1))
    assertFalse(safeValues.safeSwap(-1, 0))
  }

  @Test
  fun rotateOperations_modifyListInPlace() {
    val left = mutableListOf(1, 2, 3, 4)
    left.rotateLeftInPlace(1)
    assertEquals(listOf(2, 3, 4, 1), left)

    val right = mutableListOf(1, 2, 3, 4)
    right.rotateRightInPlace(2)
    assertEquals(listOf(3, 4, 1, 2), right)

    val negativeLeft = mutableListOf(1, 2, 3, 4)
    negativeLeft.rotateLeftInPlace(-1)
    assertEquals(listOf(4, 1, 2, 3), negativeLeft)

    val negativeRight = mutableListOf(1, 2, 3, 4)
    negativeRight.rotateRightInPlace(-1)
    assertEquals(listOf(2, 3, 4, 1), negativeRight)
  }

  @Test
  fun removeDuplicates_returnsCountOfRemovedItems() {
    val values = mutableListOf(1, 2, 2, 3, 3, 3)
    val removed = values.removeDuplicates()
    assertEquals(3, removed)
    assertEquals(listOf(1, 2, 3), values)
  }

  @Test
  fun addIfAbsent_and_addAllAbsent_insertOnlyMissingItems() {
    val items = mutableListOf("a")
    assertTrue(items.addIfAbsent("b"))
    assertFalse(items.addIfAbsent("b"))
    assertEquals(listOf("a", "b"), items)

    val addedCount = items.addAllAbsent(listOf("b", "c", "d"))
    assertEquals(2, addedCount)
    assertEquals(listOf("a", "b", "c", "d"), items)
  }

  @Test
  fun move_and_safeMove_shiftElementsToNewPositions() {
    val values = mutableListOf(1, 2, 3, 4)
    values.move(0, 3)
    assertEquals(listOf(2, 3, 1, 4), values)

    val safe = mutableListOf(1, 2, 3, 4)
    assertTrue(safe.safeMove(3, 0))
    assertEquals(listOf(4, 1, 2, 3), safe)
    assertFalse(safe.safeMove(-1, 0))

    assertFailsWith<IndexOutOfBoundsException> { values.move(10, 0) }
  }
}

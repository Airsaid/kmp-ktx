package com.airsaid.ktx

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ListExtensionsTest {
  @Test
  fun evenIndexed_and_oddIndexed_splitByParity() {
    val numbers = listOf(0, 1, 2, 3, 4, 5)
    assertEquals(listOf(0, 2, 4), numbers.evenIndexed())
    assertEquals(listOf(1, 3, 5), numbers.oddIndexed())
  }

  @Test
  fun chunkFromEnd_respectsRemainderAndSizeValidation() {
    val input = listOf(1, 2, 3, 4, 5)
    val chunks = input.chunkFromEnd(2)
    assertEquals(listOf(listOf(1), listOf(2, 3), listOf(4, 5)), chunks)

    assertEquals(emptyList(), emptyList<Int>().chunkFromEnd(3))
    assertFailsWith<IllegalArgumentException> { input.chunkFromEnd(0) }
  }

  @Test
  fun positionalHelpers_returnCorrectElements() {
    val letters = listOf("a", "b", "c", "d")
    assertEquals("b", letters.second())
    assertEquals("c", letters.third())
    assertEquals("b", letters.secondOrNull())
    assertEquals("c", letters.thirdOrNull())

    assertFailsWith<NoSuchElementException> { listOf(1).second() }
    assertFailsWith<NoSuchElementException> { listOf(1, 2).third() }
    assertEquals(null, listOf(1).secondOrNull())
    assertEquals(null, listOf(1, 2).thirdOrNull())
  }

  @Test
  fun rotateLeft_and_rotateRight_wrapElements() {
    val numbers = listOf(1, 2, 3, 4)
    assertEquals(listOf(3, 4, 1, 2), numbers.rotateLeft(2))
    assertEquals(listOf(4, 1, 2, 3), numbers.rotateRight(1))
    assertEquals(listOf(4, 1, 2, 3), numbers.rotateLeft(-1))
    assertEquals(listOf(2, 3, 4, 1), numbers.rotateRight(-1))
  }

  @Test
  fun without_and_replaceAt_leaveOriginalListUntouched() {
    val original = listOf(1, 2, 2, 3)
    assertEquals(listOf(1, 2, 3), original.without(2))
    assertEquals(listOf(1), original.withoutAll(2, 3))
    assertEquals(listOf(1), original.withoutAll(listOf(2, 3)))
    assertEquals(listOf(1, 9, 2, 3), original.replaceAt(1, 9))
    assertEquals(original, original.replaceAt(10, 5))
    assertEquals(original, original)
  }

  @Test
  fun swap_handlesInvalidIndices_gracefully() {
    val numbers = listOf(1, 2, 3)
    assertEquals(listOf(1, 3, 2), numbers.swap(1, 2))
    assertEquals(numbers, numbers.swap(1, 10))
    assertEquals(numbers, numbers.swap(1, 1))
  }

  @Test
  fun groupConsecutive_capturesStructure() {
    val input = listOf(1, 1, 2, 3, 3, 3, 4)
    assertEquals(listOf(listOf(1, 1), listOf(2), listOf(3, 3, 3), listOf(4)), input.groupConsecutive())
  }

  @Test
  fun duplicates_and_containsDuplicates_detectRepeatEntries() {
    val input = listOf("a", "b", "b", "c", "c")
    assertTrue(input.containsDuplicates())
    assertEquals(setOf("b", "c"), input.duplicates().toSet())
    assertFalse(listOf(1, 2, 3).containsDuplicates())
    assertTrue(listOf(1, 2, 3).allUnique())
  }

  @Test
  fun middle_returnsCorrectCenterElements() {
    assertEquals(listOf(3), listOf(1, 2, 3, 4, 5).middle())
    assertEquals(listOf(2, 3), listOf(1, 2, 3, 4).middle())
    assertFailsWith<NoSuchElementException> { emptyList<Int>().middle() }
  }
}

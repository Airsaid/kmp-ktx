package com.airsaid.ktx

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class MapExtensionsTest {
  @Test
  fun invert_and_invertGrouping_handleDuplicateValues() {
    val original = mapOf(1 to "a", 2 to "b", 3 to "a")
    assertEquals(mapOf("a" to 3, "b" to 2), original.invert())
    assertEquals(mapOf("a" to listOf(1, 3), "b" to listOf(2)), original.invertGrouping())
  }

  @Test
  fun mergeWith_combinesEntriesUsingMerger() {
    val base = mapOf("a" to 1, "b" to 2)
    val other = mapOf("b" to 3, "c" to 4)
    val merged = base.mergeWith(other) { left, right -> left + right }
    assertEquals(mapOf("a" to 1, "b" to 5, "c" to 4), merged)
  }

  @Test
  fun sorting_and_filtering_helpers_transformMaps() {
    val map = mapOf("c" to 3, "a" to 1, "b" to 2)
    assertEquals(listOf("a", "b", "c"), map.sortedByValues().keys.toList())
    assertEquals(listOf("c", "b", "a"), map.sortedByValues(compareByDescending { it }).keys.toList())

    assertEquals(mapOf("b" to 2, "c" to 3), map.subtract(mapOf("a" to 1)))
    assertEquals("b", map.keyOf(2))
    assertEquals(setOf("b"), map.keysOf(2))

    assertEquals(mapOf("a" to 1, "b" to 2), map.except("c"))
    assertEquals(mapOf("c" to 3), map.only("c"))
    assertTrue(map.containsAll(mapOf("a" to 1)))
  }

  @Test
  fun joinToString_formatsEntries() {
    val map = mapOf("a" to 1, "b" to 2)
    val result = map.joinToString(entrySeparator = ";", keyValueSeparator = ":")
    assertEquals("{a:1;b:2}", result)
  }

  @Test
  fun combine_handlesNullMaps() {
    val left: Map<String, Int>? = null
    val right = mapOf("a" to 1)
    assertEquals(right, left.combine(right))
    assertEquals(right, right.combine(null))
  }

  @Test
  fun deepMerge_mergesNestedMaps() {
    val base = mapOf("a" to 1, "nested" to mapOf("x" to 1))
    val other = mapOf("b" to 2, "nested" to mapOf("y" to 2))
    val merged = base.deepMerge(other)
    assertEquals(1, merged["a"])
    assertEquals(2, merged["b"])
    assertEquals(mapOf("x" to 1, "y" to 2), merged["nested"])
  }

  @Test
  fun flatten_expandsNestedKeys() {
    val input = mapOf(
      "a" to 1,
      "nested" to mapOf(
        "b" to 2,
        "deep" to mapOf("c" to 3),
      ),
    )
    val flattened = input.flatten()
    assertEquals(1, flattened["a"])
    assertEquals(2, flattened["nested.b"])
    assertEquals(3, flattened["nested.deep.c"])
  }

  @Test
  fun filterNotNullValues_removesNulls() {
    val input = mapOf("a" to 1, "b" to null, "c" to 3)
    assertEquals(mapOf("a" to 1, "c" to 3), input.filterNotNullValues())
  }

  @Test
  fun groupByKey_groupsEntriesIntoMaps() {
    val input = mapOf("ab" to 1, "ac" to 2, "bb" to 3)
    val grouped = input.groupByKey { it.key.first() }
    assertEquals(mapOf("ab" to 1, "ac" to 2), grouped['a'])
    assertEquals(mapOf("bb" to 3), grouped['b'])
  }

  @Test
  fun filterKeysOrValuesByType_and_getAs_work() {
    val input: Map<Any, Any> = mapOf(1 to "one", "two" to 2, 3 to 3)
    val keys = input.filterKeysIsInstance<Int, Any>()
    val values = input.filterValuesIsInstance<Any, Int>()
    assertEquals(setOf(1, 3), keys.keys)
    assertEquals(mapOf<Any, Int>("two" to 2, 3 to 3), values)
    assertEquals("one", input.getAs<String>(1))
    assertNull(input.getAs<String>(2))
  }

  @Test
  fun distinctByValue_keepsFirstOccurrence() {
    val input = mapOf("a" to 1, "b" to 1, "c" to 2)
    assertEquals(mapOf("a" to 1, "c" to 2), input.distinctByValue())
  }
}

package com.airsaid.ktx

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class MutableMapExtensionsTest {
  @Test
  fun removeAll_removesMatchingEntries() {
    val map = mutableMapOf("a" to 1, "b" to 2, "c" to 3)
    val removed = map.removeAll { it.value % 2 == 1 }
    assertEquals(2, removed)
    assertEquals(mapOf("b" to 2), map)
  }

  @Test
  fun removeFirst_returnsEntryAndRemovesIt() {
    val map = mutableMapOf("a" to 1, "b" to 2)
    val removed = map.removeFirst { it.value == 2 }
    assertEquals("b" to 2, removed?.let { it.key to it.value })
    assertEquals(mapOf("a" to 1), map)
    assertNull(map.removeFirst { it.value == 99 })
  }

  @Test
  fun putOrRemove_handlesNullValues() {
    val map = mutableMapOf("a" to 1)
    map.putOrRemove("b", 2)
    assertEquals(mapOf("a" to 1, "b" to 2), map)
    map.putOrRemove("a", null)
    assertTrue("a" !in map)
  }
}

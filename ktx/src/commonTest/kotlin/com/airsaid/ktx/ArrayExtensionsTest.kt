package com.airsaid.ktx

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ArrayExtensionsTest {
  @Test
  fun emptyArrayConstants_haveExpectedSizes() {
    assertEquals(0, emptyByteArray.size)
    assertEquals(0, emptyIntArray.size)
    assertEquals(0, emptyFloatArray.size)
    assertEquals(0, emptyLongArray.size)
    assertEquals(0, emptyBooleanArray.size)
    assertEquals(0, emptyStringArray.size)
  }

  @Test
  fun emptyStringArray_containsNoElements() {
    assertTrue(emptyStringArray.isEmpty())
  }
}

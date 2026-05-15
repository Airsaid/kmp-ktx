package com.airsaid.ktx

import kotlin.test.Test
import kotlin.test.assertEquals

class NumberExtensionsTest {
  @Test
  fun orZero_handlesNulls() {
    val intValue: Int? = null
    val longValue: Long? = null
    val floatValue: Float? = null
    val doubleValue: Double? = null
    val uLongValue: ULong? = null

    assertEquals(0, intValue.orZero())
    assertEquals(0L, longValue.orZero())
    assertEquals(0f, floatValue.orZero())
    assertEquals(0.0, doubleValue.orZero())
    assertEquals(0UL, uLongValue.orZero())
  }

  @Test
  fun orZero_returnsValueWhenPresent() {
    val intValue: Int? = 7
    val longValue: Long? = 9L
    val floatValue: Float? = 1.5f
    val doubleValue: Double? = 2.25
    val uLongValue: ULong? = 3UL

    assertEquals(7, intValue.orZero())
    assertEquals(9L, longValue.orZero())
    assertEquals(1.5f, floatValue.orZero())
    assertEquals(2.25, doubleValue.orZero())
    assertEquals(3UL, uLongValue.orZero())
  }
}

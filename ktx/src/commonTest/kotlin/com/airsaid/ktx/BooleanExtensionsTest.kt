package com.airsaid.ktx

import kotlin.test.Test
import kotlin.test.assertEquals

class BooleanExtensionsTest {
  @Test
  fun select_returnsExpectedValue() {
    assertEquals("yes", true.select("yes", "no"))
    assertEquals("no", false.select("yes", "no"))
  }

  @Test
  fun orFalse_orTrue_handleNulls() {
    val value: Boolean? = null
    assertEquals(false, value.orFalse())
    assertEquals(true, value.orTrue())
  }

  @Test
  fun toBinary_convertsNullableBoolean() {
    assertEquals(1, true.toBinary())
    assertEquals(0, false.toBinary())
    val value: Boolean? = null
    assertEquals(0, value.toBinary())
  }
}

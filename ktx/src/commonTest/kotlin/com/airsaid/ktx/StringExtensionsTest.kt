package com.airsaid.ktx

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

class StringExtensionsTest {
  @Test
  fun toLongSafe_handlesNullAndInvalidInputs() {
    assertEquals(0L, (null as String?).toLongSafe())
    assertEquals(0L, "abc".toLongSafe())
  }

  @Test
  fun toLongSafe_parsesValidNumber() {
    assertEquals(123L, "123".toLongSafe())
  }

  @Test
  fun toIntSafe_and_toFloatSafe_behaveLikeSafeDefaults() {
    assertEquals(0, (null as String?).toIntSafe())
    assertEquals(0, "not-a-number".toIntSafe())
    assertEquals(42, "42".toIntSafe())

    assertEquals(0f, (null as String?).toFloatSafe())
    assertEquals(0f, "oops".toFloatSafe())
    assertEquals(3.5f, "3.5".toFloatSafe())
  }

  @Test
  fun substringSafe_clampsOutOfBoundsIndices() {
    val actual = "Kotlin".substringSafe(startIndex = -2, endIndex = 10)
    assertEquals("Kotlin", actual)
  }

  @Test
  fun ellipsize_truncatesAndThrowsOnNegativeLength() {
    assertEquals("Hello...", "HelloWorld".ellipsize(5))
    assertEquals("Hi", "Hi".ellipsize(5))
    assertFailsWith<IllegalArgumentException> { "Test".ellipsize(-1) }
  }

  @Test
  fun excludeNonAscii_filtersOutNonAsciiChars() {
    val original = "Héllo\tWørld"
    assertEquals("Hllo\tWrld", original.excludeNonAscii())
    assertNull((null as String?).excludeNonAscii())
  }

  @Test
  fun toQueryMap_ignoresMalformedPairsAndKeepsLastDuplicate() {
    val query = "a=1&b=2&empty=&=value&novalue&b=updated"
    val result = query.toQueryMap()

    assertEquals(mapOf("a" to "1", "b" to "updated"), result)
    assertTrue("empty" !in result)
  }

  @Test
  fun ifNullOrEmpty_and_ifNotNullNorEmpty_executeExpectedBranches() {
    var fallbackInvoked = false
    val default = (null as String?).ifNullOrEmpty {
      fallbackInvoked = true
      "default"
    }
    assertTrue(fallbackInvoked)
    assertEquals("default", default)

    var callbackValue: String? = null
    "value".ifNotNullNorEmpty { callbackValue = it }
    assertEquals("value", callbackValue)
  }
}

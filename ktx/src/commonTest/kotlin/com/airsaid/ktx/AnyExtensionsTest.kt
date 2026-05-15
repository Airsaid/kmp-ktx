package com.airsaid.ktx

import kotlin.test.Test
import kotlin.test.assertEquals

class AnyExtensionsTest {
  @Test
  fun toNumberSafe_handlesNullAndInvalidValues() {
    val value: Any? = null
    assertEquals(5, value.toIntSafe(5))
    assertEquals(1.5f, value.toFloatSafe(1.5f))
    assertEquals(2.5, value.toDoubleSafe(2.5))
    assertEquals(7L, value.toLongSafe(7L))
    assertEquals(9UL, value.toULongSafe(9UL))
  }

  @Test
  fun toNumberSafe_parsesValidValues() {
    val intValue: Any = "42"
    val floatValue: Any = "3.25"
    val doubleValue: Any = "4.5"
    val longValue: Any = "123456"
    val uLongValue: Any = "99"

    assertEquals(42, intValue.toIntSafe())
    assertEquals(3.25f, floatValue.toFloatSafe())
    assertEquals(4.5, doubleValue.toDoubleSafe())
    assertEquals(123456L, longValue.toLongSafe())
    assertEquals(99UL, uLongValue.toULongSafe())
  }

  @Test
  fun toBooleanSafe_usesStrictParsing() {
    val strictTrue: Any = "true"
    val strictFalse: Any = "false"
    val invalid: Any = "True"

    assertEquals(true, strictTrue.toBooleanSafe())
    assertEquals(false, strictFalse.toBooleanSafe())
    assertEquals(true, invalid.toBooleanSafe(true))
  }

  @Test
  fun toStringSafe_returnsDefaultOnNull() {
    val value: Any? = null
    assertEquals("fallback", value.toStringSafe("fallback"))
  }
}

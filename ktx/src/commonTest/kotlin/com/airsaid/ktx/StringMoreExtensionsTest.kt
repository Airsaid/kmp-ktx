package com.airsaid.ktx

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

class StringMoreExtensionsTest {
  @Test
  fun subSequenceSafe_clampsBounds() {
    val text = "Kotlin"
    assertEquals("Kot", text.subSequenceSafe(-1, 3).toString())
    assertEquals("tlin", text.subSequenceSafe(2, 100).toString())
  }

  @Test
  fun removePrefixSuffixIgnoreCase_workAsExpected() {
    assertEquals("World", "HelloWorld".removePrefixIgnoreCase("hello"))
    assertEquals("Hello", "HelloWorld".removeSuffixIgnoreCase("WORLD"))
  }

  @Test
  fun isLettersDigitsChecks_handleEmptyAndMixed() {
    assertTrue("abc".isLettersOnly())
    assertTrue("123".isDigitsOnly())
    assertTrue("a1b2".isLetterOrDigitOnly())
    assertEquals(false, "".isDigitsOnly())
    assertEquals(false, "a-1".isLetterOrDigitOnly())
  }

  @Test
  fun countOccurrences_countsNonOverlappingMatches() {
    assertEquals(3, "ababab".countOccurrences("ab"))
    assertEquals(0, "text".countOccurrences(""))
  }

  @Test
  fun wrap_and_repeatWithSeparator_work() {
    assertEquals("*hi*", "hi".wrap("*"))
    assertEquals("[hi]", "hi".wrap("[", "]"))
    assertEquals("a-a-a", "a".repeatWithSeparator(3, "-"))
    assertFailsWith<IllegalArgumentException> { "a".repeatWithSeparator(-1) }
  }

  @Test
  fun ensurePrefixSuffix_addWhenMissing() {
    assertEquals("https://example.com", "example.com".ensurePrefix("https://"))
    assertEquals("hello!", "hello".ensureSuffix("!"))
    assertEquals("HELLO", "HELLO".ensurePrefix("hello", ignoreCase = true))
  }

  @Test
  fun substringBetween_extractsContent() {
    assertEquals("world", "<world>".substringBetween("<", ">"))
    assertNull("<world".substringBetween("<", ">"))
  }

  @Test
  fun capitalizeWords_and_swapCase_transformText() {
    assertEquals("Hello World", "hELLO wORLD".capitalizeWords())
    assertEquals("kOtLiN", "KoTlIn".swapCase())
  }
}

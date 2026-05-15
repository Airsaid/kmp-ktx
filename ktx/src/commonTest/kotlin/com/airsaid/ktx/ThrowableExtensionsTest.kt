package com.airsaid.ktx

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ThrowableExtensionsTest {
  @Test
  fun tryWithHandler_invokesCallbackOnFailure() {
    var captured: Throwable? = null
    val result = tryWithHandler(block = { error("boom") }, onFailure = { captured = it })

    assertNull(result)
    assertTrue(captured?.message?.contains("boom") == true)
  }

  @Test
  fun tryWithCallbacks_routesControlFlowCorrectly() {
    val successSignals = mutableListOf<String>()
    var finallyInvoked = false

    val successResult = tryWithCallbacks(
      block = { "ok" },
      onSuccess = { successSignals += "success:$it" },
      onFailure = { successSignals += "failure" },
      finally = { finallyInvoked = true }
    )

    assertEquals("ok", successResult)
    assertEquals(listOf("success:ok"), successSignals)
    assertTrue(finallyInvoked)

    successSignals.clear()
    finallyInvoked = false

    val failureResult = tryWithCallbacks<String>(
      block = { error("boom") },
      onSuccess = { successSignals += "success:$it" },
      onFailure = { successSignals += "failure" },
      finally = { finallyInvoked = true }
    )

    assertNull(failureResult)
    assertEquals(listOf("failure"), successSignals)
    assertTrue(finallyInvoked)
  }
}

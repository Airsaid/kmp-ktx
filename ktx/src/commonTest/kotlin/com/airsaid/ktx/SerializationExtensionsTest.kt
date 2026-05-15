package com.airsaid.ktx

import kotlinx.serialization.Serializable
import kotlin.test.Test
import kotlin.test.assertEquals

class SerializationExtensionsTest {
  @Serializable
  data class Item(val id: Int)

  @Test
  fun toObjectList_decodesJsonArray() {
    val json = """[{"id":1},{"id":2}]"""
    val result = json.toObjectList<Item>()
    assertEquals(listOf(Item(1), Item(2)), result)
  }

}

# ktx 模块

[English](README.md)

`ktx` 是一组面向 Kotlin Multiplatform 公共代码的轻量工具扩展库。

## 功能概览

- 为可空数字、布尔值和常见类型转换提供更安全的默认值。
- 提供字符串解析、截取、包裹、前后缀处理和 Query 参数工具。
- 提供集合与可变集合的旋转、切分、重复处理和安全更新工具。
- 提供 Map 反转、合并、扁平化、过滤和类型安全取值工具。
- 基于 `kotlinx.serialization` 提供共享 JSON 默认配置与安全序列化工具。
- 提供基于回调的异常处理工具。

## 安装

```kotlin
kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation("com.airsaid:ktx:$version")
    }
  }
}
```

## 使用示例

```kotlin
import com.airsaid.ktx.*
import kotlinx.serialization.Serializable

@Serializable
data class User(val id: Int, val name: String)

val count = "42".toIntSafe()
val title = "Kotlin Multiplatform".ellipsize(maxLength = 6)
val segment = "abcdef".substringSafe(startIndex = -2, endIndex = 3)
val params = "page=1&sort=latest".toQueryMap()

val rotatedIds = listOf(1, 2, 3, 4).rotateRight(1)
val duplicateIds = listOf(1, 2, 2, 3).duplicates()

val profile = mapOf<String, Any?>(
  "user" to mapOf("name" to "Ada"),
).deepMerge(
  mapOf("user" to mapOf("age" to 36)),
)

val user = """{"id":1,"name":"Ada"}""".toObjectSafe<User>()
val json = user.toJsonSafe()

val value = tryWithCallbacks(
  block = { "100".toInt() },
  onSuccess = { println("Parsed $it") },
  onFailure = { println("Could not parse value") },
  finally = { println("Done") },
)
```

## 测试

执行：

```shell
./gradlew :ktx:check
```

测试位于 `ktx/src/commonTest/kotlin/com/airsaid/ktx`。

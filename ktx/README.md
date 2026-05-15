# ktx Module

[中文说明](README.zh.md)

`ktx` is a lightweight Kotlin Multiplatform utility extension library for shared Kotlin code.

## API Overview

- Safer defaults for nullable numbers, booleans, and common type conversions.
- String helpers for parsing, slicing, wrapping, prefix/suffix handling, and query parameters.
- Collection and mutable collection helpers for rotation, chunking, duplicate handling, and safe updates.
- Map helpers for inversion, merging, flattening, filtering, and typed value access.
- Shared JSON defaults and safe serialization helpers based on `kotlinx.serialization`.
- Callback-based exception handling helpers.

## Installation

```kotlin
kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation("com.airsaid:ktx:$version")
    }
  }
}
```

## Examples

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

## Testing

Run:

```shell
./gradlew :ktx:check
```

Tests live in `ktx/src/commonTest/kotlin/com/airsaid/ktx`.

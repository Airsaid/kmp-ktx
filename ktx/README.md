# ktx Module

[中文说明](README.zh.md)

`ktx` is a lightweight Kotlin Multiplatform extension toolkit. It keeps dependencies low and only includes APIs that add clear semantics beyond Kotlin, kotlinx, or AndroidX defaults.

## Boundary Principles

- Do not duplicate official Kotlin, kotlinx, or AndroidX APIs.
- Do not add thin wrappers only to save a few characters.
- Do not introduce Compose, Lifecycle, Android UI, or platform-heavy dependencies into this base utility library.
- Keep helpers that unify default behavior, remove error-prone boilerplate, or express clear domain semantics.
- Keep one `ktx` module and avoid additional third-party dependencies unless the benefit is explicit.

## API Overview

- Basic types: `orZero`, `orFalse`, `orTrue`, `toBinary`, and safe `Any` conversions.
- Strings: safe number conversion, `substringSafe`, `ellipsize`, `excludeNonAscii`, `toQueryMap`, prefix/suffix helpers, case conversion, and related utilities.
- Collections: chunking from the end, rotation, duplicate detection, sampling, middle element lookup, and safe replacement.
- Mutable collections: safe swap, safe move, duplicate removal, and absent-only insertion.
- Maps: inversion, grouped inversion, merging, deep merge, flattening, typed filtering, and distinct values.
- Serialization: `JsonUtil`, safe JSON parsing, and `JsonObject` value access.
- Throwable helpers: `tryWithHandler` and `tryWithCallbacks`.

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

## Example

```kotlin
import com.airsaid.ktx.*

val count = "42".toIntSafe()
val safeText = "Hello World".ellipsize(5)
val params = "a=1&b=2".toQueryMap()

val rotated = listOf(1, 2, 3, 4).rotateRight(1)
val duplicateValues = listOf(1, 2, 2, 3).duplicates()

val merged = mapOf(
  "user" to mapOf("name" to "Ada"),
).deepMerge(
  mapOf("user" to mapOf("age" to 36)),
)
```

## Testing

Run:

```shell
./gradlew :ktx:check
```

Tests live in `ktx/src/commonTest/kotlin/com/airsaid/ktx`.

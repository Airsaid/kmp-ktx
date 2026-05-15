# kmp-ktx

[中文说明](README.zh.md)

[![Maven Central](https://img.shields.io/maven-central/v/com.airsaid/ktx.svg)](https://central.sonatype.com/artifact/com.airsaid/ktx)
[![CI](https://img.shields.io/github/actions/workflow/status/Airsaid/kmp-ktx/ci.yml?branch=main)](https://github.com/Airsaid/kmp-ktx/actions/workflows/ci.yml)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
![Kotlin](https://img.shields.io/badge/Kotlin-2.2.21-7F52FF.svg)

Lightweight Kotlin Multiplatform utility extensions.

## Features

- Nullable number and boolean defaults.
- Safe string conversion, slicing, wrapping, prefix/suffix, and query parsing helpers.
- List rotation, chunking, duplicate detection, and safe element replacement.
- Mutable list and mutable map helpers with safe in-place operations.
- Map inversion, grouping, merging, flattening, and typed filtering.
- Shared JSON defaults and safe serialization helpers based on `kotlinx.serialization`.
- Callback-based exception handling helpers.

## Installation

Add the dependency to `commonMain`:

```kotlin
kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation("com.airsaid:ktx:$version")
    }
  }
}
```

Make sure Maven Central is available:

```kotlin
repositories {
  mavenCentral()
}
```

## Quick Start

```kotlin
import com.airsaid.ktx.*

val count = "42".toIntSafe()
val title = "Kotlin Multiplatform".ellipsize(maxLength = 6)
val params = "page=1&sort=latest".toQueryMap()

val rotated = listOf(1, 2, 3, 4).rotateRight(1)
val duplicates = listOf(1, 2, 2, 3).duplicates()
```

Detailed documentation: [ktx/README.md](ktx/README.md)

## Platforms

- Android: minSdk 24, compileSdk 36, JVM target 17
- iOS: iosArm64, iosX64, iosSimulatorArm64
- Kotlin: 2.2.21

## Release

- Changelog: [CHANGELOG.md](CHANGELOG.md)
- Releasing: [docs/releasing.md](docs/releasing.md)

## License

Apache-2.0. See [LICENSE](LICENSE).

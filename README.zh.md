# kmp-ktx

[English](README.md)

[![Maven Central](https://img.shields.io/maven-central/v/com.airsaid/ktx.svg)](https://central.sonatype.com/artifact/com.airsaid/ktx)
[![CI](https://img.shields.io/github/actions/workflow/status/Airsaid/kmp-ktx/ci.yml?branch=main)](https://github.com/Airsaid/kmp-ktx/actions/workflows/ci.yml)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
![Kotlin](https://img.shields.io/badge/Kotlin-2.2.21-7F52FF.svg)

轻量的 Kotlin Multiplatform 工具扩展库。

## 特性

- 可空数字与布尔值默认值。
- 安全字符串转换、截取、包裹、前后缀处理和 Query 解析。
- List 旋转、切分、重复检测和安全替换。
- MutableList 与 MutableMap 的安全原地操作。
- Map 反转、分组、合并、扁平化和类型过滤。
- 基于 `kotlinx.serialization` 的共享 JSON 默认配置与安全序列化工具。
- 基于回调的异常处理工具。

## 安装

在 `commonMain` 中添加依赖：

```kotlin
kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation("com.airsaid:ktx:$version")
    }
  }
}
```

确保已添加 Maven Central：

```kotlin
repositories {
  mavenCentral()
}
```

## 快速开始

```kotlin
import com.airsaid.ktx.*

val count = "42".toIntSafe()
val title = "Kotlin Multiplatform".ellipsize(maxLength = 6)
val params = "page=1&sort=latest".toQueryMap()

val rotated = listOf(1, 2, 3, 4).rotateRight(1)
val duplicates = listOf(1, 2, 2, 3).duplicates()
```

详细文档：[ktx/README.zh.md](ktx/README.zh.md)

## 平台与版本

- Android: minSdk 24, compileSdk 36, JVM target 17
- iOS: iosArm64, iosX64, iosSimulatorArm64
- Kotlin: 2.2.21

## 变更记录与发布

- 变更记录：[CHANGELOG.md](CHANGELOG.md)
- 发布流程：[docs/releasing.md](docs/releasing.md)

## License

Apache-2.0. See [LICENSE](LICENSE).

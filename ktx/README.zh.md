# ktx 模块

[English](README.md)

`ktx` 是一组面向 Kotlin Multiplatform (KMP) 的轻量通用扩展工具集。模块只保留低依赖、KMP 友好，并且相对 Kotlin 标准库或官方库有明确语义增益的 API。

## 边界原则

- 不复制 Kotlin、kotlinx、AndroidX 已经提供的官方 API。
- 不为了少写几个字符而增加薄封装。
- 不在基础工具库中引入 Compose、Lifecycle、Android UI 或平台重依赖。
- 保留能统一默认行为、减少易错样板或表达明确语义的工具。
- 继续只维护一个 `ktx` 模块，除非收益明确，否则不引入新的第三方依赖。

## 功能概览

- 基础类型扩展：`orZero`、`orFalse`、`orTrue`、`toBinary`，以及 `Any` 的安全转换。
- 字符串扩展：安全数值转换、`substringSafe`、`ellipsize`、`excludeNonAscii`、`toQueryMap`、前后缀处理、大小写转换等。
- 集合扩展：尾部切分、旋转、重复检测、采样、中间元素查找和安全替换。
- 可变集合扩展：安全交换、安全移动、去重和缺失时插入。
- Map 扩展：反转、分组反转、合并、深度合并、扁平化、类型过滤和值去重。
- 序列化扩展：`JsonUtil` 默认配置、安全 JSON 解析和 `JsonObject` 取值。
- 异常处理：`tryWithHandler`、`tryWithCallbacks`。

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

## 测试

执行：

```shell
./gradlew :ktx:check
```

测试位于 `ktx/src/commonTest/kotlin/com/airsaid/ktx`。

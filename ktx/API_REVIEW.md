# ktx API Review

This document records the API convergence result for the standalone `ktx` library. New APIs should first check whether Kotlin, kotlinx, or AndroidX already provides an official equivalent.

## Removed Before Extraction

| API | Reason | Official alternative |
| --- | --- | --- |
| `List.safeGet` | Overlaps with standard safe indexed reads | `getOrElse(index) { default }` |
| `List.adjacentPairs` | Overlaps with standard adjacent pairing | `zipWithNext()` |
| `Map.first` / `Map.firstOrNull` | Only delegates to `entries` | `entries.first...` |
| `Map.firstValue` / `Map.firstValueOrNull` | Only delegates to `values` | `values.first...` |
| `Map.maxValue` / `Map.minValue` | Overlaps with standard max/min selection | `entries.maxByOrNull { it.value }` / `entries.minByOrNull { it.value }` |
| `Map.copy` | Overlaps with standard map copying | `toMutableMap()` |
| `MutableList.swap` | Too close to immutable `List.swap`; local swap code is clearer | Local swap code or `safeSwap` |
| `runCatchingOrNull` / `runCatchingOrDefault` | Overlaps with standard `Result` API | `runCatching { ... }.getOrNull()` / `getOrDefault(...)` |
| `retryOnFailure` | Introduced coroutines for one helper, against the low-dependency goal | Implement in application code with `kotlinx.coroutines` |
| `launchMain` / `launchIO` / `launchDefault` | Only aliases official coroutine APIs | `launch(Dispatchers.X)` |
| `withMainDispatcher` / `withIODispatcher` / `withDefaultDispatcher` / `withDispatcher` | Only aliases official coroutine APIs | `withContext(...)` |
| `LifecycleObserver` / `ResumeEffect` / `PauseEffect` / `StartEffect` / `StopEffect` / `rememberLifecycleState` / `LifecycleRepeatEffect` | AndroidX Lifecycle Compose provides official APIs and would add Compose/Lifecycle dependencies | `LifecycleEventEffect`, `LifecycleStartEffect`, `LifecycleResumeEffect`, `currentStateAsState()`, `repeatOnLifecycle` |

## Kept

| Category | API | Reason |
| --- | --- | --- |
| Basic types | `orZero`, `orFalse`, `orTrue`, `toBinary` | Clear semantics, no extra dependencies |
| Any conversion | `Any?.toIntSafe` and related APIs | Keeps project default conversion semantics |
| String | `substringSafe`, `ellipsize`, `excludeNonAscii`, `toQueryMap`, `ensurePrefix`, `ensureSuffix`, `substringBetween`, `swapCase`, etc. | Standard library has no direct same-semantics equivalent |
| List | `chunkFromEnd`, `rotateLeft`, `rotateRight`, `duplicates`, `middle`, `sample`, etc. | Clear collection semantics |
| MutableList | `safeSwap`, `safeMove`, `removeDuplicates`, `addIfAbsent`, `addAllAbsent` | Keeps safe or de-duplicating semantics |
| Map | `invertGrouping`, `deepMerge`, `flatten`, `filterNotNullValues`, `distinctByValue`, etc. | No direct official same-name semantics |
| Serialization | `JsonUtil`, safe JSON parsing, and `JsonObject` getters | Unifies default JSON behavior |
| Throwable | `tryWithHandler`, `tryWithCallbacks` | Keeps callback routing semantics |

## Maintenance Principles

- A new API must express a clear semantic beyond official APIs.
- A new API should not exist only to save a few characters.
- A new API should not make the base library depend on UI, Android, or coroutines.
- Thin wrappers over third-party libraries should only remain when they unify default behavior.

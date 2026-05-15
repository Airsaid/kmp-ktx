# Repository Guidelines

## Project Structure & Module Organization

This repository hosts a lightweight Kotlin Multiplatform extension library.
`ktx/` contains all production code in `src/commonMain/kotlin/com/airsaid/ktx` and shared tests in `src/commonTest/kotlin/com/airsaid/ktx`.
Keep the library focused on low-dependency common Kotlin utilities; do not add UI, Android-only, or coroutine helpers without a clear multiplatform maintenance reason.

## Build, Test, and Development Commands

- `./gradlew :ktx:check` runs the core verification suite.
- `./gradlew :ktx:publishToMavenLocal` verifies local publication metadata and artifacts.
- `./gradlew :ktx:iosSimulatorArm64Test :ktx:compileKotlinIosArm64 :ktx:compileKotlinIosX64` verifies iOS targets on macOS.

## Coding Style & Naming Conventions

- Follow Kotlin official style with two-space indentation and tidy imports.
- Name classes with `UpperCamelCase`, functions and extensions with `lowerCamelCase`, and constants with `SCREAMING_SNAKE_CASE` only when they are true compile-time constants.
- Public code comments should be written in English.

## Testing Guidelines

- Tests live under `ktx/src/commonTest/kotlin` and use `kotlin.test`.
- Test classes must end with `Test`.
- Every behavior change must include matching common tests.

## Commit & Pull Request Guidelines

- Use Conventional Commits (`feat:`, `fix:`, `docs:`, etc.).
- Keep each change focused and ensure CI-critical tasks pass locally before opening a PR.

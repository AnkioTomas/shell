# shell

Android Root / Shizuku 命令执行库（从 AutoAccountingOrg/AutoAccounting 拆分，保留历史）。

- `lib/`：库模块（`net.ankio.shell.Shell`）
- `app/`：示例应用

## 使用

```kotlin
// settings.gradle.kts: maven { url = uri("https://jitpack.io") }
implementation("com.github.AnkioTomas:shell:<tag>")
```

```kotlin
val shell = Shell(context.packageName)
if (!shell.checkPermission()) shell.requestPermission()
val out = shell.exec("id")   // 优先 Root，其次 Shizuku
shell.close()
```

## 构建

```bash
./gradlew build
```

# Tic-Tac-Toe

## Run locally

```bash
./gradlew :app:run
```

## Build desktop application

Create a self-contained desktop app image:

```bash
./gradlew :app:jlink
```

Output:
- `app/build/image/` (includes a runnable launcher named `tictactoe`)

Create a native installer for your OS:

```bash
./gradlew :app:jpackage
```

Typical installer output:
- macOS: `.dmg`
- Windows: `.msi`
- Linux: `.deb`

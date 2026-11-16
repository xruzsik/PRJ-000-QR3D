# Postavičky na trase – Android app

AR app for the PRJ-000-QR3D trail game: scan a QR code at a trail stop and the
place's character (an animal for now) appears beside the code, tells its short
story, joins your collection, and then leaves the code to roam the ground around
you. Collecting every character on the trail earns a badge. Works offline.

Kotlin, Jetpack Compose, ARCore, SceneView (Filament), ML Kit QR scanning.

## Build

Requirements:

- **JDK 17 or newer** (21 recommended), e.g. the one bundled with Android Studio.
- **Android SDK** with platform **android-37** and build-tools **37.0.0**, found
  through `ANDROID_HOME` or `sdk.dir` in `local.properties` (Android Studio
  writes that file for you). Missing components are installed by the build once
  the SDK licences are accepted (`sdkmanager --licenses`).
- To run it: an [ARCore-supported](https://developers.google.com/ar/devices)
  phone with Android 9+ and Google Play Services for AR.

From this folder:

```bash
./gradlew :app:assembleDebug        # APK: app/build/outputs/apk/debug/app-debug.apk
./gradlew :app:installDebug         # install on a connected phone (USB debugging on)
./gradlew :app:testDebugUnitTest    # unit tests (run on the PC, no phone needed)
```

Or open this folder in Android Studio and run the `app` configuration.

## Try it

Print `content/markers/qr-test-sheet.pdf` or `content/markers/animals.pdf` at
**100 % / Actual size**: the ruler on each page must measure exactly 10 cm, since
the app works out distances from the codes' printed size. Scan a code at a
slight angle (square-on views are least accurate), then walk away from it and
the character follows you.

## Layout

| Path | What |
|---|---|
| `app/src/main/java/com/smvit/ar/Catalog.kt` | **Trail content**: each code's character, model, sizes, placement, animations and story; which characters make up the trail. |
| `app/src/main/java/com/smvit/ar/ArScreen.kt` | AR screen: scanning, tracking, placing and releasing characters. |
| `app/src/main/java/com/smvit/ar/TrailUi.kt` | Story card, collection, badge; all UI texts (Slovak). |
| `app/src/main/java/com/smvit/ar/qr/` | QR pose maths: corners → 3D pose, orientation from finder patterns, smoothing. |
| `app/src/main/java/com/smvit/ar/companion/` | Roaming behaviour and floor estimate. |
| `app/src/main/assets/models/` | Character models (GLB). |
| `app/src/test/` | Unit tests (pose solver, orientation, companions, catalog, …). |
| `content/markers/` | Printable marker sheets (PDF). |
| `content/qrsheet/` | PC tool that renders marker sheets. |
| `content/tools/` | Model helpers (glTF → GLB, placeholder cube). |

## Adding a character

1. Export or convert the model to **GLB** (`content/tools/gltf_to_glb.py` packs a
   `.gltf` with embedded buffers). Keep it light: a few thousand triangles.
2. Put it in `app/src/main/assets/models/`.
3. Add a `CatalogEntry` in `Catalog.kt` (and its id to `Catalog.trail` if it's to
   be collected). Animation names must match the clips in the GLB; the unit
   tests check that.
4. Render its marker. Codes carry `smvit-ar:<id>`:

   ```bash
   ./gradlew :qrsheet:run --args="content/markers/new.pdf 100 smvit-ar:<id>"
   ```

## Optional: self-contained toolchain

If this folder contains a `tools/` directory (git-ignored), `env.sh`, `gradlew`
and `studio.sh` use it instead of anything installed system-wide, keeping all
Android/Gradle state inside the folder:

```
tools/android-studio/   Android Studio (its bundled JDK is used for builds)
tools/android-sdk/      Android SDK
tools/gradle-home/      Gradle caches (GRADLE_USER_HOME)
tools/android-user/     adb keys, SDK settings (ANDROID_USER_HOME)
tools/studio-config/    Studio settings, caches, logs
```

`./studio.sh` starts that Studio. Without `tools/`, everything behaves as a
normal Android project.

## Notes from testing (Samsung Galaxy A53)

- ARCore is CPU-bound on mid-range phones: plane finding and depth are off, the
  display is capped at 60 Hz, and characters follow the QR code directly while
  it's in view rather than trusting ARCore's drifting position.
- Long sessions while charging can overheat the phone (thermal throttling), which
  makes tracking unreliable.
- `HIGH_SAMPLING_RATE_SENSORS` is required: without it the A53 delivered motion
  sensor data at 6 Hz and tracking never stabilised.

Models: Quaternius "Ultimate Animated Animals" (CC0).

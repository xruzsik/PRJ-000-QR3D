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
| `app/src/main/assets/route.json` | **Trail content**: each code's character, model, sizes, placement, animations and story; the stops in walking order. No code changes needed to edit it. |
| `app/src/main/java/com/smvit/ar/Catalog.kt` | Reads and checks `route.json`. |
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
3. Add it to `app/src/main/assets/route.json` under `"characters"`, and its id
   to `"stops"` if it's to be collected (see below).
4. Run `./gradlew :app:testDebugUnitTest`. The tests read the real route file
   and check that every model exists and every animation name is in its GLB.
5. Render its marker. Codes carry `smvit-ar:<id>`:

   ```bash
   ./gradlew :qrsheet:run --args="content/markers/new.pdf 100 smvit-ar:<id>"
   ```

## The route file

`app/src/main/assets/route.json`:

```json
{
  "name": "Postavičky na trase",
  "lengthKm": 3.5,
  "minutes": 90,
  "stops": ["fox", "wolf"],
  "aliases": { "test_cube": "fox" },
  "characters": [
    {
      "id": "fox",
      "name": "Líška",
      "model": "models/fox.glb",
      "qrSizeMeters": 0.10,
      "modelSizeMeters": 0.35,
      "roamSizeMeters": 0.8,
      "offset": { "x": 0.20, "y": 0, "z": 0, "yawDegrees": 0 },
      "animations": { "idle": "Idle", "walk": "Walk", "run": "Gallop" },
      "story": ["First sentence.", "Second sentence."]
    }
  ]
}
```

| Field | Meaning |
|---|---|
| `name`, `lengthKm`, `minutes` | Optional route info for the start screen. |
| `stops` | Character ids to collect, in walking order. Finding them all earns the badge. |
| `aliases` | Optional. Extra printed code ids that show an existing character. |
| `id` | Lowercase `a-z`, `0-9`, `_`. The code printed for it reads `smvit-ar:<id>`. |
| `name` | Shown on screen and in the collection. |
| `model` | GLB path under `app/src/main/assets/`. |
| `qrSizeMeters` | Printed edge of the QR symbol, without its white margin (0.10 for a 10 cm code). It must be right, or the character is placed at the wrong distance. |
| `modelSizeMeters` | Length of the model's longest side at the code. |
| `roamSizeMeters` | Optional. Size once it walks with the player; leave it out to keep the character on its code. |
| `offset` | Optional. Where it stands relative to the code, in metres: `x` to the code's right, `y` up, `z` towards the reader; `yawDegrees` turns it. |
| `animations` | Optional. Clip names in the GLB for standing (`idle`), walking and running. |
| `story` | Optional. One string, or a list of sentences joined with spaces. |

Plain JSON: no comments and no trailing commas. A mistake stops the app at
start with a message such as `route.json: character "fox": unknown field
"modelSize"`; the unit tests report the same message.

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

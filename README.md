# TempConvert

TempConvert is a simple Android temperature converter. The conversion calculations are written in C, and the Android user interface is written in Kotlin.

## Features
- Convert Celsius to Fahrenheit.
- Convert Fahrenheit to Celsius.
- Input validation for empty or invalid values.
- Clear/reset button.
- Results displayed to two decimal places.

## Formulas
- Fahrenheit = (Celsius × 9/5) + 32
- Celsius = (Fahrenheit − 32) × 5/9

## Technologies
- C for conversion calculations
- Kotlin for the Android interface
- Android NDK and CMake for native C integration
- GitHub Actions for cloud APK builds

## Build the APK using GitHub
1. Upload this project to a GitHub repository, keeping the folder structure.
2. Open the repository's **Actions** tab.
3. Select **Build TempConvert APK**.
4. Press **Run workflow** if it has not started automatically.
5. Open the completed workflow run.
6. Under **Artifacts**, download `TempConvert-debug-apk`.
7. Extract the downloaded ZIP to get `app-debug.apk`.
8. Open the APK on your Android phone and follow the installation prompts.

## Test examples
- 0 °C should convert to 32.00 °F.
- 100 °C should convert to 212.00 °F.
- 32 °F should convert to 0.00 °C.
- 212 °F should convert to 100.00 °C.

## Note
The generated debug APK is for testing and demonstration. It is not a Play Store release build.

#include <jni.h>

/*
 * Celsius to Fahrenheit:
 * F = (C * 9 / 5) + 32
 */
JNIEXPORT jdouble JNICALL
Java_com_example_tempconvert_MainActivity_celsiusToFahrenheit(
        JNIEnv *env, jobject thiz, jdouble celsius) {
    return (celsius * 9.0 / 5.0) + 32.0;
}

/*
 * Fahrenheit to Celsius:
 * C = (F - 32) * 5 / 9
 */
JNIEXPORT jdouble JNICALL
Java_com_example_tempconvert_MainActivity_fahrenheitToCelsius(
        JNIEnv *env, jobject thiz, jdouble fahrenheit) {
    return (fahrenheit - 32.0) * 5.0 / 9.0;
}

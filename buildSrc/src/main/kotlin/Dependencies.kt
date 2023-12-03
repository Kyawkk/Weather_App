import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

object Dependencies {
    const val composeNavigation = "androidx.navigation:navigation-compose:${Versions.NAVIGATION}"
    const val room = "androidx.room:room-runtime:${Versions.ROOM}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.ROOM}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.ROOM}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val daggerHilt = "com.google.dagger:hilt-android:${Versions.DAGGER}"
    const val daggerHiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.DAGGER}"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"
    const val coil = "io.coil-kt:coil-compose:${Versions.COIL}"
    const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:1.0.0"
}

fun DependencyHandler.composeNavigation() {
    implementation(Dependencies.composeNavigation)
}

fun DependencyHandler.room() {
    implementation(Dependencies.room)
    kapt(Dependencies.roomCompiler)
    implementation(Dependencies.roomKtx)
}

fun DependencyHandler.retrofit(){
    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofitGsonConverter)
    implementation(Dependencies.okHttp)
}

fun DependencyHandler.daggerHilt() {
    implementation(Dependencies.daggerHilt)
    implementation(Dependencies.hiltNavigationCompose)
    kapt(Dependencies.daggerHiltCompiler)
}

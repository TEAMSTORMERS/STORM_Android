import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `kotlin-dsl`
}
// Required since Gradle 4.10+.
repositories {
    google()
    jcenter()
    mavenCentral()
}

object PluginsVersions {
    const val GRADLE = "4.1.3"
    const val KOTLIN = "1.4.32"
    const val GOOGLE_SERVICE = "4.3.5"
    const val NAVIGATION = "2.3.5"
}

dependencies {
    implementation("com.android.tools.build:gradle:${PluginsVersions.GRADLE}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginsVersions.KOTLIN}")
    implementation("com.google.gms:google-services:${PluginsVersions.GOOGLE_SERVICE}")
    implementation("androidx.navigation:navigation-safe-args-gradle-plugin:${PluginsVersions.NAVIGATION}")
}
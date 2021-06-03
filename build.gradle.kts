// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    val kotlin_version by extra("1.5.0")
    repositories {
        google()
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:${BuildDependenciesVersions.GRADLE}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${BuildDependenciesVersions.KOTLIN}")
        classpath("com.google.gms:google-services:${BuildDependenciesVersions.GOOGLE_SERVICE}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${BuildDependenciesVersions.NAVIGATION}")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
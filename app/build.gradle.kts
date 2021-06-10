import dependencies.Dependencies
import dependencies.AnnotationProcessorsDependencies
import extensions.addTestsDependencies
import extensions.implementation
import extensions.kapt
import java.util.Properties
import java.io.FileInputStream

plugins {
    id(BuildPlugins.ANDROID_APPLICATION)
    id(BuildPlugins.KOTLIN_ANDROID)
    id(BuildPlugins.KOTLIN_ANDROID_EXTENSIONS)
    id(BuildPlugins.KOTLIN_KAPT)
    id(BuildPlugins.GOOGLE_SERVICES)
    id(BuildPlugins.NAVIGATION_SAFE_ARGS)
    id(BuildPlugins.HILT_PLUGIN)
}

val properties = Properties()
properties.load(FileInputStream(rootProject.file("./local.properties")))

android {
    compileSdkVersion(BuildAndroidConfig.COMPILE_SDK_VERSION)

    defaultConfig {
        applicationId = BuildAndroidConfig.APPLICATION_ID
        targetSdkVersion(BuildAndroidConfig.TARGET_SDK_VERSION)
        minSdkVersion(BuildAndroidConfig.MIN_SDK_VERSION)
        buildToolsVersion(BuildAndroidConfig.BUILD_TOOLS_VERSION)

        versionCode = BuildAndroidConfig.VERSION_CODE
        versionName = BuildAndroidConfig.VERSION_NAME

        testInstrumentationRunner = BuildAndroidConfig.TEST_INSTRUMENTATION_RUNNER
        buildConfigField("String", "BASE_URL", properties["baseUrl"] as String)
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }

        getByName("debug") {
        }
    }

    buildFeatures {
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    dependencies {
        implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
        implementation(Dependencies.KOTLIN)
        implementation(Dependencies.CORE_KTX)
        implementation(Dependencies.RECYCLE_VIEW)
        implementation(Dependencies.CONSTRAIN_LAYOUT)
        implementation(Dependencies.APPCOMPAT)
        implementation(Dependencies.CARD_VIEW)
        implementation(Dependencies.VIEW_PAGER)
        implementation(Dependencies.MATERIAL)
        implementation(Dependencies.NAVIGATION_FRAGMENT)
        implementation(Dependencies.NAVIGATION_UI)
        implementation(Dependencies.ROOM)
        kapt(AnnotationProcessorsDependencies.ROOM_COMPILER)

        implementation(Dependencies.FIREBASE_ANALYTICS)

        implementation(Dependencies.LOTTIE)
        implementation(Dependencies.GLIDE)
        kapt(AnnotationProcessorsDependencies.GLIDE_COMPILER)
        implementation(Dependencies.CIRCLE_IMAGE_VIEW)
        implementation(Dependencies.PICASSO)
        implementation(Dependencies.SOCKET)
        implementation(Dependencies.RETROFIT)
        implementation(Dependencies.RETROFIT_CONVERTER)
        implementation(Dependencies.FREE_DRAW_VIEW)
        implementation(Dependencies.TED_PERMISSION)
        implementation(Dependencies.WHEEL_PICKER)

        implementation(Dependencies.CHROME_TAB)

        implementation(Dependencies.HILT)
        kapt(AnnotationProcessorsDependencies.HILT_COMPILER)

        addTestsDependencies()
    }
}

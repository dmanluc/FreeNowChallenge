plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
    }

    sourceSets.getByName("main") {
        java.srcDir("src/main/java")
        java.srcDir("src/main/kotlin")
    }

    sourceSets.getByName("test") {
        java.srcDir("src/test/java")
        java.srcDir("src/test/kotlin")
    }

    val baseUrl = (project.property("POI_API_BASE_URL") as? String).orEmpty()
    buildTypes.forEach {
        it.buildConfigField("String", "BASE_URL", baseUrl)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    api(project(":domain"))

    implementation(AppDependencies.dataImplLibraries)
    kapt(AppDependencies.dataKaptLibraries)
    testImplementation(AppDependencies.testLibraries)
}
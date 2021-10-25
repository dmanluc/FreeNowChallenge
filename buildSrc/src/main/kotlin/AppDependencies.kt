import org.gradle.api.artifacts.dsl.DependencyHandler

object AppDependencies {
    // Kotlin
    val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"

    //Android
    private val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    private val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    private val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    private val materialDesign = "com.google.android.material:material:${Versions.materialDesign}"
    private val lifecycleKtx =
        "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleKtx}"
    private val lifecycleJava8 =
        "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycleJava8}"

    private val viewModelKtx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewModelKtx}"
    private val liveDataKtx =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.liveDataKtx}"
    private val navigationFragmentKtx =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigationFragmentKtx}"
    private val navigationUiKtx =
        "androidx.navigation:navigation-ui-ktx:${Versions.navigationUiKtx}"
    private val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    private val roomKapt = "androidx.room:room-compiler:${Versions.room}"
    private val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    private val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    private val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    private val swipeRefreshLayout =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}"
    private val maps = "com.google.android.gms:play-services-maps:${Versions.maps}"
    private val splash = "androidx.core:core-splashscreen:${Versions.splash}"

    //Frameworks
    private val javaxInject = "javax.inject:javax.inject:${Versions.javaxInject}"
    private val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    private val retrofitMoshiConverter =
        "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    private val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    private val moshiKotlinCodegen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    private val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    private val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    private val httpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.httpLoggingInterceptor}"


    private val arrowCore = "io.arrow-kt:arrow-core:${Versions.arrowCore}"
    private val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    private val glideCompilerKapt = "com.github.bumptech.glide:compiler:${Versions.glide}"
    private val lottie = "com.airbnb.android:lottie:${Versions.lottie}"

    //Testing
    private val kotlinCoroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinCoroutinesTest}"
    private val junit = "junit:junit:${Versions.junit}"
    private val kotlinJunit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
    private val mockWebServer = "com.squareup.okhttp:mockwebserver:${Versions.mockWebServer}"
    private val mockk = "io.mockk:mockk:${Versions.mockk}"
    private val androidXTestRunner = "androidx.test:runner:${Versions.androidXTestRunner}"
    private val androidXCoreTesting =
        "androidx.arch.core:core-testing:${Versions.androidXCoreTesting}"
    private val androidXJunitTesting = "androidx.test.ext:junit:${Versions.androidXJUnitTest}"

    private val androidXFragmentTesting =
        "androidx.fragment:fragment-testing:${Versions.androidXFragmentTesting}"
    private val mockkAndroid = "io.mockk:mockk-android:${Versions.mockk}"
    private val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    private val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
    private val espressoIdlingResource =
        "androidx.test.espresso:espresso-idling-resource:${Versions.espresso}"
    private val espressoIntents = "androidx.test.espresso:espresso-intents:${Versions.espresso}"
    private val kotestArrow = "io.kotest.extensions:kotest-assertions-arrow:${Versions.kotestArrow}"
    private val kotestCore = "io.kotest:kotest-assertions-core:${Versions.kotestCore}"

    val appLibraries = arrayListOf<String>().apply {
        add(kotlinStdLib)
        add(coreKtx)
        add(appcompat)
        add(constraintLayout)
        add(materialDesign)
        add(lifecycleKtx)
        add(lifecycleJava8)
        add(viewModelKtx)
        add(liveDataKtx)
        add(navigationFragmentKtx)
        add(navigationUiKtx)
        add(roomRuntime)
        add(roomKtx)
        add(hilt)
        add(hiltCompiler)
        add(maps)
        add(splash)
        add(retrofit)
        add(retrofitMoshiConverter)
        add(moshiKotlin)
        add(coroutinesAndroid)
        add(httpLoggingInterceptor)
        add(arrowCore)
        add(glide)
        add(lottie)
        add(androidXTestRunner)
        add(androidXCoreTesting)
        add(androidXJunitTesting)
        add(androidXFragmentTesting)
        add(espressoCore)
        add(espressoIdlingResource)
        add(swipeRefreshLayout)
    }

    val androidTestLibraries = arrayListOf<String>().apply {
        add(mockkAndroid)
        add(espressoContrib)
        add(espressoIntents)
    }

    val testLibraries = arrayListOf<String>().apply {
        add(kotlinCoroutinesTest)
        add(mockk)
        add(junit)
        add(mockWebServer)
        add(kotestCore)
        add(kotestArrow)
    }

    val kaptLibraries = arrayListOf<String>().apply {
        add(roomKapt)
        add(hiltCompiler)
        add(glideCompilerKapt)
        add(moshiKotlinCodegen)
    }

    val domainLibraries = arrayListOf<String>().apply {
        add(kotlinStdLib)
        add(javaxInject)
        add(coroutinesCore)
        add(arrowCore)
    }

    val domainTestLibraries = arrayListOf<String>().apply {
        add(kotlinJunit)
        add(junit)
        add(kotlinCoroutinesTest)
    }
}

fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}
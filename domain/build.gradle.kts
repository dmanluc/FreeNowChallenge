plugins {
    id("kotlin")
}

dependencies {
    implementation(AppDependencies.domainLibraries)
    testImplementation(AppDependencies.domainTestLibraries)
}
plugins {
    id("kotlin")
}

dependencies {
    implementation(AppDependencies.domainImplLibraries)
    testImplementation(AppDependencies.domainTestLibraries)
}
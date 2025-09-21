dependencies {
    api(api)
}
tasks {
    register<Jar>("coreReobfJar") {
        from(sourceSets["main"].output)
    }
}
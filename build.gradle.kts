import com.vanniktech.maven.publish.SonatypeHost

plugins {
    kotlin("jvm") version "2.0.10"
    id("com.gradleup.shadow") version "8.3.0"
    id("com.vanniktech.maven.publish") version "0.31.0"
    id("org.jetbrains.dokka") version "2.0.0"
}

group = "io.github.haburashi76"
version = "1.1"
allprojects {
    repositories {
        mavenCentral()
    }
}
rootProject.apply(plugin = "org.jetbrains.dokka")




subprojects {

    apply(plugin = "org.jetbrains.kotlin.jvm")

    repositories {
        maven("https://repo.papermc.io/repository/maven-public/")
    }

    dependencies {
        compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
        implementation(kotlin("stdlib"))
    }
}
listOf("api", "core").forEach { projectName ->
    project(":${rootProject.name}-$projectName") {

        apply(plugin = "org.jetbrains.dokka")
        apply(plugin = "com.vanniktech.maven.publish")

        tasks {
            create<Jar>("sourcesJar") {
                archiveClassifier.set("sources")
                from(sourceSets["main"].allSource)
            }

            create<Jar>("dokkaJar") {
                archiveClassifier.set("javadoc")
                dependsOn("dokkaHtml")

                from("$buildDir/dokka/html/") {
                    include("**")
                }
            }
        }
        mavenPublishing {
            coordinates(
                groupId = "io.github.haburashi76",
                artifactId = this@project.name,
                version = "1.0.7"
            )

            pom {
                name.set(this@project.name)
                description.set("Supporting Display Entity")
                url.set("https://github.com/haburashi76/${rootProject.name}")

                licenses {
                    license {
                        name.set("GNU General Public License version 3")
                        url.set("https://opensource.org/licenses/GPL-3.0")
                    }
                }

                developers {
                    developer {
                        id.set("haburashi76")
                        name.set("Haburashi76")
                        email.set("haburashi76@gmail.com")
                        url.set("https://github.com/haburashi76")
                        roles.addAll("developer")
                        timezone.set("Asia/Seoul")
                    }
                }


                scm {
                    connection.set("scm:git:git://github.com/haburashi76/${rootProject.name}.git")
                    developerConnection.set("scm:git:ssh://github.com:haburashi76/${rootProject.name}.git")
                    url.set("https://github.com/haburashi76/${rootProject.name}")
                }
            }
            publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
            signAllPublications()
        }
    }
}
afterEvaluate {
    publishing.publications.withType<MavenPublication>().configureEach {
        if (name == "core") {
            artifact(tasks.named("coreReobfJar"))
        }
    }
}
val targetJavaVersion = 17
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}

import org.gradle.api.Project
import org.gradle.jvm.tasks.Jar

private fun Project.subproject(name: String) = project(":${rootProject.name}-$name")
val Project.api: Project
    get() = subproject("api")
val Project.core: Project
    get() = subproject("core")

private fun Project.coreTask(name: String) = core.tasks.named(name, Jar::class.java)
val Project.coreReobfJar
    get() = coreTask("coreReobfJar")
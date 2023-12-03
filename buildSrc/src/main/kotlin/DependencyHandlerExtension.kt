import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

fun DependencyHandler.implementation(dependency: String) {
    add("implementation",dependency)
}

fun DependencyHandler.kapt(dependency: String) {
    add("kapt",dependency)
}

fun DependencyHandler.moduleImplementation(module: String) {
    add("implementation",project(module))
}
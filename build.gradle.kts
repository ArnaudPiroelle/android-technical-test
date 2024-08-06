import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    kotlin("plugin.serialization").version("1.9.10") apply false
    alias(libs.plugins.ksp)
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.androidLibrary) apply false
}

subprojects {
    apply(plugin = rootProject.libs.plugins.ksp.get().pluginId)

    tasks.withType(KotlinCompile::class.java).all {
        kotlinOptions.jvmTarget = "11"
    }
}
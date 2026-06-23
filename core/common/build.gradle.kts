plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "ru.practicum.shopping_list.core.common"
    //noinspection GradleDependency
    compileSdk = libs.versions.targetSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }
}

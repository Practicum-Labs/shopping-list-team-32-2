plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.practicum.list.feature.main"
    //noinspection GradleDependency
    compileSdk = libs.versions.targetSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlinOptions {
        jvmTarget = "21"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:mvi"))
    implementation(project(":core:design"))
    implementation(project(":core:common"))
    implementation(project(":core:navigation"))

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    detektPlugins(libs.detekt.formatting)
    implementation(libs.hilt)
    implementation(libs.hilt.compose)
    ksp(libs.hilt.compiler)
}

detekt {
    config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
    allRules = false
}

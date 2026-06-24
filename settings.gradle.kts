pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "shopping_list"
include(":app")
include(":core:common")
include(":core:mvi")
include(":core:design")
include(":core:navigation")
include(":core:data")
include(":feature:main")
include(":feature:product")

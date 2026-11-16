pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "PRJ-000-QR3D"
include(":app")

// PC-side tool that renders printable QR marker sheets (not part of the app).
include(":qrsheet")
project(":qrsheet").projectDir = file("content/qrsheet")

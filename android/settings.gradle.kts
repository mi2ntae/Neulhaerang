pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}


rootProject.name = "NeulHaeRang"
include(":app")
include(":ui")
include(":data")
include(":domain")
include(":common")
include(":watch")

// unity
include(":unityLibrary")
project(":unityLibrary").projectDir = file("..\\UnityProject\\androidBuild\\unityLibrary")
include (":unityLibrary:xrmanifest.androidlib")


dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://devrepo.kakao.com/nexus/content/groups/public/")
        maven(url = "https://jitpack.io")
        flatDir {
            dirs("${project(":unityLibrary").projectDir}\\libs")
        }
    }
}

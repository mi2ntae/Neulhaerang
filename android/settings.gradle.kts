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
        maven(url = "https://devrepo.kakao.com/nexus/content/groups/public/")
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "NeulHaeRang"
include(":app")
include(":ui")
include(":data")
include(":domain")
include(":common")
include(":watch")

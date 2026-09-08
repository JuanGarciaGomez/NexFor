rootProject.name = "NexFor"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":androidApp")
include(":shared")
include(":core:network")
include(":core:ui")
include(":core:database")
include(":core:common")
include(":domain")
include(":domain:appointment")
include(":domain:customer")
include(":domain:service")
include(":domain:login")
include(":data")
include(":data:appointment")
include(":data:customer")
include(":data:service")
include(":data:login")
include(":feature")
include(":feature:login")
include(":feature:appointment")
plugins {
    alias(libs.plugins.android.library)
    `maven-publish`
}

group = findProperty("GROUP")?.toString() ?: "com.github.AnkioTomas.shell"
version = findProperty("VERSION_NAME")?.toString() ?: "unspecified"

android {
    namespace = "net.ankio.shell"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }

    buildFeatures {
        aidl = true
        buildConfig = true
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    api(libs.kotlinx.coroutines.android)
    // Shizuku API - 用于以 shell/system 身份访问系统服务
    implementation(libs.shizuku.api)
    implementation(libs.shizuku.provider)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                from(components["release"])
                groupId = project.group.toString()
                artifactId = project.name
                version = project.version.toString()

                pom {
                    name.set(findProperty("POM_NAME")?.toString() ?: project.name)
                    description.set(findProperty("POM_DESCRIPTION")?.toString() ?: "")
                    url.set(findProperty("POM_URL")?.toString() ?: "")
                    licenses {
                        license {
                            name.set(findProperty("POM_LICENCE_NAME")?.toString() ?: "Apache License, Version 2.0")
                            url.set(findProperty("POM_LICENCE_URL")?.toString() ?: "https://www.apache.org/licenses/LICENSE-2.0")
                        }
                    }
                    developers {
                        developer {
                            id.set(findProperty("POM_DEVELOPER_ID")?.toString() ?: "AnkioTomas")
                            name.set(findProperty("POM_DEVELOPER_NAME")?.toString() ?: "Ankio")
                        }
                    }
                    scm {
                        url.set(findProperty("POM_SCM_URL")?.toString() ?: "")
                        connection.set(findProperty("POM_SCM_CONNECTION")?.toString() ?: "")
                        developerConnection.set(findProperty("POM_SCM_DEV_CONNECTION")?.toString() ?: "")
                    }
                }
            }
        }
    }
}

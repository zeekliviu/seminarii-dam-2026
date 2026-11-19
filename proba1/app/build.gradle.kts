plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "ro.ase.dam.examen"
    compileSdk {
        version = release(37)
    }

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "ro.ase.dam.examen.g1086"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    implementation(libs.activity.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.material)
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    testImplementation(libs.junit)
    testImplementation(libs.org.json)
}

tasks.register<Zip>("zipPentruMoodle") {
    group = "examen"
    description = "Arhiveaza doar codul sursa (src/main) pentru upload pe Moodle"
    archiveFileName.set("Solutie_ZEEK_Liviu-Ioan.zip")
    destinationDirectory.set(rootProject.layout.projectDirectory.dir("moodle"))
    from("src/main") {
        into("app/src/main")
    }
    exclude("**/build/**")
}

tasks.withType<Test>().configureEach {
    systemProperty("app.module.dir", project.projectDir.absolutePath)
    testLogging {
        events("passed", "failed", "skipped")
        showStandardStreams = true
    }
}

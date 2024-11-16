plugins {
    alias(libs.plugins.currencylist.android.library)
    alias(libs.plugins.currencylist.hilt.library)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.nhatbui.currency.ui"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(projects.currency.currencyPresentation)
    implementation(projects.common.commonPresentation)
    implementation(projects.common.commonUi)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.compose.material3)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.ui.tooling)

    // DI
    implementation(libs.hilt.navigation.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
}

plugins {
    alias(libs.plugins.currencylist.android.library)
}

android {
    namespace = "com.nhatbui.common.domain"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
}

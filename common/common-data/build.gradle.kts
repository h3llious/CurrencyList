plugins {
    alias(libs.plugins.currencylist.android.library)
}

android {
    namespace = "com.nhatbui.common.data"
}

dependencies {
    implementation(libs.gson)
}

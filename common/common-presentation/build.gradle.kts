plugins {
    alias(libs.plugins.currencylist.android.library)
    alias(libs.plugins.currencylist.hilt.library)
}

android {
    namespace = "com.nhatbui.common.presentation"
}

dependencies {
    implementation(projects.common.commonDomain)

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.lifecycle.viewmodel.ktx)
}

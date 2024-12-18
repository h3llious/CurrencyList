plugins {
    alias(libs.plugins.currencylist.android.library)
}

android {
    namespace = "com.nhatbui.currency.domain"
}

dependencies {
    implementation(projects.common.commonDomain)

    implementation(libs.kotlinx.coroutines.android)
}

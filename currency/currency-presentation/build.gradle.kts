plugins {
    alias(libs.plugins.currencylist.android.library)
    alias(libs.plugins.currencylist.hilt.library)
}

android {
    namespace = "com.nhatbui.currency.presentation"
}

dependencies {
    implementation(projects.currency.currencyDomain)
    implementation(projects.common.commonPresentation)
    implementation(projects.common.commonDomain)

    testImplementation(libs.junit)
}

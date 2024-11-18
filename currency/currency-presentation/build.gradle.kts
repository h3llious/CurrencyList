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

    testImplementation(projects.common.unitTest)
    testImplementation(libs.junit)
    testImplementation(libs.androidx.junit)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.kotlinx.coroutines.test)
}

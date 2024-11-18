plugins {
    alias(libs.plugins.currencylist.android.library)
}

android {
    namespace = "com.nhatbui.unit.test"
}

dependencies {
    implementation(projects.common.commonDomain)
    implementation(projects.common.commonPresentation)

    implementation(libs.junit)
    implementation(libs.androidx.junit)
    implementation(libs.mockito.kotlin)
    implementation(libs.mockito.inline)
    implementation(libs.kotlinx.coroutines.test)
}

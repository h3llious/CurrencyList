plugins {
    alias(libs.plugins.currencylist.android.library)
    alias(libs.plugins.currencylist.hilt.library)
}

android {
    namespace = "com.nhatbui.currency.presentation"
}

dependencies {
    implementation(projects.currency.currencyDomain)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

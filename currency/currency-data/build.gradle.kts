plugins {
    alias(libs.plugins.currencylist.android.library)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.nhatbui.currency.data"
}


dependencies {
    implementation(projects.currency.currencyDomain)
    implementation(projects.common.commonDomain)
    implementation(projects.common.commonData)

    // Room
    ksp(libs.room.compiler)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.datastore.preferences)

    testImplementation(libs.junit)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.kotlinx.coroutines.test)
}

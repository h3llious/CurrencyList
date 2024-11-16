plugins {
    alias(libs.plugins.currencylist.android.library)
}

android {
    namespace = "com.nhatbui.currency.data"
}


dependencies {
    implementation(projects.currency.currencyDomain)
}

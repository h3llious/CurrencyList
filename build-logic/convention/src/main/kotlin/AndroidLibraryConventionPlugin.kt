import com.android.build.gradle.LibraryExtension
import com.nhatbui.currencylist.logic.Versions.ANDROID_COMPILE_SDK_VERSION
import com.nhatbui.currencylist.logic.Versions.ANDROID_MIN_SDK_VERSION
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                compileSdk = ANDROID_COMPILE_SDK_VERSION

                defaultConfig {
                    minSdk = ANDROID_MIN_SDK_VERSION
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_11
                    targetCompatibility = JavaVersion.VERSION_11
                }


                defaultConfig.targetSdk = ANDROID_COMPILE_SDK_VERSION
                defaultConfig.consumerProguardFile("consumer-rules.pro")
                defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }
            configure<KotlinAndroidProjectExtension> {
                compilerOptions.jvmTarget.set(JvmTarget.JVM_11)
            }
        }
    }
}

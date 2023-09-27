plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
}

android {
	namespace = "com.sorokin.kirill.chat.emulator"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.sorokin.kirill.chat.emulator"
		minSdk = 21
		targetSdk = 33
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	testOptions {
		unitTests {
			all {
				it.useJUnitPlatform()
			}
			isReturnDefaultValues = true
			isIncludeAndroidResources = true
		}
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
	kotlinOptions {
		jvmTarget = "17"
	}
}

dependencies {

	val daggerVersion = "2.47"
	val mockkVersion = "1.13.5"

	implementation("androidx.core:core-ktx:1.12.0")
	implementation("androidx.appcompat:appcompat:1.6.1")
	implementation("com.google.android.material:material:1.9.0")
	implementation("androidx.constraintlayout:constraintlayout:2.1.4")

	implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
	implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

	//dagger
	implementation("com.google.dagger:dagger:$daggerVersion")
	annotationProcessor("com.google.dagger:dagger-compiler:$daggerVersion")

	testImplementation("com.google.truth:truth:1.1.3")
	testImplementation("junit:junit:4.13.2")
	testImplementation("org.junit.jupiter:junit-jupiter:5.9.3")
	testImplementation("io.mockk:mockk:$mockkVersion")
	testImplementation("io.mockk:mockk-android:$mockkVersion")
	testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
}
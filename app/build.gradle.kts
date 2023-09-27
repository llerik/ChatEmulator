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

	implementation("androidx.core:core-ktx:1.12.0")
	implementation("androidx.appcompat:appcompat:1.6.1")
	implementation("com.google.android.material:material:1.9.0")
	implementation("androidx.constraintlayout:constraintlayout:2.1.4")

	implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
	implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

	//dagger
	implementation("com.google.dagger:dagger:$daggerVersion")
	annotationProcessor("com.google.dagger:dagger-compiler:$daggerVersion")

	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
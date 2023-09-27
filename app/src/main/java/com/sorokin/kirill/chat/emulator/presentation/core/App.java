package com.sorokin.kirill.chat.emulator.presentation.core;

import android.app.Application;

import com.sorokin.kirill.chat.emulator.dagger.DaggerEmulatorComponent;
import com.sorokin.kirill.chat.emulator.dagger.EmulatorComponent;

public class App extends Application {

	private EmulatorComponent component;

	public EmulatorComponent getComponent() {
		return component;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		component = DaggerEmulatorComponent.create();
	}
}

package com.sorokin.kirill.chat.emulator.dagger;

import androidx.lifecycle.ViewModelProvider;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {EmulatorModule.class})
public interface EmulatorComponent {

	String CHAT_VIEW_MODEL = "ChatViewModel";

	@Named(CHAT_VIEW_MODEL)
	ViewModelProvider.Factory getChatViewModelFactory();

}

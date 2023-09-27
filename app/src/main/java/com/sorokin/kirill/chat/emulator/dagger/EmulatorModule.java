package com.sorokin.kirill.chat.emulator.dagger;

import static com.sorokin.kirill.chat.emulator.dagger.EmulatorComponent.CHAT_VIEW_MODEL;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.sorokin.kirill.chat.emulator.data.repository.MessagesRepositoryImpl;
import com.sorokin.kirill.chat.emulator.data.store.MessagesStore;
import com.sorokin.kirill.chat.emulator.data.store.MessagesStoreImpl;
import com.sorokin.kirill.chat.emulator.domain.chat.ChatEmulatorInteractor;
import com.sorokin.kirill.chat.emulator.domain.chat.ChatInteractor;
import com.sorokin.kirill.chat.emulator.domain.repository.MessagesRepository;
import com.sorokin.kirill.chat.emulator.presentation.chat.ChatViewModel;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class EmulatorModule {

	@Named(CHAT_VIEW_MODEL)
	@Provides
	public static ViewModelProvider.Factory provideChatViewModel(
		ChatInteractor chatInteractor,
		ChatEmulatorInteractor chatEmulatorInteractor
	) {
		return new ViewModelProvider.Factory() {
			@NonNull
			@Override
			public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
				//noinspection unchecked
				return (T) new ChatViewModel(chatInteractor, chatEmulatorInteractor);
			}
		};
	}

	@Provides
	@Singleton
	public static ChatInteractor provideChatInteractor(MessagesRepository repository) {
		return new ChatInteractor(repository);
	}

	@Provides
	@Singleton
	public static ChatEmulatorInteractor provideChatEmulatorInteractor(MessagesRepository repository) {
		return new ChatEmulatorInteractor(repository);
	}

	@Provides
	@Singleton
	public static MessagesRepository provideMessagesRepository(MessagesStore messagesStore) {
		return new MessagesRepositoryImpl(messagesStore);
	}

	@Provides
	@Singleton
	public static MessagesStore provideMessagesStore() {
		return new MessagesStoreImpl();
	}
}

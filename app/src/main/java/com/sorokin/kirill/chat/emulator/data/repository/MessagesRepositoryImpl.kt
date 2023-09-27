package com.sorokin.kirill.chat.emulator.data.repository

import android.util.Log
import com.sorokin.kirill.chat.emulator.data.store.MessagesStore
import com.sorokin.kirill.chat.emulator.domain.models.MessageModel
import com.sorokin.kirill.chat.emulator.domain.repository.MessagesRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class MessagesRepositoryImpl(
	private val store: MessagesStore
) : MessagesRepository {

	override fun getMessages(): Flow<List<MessageModel>> =
		callbackFlow {
			store.subscribe {
				Log.d(TAG, "getMessages: ")
				trySend(it)
			}
			awaitClose {
				Log.w(TAG, "getMessages: close")
				store.unsubscribe()
			}
		}

	override fun addMessage(text: String) {
		Log.d(TAG, "addMessage: ")
		store.addMessage(text)
	}

	override fun getLastMessage(): MessageModel? = store.getLastMessage()

	override fun updateMessage(message: MessageModel) {
		Log.d(TAG, "updateMessage: ")
		store.updateMessage(message)
	}

	companion object {
		private const val TAG = "MessagesRepositoryImpl"
	}
}
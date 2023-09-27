package com.sorokin.kirill.chat.emulator.domain.chat

import android.util.Log
import com.sorokin.kirill.chat.emulator.domain.repository.MessagesRepository
import kotlinx.coroutines.delay

class ChatEmulatorInteractor(
	private val repository: MessagesRepository
) {
	private var lastNumber = 1

	suspend fun posterStart() {
		while (true) {
			Log.d(TAG, "posterStart: delay")
			delay(ADD_MESSAGE_DELAY_IN_MILLS)
			addMessage()
		}
	}

	suspend fun updaterStart() {
		while (true) {
			Log.d(TAG, "updaterStart: delay")
			delay(UPDATE_MESSAGE_DELAY_IN_MILLS)
			updateMessage()
		}
	}

	private fun addMessage() {
		Log.d(TAG, "addMessage: ")
		runCatching {
			repository.addMessage("Message $lastNumber")
			lastNumber++
		}.onFailure {
			Log.e(TAG, "addMessage: ", it)
		}
	}

	private fun updateMessage() {
		Log.d(TAG, "updateMessage: ")
		runCatching {
			repository.getLastMessage()?.let {
				Log.d(TAG, "updateMessage: $it will be updated")
				repository.updateMessage(it.copy(isPlusShow = true))
			}
		}.onFailure {
			Log.e(TAG, "updateMessage: ", it)
		}
	}

	companion object {
		private const val TAG = "ChatEmulatorInteractor"
		private const val ADD_MESSAGE_DELAY_IN_MILLS = 1_000L
		private const val UPDATE_MESSAGE_DELAY_IN_MILLS = 2_000L
	}
}
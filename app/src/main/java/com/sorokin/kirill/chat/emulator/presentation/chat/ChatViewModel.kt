package com.sorokin.kirill.chat.emulator.presentation.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sorokin.kirill.chat.emulator.domain.chat.ChatEmulatorInteractor
import com.sorokin.kirill.chat.emulator.domain.chat.ChatInteractor
import com.sorokin.kirill.chat.emulator.domain.models.MessageModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ChatViewModel(
	private val chatInteractor: ChatInteractor,
	private val chatEmulatorInteractor: ChatEmulatorInteractor
) : ViewModel() {
	private var senderJob: Job? = null
	private var updaterJob: Job? = null

	private val messagesLiveData = MutableLiveData<List<MessageModel>>()

	fun getMessagesLiveData(): LiveData<List<MessageModel>> = messagesLiveData

	fun init() {
		Log.d(TAG, "init: ")
		subscribe()
	}

	fun sendMessage(text: String) {
		Log.d(TAG, "sendMessage: $text")
		viewModelScope.launch {
			runCatching {
				chatInteractor.sendText(text)
			}.onFailure {
				Log.e(TAG, "sendMessage: ", it)
			}
		}
	}

	private fun subscribe() {
		Log.d(TAG, "subscribe: ")
		viewModelScope.launch {
			chatInteractor.loadMessages()
				.collect {
					Log.d(TAG, "subscribe: collect: $it")
					messagesLiveData.value = it
				}
		}
	}


	fun start() {
		senderJob = viewModelScope.launch {
			runCatching {
				Log.d(TAG, "startEmulators: posterStart")
				chatEmulatorInteractor.posterStart()
			}.onFailure {
				Log.e(TAG, "startEmulators: posterStart: ", it)
			}
		}
		updaterJob = viewModelScope.launch {
			runCatching {
				Log.d(TAG, "startEmulators: updaterStart")
				chatEmulatorInteractor.updaterStart()
			}.onFailure {
				Log.e(TAG, "startEmulators: updaterStart: ", it)
			}
		}
	}

	fun stop() {
		senderJob?.cancel()
		updaterJob?.cancel()
	}

	companion object {
		private const val TAG = "ChatViewModel"
	}
}
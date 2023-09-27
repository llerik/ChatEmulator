package com.sorokin.kirill.chat.emulator.data.store

import android.util.Log
import com.sorokin.kirill.chat.emulator.domain.models.MessageModel

class MessagesStoreImpl : MessagesStore {

	private var subscriberLink: ((List<MessageModel>) -> Unit)? = null
	private val messages = mutableListOf<MessageModel>()

	override fun addMessage(text: String) {
		Log.d(TAG, "addMessage: ")
		synchronized(messages) {
			messages += MessageModel(id = messages.size, text = text, isPlusShow = false)
		}
		notifyUpdate()
	}

	override fun getLastMessage(): MessageModel? =
		synchronized(messages) {
			messages.lastOrNull()
		}

	override fun updateMessage(message: MessageModel) {
		Log.d(TAG, "updateMessage: $message")
		synchronized(messages) {
			messages[message.id] = message
		}
		notifyUpdate()
	}

	override fun subscribe(subscriber: (List<MessageModel>) -> Unit) {
		Log.d(TAG, "subscribe: ")
		subscriberLink = subscriber
	}

	override fun unsubscribe() {
		Log.d(TAG, "unsubscribe: ")
		subscriberLink = null
	}

	private fun notifyUpdate() {
		Log.d(TAG, "notifyUpdate: ...")
		subscriberLink?.let { callback ->
			Log.d(TAG, "notifyUpdate: invoke")
			synchronized(messages) {
				messages.toList().reversed()
			}.let { copy ->
				callback.invoke(copy)
			}
		}
	}

	companion object {
		private const val TAG = "MessagesStoreImpl"
	}
}
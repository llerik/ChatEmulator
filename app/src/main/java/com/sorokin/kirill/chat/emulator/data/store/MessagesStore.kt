package com.sorokin.kirill.chat.emulator.data.store

import com.sorokin.kirill.chat.emulator.domain.models.MessageModel

interface MessagesStore {

	fun addMessage(text: String)

	fun getLastMessage(): MessageModel?

	fun updateMessage(message: MessageModel)

	fun subscribe(subscriber: (List<MessageModel>)->Unit)

	fun unsubscribe()

}
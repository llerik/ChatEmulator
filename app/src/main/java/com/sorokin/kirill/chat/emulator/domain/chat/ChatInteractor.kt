package com.sorokin.kirill.chat.emulator.domain.chat

import com.sorokin.kirill.chat.emulator.domain.repository.MessagesRepository

class ChatInteractor(
	private val repository: MessagesRepository
) {

	fun loadMessages() = repository.getMessages()

	fun sendText(text: String) {
		repository.addMessage(text)
	}
}
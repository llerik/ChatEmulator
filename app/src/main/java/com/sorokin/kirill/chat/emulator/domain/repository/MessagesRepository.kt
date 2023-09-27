package com.sorokin.kirill.chat.emulator.domain.repository

import com.sorokin.kirill.chat.emulator.domain.models.MessageModel
import kotlinx.coroutines.flow.Flow

interface MessagesRepository {

	fun getMessages(): Flow<List<MessageModel>>

	fun addMessage(text: String)

	fun getLastMessage(): MessageModel?

	fun updateMessage(message: MessageModel)

}
package com.sorokin.kirill.chat.emulator.domain.models

data class MessageModel(
	val id: Int,
	val text: String,
	val isPlusShow: Boolean
)

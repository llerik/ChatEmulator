package com.sorokin.kirill.chat.emulator.presentation.chat.adapter

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.sorokin.kirill.chat.emulator.domain.models.MessageModel

class MessageDiffUtil: ItemCallback<MessageModel>() {
	override fun areItemsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean =
		oldItem.id == newItem.id

	override fun areContentsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean =
		oldItem == newItem
}
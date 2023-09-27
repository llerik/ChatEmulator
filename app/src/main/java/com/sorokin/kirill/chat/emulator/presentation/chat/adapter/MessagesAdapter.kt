package com.sorokin.kirill.chat.emulator.presentation.chat.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.sorokin.kirill.chat.emulator.R
import com.sorokin.kirill.chat.emulator.domain.models.MessageModel

class MessagesAdapter : ListAdapter<MessageModel, MessageViewHolder>(MessageDiffUtil()) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder =
		LayoutInflater.from(parent.context)
			.inflate(R.layout.view_holder_message_item, parent, false)
			.let { MessageViewHolder(it) }

	override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
		holder.bind(getItem(position))
	}
}
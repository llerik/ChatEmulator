package com.sorokin.kirill.chat.emulator.presentation.chat.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sorokin.kirill.chat.emulator.R
import com.sorokin.kirill.chat.emulator.domain.models.MessageModel

class MessageViewHolder(itemView: View) : ViewHolder(itemView) {
	private val textView: TextView = itemView.findViewById(R.id.message_text)
	private val plusImage: ImageView = itemView.findViewById(R.id.image_plus)

	fun bind(model: MessageModel) {
		plusImage.visibility = if (model.isPlusShow) View.VISIBLE else View.INVISIBLE
		textView.text = model.text
	}
}
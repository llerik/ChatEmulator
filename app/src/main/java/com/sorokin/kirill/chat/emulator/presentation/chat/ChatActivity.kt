package com.sorokin.kirill.chat.emulator.presentation.chat

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.sorokin.kirill.chat.emulator.R
import com.sorokin.kirill.chat.emulator.R.layout
import com.sorokin.kirill.chat.emulator.domain.models.MessageModel
import com.sorokin.kirill.chat.emulator.presentation.chat.adapter.MessagesAdapter
import com.sorokin.kirill.chat.emulator.presentation.core.App

class ChatActivity : AppCompatActivity() {
	private lateinit var textInputField: TextInputLayout
	private lateinit var viewModel: ChatViewModel
	private val messagesAdapter = MessagesAdapter()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(layout.activity_main)

		findViewById<RecyclerView>(R.id.recycler_view).apply {
			adapter = messagesAdapter
			layoutManager = LinearLayoutManager(this.context).apply {
				stackFromEnd = true
				reverseLayout = false
			}
		}
		textInputField = findViewById(R.id.text_field)

		var buttonIsEnable = true
		findViewById<ImageButton>(R.id.send_button).setOnClickListener {
			Log.d(TAG, "onCreate: button click : $buttonIsEnable")
			if (buttonIsEnable) {
				buttonIsEnable = false
				textInputField.editText?.text?.let {
					val text = it.toString()
					if (text.isNotBlank()) {
						Log.d(TAG, "onCreate: button click: $text")
						it.clear()
						viewModel.sendMessage(text)
						hideKeyboard(textInputField)
					}
				}
				buttonIsEnable = true
			}
		}

		viewModel = ViewModelProvider(
			viewModelStore, (application as App).component.chatViewModelFactory
		)[ChatViewModel::class.java]
		viewModel.init()
		viewModel.getMessagesLiveData().observe(this, ::updateMessages)
	}

	override fun onStart() {
		super.onStart()
		viewModel.start()
	}

	override fun onStop() {
		super.onStop()
		viewModel.stop()
	}

	private fun hideKeyboard(view: View) {
		getSystemService<InputMethodManager>()?.hideSoftInputFromWindow(view.windowToken, 0)
	}

	private fun updateMessages(messages: List<MessageModel>) {
		Log.d(TAG, "updateMessages: $messages")
		messagesAdapter.submitList(messages)
	}

	companion object {
		private const val TAG = "ChatActivity"
	}
}
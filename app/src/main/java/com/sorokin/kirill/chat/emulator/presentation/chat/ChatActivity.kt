package com.sorokin.kirill.chat.emulator.presentation.chat

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.sorokin.kirill.chat.emulator.R
import com.sorokin.kirill.chat.emulator.R.layout
import com.sorokin.kirill.chat.emulator.presentation.chat.adapter.MessagesAdapter

class ChatActivity : AppCompatActivity() {
	private lateinit var textInputField: TextInputLayout
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

						hideKeyboard(textInputField)
					}
				}
				buttonIsEnable = true
			}
		}
	}

	private fun hideKeyboard(view: View) {
		getSystemService<InputMethodManager>()?.hideSoftInputFromWindow(view.windowToken, 0)
	}

	companion object {
		private const val TAG = "ChatActivity"
	}
}
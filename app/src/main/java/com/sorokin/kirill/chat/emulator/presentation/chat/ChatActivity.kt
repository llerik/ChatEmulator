package com.sorokin.kirill.chat.emulator.presentation.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sorokin.kirill.chat.emulator.R.layout

class ChatActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(layout.activity_main)
	}
}
package com.sorokin.kirill.chat.emulator.domain.chat

import com.google.common.truth.Truth.assertThat
import com.sorokin.kirill.chat.emulator.domain.models.MessageModel
import com.sorokin.kirill.chat.emulator.domain.repository.MessagesRepository
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
internal class ChatInteractorTest {
	@MockK(relaxUnitFun = true)
	private lateinit var repository: MessagesRepository
	@MockK
	private lateinit var message: MessageModel
	private lateinit var interactor: ChatInteractor

	@BeforeEach
	fun setUp() {
		interactor = ChatInteractor(repository)
	}

	@Test
	fun loadMessages() = runTest {
		every { repository.getMessages() } returns flowOf(listOf(message))
		var messages: List<MessageModel>? = null
		launch { messages = interactor.loadMessages().first() }
		advanceUntilIdle()

		verify { repository.getMessages() }
		assertThat(messages)
			.containsExactly(message)
	}

	@Test
	fun sendText() {
		interactor.sendText("test")

		verify {
			repository.addMessage("test")
		}
	}
}
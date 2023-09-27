package com.sorokin.kirill.chat.emulator.data.repository

import com.google.common.truth.Truth.assertThat
import com.sorokin.kirill.chat.emulator.data.store.MessagesStore
import com.sorokin.kirill.chat.emulator.domain.models.MessageModel
import com.sorokin.kirill.chat.emulator.domain.repository.MessagesRepository
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
internal class MessagesRepositoryImplTest {
	@MockK(relaxUnitFun = true)
	private lateinit var store: MessagesStore
	@MockK
	private lateinit var message: MessageModel
	private lateinit var repository: MessagesRepository

	private lateinit var subscriber: (List<MessageModel>) -> Unit

	@BeforeEach
	fun setUp() {
		repository = MessagesRepositoryImpl(store)
		every { store.subscribe(any()) } answers {
			@Suppress("UNCHECKED_CAST")
			subscriber = it.invocation.args[0] as (List<MessageModel>) -> Unit
			assertThat(subscriber).isNotNull()
		}
	}

	@Test
	fun getMessages() = runTest {
		var result: List<MessageModel>? = null
		launch {
			result = repository.getMessages().first()
		}
		advanceUntilIdle() //init subscriber

		val messages = listOf(message)
		subscriber.invoke(messages)
		advanceUntilIdle() //collect messages

		assertThat(result)
			.containsExactlyElementsIn(messages)
			.inOrder()
	}

	@Test
	fun addMessage() {
		repository.addMessage("Test")

		verify {
			store.addMessage("Test")
		}
	}

	@Test
	fun lastMessage() {
		every { store.getLastMessage() } returns message

		assertThat(repository.getLastMessage())
			.isEqualTo(message)
	}

	@Test
	fun updateMessage() {
		repository.updateMessage(message)

		verify { store.updateMessage(message) }

	}
}
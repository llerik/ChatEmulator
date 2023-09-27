package com.sorokin.kirill.chat.emulator.data.store

import com.google.common.truth.Truth.assertThat
import com.sorokin.kirill.chat.emulator.domain.models.MessageModel
import io.mockk.called
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class MessagesStoreImplTest {

	private lateinit var store: MessagesStore

	@BeforeEach
	fun setUp() {
		store = MessagesStoreImpl()
	}

	@Test
	fun addMessage() {
		store.addMessage("test")
		assertThat(store.getLastMessage()).isEqualTo(MessageModel(0, "test", false))

		store.addMessage("test1")
		assertThat(store.getLastMessage()).isEqualTo(MessageModel(1, "test1", false))
	}

	@Test
	fun updateMessage() {
		store.addMessage("test")
		assertThat(store.getLastMessage()).isEqualTo(MessageModel(0, "test", false))

		store.updateMessage(MessageModel(0, "test2", true))
		assertThat(store.getLastMessage()).isEqualTo(MessageModel(0, "test2", true))
	}

	@Test
	fun subscribe() {
		val subscriber = mockk<(List<MessageModel>) -> Unit>()
		every { subscriber.invoke(any()) } returns Unit
		store.subscribe(subscriber)

		store.addMessage("test")
		verify {
			subscriber.invoke(listOf(MessageModel(0, "test", false)))
		}

		store.addMessage("test1")
		verify {
			subscriber.invoke(
				listOf(
					MessageModel(1, "test1", false),
					MessageModel(0, "test", false)
				)
			)
		}

		store.updateMessage(MessageModel(0, "test", true))
		verify {
			subscriber.invoke(
				listOf(
					MessageModel(1, "test1", false),
					MessageModel(0, "test", true)
				)
			)
		}

		clearMocks(subscriber)
		store.unsubscribe()
		store.addMessage("test")
		verify {
			subscriber wasNot called
		}
	}
}
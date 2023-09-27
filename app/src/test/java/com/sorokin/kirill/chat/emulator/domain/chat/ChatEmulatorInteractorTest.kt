package com.sorokin.kirill.chat.emulator.domain.chat

import com.sorokin.kirill.chat.emulator.domain.models.MessageModel
import com.sorokin.kirill.chat.emulator.domain.repository.MessagesRepository
import io.mockk.called
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
internal class ChatEmulatorInteractorTest {
	@MockK(relaxUnitFun = true)
	private lateinit var repository: MessagesRepository
	private lateinit var interactor: ChatEmulatorInteractor

	@BeforeEach
	fun setUp() {
		interactor = ChatEmulatorInteractor(repository)
	}

	@Test
	fun posterStart() = runTest {
		val job = launch { interactor.posterStart() }

		verify { repository wasNot called }

		advanceTimeBy(1_100)

		verify { repository.addMessage("Message 1") }

		advanceTimeBy(1_100)
		verify {
			repository.addMessage("Message 1")
			repository.addMessage("Message 2")
		}

		job.cancel()
	}

	@Test
	fun updaterStart() = runTest {
		every { repository.getLastMessage() } returns MessageModel(0, "test", false)
		val job = launch { interactor.updaterStart() }

		verify { repository wasNot called }

		advanceTimeBy(2_100)

		verify {
			repository.getLastMessage()
			repository.updateMessage(MessageModel(0, "test", true))
		}

		every { repository.getLastMessage() } returns MessageModel(1, "test", false)
		advanceTimeBy(2_100)

		verify {
			repository.getLastMessage()
			repository.updateMessage(MessageModel(0, "test", true))
			repository.updateMessage(MessageModel(1, "test", true))
		}
		job.cancel()
	}
}
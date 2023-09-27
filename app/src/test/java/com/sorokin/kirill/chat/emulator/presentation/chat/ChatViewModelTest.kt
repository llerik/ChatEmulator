package com.sorokin.kirill.chat.emulator.presentation.chat

import androidx.lifecycle.Observer
import com.sorokin.kirill.chat.emulator.InstantExecutorExtension
import com.sorokin.kirill.chat.emulator.domain.chat.ChatEmulatorInteractor
import com.sorokin.kirill.chat.emulator.domain.chat.ChatInteractor
import com.sorokin.kirill.chat.emulator.domain.models.MessageModel
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(value = [InstantExecutorExtension::class, MockKExtension::class])
internal class ChatViewModelTest {

	@MockK(relaxUnitFun = true)
	private lateinit var chatInteractor: ChatInteractor

	@MockK(relaxUnitFun = true)
	private lateinit var chatEmulatorInteractor: ChatEmulatorInteractor

	@MockK(relaxUnitFun = true)
	private lateinit var messageObserver: Observer<List<MessageModel>>

	@MockK
	private lateinit var message: MessageModel

	private lateinit var viewModel: ChatViewModel
	private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

	@BeforeEach
	fun setUp() {
		Dispatchers.setMain(testDispatcher)
		every { chatInteractor.loadMessages() } returns flowOf(listOf(message))

		viewModel = ChatViewModel(chatInteractor, chatEmulatorInteractor)

	}

	@AfterEach
	fun setDown() {
		Dispatchers.resetMain()
	}

	@Test
	fun init() = runTest {
		viewModel.getMessagesLiveData().observeForever(messageObserver)

		viewModel.init()

		verify {
			messageObserver.onChanged(listOf(message))
		}
	}

	@Test
	fun sendMessage() {
		viewModel.sendMessage("test")

		verify {
			chatInteractor.sendText("test")
		}
	}

	@Test
	fun start() = runTest {
		viewModel.start()

		coVerify {
			chatEmulatorInteractor.posterStart()
			chatEmulatorInteractor.updaterStart()
		}

		viewModel.stop()
	}
}
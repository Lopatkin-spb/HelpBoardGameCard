package space.lopatkin.spb.helpboardgamecard.presentation.settings

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import space.lopatkin.spb.helpboardgamecard.di.ApplicationModule
import space.lopatkin.spb.helpboardgamecard.domain.model.Completable
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetKeyboardTypeUseCase
import space.lopatkin.spb.helpboardgamecard.domain.usecase.SaveKeyboardTypeByUserChoiceUseCase

class SettingsViewModelTest {

    private lateinit var viewModel: SettingsViewModel
    private var mockSaveKeyboardTypeByUserChoiceUseCase = mock<SaveKeyboardTypeByUserChoiceUseCase>()
    private var mockGetKeyboardTypeUseCase = mock<GetKeyboardTypeUseCase>()
    private var mockDispatchers = mock<ApplicationModule.CoroutineDispatchers>()

    @BeforeEach
    fun setUp() {
        viewModel = SettingsViewModel(
            mockSaveKeyboardTypeByUserChoiceUseCase,
            mockGetKeyboardTypeUseCase,
            mockDispatchers
        )
        Dispatchers.setMain(StandardTestDispatcher())
        // For live data loopers
        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) {
                runnable.run()
            }

            override fun postToMainThread(runnable: Runnable) {
                runnable.run()
            }

            override fun isMainThread(): Boolean {
                return true
            }
        })
    }

    @AfterEach
    fun tearDown() {
        Mockito.reset(mockSaveKeyboardTypeByUserChoiceUseCase)
        Mockito.reset(mockGetKeyboardTypeUseCase)
        Mockito.reset(mockDispatchers)
        Dispatchers.resetMain()
        ArchTaskExecutor.getInstance().setDelegate(null)
    }

    @Test
    fun settingsViewModel_LoadKeyboardType_ReturnKeyboardType() = runTest {
        val repositoryResult = flowOf(KeyboardType.DEFAULT)
        Mockito.`when`(mockGetKeyboardTypeUseCase.execute()).thenReturn(repositoryResult)
        Mockito.`when`(mockDispatchers.main()).thenReturn(StandardTestDispatcher())

        viewModel.loadKeyboardType() // Start method with coroutine
        advanceUntilIdle() // Start coroutine
        val actual = viewModel.uiState.value?.keyboard // This must init after started testing method
        val expected = KeyboardType.DEFAULT

        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun settingsViewModel_LoadKeyboardTypeReturnError_SetDefaultKeyboardType() = runTest {
        val throwError = true
        val repositoryResult = flow {
            if (throwError) throw Exception()
            else emit(KeyboardType.DEFAULT)
        }
        Mockito.`when`(mockGetKeyboardTypeUseCase.execute()).thenReturn(repositoryResult)
        Mockito.`when`(mockDispatchers.main()).thenReturn(StandardTestDispatcher())

        viewModel.loadKeyboardType() // Start method with coroutine
        advanceUntilIdle() // Start coroutine
        val actual = viewModel.uiState.value?.keyboard // This must init after started testing method
        val expected = KeyboardType.CUSTOM

        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun settingsViewModel_SaveCorrectKeyboardType_NotThrowErrorAndNotInvokeLoad() = runTest {
        val repositoryResult = flowOf(Completable.onComplete(Message.ACTION_ENDED_SUCCESS))
        Mockito.`when`(mockSaveKeyboardTypeByUserChoiceUseCase.execute(any())).thenReturn(repositoryResult)
        Mockito.`when`(mockDispatchers.main()).thenReturn(StandardTestDispatcher())
        val testData = "CUSTOM"

        viewModel.saveKeyboardType(testData)
        advanceUntilIdle()
        val expected = Message.ACTION_ENDED_ERROR
        val actual = viewModel.uiState.value?.message // This must init after started testing method

        assertNotEquals(
            expected,
            actual
        )
    }

    @Test
    fun settingsViewModel_SaveKeyboardTypeReturnError_ShowMessageErrorAndInvokeLoad() = runTest {
        val throwError = true
        val repositoryResult = flow {
            if (throwError) throw Exception()
            else emit(Completable.onComplete(Message.ACTION_ENDED_SUCCESS))
        }
        Mockito.`when`(mockSaveKeyboardTypeByUserChoiceUseCase.execute(any())).thenReturn(repositoryResult)
        Mockito.`when`(mockDispatchers.main()).thenReturn(StandardTestDispatcher())
        val testData = null

        viewModel.saveKeyboardType(testData)
        advanceUntilIdle()
        val expected = Message.ACTION_ENDED_ERROR
        val actual = viewModel.uiState.value?.message // This must init after started testing method

        Mockito.verify(mockGetKeyboardTypeUseCase, Mockito.times(1)).execute()
        assertEquals(
            expected,
            actual
        )
    }

}
package space.lopatkin.spb.helpboardgamecard.domain.usecase

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import space.lopatkin.spb.helpboardgamecard.domain.model.Completable
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.repository.SettingsRepository

class SaveKeyboardTypeByUserChoiceUseCaseTest {
    private lateinit var usecase: SaveKeyboardTypeByUserChoiceUseCase
    private val mockRepository = mock<SettingsRepository>()

    @BeforeEach
    fun setUp() {
        usecase = SaveKeyboardTypeByUserChoiceUseCase(mockRepository)
    }

    @AfterEach
    fun tearDown() {
        Mockito.reset(mockRepository)
    }

    @Test
    fun saveKeyboardType_CorrectUserChoice_InvokedRepositoryAndReturnSuccess() = runTest {
        val repositoryResult = flowOf(Completable.onComplete(Message.ACTION_ENDED_SUCCESS))
        Mockito.`when`(mockRepository.saveKeyboardType(any())).thenReturn(repositoryResult)
        val testData = "DEFAULT"

        val actual = usecase.execute(testData)
            .firstOrNull()
        val expected = Completable.onComplete(Message.ACTION_ENDED_SUCCESS)

        Mockito.verify(mockRepository, Mockito.times(1)).saveKeyboardType(KeyboardType.DEFAULT)
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun saveKeyboardType_IncorrectUserChoice_InvokedRepositoryAndReturnSuccess() = runTest {
        val repositoryResult = flowOf(Completable.onComplete(Message.ACTION_ENDED_SUCCESS))
        Mockito.`when`(mockRepository.saveKeyboardType(any())).thenReturn(repositoryResult)
        val testData = "rtyuui"

        val actual = usecase.execute(testData)
            .firstOrNull()
        val expected = Completable.onComplete(Message.ACTION_ENDED_SUCCESS)

        Mockito.verify(mockRepository, Mockito.times(1)).saveKeyboardType(KeyboardType.CUSTOM)
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun saveKeyboardType_IncorrectUserChoice_NotInvokedRepositoryAndThrowException() = runTest {
        Mockito.`when`(mockRepository.saveKeyboardType(any())).thenReturn(emptyFlow())
        val testData = null

        usecase.execute(testData)
            .catch { actual ->
                assertInstanceOf(
                    Exception::class.java,
                    actual
                )
            }
            .firstOrNull()

        Mockito.verify(mockRepository, Mockito.times(0)).saveKeyboardType(any())
    }

}
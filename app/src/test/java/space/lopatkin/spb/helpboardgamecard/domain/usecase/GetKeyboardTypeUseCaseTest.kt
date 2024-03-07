package space.lopatkin.spb.helpboardgamecard.domain.usecase

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.mockito.Mockito
import org.mockito.kotlin.mock
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType
import space.lopatkin.spb.helpboardgamecard.domain.repository.SettingsRepository

class GetKeyboardTypeUseCaseTest {
    private lateinit var usecase: GetKeyboardTypeUseCase
    private val mockRepository = mock<SettingsRepository>()

    @BeforeEach
    fun setUp() {
        usecase = GetKeyboardTypeUseCase(mockRepository)
    }

    @AfterEach
    fun tearDown() {
        Mockito.reset(mockRepository);
    }

    @Test
    fun getKeyboardType_ReturnKeyboardType() = runTest {
        val repositoryResult = flowOf(KeyboardType.DEFAULT)
        Mockito.`when`(mockRepository.getKeyboardType()).thenReturn(repositoryResult)

        val actual = usecase.execute().firstOrNull()
        val expected = KeyboardType.DEFAULT

        assertEquals(
            expected,
            actual
        )

    }

    @Test
    fun getKeyboardType_ReturnException() = runTest {
        val throwError = true
        val repositoryResult = flow {
            if (throwError) throw Exception()
            else emit(KeyboardType.DEFAULT)
        }
        Mockito.`when`(mockRepository.getKeyboardType()).thenReturn(repositoryResult)

        usecase.execute()
            .catch { actual ->
                assertInstanceOf(
                    Exception::class.java,
                    actual
                )
            }
            .firstOrNull()
    }

}
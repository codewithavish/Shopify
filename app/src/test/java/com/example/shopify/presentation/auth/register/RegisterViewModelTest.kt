package com.example.shopify.presentation.auth.register

import com.example.shopify.core.common.Resource
import com.example.shopify.domain.model.User
import com.example.shopify.domain.repository.AuthRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(org.robolectric.RobolectricTestRunner::class)
class RegisterViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var authRepository: AuthRepository
    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setUp() {
        authRepository = mockk()
        viewModel = RegisterViewModel(authRepository)
    }

    @Test
    fun `initial state is Idle`() {
        // Arrange
        // ViewModel is created in setUp()

        // Assert
        assertEquals(RegisterUiState.Idle, viewModel.uiState.value)
    }

    @Test
    fun `register with blank name emits name required error`() =
        runTest {
            // Act
            viewModel.register(
                name = "",
                email = "john@example.com",
                password = "secret123"
            )

            // Assert
            assertEquals(
                RegisterUiState.Error("Name is required"),
                viewModel.uiState.value
            )

            verify(exactly = 0) {
                authRepository.register(any(), any(), any())
            }
        }

    @Test
    fun `register with blank email emits email required error`() =
        runTest {
            // Act
            viewModel.register(
                name = "John",
                email = " ",
                password = "secret123"
            )

            // Assert
            assertEquals(
                RegisterUiState.Error("Email is required"),
                viewModel.uiState.value
            )

            verify(exactly = 0) {
                authRepository.register(any(), any(), any())
            }
        }

    @Test
    fun `register with invalid email emits validation error`() =
        runTest {
            // Act
            viewModel.register(
                name = "John",
                email = "invalid-email",
                password = "secret123"
            )

            // Assert
            assertEquals(
                RegisterUiState.Error(
                    "Enter a valid email address"
                ),
                viewModel.uiState.value
            )

            verify(exactly = 0) {
                authRepository.register(any(), any(), any())
            }
        }

    @Test
    fun `register with short password emits validation error`() =
        runTest {
            // Act
            viewModel.register(
                name = "John",
                email = "john@example.com",
                password = "12345"
            )

            // Assert
            assertEquals(
                RegisterUiState.Error(
                    "Password must contain at least 6 characters"
                ),
                viewModel.uiState.value
            )

            verify(exactly = 0) {
                authRepository.register(any(), any(), any())
            }
        }

    @Test
    fun `register with valid input emits success`() = runTest {
        // Arrange
        val user = mockk<User>()

        every {
            authRepository.register(
                name = "John",
                email = "john@example.com",
                password = "secret123"
            )
        } returns flowOf(
            Resource.Loading,
            Resource.Success(user)
        )

        // Act
        viewModel.register(
            name = "John",
            email = "john@example.com",
            password = "secret123"
        )

        advanceUntilIdle()

        // Assert
        assertEquals(
            RegisterUiState.Success(user),
            viewModel.uiState.value
        )

        verify(exactly = 1) {
            authRepository.register(
                name = "John",
                email = "john@example.com",
                password = "secret123"
            )
        }
    }

    @Test
    fun `register when repository fails emits error`() = runTest {
        // Arrange
        every {
            authRepository.register(any(), any(), any())
        } returns flowOf(
            Resource.Error("Email already exists")
        )

        // Act
        viewModel.register(
            name = "John",
            email = "john@example.com",
            password = "secret123"
        )

        advanceUntilIdle()

        // Assert
        assertEquals(
            RegisterUiState.Error("Email already exists"),
            viewModel.uiState.value
        )
    }

    @Test
    fun `register exposes loading state while request is running`() =
        runTest {
            // Arrange
            val user = mockk<User>()

            every {
                authRepository.register(any(), any(), any())
            } returns flow {
                emit(Resource.Loading)
                delay(100)
                emit(Resource.Success(user))
            }

            // Act
            viewModel.register(
                name = "John",
                email = "john@example.com",
                password = "secret123"
            )

            runCurrent()

            // Assert: request is still running
            assertEquals(
                RegisterUiState.Loading,
                viewModel.uiState.value
            )

            // Advance virtual time and execute all remaining work
            advanceUntilIdle()

            // Assert: request completed successfully
            assertEquals(
                RegisterUiState.Success(user),
                viewModel.uiState.value
            )
        }
}
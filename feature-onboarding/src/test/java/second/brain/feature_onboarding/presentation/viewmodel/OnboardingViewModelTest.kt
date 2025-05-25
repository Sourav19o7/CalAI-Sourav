package second.brain.feature_onboarding.presentation.viewmodel

import data.repository.DataPreferencesManager
import data.repository.DataRepository
import domain.User
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import second.brain.feature_onboarding.domain.repository.OnboardingRepository
import second.brain.feature_onboarding.presentation.authentication.SignInResult
import second.brain.feature_onboarding.presentation.authentication.UserData
import second.brain.feature_onboarding.presentation.event.OnboardingEvent

@OptIn(ExperimentalCoroutinesApi::class)
class OnboardingViewModelTest {

    private lateinit var viewModel: OnboardingViewModel
    private lateinit var onboardingRepository: OnboardingRepository
    private lateinit var dataRepository: DataRepository
    private lateinit var dataPreferencesManager: DataPreferencesManager

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        onboardingRepository = mockk()
        dataRepository = mockk(relaxed = true)
        dataPreferencesManager = mockk(relaxed = true)

        // Mock initial logged in state as false by default
        every { dataPreferencesManager.isLoggedIn } returns false
    }

    private fun createViewModel(): OnboardingViewModel {
        return OnboardingViewModel(
            dataRepository = dataRepository,
            dataPreferencesManager = dataPreferencesManager
        )
    }

    @Test
    fun `SignInSuccess event with successful result`() = runTest {

        // Given
        val userData = UserData(
            email = "test@example.com",
            name = "John Doe",
            id = "123",
            photoUrl = "https://photo.url"
        )
        val signInResult = SignInResult(
            data = userData,
            errorMessage = null
        )

        viewModel = createViewModel()

        // When
        viewModel.onEvent(OnboardingEvent.SignInSuccess(signInResult))

        // Then
        val state = viewModel.state.first()

        assertTrue(state.isSignInSuccessful)
        assertNull(state.signInErrorMessage)
        assertEquals("test@example.com", state.email)
        assertEquals("John Doe", state.name)
        assertEquals("https://photo.url", state.photoUrl)

        // Verify repository interactions
        coVerify {
            dataRepository.updateData(
                User(
                    name = "John Doe",
                    email = "test@example.com",
                    profileImage = "https://photo.url",
                    userId = "123"
                )
            )
        }

        verify { dataPreferencesManager.isLoggedIn = true }
    }

    @Test
    fun `SignInSuccess event with failed result - null data + error message`() = runTest {
        val signInResult = SignInResult(
            data = null,
            errorMessage = "Authentication failed"
        )

        viewModel = createViewModel()

        // When
        viewModel.onEvent(OnboardingEvent.SignInSuccess(signInResult))

        // Then
        val state = viewModel.state.first()

        assertFalse(state.isSignInSuccessful)
        assertEquals("Authentication failed", state.signInErrorMessage)
        assertEquals("", state.email)
        assertEquals("", state.name)
        assertEquals("", state.photoUrl)

        // Verify repository methods are NOT called
        coVerify(exactly = 0) { dataRepository.updateData(any()) }
        verify(exactly = 0) { dataPreferencesManager.isLoggedIn = true }
    }

    @Test
    fun `SignInSuccess event with failed result - null data + null error message`() = runTest {

        val signInResult = SignInResult(
            data = null,
            errorMessage = null
        )

        viewModel = createViewModel()

        // When
        viewModel.onEvent(OnboardingEvent.SignInSuccess(signInResult))

        // Then
        val state = viewModel.state.first()

        assertFalse(state.isSignInSuccessful)
        assertNull(state.signInErrorMessage)
        assertEquals("", state.email)
        assertEquals("", state.name)
        assertEquals("", state.photoUrl)

        // Verify repository methods are NOT called
        coVerify(exactly = 0) { dataRepository.updateData(any()) }
        verify(exactly = 0) { dataPreferencesManager.isLoggedIn = true }
    }

    @Test
    fun `SignInSuccess event with partial user data`() = runTest {

        val userData = UserData(
            email = "test@example.com",
            name = "", // Empty name
            id = "123",
            photoUrl = "" // Empty photo URL
        )
        val signInResult = SignInResult(
            data = userData,
            errorMessage = null
        )

        viewModel = createViewModel()

        // When
        viewModel.onEvent(OnboardingEvent.SignInSuccess(signInResult))

        // Then
        val state = viewModel.state.first()

        assertTrue(state.isSignInSuccessful)
        assertNull(state.signInErrorMessage)
        assertEquals("test@example.com", state.email)
        assertEquals("", state.name) // Empty name propagated
        assertEquals("", state.photoUrl) // Empty photo URL propagated

        // Verify repository is called with partial data
        coVerify {
            dataRepository.updateData(
                User(
                    name = "",
                    email = "test@example.com",
                    profileImage = "",
                    userId = "123"
                )
            )
        }

        verify { dataPreferencesManager.isLoggedIn = true }
    }

    @Test
    fun `SignInSuccess event - dataRepository updateData throws exception`() = runTest {
        // Verify the ViewModel handles exceptions from dataRepository.updateData gracefully.
        // Check if the state reflects the sign-in success parts, and the app doesn't crash.
        // Ensure userLoggedIn() is still called.

        // Given
        val userData = UserData(
            email = "test@example.com",
            name = "John Doe",
            id = "123",
            photoUrl = "https://photo.url"
        )
        val signInResult = SignInResult(
            data = userData,
            errorMessage = null
        )

        coEvery { dataRepository.updateData(any()) } throws RuntimeException("Database error")

        viewModel = createViewModel()

        // When
        viewModel.onEvent(OnboardingEvent.SignInSuccess(signInResult))

        // Then - Should not crash and state should still be updated
        val state = viewModel.state.first()

        assertTrue(state.isSignInSuccessful)
        assertNull(state.signInErrorMessage)
        assertEquals("test@example.com", state.email)
        assertEquals("John Doe", state.name)
        assertEquals("https://photo.url", state.photoUrl)

        // Verify repository was called (and threw exception)
        coVerify { dataRepository.updateData(any()) }

        // userLoggedIn() should still be called despite repository exception
        verify { dataPreferencesManager.isLoggedIn = true }
    }

    @Test
    fun `ResetGoogleState event`() = runTest {
        // Verify state updates correctly when ResetGoogleState event is received.
        // Check isSignInSuccessful is set to false and signInErrorMessage is set to null.

        // Given - First set some success state
        val userData = UserData(
            email = "test@example.com",
            name = "John Doe",
            id = "123",
            photoUrl = "https://photo.url"
        )
        val signInResult = SignInResult(data = userData, errorMessage = null)

        viewModel = createViewModel()
        viewModel.onEvent(OnboardingEvent.SignInSuccess(signInResult))

        // When
        viewModel.onEvent(OnboardingEvent.ResetGoogleState)

        // Then
        val state = viewModel.state.first()

        assertFalse(state.isSignInSuccessful)
        assertNull(state.signInErrorMessage)
        // User data should remain unchanged
        assertEquals("test@example.com", state.email)
        assertEquals("John Doe", state.name)
        assertEquals("https://photo.url", state.photoUrl)
    }

    @Test
    fun `Multiple SignInSuccess events - successful then failed`() = runTest {
        // Send a successful SignInSuccess, then a failed one.
        // Verify the state correctly reflects the outcome of the last event.

        // Given
        val successfulUserData = UserData(
            email = "success@example.com",
            name = "Success User",
            id = "123",
            photoUrl = "https://success.url"
        )
        val successResult = SignInResult(data = successfulUserData, errorMessage = null)
        val failedResult = SignInResult(data = null, errorMessage = "Second attempt failed")

        viewModel = createViewModel()

        // When - First successful
        viewModel.onEvent(OnboardingEvent.SignInSuccess(successResult))

        // Then - Second failed
        viewModel.onEvent(OnboardingEvent.SignInSuccess(failedResult))

        // Then
        val state = viewModel.state.first()

        // Should reflect the failed state
        assertFalse(state.isSignInSuccessful)
        assertEquals("Second attempt failed", state.signInErrorMessage)
        // But user data from successful attempt should remain
        assertEquals("success@example.com", state.email)
        assertEquals("Success User", state.name)
        assertEquals("https://success.url", state.photoUrl)
    }

    @Test
    fun `Multiple SignInSuccess events - failed then successful`() = runTest {

        val failedResult = SignInResult(data = null, errorMessage = "First attempt failed")
        val successfulUserData = UserData(
            email = "success@example.com",
            name = "Success User",
            id = "456",
            photoUrl = "https://success.url"
        )
        val successResult = SignInResult(data = successfulUserData, errorMessage = null)

        viewModel = createViewModel()

        // When - First failed
        viewModel.onEvent(OnboardingEvent.SignInSuccess(failedResult))

        // Then - Second successful
        viewModel.onEvent(OnboardingEvent.SignInSuccess(successResult))

        // Then
        val state = viewModel.state.first()

        // Should reflect the successful state
        assertTrue(state.isSignInSuccessful)
        assertNull(state.signInErrorMessage)
        assertEquals("success@example.com", state.email)
        assertEquals("Success User", state.name)
        assertEquals("https://success.url", state.photoUrl)

        // Verify repository interactions for successful attempt
        coVerify {
            dataRepository.updateData(
                User(
                    name = "Success User",
                    email = "success@example.com",
                    profileImage = "https://success.url",
                    userId = "456"
                )
            )
        }
        verify { dataPreferencesManager.isLoggedIn = true }
    }

    @Test
    fun `SignInSuccess followed by ResetGoogleState`() = runTest {
        // Send a successful SignInSuccess event, then a ResetGoogleState event.
        // Verify the state is reset (isSignInSuccessful false, signInErrorMessage null) after the reset event.

        // Given
        val userData = UserData(
            email = "test@example.com",
            name = "John Doe",
            id = "123",
            photoUrl = "https://photo.url"
        )
        val signInResult = SignInResult(data = userData, errorMessage = null)

        viewModel = createViewModel()

        // When - First successful sign-in
        viewModel.onEvent(OnboardingEvent.SignInSuccess(signInResult))

        // Then reset
        viewModel.onEvent(OnboardingEvent.ResetGoogleState)

        // Then
        val state = viewModel.state.first()

        // Google-specific states should be reset
        assertFalse(state.isSignInSuccessful)
        assertNull(state.signInErrorMessage)
        // User data should remain
        assertEquals("test@example.com", state.email)
        assertEquals("John Doe", state.name)
        assertEquals("https://photo.url", state.photoUrl)
    }

    @Test
    fun `ResetGoogleState followed by SignInSuccess - successful`() = runTest {
        // Send a ResetGoogleState event, then a successful SignInSuccess event.
        // Verify the state correctly reflects the successful sign-in after the reset.

        // Given
        val userData = UserData(
            email = "after@reset.com",
            name = "After Reset",
            id = "789",
            photoUrl = "https://after.url"
        )
        val signInResult = SignInResult(data = userData, errorMessage = null)

        viewModel = createViewModel()

        // When - First reset (should have no effect on initial state)
        viewModel.onEvent(OnboardingEvent.ResetGoogleState)

        // Then successful sign-in
        viewModel.onEvent(OnboardingEvent.SignInSuccess(signInResult))

        // Then
        val state = viewModel.state.first()

        assertTrue(state.isSignInSuccessful)
        assertNull(state.signInErrorMessage)
        assertEquals("after@reset.com", state.email)
        assertEquals("After Reset", state.name)
        assertEquals("https://after.url", state.photoUrl)

        // Verify repository interactions
        coVerify {
            dataRepository.updateData(
                User(
                    name = "After Reset",
                    email = "after@reset.com",
                    profileImage = "https://after.url",
                    userId = "789"
                )
            )
        }
        verify { dataPreferencesManager.isLoggedIn = true }
    }

    @Test
    fun `ViewModel initialization state`() = runTest {
        // Verify the initial state of userLoggedIn by checking dataPreferencesManager.isLoggedIn during the init block.
        // Test scenarios where isLoggedIn is initially true and false.

        // Test case 1: Initially logged out
        every { dataPreferencesManager.isLoggedIn } returns false

        val viewModel1 = createViewModel()

        val state1 = viewModel1.state.first()
        assertFalse(state1.userLoggedIn)

        // Test case 2: Initially logged in
        every { dataPreferencesManager.isLoggedIn } returns true

        val viewModel2 = createViewModel()

        val state2 = viewModel2.state.first()
        assertTrue(state2.userLoggedIn)
    }

    @Test
    fun `Concurrent onEvent calls - if applicable possible`() = runTest {
        // If the execution environment could lead to concurrent calls to onEvent (though typically ViewModels process events sequentially on the main thread),
        // consider if any race conditions could occur with state updates or repository calls.
        // For this specific code, sequential StateFlow updates should prevent most issues, but it's a general consideration.

        // Given
        val userData1 = UserData(
            email = "user1@example.com",
            name = "User 1",
            id = "123",
            photoUrl = "https://user1.url"
        )
        val userData2 = UserData(
            email = "user2@example.com",
            name = "User 2",
            id = "456",
            photoUrl = "https://user2.url"
        )
        val signInResult1 = SignInResult(data = userData1, errorMessage = null)
        val signInResult2 = SignInResult(data = userData2, errorMessage = null)

        viewModel = createViewModel()

        // When - Send multiple events rapidly (simulating potential concurrency)
        viewModel.onEvent(OnboardingEvent.SignInSuccess(signInResult1))
        viewModel.onEvent(OnboardingEvent.ResetGoogleState)
        viewModel.onEvent(OnboardingEvent.SignInSuccess(signInResult2))

        // Then - Final state should reflect the last successful sign-in
        val state = viewModel.state.first()

        assertTrue(state.isSignInSuccessful)
        assertNull(state.signInErrorMessage)
        assertEquals("user2@example.com", state.email)
        assertEquals("User 2", state.name)
        assertEquals("https://user2.url", state.photoUrl)

        // Verify both repository calls were made (for both sign-in attempts)
        coVerify(exactly = 2) { dataRepository.updateData(any()) }

        // Verify final user data matches the last successful sign-in
        coVerify {
            dataRepository.updateData(
                User(
                    name = "User 2",
                    email = "user2@example.com",
                    profileImage = "https://user2.url",
                    userId = "456"
                )
            )
        }
    }
}
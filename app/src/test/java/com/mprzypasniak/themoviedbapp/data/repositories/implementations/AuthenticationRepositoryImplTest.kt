package com.mprzypasniak.themoviedbapp.data.repositories.implementations

import com.google.common.truth.Truth.assertThat
import com.mprzypasniak.themoviedbapp.data.models.responses.StatusResponse
import com.mprzypasniak.themoviedbapp.network.api.AuthApi
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.IOException
import java.lang.RuntimeException

class AuthenticationRepositoryImplTest {
    private lateinit var sut: AuthenticationRepositoryImpl
    private lateinit var apiMock: AuthApi

    @Before
    fun setUp() {
        apiMock = mockk<AuthApi>()
        sut = AuthenticationRepositoryImpl(apiMock)
    }

    @Test
    fun `authenticateToken returns successful Result instance when api call succeeds`() =
        runBlocking {
            val response = StatusResponse(
                success = true,
                statusCode = 200,
                statusMessage = "Success"
            )

            every { apiMock.authenticateToken().execute() } returns Response.success(response)

            val result = sut.authenticateToken()

            verify { apiMock.authenticateToken().execute() }
            confirmVerified(apiMock)

            assertThat(result.isSuccess)
                .isTrue()
            assertThat(result.isFailure)
                .isFalse()
            assertThat(result.getOrNull())
                .isEqualTo(response)
            assertThat(result.exceptionOrNull())
                .isNull()
        }

    @Test
    fun `authenticateToken returns failed Result instance when api cannot connect with server`() =
        runBlocking {
            val error = IOException("Test error")

            every { apiMock.authenticateToken().execute() } throws error

            val result = sut.authenticateToken()

            verify { apiMock.authenticateToken().execute() }
            confirmVerified(apiMock)

            assertThat(result.isSuccess)
                .isFalse()
            assertThat(result.isFailure)
                .isTrue()
            assertThat(result.getOrNull())
                .isNull()
            assertThat(result.exceptionOrNull())
                .hasMessageThat()
                .isEqualTo("Test error")
        }

    @Test
    fun `authenticateToken returns failed Result instance when api cannot decode response`() =
        runBlocking {
            val error = RuntimeException("Cannot decode response")

            every { apiMock.authenticateToken().execute() } throws error

            val result = sut.authenticateToken()

            verify { apiMock.authenticateToken().execute() }
            confirmVerified(apiMock)

            assertThat(result.isSuccess)
                .isFalse()
            assertThat(result.isFailure)
                .isTrue()
            assertThat(result.getOrNull())
                .isNull()
            assertThat(result.exceptionOrNull())
                .hasMessageThat()
                .isEqualTo("Cannot decode response")
        }
}

package com.mprzypasniak.themoviedbapp.base

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response


class BaseRepositoryTest {
    data class TestResponse(
        val id: Int,
        val message: String
    )

    class TestRepository: BaseRepository() {
        suspend fun resolveResponse(block: suspend () -> Response<TestResponse>): TestResponse =
            super.resolveNetworkResponse(block)

        suspend fun getResult(block: suspend  () -> TestResponse): Result<TestResponse> =
            super.result(block)
    }

    private lateinit var sut: TestRepository

    @Before
    fun setUp() {
        sut = TestRepository()
    }

    @Test
    fun `resolveNetworkResponse returns specified object type from Response`() = runBlocking {
        val testBody = TestResponse(id = 1, message = "Hello world")
        val response = Response.success(200, testBody)

        val resolved = sut.resolveResponse {
            response
        }

        assertThat(resolved)
            .isEqualTo(testBody)
    }

    @Test
    fun `resolveNetworkResponse throws an error when Response has null body`() = runBlocking {
        val response = Response.success<TestResponse>(200, null)

        var error: Throwable? = null

        try {
            sut.resolveResponse {
                response
            }
        } catch (e: Throwable) {
            error = e
        }

        assertThat(error)
            .isNotNull()

        assertThat(error)
            .hasMessageThat()
            .isEqualTo("Unable to parse response")
    }

    @Test
    fun `resolveNetworkResponse throws an error when Response contains an error`() = runBlocking {
        val json = """
            {
                "success": false,
                "status_code": 400,
                "status_message": "Test error message"
            }
        """.trimIndent()
        val response = Response.error<TestResponse>(400, ResponseBody.create(null, json))
        var error: Throwable? = null

        try {
            sut.resolveResponse { response }
        } catch (e: Throwable) {
            error = e
        }

        assertThat(error)
            .hasMessageThat()
            .isEqualTo("Test error message")
    }

    @Test
    fun `result returns a successful Result instance when block does not throw an error`() = runBlocking {
        val testBody = TestResponse(id = 1, message = "Hello world")

        val result = sut.getResult { testBody }

        assertThat(result.isSuccess)
            .isTrue()

        assertThat(result.isFailure)
            .isFalse()

        assertThat(result.getOrNull())
            .isEqualTo(testBody)

        assertThat(result.exceptionOrNull())
            .isNull()
    }

    @Test
    fun `result returns a failed Result instance when block throws an error`() = runBlocking {
        val testException = Exception("Test error message")

        val result = sut.getResult { throw testException }

        assertThat(result.isSuccess)
            .isFalse()

        assertThat(result.isFailure)
            .isTrue()

        assertThat(result.getOrNull())
            .isNull()

        assertThat(result.exceptionOrNull())
            .isEqualTo(testException)
    }
}
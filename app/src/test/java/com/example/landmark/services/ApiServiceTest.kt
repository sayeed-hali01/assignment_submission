package com.example.landmark.services

import com.example.Helper
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ApiServiceTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create()
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_empty_getProduct() = runTest {

        val mockResponse = MockResponse()

        mockResponse.setBody("{}")

        mockWebServer.enqueue(mockResponse)

        val respose = apiService.getNewsData(5)

        mockWebServer.takeRequest()

        Truth.assertThat(respose.body()?.data?.isEmpty()).isTrue()
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
   fun `test response with some data`() = runTest {

        val mockResponse = MockResponse()

        val content = Helper.readFakeFile()

        mockResponse.setResponseCode(200)
        mockResponse.setBody(content)
        mockWebServer.enqueue(mockResponse)
        val response = apiService.getNewsData(1)
        mockWebServer.takeRequest()

        Truth.assertThat(response.isSuccessful).isTrue()
        Truth.assertThat(response.body()?.data?.size).isEqualTo(17)
    }
}
package com.example.landmark.repository

import com.example.Helper
import com.example.landmark.entity.NewsData
import com.example.landmark.repositiry.NetworkResult
import com.example.landmark.repositiry.NewsRepo
import com.example.landmark.services.ApiService
import com.google.common.truth.Truth
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

class NewsRepoTest {
    @Mock
    lateinit var apiService: ApiService

    private lateinit var repo: NewsRepo

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        /*System under test*/
        repo = NewsRepo(apiService)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `get product api list`() = runTest {
        val json = Helper.readFileResource("/response.json")
        val fakeNewsData = Gson().fromJson(json, NewsData::class.java)

        Mockito.`when`(apiService.getNewsData(anyInt()))
            .thenReturn(retrofit2.Response.success(fakeNewsData))

        val result = repo.requestNewsData(1)


        Truth.assertThat(result is NetworkResult.Success).isTrue()
        Truth.assertThat(result.data).isEqualTo(fakeNewsData)
    }

  /*  private fun readFile(filePath: String): String {
        val path = Paths.get(filePath)
        return String(Files.readAllBytes(path), StandardCharsets.UTF_8)
    }*/

   @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `get product api error`() = runTest {

        Mockito.`when`(apiService.getNewsData(anyInt()))
            .thenReturn(retrofit2.Response.error(401, "unauthorized".toResponseBody()))

        val result = repo.requestNewsData(1)

        Truth.assertThat(result is NetworkResult.Error).isTrue()
        Truth.assertThat(result.message).isEqualTo("No Data Found")
    }
      @Test
     fun `get product throws exception`() = runTest {

         Mockito.`when`(apiService.getNewsData(anyInt())).thenThrow(RuntimeException("API call failed"))
         val result = repo.requestNewsData(1)

         Truth.assertThat(result is NetworkResult.Error).isTrue()
         Truth.assertThat(result.message).isEqualTo("No Data Found")
     }
    @After
    fun tearDown() {
        //Mockito.verifyNoMoreInteractions(apiService)
    }
}
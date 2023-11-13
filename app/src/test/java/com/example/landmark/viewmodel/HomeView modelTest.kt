package com.example.landmark.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.Helper
import com.example.landmark.entity.NewsData
import com.example.landmark.repositiry.NetworkResult
import com.example.landmark.repositiry.NewsRepo
import com.google.common.truth.Truth
import com.google.gson.Gson
import getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class `HomeView modelTest` {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: NewsRepo

    lateinit var homeViewModels:HomeViewModel
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        homeViewModels = HomeViewModel(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_empty_getNews() = runTest {/*On JVM and Native, this function behaves similarly to runBlocking,
    with the difference that the code that it runs will skip delays. This allows to use delay in
    without causing the tests to take m mainViewModels.getProducts()ore time than necessary. On JS, this function creates a Promise that executes the test body with the delay-skipping behavior.*/

        Mockito.`when`(repository.requestNewsData(anyInt()))
            .thenReturn(NetworkResult.Success(NewsData()))

        homeViewModels.requestNewsData(1)

        testDispatcher.scheduler.advanceUntilIdle() /*Runs the enqueued tasks in the specified order, */

        val result = homeViewModels.newsLiveData.getOrAwaitValue()

        Assert.assertEquals(0, result.data?.data?.size ?: anyInt())
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test getNews`() = runTest {

        var fakeData = Gson().fromJson(Helper.readFakeFile(), NewsData::class.java)

        Mockito.`when`(repository.requestNewsData(anyInt())).thenReturn(
            NetworkResult.Success(fakeData)
        )
        homeViewModels.requestNewsData(1)
        testDispatcher.scheduler.advanceUntilIdle()
        val result = homeViewModels.newsLiveData.getOrAwaitValue()

        Assert.assertEquals(17, result.data?.data?.size)
        Assert.assertEquals(3, result.data?.meta?.limit ?: anyInt())
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getNews Error on Api failure`() = runTest {

        Mockito.`when`(repository.requestNewsData(anyInt())).thenReturn(NetworkResult.Error("No Data Found"))

        homeViewModels.requestNewsData(1)

        testDispatcher.scheduler.advanceUntilIdle()

        val result = homeViewModels.newsLiveData.getOrAwaitValue()

        Truth.assertThat(true).isEqualTo(result is NetworkResult.Error)
        Truth.assertThat("No Data Found").isEqualTo(result.message)
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
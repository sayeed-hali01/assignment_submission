package com.example.landmark.activity

import android.content.ComponentName
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.landmark.R
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PhotoZooomInstrumentedTest {

    private val countingIdlingResource = CountingIdlingResource("AsyncOperations")

    @Before
    fun setUp() {
        countingIdlingResource.increment()
    }
    @Test
    fun testPhotoZoomActivity() {

        val intent = Intent()
        intent.component = ComponentName("com.example.landmark", "com.example.landmark.activity.PhotoZooom")
        intent.putExtra("photo_url", "https://static.foxnews.com/foxnews.com/content/uploads/2023/11/katylee-thumb.jpg")
        intent.putExtra("news_content", "This is an example of news")

        val scenario = ActivityScenario.launch<PhotoZooom>(intent)

        countingIdlingResource.increment()

        onView(withId(R.id.photo_view)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_show_news)).perform(click())

        onView(withId(R.id.tv_full_content))
            .check(matches(isDisplayed()))
            .check(matches(withText("This is an example of news")))
        countingIdlingResource.decrement()
        scenario.close()
    }
    @After
    fun tearDown() {
        countingIdlingResource.decrement()
    }
}
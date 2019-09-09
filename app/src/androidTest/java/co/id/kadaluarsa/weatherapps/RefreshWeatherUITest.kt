package co.id.kadaluarsa.weatherapps


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import co.id.kadaluarsa.weatherapps.Test.Companion.setMobileData
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * disable all of internet connection at first time run it will show up retry button to refresh the weather.
 */
@LargeTest
@RunWith(JUnit4::class)
class RefreshWeatherUITest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)


    /**
     * Im so sorry its not pass , im not able to turn off and on data scenarion because of permission issue :(
     * and it should be done with manual action turn off internet -> open apps -> turn on internet before hit retry button
     */
    @Test
    fun refreshWeatherUITest() {
        setMobileData(WeatherApp.getAppContext(),false)
        val appCompatButton = onView(
            allOf(
                withId(R.id.btnRetry), withText("Try Again"),
                childAtPosition(
                    allOf(
                        withId(R.id.viewError),
                        childAtPosition(
                            withClassName(`is`("android.widget.FrameLayout")),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        setMobileData(WeatherApp.getAppContext(),true)
        appCompatButton.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.txtDegree),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.listWeather),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView.check(matches(isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}

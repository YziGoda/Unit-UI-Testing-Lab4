package com.example.notev1;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTestUI {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void add_Note_Test_and_delete()
    {
        final String noteName = "Test";
        final String noteText = "123";

        onView(withContentDescription("More options")).perform(click());
        onView(withText("Add Note")).perform(click());
        onView(withId(R.id.etName)).perform(replaceText(noteName), closeSoftKeyboard());
        onView(withId(R.id.etContent)).perform(replaceText(noteText), closeSoftKeyboard());
        onView(withId(R.id.btn_save)).perform(click());
        onData(allOf(is(instanceOf(String.class)),is(noteName))).inAdapterView(withId(R.id.listView)).check(matches(isDisplayed()));
        try
        {
            Thread.sleep(3000); // teko prideti nes UI nespeja ir kartais menu nepasispaudzia
        }
        catch (InterruptedException e)
        {
            System.out.println("Exception");
        }
        onView(withContentDescription("More options")).perform(click());
        onView(withText("Delete Note")).perform(click());
        onView(withId(R.id.spinner)).perform(click());
        onView(withText(noteName)).perform(click());
        onView(withId(R.id.btn_delete)).perform(click());
        onView(withId(R.id.listView)).equals(null);
    }

    @Test
    public void test_add_note_cancel()
    {
        final String noteName = "Testv2";
        final String noteText = "111";

        onView(withContentDescription("More options")).perform(click());
        onView(withText("Add Note")).perform(click());
        onView(withId(R.id.etName)).perform(replaceText(noteName), closeSoftKeyboard());
        onView(withId(R.id.etContent)).perform(replaceText(noteText), closeSoftKeyboard());
        onView(withId(R.id.btn_cancel)).perform(click());
        onView(withId(R.id.listView)).equals(null);
    }

    @Test
    public void test_delete_note_cancel()
    {
        final String noteName = "Test";
        final String noteText = "123";

        onView(withContentDescription("More options")).perform(click());
        onView(withText("Add Note")).perform(click());
        onView(withId(R.id.etName)).perform(replaceText(noteName), closeSoftKeyboard());
        onView(withId(R.id.etContent)).perform(replaceText(noteText), closeSoftKeyboard());
        onView(withId(R.id.btn_save)).perform(click());
        try
        {
            Thread.sleep(3000); // teko prideti nes UI nespeja ir kartais menu nepasispaudzia
        }
        catch (InterruptedException e)
        {
            System.out.println("Exception");
        }
        onView(withContentDescription("More options")).perform(click());
        onView(withText("Delete Note")).perform(click());
        onView(withId(R.id.spinner)).perform(click());
        onView(withText(noteName)).perform(click());
        onView(withId(R.id.cancel_delete)).perform(click());
        onData(allOf(is(instanceOf(String.class)),is(noteName))).inAdapterView(withId(R.id.listView)).check(matches(isDisplayed()));

    }
    @Test
    public void mainActivityTestUI() {
        ViewInteraction overflowMenuButton = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        overflowMenuButton.perform(click());

        ViewInteraction materialTextView = onView(
                allOf(withId(androidx.transition.R.id.title), withText("Add Note"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.etName),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Test"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.etContent),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("123"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withText("Save"),
                        childAtPosition(
                                allOf(withId(R.id.bottomLineLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction overflowMenuButton2 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        overflowMenuButton2.perform(click());

        ViewInteraction materialTextView2 = onView(
                allOf(withId(androidx.transition.R.id.title), withText("Add Note"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView2.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.etName),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("123"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.etContent),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("Test"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withText("Cancel"),
                        childAtPosition(
                                allOf(withId(R.id.bottomLineLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction overflowMenuButton3 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        overflowMenuButton3.perform(click());

        ViewInteraction materialTextView3 = onView(
                allOf(withId(androidx.transition.R.id.title), withText("Delete Note"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView3.perform(click());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.spinner),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(0);
        appCompatCheckedTextView.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withText("Delete"),
                        childAtPosition(
                                allOf(withId(R.id.bottomLineLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        materialButton3.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}

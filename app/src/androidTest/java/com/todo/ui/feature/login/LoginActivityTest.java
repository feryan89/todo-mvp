package com.todo.ui.feature.login;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.todo.R;
import com.todo.ui.feature.register.RegisterActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.todo.testutils.EspressoTextInputLayoutUtils.onErrorViewWithinTilWithId;
import static org.hamcrest.CoreMatchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {

    public static final String INVALID_EMAIL = "invalid_email";
    public static final String VALID_EMAIL = "valid_email@android.com";


    @Rule
    public IntentsTestRule<LoginActivity> loginActivityIntentsTestRule = new IntentsTestRule<>(LoginActivity.class);


    @Test
    public void loginForm_invalidEmailAddress_shouldShowEmailError() {

        onView(withId(R.id.login_input_edit_text_email)).perform(typeText(INVALID_EMAIL));

        onView(withId(R.id.login_button_login)).check(matches(not(isEnabled())));
        onErrorViewWithinTilWithId(R.id.login_input_layout_email).check(matches(withText(getString(R.string.all_error_email_invalid))));

    }

    @Test
    public void loginForm_invalidEmptyPassword_shouldShowPasswordError() {

        onView(withId(R.id.login_input_edit_text_password)).perform(typeText("password"), clearText());

        onView(withId(R.id.login_button_login)).check(matches(not(isEnabled())));
        onErrorViewWithinTilWithId(R.id.login_input_layout_password).check(matches(withText(getString(R.string.all_error_password_required))));

    }

    @Test
    public void buttonRegisterClicked_shouldShowRegisterScreen() {

        onView(withId(R.id.login_button_register)).perform(click());

        intended(hasComponent(RegisterActivity.class.getName()));
    }

    private String getString(int resId) {

        return loginActivityIntentsTestRule.getActivity().getString(resId);
    }
}
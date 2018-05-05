package com.todo.ui.feature.tasks;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.todo.R;
import com.todo.ui.feature.addedittask.AddEditTaskActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TasksActivityTest {

    @Rule
    public IntentsTestRule<TasksActivity> tasksActivityIntentsTestRule = new IntentsTestRule<>(TasksActivity.class);

    @Test
    public void buttonAddTaskClicked_shouldShowAddEditTaskScreen() {

        onView(withId(R.id.tasks_button_add_task)).perform(click());
        intended(hasComponent(AddEditTaskActivity.class.getName()));
    }

}
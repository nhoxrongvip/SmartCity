package ca.chesm.it.smartcity.View.accounts;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import ca.chesm.it.smartcity.R;

public class LoginActivityTest
{
    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void LoginWithEmpty()
    {
        onView(withId(R.id.txteditusername)).perform(typeText(""));
        onView(withId(R.id.txteditpassword)).perform(typeText(""));
        onView(withId(R.id.bntlogin)).perform(click());
    }

    @Test
    public void LoginWithEmailOnly()
    {
        onView(withId(R.id.txteditusername)).perform(typeText("danks76429@gmail.com"));
        onView(withId(R.id.txteditpassword)).perform(typeText(""));
        onView(withId(R.id.bntlogin)).perform(click());
    }

    @Test
    public void LoginWithPasswordOnly()
    {
        onView(withId(R.id.txteditusername)).perform(typeText(""));
        onView(withId(R.id.txteditpassword)).perform(typeText("Abc123456"));
        onView(withId(R.id.bntlogin)).perform(click());
    }

    @Test
    public void LoginWithCorrectEmailOnly()
    {
        onView(withId(R.id.txteditusername)).perform(typeText("danks76429@gmail.com"));
        onView(withId(R.id.txteditpassword)).perform(typeText("Abc123456"));
        onView(withId(R.id.bntlogin)).perform(click());
    }

    @Test
    public void LoginWithCorrectPasswordOnly()
    {
        onView(withId(R.id.txteditusername)).perform(typeText("danks55@gmail.com"));
        onView(withId(R.id.txteditpassword)).perform(typeText("D@nks76429"));
        onView(withId(R.id.bntlogin)).perform(click());
    }

    @Test
    public void LoginWithNotExistEmail()
    {
        onView(withId(R.id.txteditusername)).perform(typeText("danks55@gmail.com"));
        onView(withId(R.id.txteditpassword)).perform(typeText("D@nks76429"));
        onView(withId(R.id.bntlogin)).perform(click());
    }

    @Test
    public void LoginSuccessFull()
    {
        onView(withId(R.id.txteditusername)).perform(typeText("danks76429@gmail.com"));
        onView(withId(R.id.txteditpassword)).perform(typeText("D@nks76429"));
        onView(withId(R.id.bntlogin)).perform(click());
    }


}
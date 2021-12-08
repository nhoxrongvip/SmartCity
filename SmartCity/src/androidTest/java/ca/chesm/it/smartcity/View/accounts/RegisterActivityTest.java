package ca.chesm.it.smartcity.View.accounts;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

import android.provider.Contacts;
import android.widget.EditText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.chesm.it.smartcity.R;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegisterActivityTest
{

    /* Instantiate an ActivityScenarioRule object. */
    @Rule
    public ActivityScenarioRule<RegisterActivity> activityRule =
            new ActivityScenarioRule<>(RegisterActivity.class);


    @Test
    public void RegisterWithWrongEmail()
    {
        onView(withId(R.id.txteditregfullname)).perform(typeText("Danny Ly"));
        onView(withId(R.id.txteditregpassword)).perform(typeText("Abc123456"));
        onView(withId(R.id.txteditregpasswordconfirm)).perform(typeText("Abc123456"));
        onView(withId(R.id.txteditregemail)).perform(typeText("dannyly.com"));
        onView(withId(R.id.txteditphoneno)).perform(typeText("6478187858"));
        onView(withId(R.id.bntsubmitreg)).perform(click());
    }

    @Test
    public void RegisterWithNotMatchPassword()
    {
        onView(withId(R.id.txteditregfullname)).perform(typeText("Danny Ly"));
        onView(withId(R.id.txteditregpassword)).perform(typeText("Abc123456"));
        onView(withId(R.id.txteditregpasswordconfirm)).perform(typeText("Abc456"));
        onView(withId(R.id.txteditregemail)).perform(typeText("dannyly@gmail.com"));
        onView(withId(R.id.txteditphoneno)).perform(typeText("6478187858"));
        onView(withId(R.id.bntsubmitreg)).perform(click());
    }

    @Test
    public void RegisterWithNotMatchPasswordNoStrong()
    {
        onView(withId(R.id.txteditregfullname)).perform(typeText("Danny Ly"));
        onView(withId(R.id.txteditregpassword)).perform(typeText("Abc123456"));
        onView(withId(R.id.txteditregpasswordconfirm)).perform(typeText("Abc123456"));
        onView(withId(R.id.txteditregemail)).perform(typeText("dannyly@gmail.com"));
        onView(withId(R.id.txteditphoneno)).perform(typeText("6478187858"));
        onView(withId(R.id.bntsubmitreg)).perform(click());
    }

    @Test
    public void RegisterWithMatchPasswordStrongPass()
    {
        onView(withId(R.id.txteditregfullname)).perform(typeText("Danny Ly"));
        onView(withId(R.id.txteditregpassword)).perform(typeText("AB@123456"));
        onView(withId(R.id.txteditregpasswordconfirm)).perform(typeText("AB@123456"));
        onView(withId(R.id.txteditregemail)).perform(typeText("dannyly@gmail.com"));
        onView(withId(R.id.txteditphoneno)).perform(typeText("6478187858"));
        onView(withId(R.id.bntsubmitreg)).perform(click());
    }

    @Test
    public void RegisterWithEmptyPhoneNumber()
    {
        onView(withId(R.id.txteditregfullname)).perform(typeText("Danny Ly"));
        onView(withId(R.id.txteditregpassword)).perform(typeText("Abc123456"));
        onView(withId(R.id.txteditregpasswordconfirm)).perform(typeText("Abc123456"));
        onView(withId(R.id.txteditregemail)).perform(typeText("dannyly@gmail.com"));
        onView(withId(R.id.txteditphoneno)).perform(typeText(""));
        onView(withId(R.id.bntsubmitreg)).perform(click());
    }

    @Test
    public void RegisterWithEmptyAll()
    {
        onView(withId(R.id.txteditregfullname)).perform(typeText(""));
        onView(withId(R.id.txteditregpassword)).perform(typeText(""));
        onView(withId(R.id.txteditregpasswordconfirm)).perform(typeText(""));
        onView(withId(R.id.txteditregemail)).perform(typeText(""));
        onView(withId(R.id.txteditphoneno)).perform(typeText(""));
        onView(withId(R.id.bntsubmitreg)).perform(click());
    }

    @Test
    public void RegisterWithEmptyFullName()
    {
        onView(withId(R.id.txteditregfullname)).perform(typeText(""));
        onView(withId(R.id.txteditregpassword)).perform(typeText("Abc123456"));
        onView(withId(R.id.txteditregpasswordconfirm)).perform(typeText("Abc123456"));
        onView(withId(R.id.txteditregemail)).perform(typeText("dannyly@gmail.com"));
        onView(withId(R.id.txteditphoneno)).perform(typeText("6478187858"));
        onView(withId(R.id.bntsubmitreg)).perform(click());
    }
}
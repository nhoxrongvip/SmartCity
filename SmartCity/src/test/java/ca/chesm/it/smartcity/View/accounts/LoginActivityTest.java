package ca.chesm.it.smartcity.View.accounts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.robolectric.Shadows.shadowOf;

import android.app.Activity;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowToast;


import ca.chesm.it.smartcity.R;
import ca.chesm.it.smartcity.View.activities.MainActivity;


@RunWith(RobolectricTestRunner.class)
public class LoginActivityTest
{
    Activity activity;

    @Before
    public void init() {
        activity = Robolectric.setupActivity(LoginActivity.class);
    }

    @Test
    public void failTest()
    {
        EditText results = (EditText) activity.findViewById(R.id.txteditusername);
        EditText results2 = (EditText) activity.findViewById(R.id.txteditpassword);
        String resultsText = results.getHint().toString();
        String resultsText2 = results2.getHint().toString();
        assertThat(resultsText, equalTo("Testing Android Rocks!"));
        assertThat(resultsText2, equalTo("Testing Android Rocks 2!"));
    }

    @Test
    public void passTest()
    {
        EditText results = (EditText) activity.findViewById(R.id.txteditusername);
        EditText results2 = (EditText) activity.findViewById(R.id.txteditpassword);
        String resultsText = results.getHint().toString();
        String resultsText2 = results2.getHint().toString();
        assertThat(resultsText, equalTo("Email"));
        assertThat(resultsText2, equalTo("Password"));
    }

    @Test
    public void clickingButton_shouldChangeResultsViewText() throws Exception {
        Button button = (Button) activity.findViewById(R.id.bntlogin);
        button.performClick();
        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
        assertEquals(MainActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test
    public void testButtonClick() throws Exception
    {
        Button view = (Button) activity.findViewById(R.id.bntlogin);
        assertNotNull(view);
        view.performClick();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo("Hello"));
    }

    @Test
    public  void TextViewwelcome() throws Exception
    {
        TextView tv = activity.findViewById(R.id.txtviewlogin);
        assertNotNull(tv);
        assertEquals(tv.getText(),"Welcome to Smart City");
    }


}
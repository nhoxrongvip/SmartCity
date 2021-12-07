package ca.chesm.it.smartcity.View.accounts;

import static org.junit.Assert.*;

import android.app.Application;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.manipulation.Ordering;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import ca.chesm.it.smartcity.R;

@RunWith(RobolectricTestRunner.class)
public class LoginActivityTest
{
    private LoginActivity activity;
    private Button button;
    private EditText editext1,edittext2;

    @Before
    public void setUp() throws Exception
    {
        activity = Robolectric.setupActivity(LoginActivity.class);
        button = (Button) activity.findViewById(R.id.bntlogin);
        editext1 = (EditText) activity.findViewById(R.id.txteditusername);
        edittext2 = (EditText) activity.findViewById(R.id.txtpasswords);
    }

    @After
    public void tearDown() throws Exception
    {

    }
    @Test
    public void testContext()
    {


    }

}
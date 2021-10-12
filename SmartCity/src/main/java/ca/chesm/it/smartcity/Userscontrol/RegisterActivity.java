/*
    Name:Dung Ly N01327929
    Course: CENG322-RND
    Purpose: Control register user with firebase
    Last updated:  09 Oct 2021
*/

package ca.chesm.it.smartcity.Userscontrol;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.chesm.it.smartcity.R;

public class RegisterActivity extends AppCompatActivity
{

    private EditText editTextfullname, editTextusername, editTextpassword, editTextemail, editTextphone;
    String password, username, fullname, phone, email;
    private Button bntSubmit;
    private DatabaseReference reff;
    private FirebaseAuth mAuth;
    private User user;
    long maxid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        reff = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        user = new User();
        regid();
        reff.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });

        //Use to control the submit button on Register form
        bntSubmit.setOnClickListener(view ->
        {
            AESCrypt aesCrypt = new AESCrypt();

            int phoneno;
            username = editTextusername.getText().toString().trim();
            fullname = editTextfullname.getText().toString().trim();
            password = editTextpassword.getText().toString().trim();
            email = editTextemail.getText().toString().trim();
            phone = editTextphone.getText().toString().trim();

            if (fullname.isEmpty() && username.isEmpty() && password.isEmpty() && email.isEmpty() && phone.isEmpty())
            {
                editTextfullname.setError("This field can not be blank !");
                editTextusername.setError("This field can not be blank !");
                editTextpassword.setError("This field can not be blank !");
                editTextemail.setError("This field can not be blank !");
                editTextphone.setError("This field can not be blank !");
            } else if (fullname.isEmpty())
            {
                editTextfullname.setError("This field can not be blank !");
            } else if (username.isEmpty())
            {
                editTextusername.setError("This field can not be blank !");
            } else if (password.isEmpty())
            {
                editTextpassword.setError("This field can not be blank !");
            } else if (email.isEmpty())
            {
                editTextemail.setError("This field can not be blank !");
            } else if (phone.isEmpty())
            {
                editTextphone.setError("This field can not be blank !");
            } else
            {
                if (!isEmailValid(email))
                {
                    editTextemail.setError("The email type not correct !");
                    return;
                }
                phoneno = Integer.parseInt(phone);
                user.setFullname(fullname);
                try
                {
                    user.setPassword(aesCrypt.encrypt(password));
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                user.setEmail(email);
                user.setPhoneNo(phoneno);
                emailreg(user);
            }

        });

    }

    //This method use to create User on method login with email when user register complete!
    private void emailreg(User user)
    {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {
                    reff.child(username).setValue(user);
                    AlertDialog.Builder dialog = Dialogb("Register successful !");
                    dialog.show();
                }
                else
                {
                    AlertDialog.Builder dialog = Dialogb(task.getException().getMessage());
                    dialog.show();
                }
            }
        });
    }


    //Method use to show dialog with custom mess
    private AlertDialog.Builder Dialogb(String mess)
    {
        AlertDialog.Builder builderd = new AlertDialog.Builder(RegisterActivity.this);
        builderd.setCancelable(true);
        builderd.setMessage(mess);
        builderd.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                dialogInterface.cancel();
            }
        });
        return builderd;
    }

    //Method use to check email is correct type or not
    private static boolean isEmailValid(String email)
    {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //Method use to find ID of each variable need to set on xml
    private void regid()
    {
        editTextfullname = (EditText) findViewById(R.id.txteditregfullname);
        editTextusername = (EditText) findViewById(R.id.txteditregusername);
        editTextpassword = (EditText) findViewById(R.id.txteditregpassword);
        editTextemail = (EditText) findViewById(R.id.txteditregemail);
        editTextphone = (EditText) findViewById(R.id.txteditphoneno);
        bntSubmit = (Button) findViewById(R.id.bntsubmitreg);
    }

}
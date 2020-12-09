package com.eslam.qrcodeattendancesystem;

import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.eslam.qrcodeattendancesystem.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private Dialog dialog;
    private Button submit;
    private Spinner spinner;
    private ConstraintLayout layout;
    private TextInputLayout email_layout, pass_layout, fn_layout, ln_layout;
    private TextInputEditText email_et, pass_et, firstname_reg, lastname_reg;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();


    private String accountType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        //Define Widgets
        _findViewByID();
        //Animated Background
        _animateBackground();
        //Spinner ArrayAdapter
        _spinnerData();


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                accountType = spinner.getItemAtPosition(position).toString();
                Toast.makeText(RegisterActivity.this, "" + accountType, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                _create_new_user();


            }
        });

    }


    private void _animateBackground() {
        AnimationDrawable drawable = (AnimationDrawable) layout.getBackground();
        drawable.setEnterFadeDuration(10);
        drawable.setExitFadeDuration(3000);
        drawable.start();


    }

    private void _spinnerData() {

        String[] accountType = {"Doctor", "Student"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, accountType);
        spinner.setAdapter(adapter);

    }


    private void _findViewByID() {

        spinner = findViewById(R.id.accountType);
        email_et = findViewById(R.id.email_reg);
        firstname_reg = findViewById(R.id.fn_reg);
        lastname_reg = findViewById(R.id.ln_reg);
        pass_et = findViewById(R.id.pass_reg);
        submit = findViewById(R.id.submit_btn);
        email_layout = findViewById(R.id.email_layout);
        pass_layout = findViewById(R.id.pass_layout);
        layout = findViewById(R.id.mylayout);
        fn_layout = findViewById(R.id.fn_layout);
        ln_layout = findViewById(R.id.ln_layout);


    }

    private void _show_wating_dialog() {
        dialog = new Dialog(RegisterActivity.this);
        dialog.setContentView(R.layout.loading);
        dialog.setCancelable(false);
        dialog.show();

    }

    private void _create_new_user() {


        final String first__name = firstname_reg.getText().toString();
        final String last__name = lastname_reg.getText().toString();
        final String email = email_et.getText().toString();
        String pass = pass_et.getText().toString();

        if (first__name.isEmpty()) fn_layout.setError("Please Enter Your First Name");
        else if (last__name.isEmpty()) ln_layout.setError("Please Enter Your Last Name");
        else if (email.isEmpty()) email_layout.setError("Please Enter Your Email");
        else if (pass.isEmpty()) pass_layout.setError("Please Enter Your Password");

        else {

            _show_wating_dialog();
            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String userid = mAuth.getUid();
                        UserModel model = new UserModel();
                        model.setUser_ID(userid);
                        model.setEmail(email);
                        model.setAccountType(accountType);
                        model.setFirstname(first__name);
                        model.setLastname(last__name);


                        myRef.child("users").child(model.getUser_ID()).setValue(model);



                        dialog.dismiss();


                        Toast.makeText(RegisterActivity.this, "" + userid, Toast.LENGTH_LONG).show();


                        Toast.makeText(RegisterActivity.this, "New Account Created Sucessfully", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Please Try Agian!", Toast.LENGTH_SHORT).show();


                    }
                }
            });


        }


    }


}

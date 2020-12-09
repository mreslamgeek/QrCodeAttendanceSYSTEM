package com.eslam.qrcodeattendancesystem;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    protected static final String DOCTOR = "Doctor";
    protected static final String STUDENT = "Student";
    Dialog dialog;
    Button reg_btn, submit;
    TextInputLayout email_layout, pass_layout;
    TextInputEditText email, pass;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private ConstraintLayout layout;

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            _check_account_type();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        _findViewByID();
        _animateBackground();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _loginUser();
            }
        });

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void _show_dialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.loading);
        dialog.setCancelable(false);
        dialog.show();

    }

    private void _loginUser() {
        String _email = email.getText().toString();
        String _pass = pass.getText().toString();

        if (_email.isEmpty()) email_layout.setError("enter your email");
        else if (_pass.isEmpty()) pass_layout.setError("enter your password");
        else {
            _show_dialog();

            mAuth.signInWithEmailAndPassword(_email, _pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        _check_account_type();
                        Toast.makeText(LoginActivity.this, "" + mAuth.getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        dialog.dismiss();

    }

    void _check_account_type() {

        String current_user_id = mAuth.getCurrentUser().getUid();
        reference.child(current_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String accountType = dataSnapshot.child("accountType").getValue(String.class);
                if (accountType.equals(DOCTOR)) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else if (accountType.equals(STUDENT)) {
                    Intent intent = new Intent(LoginActivity.this, StudentActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(LoginActivity.this, "Sorry,You have problem in your account type", Toast.LENGTH_SHORT).show();
                    Toast.makeText(LoginActivity.this, accountType, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void _animateBackground() {
        AnimationDrawable drawable = (AnimationDrawable) layout.getBackground();
        drawable.setEnterFadeDuration(10);
        drawable.setExitFadeDuration(3000);
        drawable.start();

    }

    private void _findViewByID() {
        reg_btn = findViewById(R.id.reg_intent_btn);
        submit = findViewById(R.id.submit_login_btn);
        layout = findViewById(R.id.login_layout);
        email = findViewById(R.id.email_log);
        pass = findViewById(R.id.pass_log);
        email_layout = findViewById(R.id.email_log_layout);
        pass_layout = findViewById(R.id.pass_log_layout);

    }
}

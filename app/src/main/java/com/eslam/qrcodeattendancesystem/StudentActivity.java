package com.eslam.qrcodeattendancesystem;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.eslam.qrcodeattendancesystem.fragments.fragmentAdapters.FragmentAdapterStu;
import com.gauravk.bubblenavigation.BubbleNavigationConstraintView;
import com.google.firebase.auth.FirebaseAuth;

public class StudentActivity extends AppCompatActivity {
    BubbleNavigationConstraintView bubbleNavigationConstraintView;
    ViewPager viewPager;
    FragmentAdapterStu fragmentAdapter = new FragmentAdapterStu(getSupportFragmentManager());
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        viewPager = findViewById(R.id.fragment_container_stu);
        bubbleNavigationConstraintView = findViewById(R.id.bottom_navigation_view_linear_stu);
        MainActivity._Navigation(fragmentAdapter, viewPager, bubbleNavigationConstraintView);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}

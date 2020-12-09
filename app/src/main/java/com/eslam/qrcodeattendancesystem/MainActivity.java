package com.eslam.qrcodeattendancesystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.eslam.qrcodeattendancesystem.fragments.fragmentAdapters.FragmentAdapter;
import com.gauravk.bubblenavigation.BubbleNavigationConstraintView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    final FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
    BubbleNavigationConstraintView bubbleNavigationConstraintView;
    ViewPager viewPager;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        viewPager = findViewById(R.id.fragment_container);
        bubbleNavigationConstraintView = findViewById(R.id.bottom_navigation_view_linear);

        _Navigation(fragmentAdapter, viewPager, bubbleNavigationConstraintView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

    }

    static void _Navigation(FragmentPagerAdapter fragmentAdapter, ViewPager viewPager, BubbleNavigationConstraintView bubbleNavigationConstraintView) {

        viewPager.setAdapter(fragmentAdapter);

        bubbleNavigationConstraintView.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                viewPager.setCurrentItem(position);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                bubbleNavigationConstraintView.setCurrentActiveItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}

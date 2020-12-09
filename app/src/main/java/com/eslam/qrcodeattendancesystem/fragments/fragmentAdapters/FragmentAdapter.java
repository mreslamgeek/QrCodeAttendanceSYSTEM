package com.eslam.qrcodeattendancesystem.fragments.fragmentAdapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.eslam.qrcodeattendancesystem.fragments.LecatureFragment;
import com.eslam.qrcodeattendancesystem.fragments.StudentFragment;

public class FragmentAdapter extends FragmentPagerAdapter {

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {

            case 0:
                fragment = new LecatureFragment();
                break;
            case 1:
                fragment = new StudentFragment();
                break;


        }
        return fragment;


    }

    @Override
    public int getCount() {
        return 2;
    }
}

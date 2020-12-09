package com.eslam.qrcodeattendancesystem.fragments.fragmentAdapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.eslam.qrcodeattendancesystem.fragments.PassedLecature;
import com.eslam.qrcodeattendancesystem.fragments.ProcessingLecature;

public class FragmentAdapterStu extends FragmentPagerAdapter {

    public FragmentAdapterStu(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {

            case 0:
                fragment = new ProcessingLecature();
                break;
            case 1:
                fragment = new PassedLecature();
                break;

        }
        return fragment;

    }

    @Override
    public int getCount() {
        return 2;
    }
}

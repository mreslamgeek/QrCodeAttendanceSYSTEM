package com.eslam.qrcodeattendancesystem.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eslam.qrcodeattendancesystem.R;

public class PassedLecature extends Fragment {

    public PassedLecature() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_passed_lecature, container, false);
    }

}

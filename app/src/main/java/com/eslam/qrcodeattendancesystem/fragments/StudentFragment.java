package com.eslam.qrcodeattendancesystem.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.eslam.qrcodeattendancesystem.R;

public class StudentFragment extends Fragment {

    public StudentFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student, container, false);
    }

}

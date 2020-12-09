package com.eslam.qrcodeattendancesystem.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eslam.qrcodeattendancesystem.LoginActivity;
import com.eslam.qrcodeattendancesystem.R;
import com.eslam.qrcodeattendancesystem.fragments.fragmentAdapters.Lec_RV_Stu_Adapter;
import com.eslam.qrcodeattendancesystem.models.SessionModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProcessingLecature extends Fragment {
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static Context context;
    ArrayList<SessionModel> sessionModelArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    Button logout;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Lecture/Doctors");

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public ProcessingLecature() {
    }

    public static void _check_qr_key(String QR_KEY, String SCCANED_KEY) {
        if (SCCANED_KEY.equals(QR_KEY)) {
            Toast.makeText(context, "Your Are Signed", Toast.LENGTH_SHORT).show();

        } else Toast.makeText(context, "wrong qr code", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_processing_lecature, container, false);
        recyclerView = view.findViewById(R.id.lec_rv);
        context = getContext();
        logout=view.findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
            }
        });
        _read_processing_lecature_from_database();

        return view;
    }

    private void _read_processing_lecature_from_database() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sessionModelArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot shot : snapshot.getChildren()) {
                        SessionModel sessionModel = SessionModel.getInstance();
                        sessionModel = shot.getValue(SessionModel.class);
                        sessionModelArrayList.add(sessionModel);
                        Lec_RV_Stu_Adapter adapter = new Lec_RV_Stu_Adapter(sessionModelArrayList, getContext());
                        adapter.setHasStableIds(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(adapter);
                        //recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount());

                        Toast.makeText(getContext(), " " + sessionModelArrayList.size(), Toast.LENGTH_SHORT).show();

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

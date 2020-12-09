package com.eslam.qrcodeattendancesystem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.eslam.qrcodeattendancesystem.fragments.ProcessingLecature;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView scannerView;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Lecture/Doctors/IUX8MRyNaEYzq2S0NVXaXELTMwi1");
    private String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        key = intent.getStringExtra("key");

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();

    }

    @Override
    public void handleResult(Result rawResult) {

        ProcessingLecature._check_qr_key(key,rawResult.getText());
        onBackPressed();

    }
}

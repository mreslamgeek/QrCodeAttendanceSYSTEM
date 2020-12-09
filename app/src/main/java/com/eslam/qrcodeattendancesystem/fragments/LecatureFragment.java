package com.eslam.qrcodeattendancesystem.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.eslam.qrcodeattendancesystem.R;
import com.eslam.qrcodeattendancesystem.models.SessionModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.WINDOW_SERVICE;

public class LecatureFragment extends Fragment implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    Uri imgUri = null;
    FloatingActionButton floatingActionButton;
    ImageView lec_img;
    EditText dialog_lecTitle_txt, dialog_lecDecs_txt, _date;
    SessionModel sessionModel = SessionModel.getInstance();

    private Dialog dialog;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Lecture/Doctors");
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;

    public LecatureFragment() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK && data != null) {
            imgUri = data.getData();
            lec_img.setImageURI(imgUri);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lecature, container, false);
        floatingActionButton = view.findViewById(R.id.floatButton);

        mStorageRef = FirebaseStorage.getInstance().getReference("Lecture/Doctors");
        mAuth = FirebaseAuth.getInstance();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _ShowCustomDialog();
            }
        });

        return view;

    }

    private void _showDateandTimeDialog() {

        Calendar calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");
                        _date.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };
                TimePickerDialog.newInstance(onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show(getFragmentManager(), "TimePick");
            }
        };
        DatePickerDialog.newInstance(onDateSetListener, calendar).show(getFragmentManager(), "DatePick");



     /*   datePickerDialog.setMinDate(calendar);
        datePickerDialog.setThemeDark(true);
        datePickerDialog.setAccentColor(Color.parseColor("#F9AA33"));
        datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
*/

    }

    public void _seletImage() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK);
        pickPhoto.setType("image/*");
        startActivityForResult(pickPhoto, 10);

    }

    private void _ShowCustomDialog() {
        //reset values
        imgUri = null;

        //setup dialog
        dialog = new Dialog(getView().getContext());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.lecture_dialog);
        dialog.setCancelable(false);

        //Widgets Initialisation
        Button dialog_cancel = dialog.findViewById(R.id.cancel_lecDialog_btn);
        Button dialog_submit = dialog.findViewById(R.id.submit_lecDialog_btn);

        _date = dialog.findViewById(R.id.lec_Date);
        lec_img = dialog.findViewById(R.id.lec_img);
        dialog_lecTitle_txt = dialog.findViewById(R.id.dialog_lecTitle_txt);
        dialog_lecDecs_txt = dialog.findViewById(R.id.dialog_lecDecs_txt);

        //widgets click Listener
        lec_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _seletImage();
            }
        });

        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgUri = null;
                dialog.dismiss();
            }
        });
        dialog_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _add_new_lec_process(imgUri);
                imgUri = null;
            }
        });

        _date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                _showDateandTimeDialog();
            }
        });

        // start dialog
        dialog.show();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

    }

    private void _add_new_lec_process(Uri uri) {
        String user_id = mAuth.getCurrentUser().getUid();
        String session_key_id = reference.push().getKey();
        String session_title = dialog_lecTitle_txt.getText().toString();
        String session_description = dialog_lecDecs_txt.getText().toString();
        String session_date = _date.getText().toString();

        if (uri == null || session_title.isEmpty() || session_description.isEmpty() || session_date.isEmpty()) {
            Toast.makeText(getContext(), "Please fill Lecature Information", Toast.LENGTH_SHORT).show();
        } else {

            dialog.setContentView(R.layout.loading);
            mStorageRef.child(user_id)
                    .child(session_key_id)
                    .child("LectureImg")
                    .putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Img Uploded", Toast.LENGTH_SHORT).show();
                        mStorageRef.child(user_id)
                                .child(session_key_id)
                                .child("LectureImg").getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {

                                String session_image = task.getResult().toString();
                                sessionModel.setUser_id(user_id);
                                sessionModel.setSession_key_id(session_key_id);
                                sessionModel.setSession_title(session_title);
                                sessionModel.setSession_description(session_description);
                                sessionModel.setSession_image(session_image);
                                sessionModel.setSession_date(session_date);
                                reference.child(user_id).child(session_key_id).setValue(sessionModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        dialog.setContentView(R.layout.qr_code);
                                        ImageView qr_code = dialog.findViewById(R.id.qr_code);
                                        Button dismiss = dialog.findViewById(R.id.dismiss_button);
                                        _show_QrCode(qr_code, session_key_id);
                                        dismiss.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialog.dismiss();
                                            }
                                        });

                                    }
                                });

                            }
                        });

                    }
                }
            });

        }

    }

    private void _show_QrCode(ImageView qrCode, String qr_key) {

        if (qr_key.length() > 0) {
            WindowManager manager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = width < height ? width : height;
            smallerDimension = smallerDimension * 3 / 4;

            QRGEncoder qrgEncoder = new QRGEncoder(
                    qr_key.trim(), null,
                    QRGContents.Type.TEXT,
                    smallerDimension);
            try {
                Bitmap bitmap = qrgEncoder.encodeAsBitmap();
                qrCode.setImageBitmap(bitmap);
            } catch (Exception e) {
                Log.v("something wrong", e.toString());
            }
        } else {
            Toast.makeText(getContext(), "Errror", Toast.LENGTH_SHORT).show();
        }
    }

}


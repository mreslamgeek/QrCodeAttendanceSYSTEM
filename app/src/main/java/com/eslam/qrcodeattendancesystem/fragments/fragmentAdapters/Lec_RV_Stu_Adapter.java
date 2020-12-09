package com.eslam.qrcodeattendancesystem.fragments.fragmentAdapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.eslam.qrcodeattendancesystem.R;
import com.eslam.qrcodeattendancesystem.ScannerActivity;
import com.eslam.qrcodeattendancesystem.models.SessionModel;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

public class Lec_RV_Stu_Adapter extends RecyclerView.Adapter<Lec_RV_Stu_Adapter.Holder> {
    List<SessionModel> list = new ArrayList<>();
    Context context;
    SessionModel myUse;
    int count = 0;

    public Lec_RV_Stu_Adapter(List<SessionModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_lec, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final SessionModel sessionModel = list.get(position);
        int[] myImageList = new int[]{R.drawable.c_gradient_1, R.drawable.c_gradient_2
                , R.drawable.c_gradient_3, R.drawable.c_gradient_4
                , R.drawable.c_gradient_5, R.drawable.c_gradient_6};

        myUse = sessionModel;


        Glide.with(context).load(sessionModel.getSession_image())
                .override(300,300)
                .apply(RequestOptions.circleCropTransform())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.qr_logo)
                .into(holder.img);


        //Picasso.get().load(sessionModel.getSession_image()).noFade().memoryPolicy(MemoryPolicy.NO_STORE).into(holder.img);

        holder.title.setText(sessionModel.getSession_title());
        holder.date.setText(sessionModel.getSession_date());
        holder.decs.setText(sessionModel.getSession_description());
        int number_gr = count;
        count++;
        if (count == 6) count = 0;

        Drawable drawable = ContextCompat.getDrawable(context, myImageList[number_gr]);

        holder.layout.setBackground(drawable);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "" + sessionModel.getSession_key_id(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ScannerActivity.class);
                intent.putExtra("key", sessionModel.getSession_key_id());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView title, date, decs;
        View view;
        ImageView scan;
        RelativeLayout layout;

        public Holder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.lec_img_prev);
            title = itemView.findViewById(R.id.title_prev);
            date = itemView.findViewById(R.id.date_prev);
            decs = itemView.findViewById(R.id.dec_prev);
            scan = itemView.findViewById(R.id.scan_prev);
            layout = itemView.findViewById(R.id.container_prev);

            view = itemView;

        }
    }
}

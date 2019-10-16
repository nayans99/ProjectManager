package com.example.android.project;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class
eod_details extends RecyclerView.Adapter<eod_details.ViewHolder> {
    private Context mContext;
    private ArrayList<projectTitles> pro_title;
    private SparseBooleanArray expandState = new SparseBooleanArray();

    public eod_details(Context context, ArrayList<projectTitles> title){
        mContext = context;
        pro_title = title;
        for(int i = 0;i<title.size();i++){
            expandState.append(i,false);
        }
    }
    @NonNull
    @Override
    public eod_details.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view =  layoutInflater.inflate(R.layout.info_task,parent,false);
        eod_details.ViewHolder viewHolder = new eod_details.ViewHolder(view);

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull final  eod_details.ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        TextView name = holder.title;
        name.setText(pro_title.get(position).getDname());
       holder.desc.setText(pro_title.get(position).getDesc());
       String stat = pro_title.get(position).getLead();
       if(stat.equals("Incomplete")) {
           holder.cv.setCardBackgroundColor(0xB0EC1414);
       }
       else {
           holder.cv.setCardBackgroundColor(0xff00f059);
           holder.done.setVisibility(View.GONE);
       }

        holder.veod.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

           }
       });
       holder.done.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(final View view) {

               Log.d("heyaaaa", "onClick: "+pro_title.get(position).getLead());
               new AlertDialog.Builder(view.getContext())
                       .setTitle("Project Status")
                       .setMessage("Mark the project as done?")
                       .setIcon(android.R.drawable.ic_dialog_alert)
                       .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                           public void onClick(DialogInterface dialog, int whichButton) {
                               Map<String, Object> promap = new HashMap<>();
                               promap.put("Status","Complete");
         FirebaseFirestore.getInstance().collection("Project").document(pro_title.get(position).getStat().toString())
                 .collection("tasks").document(pro_title.get(position).getDname().toString() ).update("Status","Complete");
         FirebaseFirestore.getInstance().collection("USERS").document(pro_title.get(position).getDate())
                 .collection("tasks").document(pro_title.get(position).getDname().toString()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
             @Override
             public void onSuccess(Void aVoid) {
                 Toast.makeText(mContext, "Marked Complete", Toast.LENGTH_SHORT).show();
             }
         });

         Intent intent = new Intent(view.getContext(), employeeTask.class);
                               intent.putExtra("title",pro_title.get(position).getStat());
                               mContext.startActivity(intent);
                           }})
                       .setNegativeButton(android.R.string.no, null).show();

           }
       });

    }
    @Override
    public int getItemCount() {
        return pro_title.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, desc;
        CardView cv;
        Button veod, done;
        public RelativeLayout buttonLayout;
        public LinearLayout expandableLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv);
            desc = itemView.findViewById(R.id.tvdesc);
            cv = itemView.findViewById(R.id.cv);
            veod = itemView.findViewById(R.id.veod);
            done = itemView.findViewById(R.id.done);
        }
    }
}
package com.example.android.project;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class project_detailsm extends RecyclerView.Adapter<project_detailsm.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private ArrayList<projectTitles> pro_title;
    String status;
    TextView projecttitle,prodesc,prolead;
    Button viewtask,report;
    String pt,pdesc,pl;
    String role;
    private SparseBooleanArray expandState = new SparseBooleanArray();

    public project_detailsm(Context context, ArrayList<projectTitles> title){
        mContext = context;
        pro_title = title;
        for(int i = 0;i<title.size();i++){
            expandState.append(i,false);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view =  layoutInflater.inflate(R.layout.info,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        parent.setOnClickListener(this);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final LayoutInflater inflater = (LayoutInflater)
                            view.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.popup_project, null);

                    if(status.equals("Done")) {
                        popupView.findViewById(R.id.bview).setVisibility(View.GONE);
                        popupView.findViewById(R.id.breport);
                    }
                    if(status.equals("Upcoming"))
                        popupView.findViewById(R.id.breport).setVisibility(View.GONE);



                    // create the popup window
                    int width = MATCH_PARENT;
                    int height = 1300;
                    boolean focusable = true; // lets taps outside the popup also dismiss it

                    projecttitle = popupView.findViewById(R.id.projecttitle);
                    prodesc = popupView.findViewById(R.id.prodesc);
                    prolead = popupView.findViewById(R.id.prolead);

                    viewtask = popupView.findViewById(R.id.bview);
                    report = popupView.findViewById(R.id.breport);

                   TextView t = view.findViewById(R.id.tv);
                   final TextView d = view.findViewById(R.id.tvdesc);
                    projecttitle.setText(t.getText().toString());
                    FirebaseFirestore.getInstance().collection("Project").document(t.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            final ProjectDetails pd = documentSnapshot.toObject(ProjectDetails.class);
                            prodesc.setText(pd.getDescription());
                        }
                    });
                  //  prolead.setText(pl);

                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                    // show the popup window
                    // which view you pass in doesn't matter, it is only used for the window tolken
                    popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                    // dismiss the popup window when touched
                    popupView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            popupWindow.dismiss();
                            return true;
                        }
                    });

                    viewtask.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(view.getContext(), employeeTask.class);
                            intent.putExtra("title",pt);
                            mContext.startActivity(intent);
                        }
                    });
                    report.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(view.getContext(), submitActivity.class);
                            intent.putExtra("title",pt);
                            mContext.startActivity(intent);
                        }
                    });

                }
            });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.setIsRecyclable(false);
        projectTitles item=pro_title.get(position);
        holder.title.setTag(item);
        TextView name = holder.title;
        name.setText(pro_title.get(position).getDname());
        status = pro_title.get(position).getStat();
        pt = pro_title.get(position).getDname();
        pdesc = pro_title.get(position).getDesc();
        pl = pro_title.get(position).getLead();
        final boolean isExpanded = expandState.get(position);
        holder.expandableLayout.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.buttonLayout.setVisibility(View.GONE);
        holder.buttonLayout.setRotation(expandState.get(position) ? 180f : 0f);
        holder.buttonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickButton(holder.expandableLayout, holder.buttonLayout,  position);
            }
        });


      /*  holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {


                //creating a popup menu
                PopupMenu popup = new PopupMenu(mContext, holder.buttonViewOption);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menum);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                      //  projectTitles cards=(projectTitles) view.getTag();
                      //  final String value = cards.getDname();

                        switch (item.getItemId()) {

                            case R.id.action_assign:

                                Intent i = new Intent(view.getContext(), addtaskActivity.class);
                                String strName = holder.title.getText().toString().trim();

                                i.putExtra("str", strName);
                                mContext.startActivity(i);
                                return true;
                            case R.id.action_submit:

                                Intent i2 = new Intent(view.getContext(), submitActivity.class);
                                String strName2 = holder.title.getText().toString().trim();
                                String strName3 = holder.desc.getText().toString().trim();
                                String strName4 = holder.lead.getText().toString().trim();
                                String strName5 = holder.stat.getText().toString().trim();
                                String strName6 = holder.title.getText().toString().trim();
                                i2.putExtra("str", strName2);
                                i2.putExtra("str1", strName3);
                                i2.putExtra("str2", strName4);
                                i2.putExtra("str3", strName5);
                                mContext.startActivity(i2);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();

            }
        });*/
      holder.buttonViewOption.setVisibility(View.GONE);
    }



    @Override
    public int getItemCount() {
        return pro_title.size();
    }

    @Override
    public void onClick(View v) {
//this.itemClickListener.onItemClick(v);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,desc,lead,stat,buttonViewOption;
        public RelativeLayout buttonLayout;
        public LinearLayout expandableLayout,buttonll;
        Button but;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv);
            desc = itemView.findViewById(R.id.tv_desc);
            lead = itemView.findViewById(R.id.tv_lead);
            stat = itemView.findViewById(R.id.tv_status);
            buttonViewOption=itemView.findViewById(R.id.textViewOptions);
            expandableLayout = (LinearLayout) itemView.findViewById(R.id.expandableLayout);
            buttonLayout = (RelativeLayout) itemView.findViewById(R.id.button);
            buttonll = itemView.findViewById(R.id.buttonll);
        }
    }
    private void onClickButton(final LinearLayout expandableLayout, final RelativeLayout buttonLayout, final  int i) {

        //Simply set View to Gone if not expanded
        //Not necessary but I put simple rotation on button layout
        if (expandableLayout.getVisibility() == View.VISIBLE){
            createRotateAnimator(buttonLayout, 180f, 0f).start();
            expandableLayout.setVisibility(View.GONE);
            expandState.put(i, false);
        }else{
            createRotateAnimator(buttonLayout, 0f, 180f).start();
            expandableLayout.setVisibility(View.VISIBLE);
            expandState.put(i, true);
        }
    }
    private ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(new LinearInterpolator());
        return animator;
    }
}

package com.example.android.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class noteadapter extends FirestoreRecyclerAdapter<note,noteadapter.noteholder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public noteadapter(@NonNull FirestoreRecyclerOptions<note> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull noteholder holder, int position, @NonNull note model) {

        holder.textViewtitle.setText(model.getTitle());
        holder.textViewdescription.setText(model.getDescription());
//        holder.textViewpriority.setText(String.valueOf(model.getDate()));
    }

    @NonNull
    @Override
    public noteholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);
        return new noteholder(v);
    }

    public void deleteitem(int position)
    {
        getSnapshots().getSnapshot(position).getReference().delete();
    }


    class noteholder extends RecyclerView.ViewHolder{
        TextView textViewtitle;
        TextView textViewdescription;
        TextView textViewpriority;

        public noteholder(@NonNull View itemView) {
            super(itemView);
            textViewtitle=itemView.findViewById(R.id.text_view_title);
            textViewdescription=itemView.findViewById(R.id.text_view_description);
        }
    }
}

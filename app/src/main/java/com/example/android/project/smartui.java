package com.example.android.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class smartui extends Fragment {
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private FirebaseAuth FA= FirebaseAuth.getInstance();
    ArrayList<note> list;
    private noteadapter adapter;
    private CollectionReference NBR=db.collection("USERS");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_smartui, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.recycler_view);

      //  FloatingActionButton buttonaddnote=view.findViewById(R.id.button_add_note);
     /*   buttonaddnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),newnoteactivity.class));
            }
        });*/
        Query query=NBR.document(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString()).collection("EOD").orderBy("Date");
        FirestoreRecyclerOptions<note> options=new FirestoreRecyclerOptions.Builder<note>().setQuery(query,note.class).build();

        adapter=new noteadapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteitem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);
        return  view;
    }
    private void  setuprecyclerview(RecyclerView recyclerView)
    {
      String erole = FirebaseAuth.getInstance().getCurrentUser().getEmail();


        Query query=NBR.document(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString()).collection("EOD").orderBy("Date");
       FirestoreRecyclerOptions<note> options=new FirestoreRecyclerOptions.Builder<note>().setQuery(query,note.class).build();

        adapter=new noteadapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteitem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}

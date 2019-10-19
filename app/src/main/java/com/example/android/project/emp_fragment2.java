package com.example.android.project;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class emp_fragment2 extends Fragment {
    RecyclerView recyclerView;
    public ArrayList<projectTitles> projectList;
    private RecyclerView.Adapter Adapter;
    List<String> list;
    String b;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("empoe11", "onCreateView: ");
        View view = inflater.inflate(R.layout.admin_tab, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        projectList = new ArrayList<>();
        Log.d("empoe", "onCreateView: "+ FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());



                FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getEmail()).collection("tasks").get().
                addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            task pd = documentSnapshot.toObject(task.class);
                            if(pd.getStatus().equals("Complete"))
                            projectList.add(new projectTitles(pd.getProjectname(),pd.getTaskname(),pd.getTaskdesc(),pd.getStatus(),pd.getDate()));
                        }

                        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        final RecyclerView.LayoutManager rvLiLayoutManager = layoutManager;
                        recyclerView.setLayoutManager(rvLiLayoutManager);
                        project_detailse dom = new project_detailse(getActivity(), projectList);
                        recyclerView.setAdapter(dom);
                        }

                });


        return view;
    }
}
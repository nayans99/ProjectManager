package com.example.android.project;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
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

public class emp_fragment extends Fragment {
    RecyclerView recyclerView;
    public ArrayList<projectTitles> projectList;
    private RecyclerView.Adapter Adapter;
    List<String> list;
    String deadate;
    NotificationCompat.Builder notification;
    private static final int id = 45412;
    String b;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("empoe11", "onCreateView: ");
        View view = inflater.inflate(R.layout.admin_tab, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        projectList = new ArrayList<>();
        Log.d("empoe", "onCreateView: "+ FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());
    /*    FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString()).collection("projects")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Log.d("empo88e", "onSuccess: ");
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Log.d("empo78e", "onSuccess: ");
                    final ProjectDetails pd = documentSnapshot.toObject(ProjectDetails.class);
                    projectList.add(new projectTitles(pd.getTitle()));
                    Log.d("empoe", "onSuccess: ");
                }
            }
        });*/

  /*      FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString()).collection("projects")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots) {


            }});
        FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString()).collection("projects").
                addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                            if (doc.getType() == DocumentChange.Type.ADDED) {
                                Log.d("Brand Name: ", doc.getDocument().getId());
                                doc.getDocument().getReference().collection("SubBrands").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                        if (e != null) {
                                            Log.d("", "Error : " + e.getMessage());
                                        }

                                        for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                            if (doc.getType() == DocumentChange.Type.ADDED) {
                                                Log.d("SubBrands Name: ", doc.getDocument().getId());
                                            }
                                        }

                                    }
                                });
                            }

                        }
                    }
                })


        FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser()
        .getEmail()).collection("projects").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot ds : queryDocumentSnapshots){
                    String d = ds.getId().toString();
                    Log.d("empppp", "onSuccess: "+d);
                    FirebaseFirestore.getInstance().collection("Project").document(d).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    // convert document to POJO
                                    ProjectDetails pd = document.toObject(ProjectDetails.class);
                                    projectList.add(new projectTitles(pd.getTitle(),pd.getDescription(),pd.getTeamLead(),pd.getStatus(),pd.getDate()));
                                }

                            } else {
                                Log.d("FragNotif", "get failed with ", task.getException());
                            }
                        }
                    });
                }

                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                final RecyclerView.LayoutManager rvLiLayoutManager = layoutManager;
                recyclerView.setLayoutManager(rvLiLayoutManager);
                project_details dom = new project_details(getActivity(), projectList);
                recyclerView.setAdapter(dom);
            }
        });*/

       // FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getEmail()).list


                FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getEmail()).collection("tasks").get().
                addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            task pd = documentSnapshot.toObject(task.class);
                            if(pd.getStatus().equals("Incomplete"))
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
package com.example.android.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class mtab3 extends Fragment {
    public RecyclerView recyclerView;
    public ArrayList<projectTitles> projectList;
    FirebaseAuth currentUser;

    public mtab3() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_tab, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        projectList = new ArrayList<>();
        currentUser = FirebaseAuth.getInstance();

        FirebaseFirestore.getInstance().collection("Project").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    ProjectDetails pd = documentSnapshot.toObject(ProjectDetails.class);
                    if(pd.getTeamLead().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString()))
                    if (pd.getStatus().equalsIgnoreCase("Done") )
                        projectList.add(new projectTitles(pd.getTitle(), pd.getDescription(), pd.getTeamLead(), pd.getStatus(), pd.getDate()));

                }
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                final RecyclerView.LayoutManager rvLiLayoutManager = layoutManager;
                recyclerView.setLayoutManager(rvLiLayoutManager);
                project_detailsm dom = new project_detailsm(getActivity(), projectList);
                recyclerView.setAdapter(dom);
            }
        });

        return view;
    }
}


/*package com.example.sdllabproject1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class mtab2 extends Fragment {
    public mtab2() {
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.manager_tab2,container,false);
        return  view;
    }
}
*/
package com.example.android.project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class ViewPieChart extends AppCompatActivity {
String value;
int value2=50;
float res=0;
float num,den;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pie_chart);
        Intent i = getIntent();
        PieChartView pieChartView = findViewById(R.id.chart);
        List<SliceValue> pieData = new ArrayList<>();

        num = i.getIntExtra("taskg",0);
        den = i.getIntExtra("task",0);

        Log.d("befafter", "onCreate: "+num+den);

        float percentage = num/den*100;
        float remaining = 100-percentage;

        Log.d("befafter", "onCreate: "+percentage+remaining);


        pieData.add(new SliceValue(percentage, Color.GREEN));
        pieData.add(new SliceValue(remaining, Color.RED));
       //pieData.add(new SliceValue(res, Color.GREEN));
     //  pieData.add(new SliceValue(value3, Color.RED));
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartView.setPieChartData(pieChartData);
    }
}

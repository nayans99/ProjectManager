package com.example.android.project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class ViewPieChart extends AppCompatActivity {
int value;
int value2=50;
float res=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pie_chart);
        Intent i = getIntent();
        value = i.getIntExtra("donetaskno",10);
        res=(value/value2)*100;
        float value3= 100-res;
        PieChartView pieChartView = findViewById(R.id.chart);
        List<SliceValue> pieData = new ArrayList<>();

        pieData.add(new SliceValue(10, Color.GREEN));
        pieData.add(new SliceValue(90, Color.RED));
       //pieData.add(new SliceValue(res, Color.GREEN));
     //  pieData.add(new SliceValue(value3, Color.RED));
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartView.setPieChartData(pieChartData);
    }
}

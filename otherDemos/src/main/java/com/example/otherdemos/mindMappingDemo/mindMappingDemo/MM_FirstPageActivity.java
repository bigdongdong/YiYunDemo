package com.example.otherdemos.mindMappingDemo.mindMappingDemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.otherdemos.R;

public class MM_FirstPageActivity extends AppCompatActivity {

    TextView createMindMapTV,historyTV;
    ImageButton backIB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mm_activity_firstpage);

        initialize();




    }

    private void initialize() {

    }

}

package com.example.nutritionpal;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements Calculate.OnFragmentInteractionListener, MealList.OnFragmentInteractionListener{

    Button calculateBtn, trackBtn;
    int selectFlag = 0;
    Intent selectedIntent;
    private static final int REQ_CODE = 2048;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calculateBtn = (Button) findViewById(R.id.calculateBtn);
        trackBtn = (Button) findViewById(R.id.trackBtn);
        selectedIntent = new Intent(MainActivity.this,SecondActivity.class);

        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //selectFlag = 1 for calculate fragment
                selectFlag = 1;
                selectedIntent.putExtra("Fragment",selectFlag);
                startActivityForResult(selectedIntent,REQ_CODE);
            }
        });
        trackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Select falg = 2 for track fragment
                selectFlag = 2;
                selectedIntent.putExtra("Fragment",selectFlag);
                startActivityForResult(selectedIntent,REQ_CODE);
            }
        });

    }
    @Override
    public void onFragmentInteraction(Uri uri){
        //
    }
}

package com.example.nutritionpal;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SecondActivity extends AppCompatActivity implements Calculate.OnFragmentInteractionListener, MealList.OnFragmentInteractionListener, Calculate.OnDataPass {

    Intent returnIntent = new Intent();
    int selectFlag;
    FragmentManager manager = getSupportFragmentManager();
    FragmentTransaction transaction = manager.beginTransaction();
    Fragment fragmentOne;
    Fragment fragmentTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        fragmentOne = new Calculate();
        fragmentTwo = new MealList();


        Bundle intent = getIntent().getExtras();

        if(intent != null){
            selectFlag = intent.getInt("Fragment");
            if(selectFlag == 1){
                transaction.add(R.id.fragment_container,fragmentOne);
                transaction.commit();
            }
            if(selectFlag == 2){
                transaction.add(R.id.fragment_container, fragmentTwo);
                transaction.commit();
            }
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri){
        //
    }

    @Override
    public double onDataPass(String data) {
            double returnData = Double.parseDouble(data);
            return returnData;
    }
}

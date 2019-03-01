package com.example.nutritionpal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Calculate.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Calculate extends Fragment {

    Button calculateBtn;
    TextView caloriesText, proteinText, carbsText, fatsText;
    EditText userHeight, userWeight, userAge;
    Spinner gender, activity;
    RadioGroup goalSelect;
    int goal = 0;
    double caloricIntake;
    double protein;
    double fats;
    double carbs;
    String userGender;
    double activityFactor;

    OnDataPass dataToActivity;
    private OnFragmentInteractionListener mListener;

    public Calculate() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculate, container, false);
        calculateBtn = (Button) view.findViewById(R.id.calculate);
        caloriesText = (TextView) view.findViewById(R.id.caloricTextView);
        proteinText = (TextView) view.findViewById(R.id.proteinAmount);
        carbsText = (TextView) view.findViewById(R.id.carbAmount);
        fatsText = (TextView) view.findViewById(R.id.fatAmount);
        userHeight = (EditText) view.findViewById(R.id.heightInput);
        userWeight = (EditText) view.findViewById(R.id.weightInput);
        userAge = (EditText) view.findViewById(R.id.ageInput);
        gender = (Spinner) view.findViewById(R.id.genderSpinner);
        activity = (Spinner) view.findViewById(R.id.activitySpinner);
        goalSelect = (RadioGroup) view.findViewById(R.id.goalGroup);

        //Gender Spinner drop down elements
        final String[] genderSpinner = getContext().getResources().getStringArray(R.array.Gender);
        ArrayAdapter genderAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, genderSpinner);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Attaching adapter to spinner
        gender.setAdapter(genderAdapter);

        //Activity Level Spinner
        final String[] activitySpinner = getContext().getResources().getStringArray(R.array.ActivityLevel);
        ArrayAdapter activityAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, activitySpinner);
        activityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Attach adapter to spinner
        activity.setAdapter(activityAdapter);

        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                //Action
                userGender = gender.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){
                //Something
            }
        });

        goalSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //Changes the goal variable
                int radioId = goalSelect.getCheckedRadioButtonId();
                if(radioId == R.id.loseWeight)
                {
                    //Flag goal is set to 1
                    goal = 1;
                }else if(radioId == R.id.maintainWeight){
                    //Flag goal is set to 2
                    goal = 2;
                }else if(radioId == R.id.gainWeight){
                    //Flag goal is set to 3
                    goal = 3;
                }
            }
        });
        activity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] activityArray = getContext().getResources().getStringArray(R.array.ActivityLevel);
                //Assigns the Activity Factor.  Depends on the user's Activity Level
                String userActivity = activity.getSelectedItem().toString();
                if(userActivity.equals(activityArray[0])) {
                    activityFactor = 1.2;
                } else if(userActivity.equals(activityArray[1])){
                    activityFactor = 1.375;
                }else if(userActivity.equals(activityArray[2])){
                    activityFactor = 1.55;
                }else if(userActivity.equals(activityArray[3])){
                    activityFactor = 1.725;
                }else if(userActivity.equals(activityArray[4])){
                    activityFactor = 1.9;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Probably dont need anything here
            }
        });



        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Takes all the user input and calculates
                double weight;
                double height;
                double BMR = 0;
                int age = 0;
                double caloricNeed;


                //Convert weight to kg
                String weightInput = userWeight.getText().toString();
                weight = Double.parseDouble(weightInput);
                weight = weight / 2.2;
                //Convert height to cm
                String heightInput = userHeight.getText().toString();
                height = Double.parseDouble(heightInput);
                height = height * 2.54;
                //Collect Age
                String ageInput = userAge.getText().toString();
                age = Integer.parseInt(ageInput);

                if(userGender.equals("Male")) {
                    //Calculate BMR Male
                    BMR = (height * 6.25) + (weight * 9.99) - (age * 4.92) + 5;
                } else if(userGender.equals("Female")) {
                    //Calculate BMR Female
                    BMR = (height * 6.25) + (weight * 9.99) - (age * 4.92) - 161;
                }
                //Harris Benedict Formula
                caloricNeed = BMR * activityFactor;

                //Modify for goal
                if(goal == 1){
                    //Lose Weight
                    caloricIntake = caloricNeed - 500;
                    protein = (caloricIntake * 0.40) / 4.0;
                    carbs = (caloricIntake * 0.30) / 4.0;
                    fats = (caloricIntake * 0.30) / 9.0;
                }else if(goal == 2){
                    //Maintain Weight
                    caloricIntake = caloricNeed;
                    protein = (caloricIntake * 0.40) / 4.0;
                    carbs = (caloricIntake * 0.40) / 4.0;
                    fats = (caloricIntake * 0.20) / 9.0;

                }else if(goal == 3){
                    //Gain Weight
                    caloricIntake = caloricNeed + 500;
                    protein = (caloricIntake * 0.35) / 4.0;
                    carbs = (caloricIntake * 0.45) / 4.0;
                    fats = (caloricIntake * 0.35) / 9.0;
                }
                String calFormat = String.format ("%.0f", caloricIntake);
                String proteinForm = String.format ("%.0f",protein);
                String carbForm = String.format ("%.0f", carbs);
                String fatForm = String.format ("%.0f", fats);
                caloriesText.setText(calFormat);
                proteinText.setText(proteinForm);
                carbsText.setText(carbForm);
                fatsText.setText(fatForm);
                //Pass Data to activity
                passData(calFormat);
            }
            });

        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    //Send data to activity
    public interface OnDataPass {
        public double onDataPass(String data);
    }


    public void passData(String data) {
        dataToActivity.onDataPass(data);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            dataToActivity = (OnDataPass) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

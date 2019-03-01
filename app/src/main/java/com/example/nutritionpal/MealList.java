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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MealList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MealList extends Fragment {

    TextView calorieTarget, dayAmount, remainingAmount;
    EditText foodLabelInput, calorieInput, fatsInput, carbsInput, proteinInput;
    Button addMealBtn, removeBtn;
    ListView foodList;
    double dayTotal;
    double remainingTotal;
    double caloricNeed;
    double caloricEntry;
    int entryVal;
    ArrayList<String> mealList = new ArrayList<String>();
    ArrayAdapter foodListAdapter;

    private OnFragmentInteractionListener mListener;

    public MealList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_list, container, false);

        //calorieTarget = (TextView) view.findViewById(R.id.targetAmount);
        //dayAmount = (TextView) view.findViewById(R.id.dayAmount);
        //remainingAmount = (TextView) view.findViewById(R.id.remainingAmount);
        foodLabelInput = (EditText) view.findViewById(R.id.labelInput);
        calorieInput = (EditText) view.findViewById(R.id.calorieInput);
        fatsInput = (EditText) view.findViewById(R.id.fatsInput);
        carbsInput = (EditText) view.findViewById(R.id.carbsInput);
        proteinInput = (EditText) view.findViewById(R.id.proteinInput);
        addMealBtn = (Button) view.findViewById(R.id.addMeals);
        removeBtn = (Button) view.findViewById(R.id.removeMeals);



        foodListAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,mealList);
        foodList = (ListView) view.findViewById(R.id.mealList);
        foodList.setAdapter(foodListAdapter);

        //Adds the input to the list
        addMealBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Take the input from the EditText boxes
                String foodLabel = foodLabelInput.getText().toString();
                String calories = calorieInput.getText().toString();
                String fats = fatsInput.getText().toString();
                String carbs = carbsInput.getText().toString();
                String protein = proteinInput.getText().toString();
                //Create entry and add to list
                String addEntry = (foodLabel + " Calories:" + calories + "\n"+ "Fats: " + fats + " Carbs: " + carbs + "Protein: " + protein);
                mealList.add(addEntry);
                //Notify List adapter to update
                foodListAdapter.notifyDataSetChanged();
                //Add the entry to the total
                //double calorieNumber = Double.parseDouble(calories);
                //dayTotal = dayTotal + calorieNumber;
                //remainingTotal = caloricNeed - dayTotal;
                //dayAmount.setText(Double.toString(dayTotal));
                //remainingAmount.setText(Double.toString(remainingTotal));

                //Reset the Entries
                foodLabelInput.setText("");
                calorieInput.setText("");
                fatsInput.setText("");
                carbsInput.setText("");
                proteinInput.setText("");
            }

        });
        //Selects Item for removal
        foodList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                String entry = parent.getItemAtPosition(position).toString() + " selected";
                /*
                String pattern =  "([0-9]*):";
                Pattern patternSearch = Pattern.compile(pattern);
                Matcher match = patternSearch.matcher(entry);
                caloricEntry = Double.parseDouble(match.group(1));
                */
                entryVal = position;
                Toast.makeText(getContext(), entry, Toast.LENGTH_LONG).show();
            }
        });
        //Remove the selected entry
        removeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Remove meal
                mealList.remove(entryVal);
                foodListAdapter.notifyDataSetChanged();
                //Change the Calorie totals
               // dayTotal = dayTotal - caloricEntry;
                //remainingTotal = caloricNeed - dayTotal;
                //dayAmount.setText(Double.toString(dayTotal));
                //remainingAmount.setText(Double.toString(remainingTotal));
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

package com.deakin.unitConverter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // spinners
        Spinner unitSpinner = findViewById(R.id.spinner_units);
        Spinner sourceSpinner = findViewById(R.id.source_units);
        Spinner destinationSpinner = findViewById(R.id.destination_units);

        // text input fields
        TextInputEditText sourceField = findViewById(R.id.source_input_text);
        TextInputEditText destinationField = findViewById(R.id.destination_input_text);

        // convert button
        Button convertButton = findViewById(R.id.convert_button);

        // units
        String[] unitCategories = {"Length", "Weight", "Temperature"};
        String[] lengthUnits = {"inch", "foot", "yard", "mile", "km", "cm", "meter"};
        String[] weightUnits = {"lbs", "kg", "ounce", "ton", "g"};
        String[] tempUnits = {"C", "F", "K"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, unitCategories);

        ArrayAdapter<String> lengthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lengthUnits);
        ArrayAdapter<String> weightAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, weightUnits);
        ArrayAdapter<String> tempAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tempUnits);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(adapter);
        sourceSpinner.setAdapter(lengthAdapter);
        destinationSpinner.setAdapter(lengthAdapter);

        // on unit chosen, change units on the two
        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                if (selectedItem.equalsIgnoreCase("length"))
                {
                    sourceSpinner.setAdapter(lengthAdapter);
                    destinationSpinner.setAdapter(lengthAdapter);
                }
                else if (selectedItem.equalsIgnoreCase("weight"))
                {
                    sourceSpinner.setAdapter(weightAdapter);
                    destinationSpinner.setAdapter(weightAdapter);
                }
                else if (selectedItem.equalsIgnoreCase("temperature"))
                {
                    sourceSpinner.setAdapter(tempAdapter);
                    destinationSpinner.setAdapter(tempAdapter);
                }

                destinationSpinner.setSelection(1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // on convert
        convertButton.setOnClickListener(v -> {
            // Get text from input field
            String inputValue = Objects.requireNonNull(sourceField.getText()).toString().trim();

            // Check if input is empty
            if (inputValue.isEmpty()) {
                sourceField.setError("Enter a number");
                return;
            }

            double inputNum;
            try {
                inputNum = Double.parseDouble(inputValue);
            } catch (NumberFormatException e) {
                sourceField.setError("Invalid number");
                return;
            }

            // Get selected units
            String unitType = unitSpinner.getSelectedItem().toString();
            String fromUnit = sourceSpinner.getSelectedItem().toString();
            String toUnit = destinationSpinner.getSelectedItem().toString();

            // Call ConvertUnit and store result
            double result = ConvertUnit.convert(inputNum, fromUnit, toUnit, unitType);
            String resultString = result+"";

            destinationField.setText(resultString);
        });
    }
}
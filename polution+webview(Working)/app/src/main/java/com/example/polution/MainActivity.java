package com.example.polution;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    //final public TextView textView=(TextView)findViewById(R.id.resultView);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner stateSpinner = (Spinner)findViewById(R.id.stateSpinner);
        final Spinner citySpinner = (Spinner)findViewById(R.id.citySpinner);
        final Button fetch = (Button)findViewById(R.id.button);
        final Button mapbutton= (Button)findViewById(R.id.mapbutton);
        final TextView textView= (TextView)findViewById(R.id.resultView);
        final String[] citySelected = new String[1];
        ArrayAdapter<CharSequence> adapterState=ArrayAdapter.createFromResource(this,R.array.state,android.R.layout.simple_spinner_item);
        adapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(adapterState);
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String stateSelected = (String) parent.getItemAtPosition(position);
                switch (position){
                    case 0: Toast.makeText(MainActivity.this, "Select State", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:ArrayAdapter<CharSequence> adapterCityAP=ArrayAdapter.createFromResource(MainActivity.this,R.array.cityAP,android.R.layout.simple_spinner_item);
                        adapterCityAP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        citySpinner.setAdapter(adapterCityAP);
                        Toast.makeText(MainActivity.this, "Select City/Station", Toast.LENGTH_SHORT).show();
                        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                citySelected[0] = (String) parent.getItemAtPosition(position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        break;
                    case 2:ArrayAdapter<CharSequence> adapterCityA=ArrayAdapter.createFromResource(MainActivity.this,R.array.cityA,android.R.layout.simple_spinner_item);
                        adapterCityA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        citySpinner.setAdapter(adapterCityA);
                        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                citySelected[0] = (String) parent.getItemAtPosition(position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        break;
                    case 3:ArrayAdapter<CharSequence> adapterCityB=ArrayAdapter.createFromResource(MainActivity.this,R.array.cityB,android.R.layout.simple_spinner_item);
                        adapterCityB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        citySpinner.setAdapter(adapterCityB);
                        Toast.makeText(MainActivity.this, "Select City/Station", Toast.LENGTH_SHORT).show();
                        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                citySelected[0] = (String) parent.getItemAtPosition(position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        break;
                    case 4:ArrayAdapter<CharSequence> adapterCityD=ArrayAdapter.createFromResource(MainActivity.this,R.array.cityD,android.R.layout.simple_spinner_item);
                        adapterCityD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        citySpinner.setAdapter(adapterCityD);
                        Toast.makeText(MainActivity.this, "Select City/Station", Toast.LENGTH_SHORT).show();
                        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                citySelected[0] = (String) parent.getItemAtPosition(position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String polutionData = new polutionData().execute(citySelected[0]).get();
                    JSONArray polutionJSON = new JSONArray(polutionData);
                    String polutionResult="Data fetched from data.gov.in\nLast updated : "+ polutionJSON.getJSONObject(0).getString("last_update");
                    for(int i=0;i<polutionJSON.length();i++)
                    {
                        polutionResult += "\nPolutant : "+polutionJSON.getJSONObject(i).getString("pollutant_id")
                                +"\nMin:"+polutionJSON.getJSONObject(i).getString("pollutant_min")
                                +"\tAvg:"+polutionJSON.getJSONObject(i).getString("pollutant_avg")
                                +"\tMax:"+polutionJSON.getJSONObject(i).getString("pollutant_max");
                    }
                    textView.setText(polutionResult);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        mapbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,livemap.class);
                startActivity(intent);
            }
        });


    }
}

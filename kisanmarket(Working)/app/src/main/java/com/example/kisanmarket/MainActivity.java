package com.example.kisanmarket;

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
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner stateSpinner = (Spinner)findViewById(R.id.stateSpinner);
        final Spinner districtSpinner = (Spinner)findViewById(R.id.districtSpinner);
        final Spinner marketSpinner= (Spinner)findViewById(R.id.marketSpinner);
        final Button fetchButton = (Button)findViewById(R.id.fetch);
        final TextView textView = (TextView)findViewById(R.id.textView);
        final String[] stateSelected = new String[1];
        final String[] districtSelected = new String[1];
        final String[] marketSelected = new String[1];
        final String districtList[]= new String[50];
        final String marketList[]=new String[100];
        final String commodityList[]=new String[200];
        final JSONArray[] itemArray = new JSONArray[1];
        ArrayAdapter<CharSequence> stateAdapter = ArrayAdapter.createFromResource(this,R.array.states,android.R.layout.simple_selectable_list_item);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateAdapter);
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){

                }
                else {
                    stateSelected[0] = parent.getItemAtPosition(position).toString();
                    JSONArray districtJSON= null;
                    try {
                        districtJSON = new marketdata().execute(stateSelected[0].replace(' ','+')).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(districtJSON.length()==0)
                        Toast.makeText(MainActivity.this, "Data not available for this state, try later", Toast.LENGTH_SHORT).show();
                    if (districtJSON.length() != 0) {
                        int j = 0;
                        for (int i = 0; i < districtJSON.length(); i++) {
                            String district = null;
                            try {
                                district = districtJSON.getJSONObject(i).getString("district");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (j == 0) {
                                districtList[0] = district;
                                j += 1;
                            } else {
                                if (districtList[j - 1].equals(district)) {

                                } else {
                                    districtList[j] = district;
                                    j += 1;
                                }
                            }
                        }
                        String districtValues[] = new String[j];
                        System.arraycopy(districtList, 0, districtValues, 0, j);
                        ArrayAdapter<String> districtAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, districtValues);
                        districtSpinner.setAdapter(districtAdapter);
                        final JSONArray finalDistrictJSON = districtJSON;
                        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                districtSelected[0] = parent.getItemAtPosition(position).toString();
                                final JSONArray marketJSON = finalDistrictJSON;
                                if (marketJSON.length() != 0) {
                                    int j = 0;
                                    for (int i = 0; i < marketJSON.length(); i++) {
                                        String market = null;
                                        String district = null;
                                        try {
                                            market = marketJSON.getJSONObject(i).getString("market");
                                            district = marketJSON.getJSONObject(i).getString("district");
                                            itemArray[0] =new JSONArray();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        if(district.equals(districtSelected[0])){
                                        if (j == 0) {
                                            marketList[0] = market;
                                            j += 1;
                                        } else {
                                            if (marketList[j - 1].equals(market)) {

                                            } else {
                                                marketList[j] = market;
                                                j += 1;
                                            }
                                        }
                                    }
                                    }
                                    String marketValues[] = new String[j];
                                    System.arraycopy(marketList, 0, marketValues, 0, j);
                                    ArrayAdapter<String> marketAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, marketValues);
                                    marketSpinner.setAdapter(marketAdapter);
                                    marketSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            marketSelected[0] = parent.getItemAtPosition(position).toString();
                                            final JSONArray commodityJSON = finalDistrictJSON;
                                            if (commodityJSON.length() != 0) {
                                                int j = 0;
                                                for (int i = 0; i < commodityJSON.length(); i++) {
                                                    JSONObject commodity = null;
                                                    String market = null;
                                                    try {
                                                        commodity = commodityJSON.getJSONObject(i);
                                                        market = marketJSON.getJSONObject(i).getString("market");
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    if(market.equals(marketSelected[0])) {
                                                        itemArray[0].put(commodity);
                                                        j += 1;
                                                    }
                                                }
                                            }
                                        }


                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });
                                }
                          }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commodityItems="";
                for(int i=0;i<itemArray[0].length();i++){
                    String item = null;
                    String variety=null;
                    String date = null;
                    String min_price =null;
                    String max_price=null;
                    try {
                        item = itemArray[0].getJSONObject(i).getString("commodity");
                        variety = itemArray[0].getJSONObject(i).getString("variety");
                        date = itemArray[0].getJSONObject(i).getString("arrival_date");
                        min_price = itemArray[0].getJSONObject(i).getString("min_price");
                        max_price = itemArray[0].getJSONObject(i).getString("max_price");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    commodityItems +="Commodity:"+item+"\nVariety:"+variety+"\nDate of arrival:"+date+"\nMin Price:"+min_price+"\nMax Price:"+max_price+"\n\n";
                }
                Intent intent= new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("commodityItems",commodityItems);
                startActivity(intent);
            }
        });
    }
}

package com.example.kisanmarket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView textView=(TextView)findViewById(R.id.textView);

        Intent intent=getIntent();
        String commodityItems= intent.getStringExtra("commodityItems");
        textView.setText(commodityItems);
        textView.setMovementMethod(new ScrollingMovementMethod());
    }
}

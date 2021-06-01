package com.tarpe19.rongiajad;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class YldinfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yldinfo);
    }

    public void onBack(View v) {
        super.onBackPressed(); // or super.finish();
    }
}

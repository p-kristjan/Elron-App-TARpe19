package com.psennoi.elron;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

public class SettingsActivity extends AppCompatActivity {

    private SwitchCompat switchTheme;

    SharedPreferences mPreferences;
    public static final String sharedPrefFile = "com.psennoi.elron";

    // Boolean for storing the state of which theme is being used.
    boolean darkThemeEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        switchTheme = findViewById(R.id.darkModeSwitch);

        // If night mode is enabled, toggle the night mode switch as TRUE.
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) switchTheme.setChecked(true);
    }

    // Switch listener
    public void onSwitch(View view) {
        switch(view.getId()){
            // If theme witch is toggled ON, enable Night Mode, if it is toggled OFF, disable Night Mode.
            case R.id.darkModeSwitch:
                if(switchTheme.isChecked()) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                // Check if dark theme is on and adjust the darkThemeEnabled boolean accordingly.
                darkThemeEnabled = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
                // Save the state of the theme in shared preferences
                SharedPreferences.Editor preferencesEditor = mPreferences.edit();
                preferencesEditor.putBoolean(MainActivity.THEME_KEY, darkThemeEnabled);
                preferencesEditor.apply();
                break;
        }
    }
}
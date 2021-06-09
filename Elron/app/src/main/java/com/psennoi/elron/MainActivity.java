package com.psennoi.elron;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    AutoCompleteTextView etLocation, etDestination;
    Button btnCalendar;
    int year, month, day;

    private static final String[] TRAIN_STOPS = new String[]{
            "Tallinn", "Tartu", "Viljandi", "Rakvere", "Narva"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Saab praeguse kuupäeva ja paneb selle nupu tekstiks
        LocalDate currentDate = LocalDate.now();
        year = currentDate.getYear();
        month = currentDate.getMonthValue();
        day = currentDate.getDayOfMonth();
        btnCalendar = findViewById(R.id.btnCalendar);
        btnCalendar.setText(String.format("%02d.%02d.%d", day, month, year));

        etLocation = findViewById(R.id.etLocation);
        etDestination = findViewById(R.id.etDestination);

        // Automaatselt pakub olemasolevaid rongijaamu
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, TRAIN_STOPS);
        etLocation.setAdapter(adapter);
        etDestination.setAdapter(adapter);
    }

    // Nupp et vahetada sihtkoht ja lähtekoht omavahel
    public void onSwap(View view) {
        String inputLocation = etLocation.getText().toString();
        String inputDestination = etDestination.getText().toString();
        etLocation.setText(inputDestination, false);
        etDestination.setText(inputLocation, false);
    }

    // Kui kalendri nuppu vajutatakse
    public void onCalendar(View view) {
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "Date picker");
    }

    // Kalender
    @Override
    public void onDateSet(DatePicker view, int _year, int _month, int _day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, _year);
        calendar.set(Calendar.MONTH, _month);
        calendar.set(Calendar.DAY_OF_MONTH, _day);
        String currentDateString = (String.format("%02d.%02d.%d", _day, _month, _year));
        btnCalendar.setText(currentDateString);
        year = _year;
        month = _month;
        day = _day;
    }

    // Inflating the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    // Listener for menu item options
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSettings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.menuInfo:
                startActivity(new Intent(this, InfoActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
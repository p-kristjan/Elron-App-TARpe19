package com.psennoi.elron;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.psennoi.elron.Database.DatabaseAdapter;

import org.apache.commons.text.WordUtils;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    AutoCompleteTextView etLocation, etDestination;
    String txtLocation, txtDestination;
    private DatabaseAdapter helper;
    Button btnCalendar;
    public static final String THEME_KEY = "darkMode";
    int year, month, day;

    private static final String[]
            TRAIN_STOPS = new String[]{
            "Tallinn", "Narva", "Kitseküla", "Ülemiste", "Vesse", "Lagendi", "Kulli", "Aruküla", "Raasiku", "Parila", "Kehra", "Lahinguvälja", "Mustjõe", "Aegviidu", "Nelijärve",
            "Jäneda", "Lehtse", "Tapa", "Kadrina", "Rakvere", "Kabala", "Sonda","Kiviõli", "Püssi", "Kohtla-Nõmme", "Jõhvi", "Oru", "Vaivara", "Tamsalu","Kiltsi", "Rakke", "Vägeva",
            "Pedja", "Jõgeva", "Kaarepere", "Tabivere","Kärkna", "Kirsi", "Ülenurme", "Uhti", "Reola", "Vana-Kuuste", "Rebase", "Vastse-Kuuste", "Valgemetsa", "Kiidjärve","Taevaskoja",
            "Põlva", "Holvandi", "Ruusa", "Veriora", "Ilumetsa", "Orava", "Koidula", "Piusa", "Aardla", "Rapka", "Nõo", "Tõravere", "Peedu", "Elva", "Paluvera", "Puka", "Mägiste", "Keeni",
            "Sangaste", "Valga", "Tallinn-Väike", "Liiva", "Valdeku", "Männiku", "Saku", "Kasemetsa", "Kiisa", "Roobuka", "Vilivere", "Kohila", "Lohu", "Hagudi", "Rapla", "Keava", "Lelle",
            "Käru", "Türi", "Taikse", "Kärevere", "Ollepa", "Võhma", "Olustvere", "Sürgavere", "Viljandi", "Lilleküla", "Tondi", "Järve", "Rahumäe", "Nõmme", "Hiiu", "Kivimäe", "Pääsküla",
            "Laagri", "Urda", "Padula", "Saue", "Valingu", "Keila", "Kulna", "Vasalemma", "Kibuna", "Laitse", "Jaanika", "Riisipere", "Turba", "Niitvälja", "Klooga", "Klooga-Aedlinn",
            "Põllküla", "Laoküla", "Paldiski", "Kloogaranna"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences mPreferences = getSharedPreferences(SettingsActivity.sharedPrefFile, MODE_PRIVATE);

        // Helper for working with the database
        helper = new DatabaseAdapter(this);
        // If database does not exist, create it.
        if(!helper.tableExists()) helper.insertData();

        // Saab praeguse kuupäeva ja paneb selle nupu tekstiks
        LocalDate currentDate = LocalDate.now();
        year = currentDate.getYear();
        month = currentDate.getMonthValue();
        day = currentDate.getDayOfMonth();
        btnCalendar = findViewById(R.id.btnCalendar);
        btnCalendar.setText(String.format("%02d.%02d.%d", day, month, year));

        // Restore Dark or Light mode through shared preferences
        if(mPreferences.getBoolean(THEME_KEY, false)) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

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

    // Menüü inflatimine
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    // Listener menüü valikute jaoks
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

    // Kui otsimise nuppu vajutatakse
    public void onSearch(View view) {
        txtLocation = WordUtils.capitalizeFully(etLocation.getText().toString());
        txtDestination = WordUtils.capitalizeFully(etDestination.getText().toString());
        // Kontrollib kas sisestatud jaamad on korrektsed
        if(Arrays.asList(TRAIN_STOPS).contains(txtLocation) || !Arrays.asList(TRAIN_STOPS).contains(txtDestination)){
            // Lisab andmed andmebaasi
            helper.addRoute(txtLocation, txtDestination);

            // Genereerib url-i ja läheb sellele url-ile
            Uri uri = Uri.parse(String.format(getString(R.string.elron_url), txtLocation, txtDestination, year, month, day));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else {
            // Kui mingi rongijaam ei sobi
            Toast.makeText(this, "Kontrolli kas peatuse nimed on õigesti sisestatud ja proovi uuesti.", Toast.LENGTH_LONG).show();
        }
    }
}
package com.example.lab8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button btnFrom;
    private Button btnTo;
    private EditText editTextFrom;
    private EditText editTextTo;
    private Geocoder geocoder;
    private  Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost); //создаём объект вкладок
        tabHost.setup(); //инициализация контейнера вкладок

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");//создание первой вкладки
        tabSpec.setContent(R.id.linearLayout1);//привязка вкладки к содержимому
        tabSpec.setIndicator("Откуда");//название вкладки
        tabHost.addTab(tabSpec);//присоединение вкладки к TabHost

        tabSpec = tabHost.newTabSpec("tag2");//создание второй вкладки
        tabSpec.setContent(R.id.linearLayout2);//привязка второй вкладки к содержимому
        tabSpec.setIndicator("Куда");
        tabHost.addTab(tabSpec);
        tabHost.setCurrentTab(0);

        btnFrom = findViewById(R.id.btnFrom);
        btnTo = findViewById(R.id.btnTo);
        editTextFrom = findViewById(R.id.editTextFrom);
        editTextTo = findViewById(R.id.editTextTo);
        geocoder = new Geocoder(this, Locale.ENGLISH);
        intent = new Intent(this, MapsActivity.class);
    }

    public void searchFrom(View view) {
        try {
            List<Address> addresses = geocoder.getFromLocationName(editTextFrom.getText().toString(), 1);
            if (addresses.size() > 0) {
                Address fetchedAddress = addresses.get(0);
                intent.putExtra("DepartureLatitude", fetchedAddress.getLatitude());
                intent.putExtra("DepartureLongitude", fetchedAddress.getLongitude());
                Toast.makeText(getApplicationContext(), fetchedAddress.getLongitude() + "," + fetchedAddress.getLatitude(),
                        Toast.LENGTH_SHORT).show();
            } else {
                editTextTo.setText("Невозможно определить координаты");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Ошибка", Toast.LENGTH_SHORT).show();
        }
    }

    public void searchTo(View view) {
        try {
            List<Address> addresses = geocoder.getFromLocationName(editTextTo.getText().toString(), 1);
            if (addresses.size() > 0) {
                Address fetchedAddress = addresses.get(0);
                Toast.makeText(getApplicationContext(), fetchedAddress.getLongitude() + "," + fetchedAddress.getLatitude(),
                        Toast.LENGTH_SHORT).show();
                intent.putExtra("ArrivalLatitude", fetchedAddress.getLatitude());
                intent.putExtra("ArrivalLongitude", fetchedAddress.getLongitude());
                startActivity(intent);
            } else {
                editTextTo.setText("Невозможно определить координаты");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Ошибка", Toast.LENGTH_SHORT).show();
        }
    }

}
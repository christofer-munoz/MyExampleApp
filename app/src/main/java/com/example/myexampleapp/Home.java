package com.example.myexampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Home extends AppCompatActivity implements View.OnClickListener {

    TextView showName;
    Button logout;

    int userId = 0;
    User user;
    daoUser dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        showName = (TextView)findViewById(R.id.loggedName);
        logout = (Button)findViewById(R.id.logoutButton);

        logout.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        userId = bundle.getInt("id");
        dao = new daoUser(this);
        user = dao.getUserById(userId);
        showName.setText("¡Bienvenido " + user.getName() + "!");

        SensorManager sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE); //SensorManager nos permite acceder a las funcionalidades de los sensores del dispositivo
        final Sensor proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);   //Indicamos el sensor que utilizaremos
        SensorEventListener sensorEventListener = new SensorEventListener() {   //Agregamos un listener al sensor
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.values[0]<proximitySensor.getMaximumRange()) {
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                } else {
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        sensorManager.registerListener(sensorEventListener, proximitySensor, 2 * 1000 * 1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logoutButton:
                Intent i = new Intent(Home.this, MainActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }
    @Override
    public void onBackPressed() {

    }
}
package com.example.myexampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Home extends AppCompatActivity implements View.OnClickListener {

    TextView showName;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        showName = (TextView)findViewById(R.id.loggedName);
        logout = (Button)findViewById(R.id.logoutButton);

        logout.setOnClickListener(this);

        showName.setText("¡Bienvenido!");
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
}
package com.example.myexampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText email, pass;
    Button logButton, signButton;
    daoUser dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Asociando/vinculando los inputs de la pantalla con nuestra variable
        email = (EditText)findViewById(R.id.LogEmail);
        pass = (EditText)findViewById(R.id.LogPass);
        logButton = (Button)findViewById(R.id.LogButton);
        signButton = (Button)findViewById(R.id.SignButton);

        logButton.setOnClickListener(this);
        signButton.setOnClickListener(this);

        dao = new daoUser(this);    //Inicializamos nuestro objeto "dao" con el contexto actual, tst

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.LogButton:

                String mail = email.getText().toString();
                String password = pass.getText().toString();

                if (mail.equals("") || password.equals("")) {    //Si email o contraseña estan vacios nos pedirá que los completemos
                    Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_LONG).show();  //Así se muestra un mensaje en android
                } else if (dao.login(mail, password)) { //Intentará hacer login (retorna true or false)
                    Intent intent = new Intent(MainActivity.this, Home.class);  //Si es true, nos redirige al home
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.SignButton:
                Intent i = new Intent(MainActivity.this, SignIn.class);
                startActivity(i);
                break;
        }
    }
    @Override
    public void onBackPressed() {

    }
}
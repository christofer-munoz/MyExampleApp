package com.example.myexampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignIn extends AppCompatActivity implements View.OnClickListener {

    EditText name, email, pass;
    Button saveButton;
    daoUser dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //vinculamos variables con la pantalla
        name = (EditText)findViewById(R.id.SignName);
        email = (EditText)findViewById(R.id.SignEmail);
        pass = (EditText)findViewById(R.id.SignPass);
        saveButton = (Button)findViewById(R.id.SaveButton);

        dao = new daoUser(this);

        saveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.SaveButton:

                User user = new User();

                String username = name.getText().toString();
                String mail = email.getText().toString();
                String password = pass.getText().toString();

                //Asignamos a nuestro objeto user la información de la pantalla
                user.setName(username);
                user.setEmail(mail);
                user.setPassword(password);

                if(!user.isNull()) {
                    Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_LONG).show();
                } else if (dao.createUser(user)) {
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignIn.this, MainActivity.class);
                    startActivity(intent);
                    finish(); //Destruye la pantalla actual
                } else {
                    Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }
}


















/*
 case R.id.CancelSignButton:
 Intent i = new Intent(SignIn.this, MainActivity.class);
 startActivity(i);
 finish();
 break;
 */
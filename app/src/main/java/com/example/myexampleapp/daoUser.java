package com.example.myexampleapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class daoUser {

    Context context;    //Declaramos un objeto de tipo Context, nos provee de una conexión entre android (OS) y nuestro proyecto
    User user;  //Declaramos un objeto de tipo User
    ArrayList<User> list;   //Declaramos un arraylist que va a contener n usuarios.
    SQLiteDatabase conn; //Acceder a los métodos para trabajar con sql.
    String bd = "ExampleApp";   //Nombre de la base de datos
    String table = "create table if not exists users(id integer primary key autoincrement, name text, email text, pass text)";  //Crea la tabla

    public daoUser() {

    }

    public daoUser(Context context) {   //Constructor que crea la base de datos y la tabla
        this.context = context; //Asignación de la conexión con el sistema operativo;
        conn = context.openOrCreateDatabase(bd, context.MODE_PRIVATE, null); //Indicar el contexto en el que se debe ejecutar la query
        conn.execSQL(table); //Ejecutamos la query
    }

    public boolean createUser(User user) {  //Recibe un objeto User por parámetro

        ContentValues cv = new ContentValues(); //Tipo de lista que contendrá varios valores diferentes.

        cv.put("name", user.getName()); //Asignamos el tag "name" con el valor que nos retorne el objeto.
        cv.put("email", user.getEmail());   //Asignamos el tag "email" con el valor que nos retorne el objeto.
        cv.put("pass", user.getPassword()); //Asignamos el tag "pass" con el valor que nos retorne el objeto.

        //return (conn.insert("users", null, cv) > 0);  Manera abreviada de realizar la inserción y retorno

        if (conn.insert("users", null, cv) > 0) {   //Si la inserción fue exitosa, retorna true
            return true;
        } else {
            return false;
        }

    }

    public ArrayList<User> getUsers() { //Método que retorna una lista de usuarios
        ArrayList<User> list = new ArrayList<User>();   //Declaramos e inicializamos una lista
        list.clear(); //Limpiar lista en caso de caché

        //Un Cursor almacena valores de una consulta, como si fuera una lista
        Cursor cursor = conn.rawQuery("SELECT * FROM users", null); //Este cursor almacenará todos los valores que retorne la query

        if (cursor != null) {   //Aquí validamos que el cursor tenga datos
            if(cursor.moveToFirst()) {  //Si el cursor se puede posicionar en el primer elemento, hará lo siguiente:
                do {
                    User user = new User(); //Creamos un nuevo usuario para agregar a nuestra lista
                    user.setId(cursor.getInt(0));   //A nuestro usuario le asignamos el id que trae el usuario del cursor
                    user.setName(cursor.getString(1));  //A nuestro usuario le asignamos el name que trae el usuario del cursor
                    user.setEmail(cursor.getString(2)); //A nuestro usuario le asignamos el email que trae el usuario del cursor
                    user.setPassword(cursor.getString(3));  //A nuestro usuario le asignamos la pass que trae el usuario del cursor
                    list.add(user);
                }
                while (cursor.moveToNext());    //La posición del cursor se mueva al elemnto siguiente
            }
            cursor.close(); //Es importante cerrar siempre los cursores cuando no se estén ocupando
        }
        return list;    //Retornamos la lista
    }

    public boolean login(String mail, String pass) {
        boolean success = false;
        Cursor cursor = conn.rawQuery("SELECT * FROM users", null); //Ejecutamos una query
        if(cursor != null) {    //Si hay datos
            if (cursor.moveToFirst()) { //Va a la primera posición
                do {    //Hará lo siguiente:
                    if (cursor.getString(2).equals(mail) && cursor.getString(3).equals(pass)) { //Compara si los datos de los parametros coinciden con el registro del cursor
                        success = true;
                        break;
                    }
                } while (cursor.moveToNext());

                if (success) {  //Si la variable cambió a true:
                    cursor.close(); //Cierra el cursor
                    return true;    //Retorna true;
                }
            }
            cursor.close(); //Importante cerrar cursor
        }
        return false;   //Retorna false
    }

}

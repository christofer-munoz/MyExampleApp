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
            if(search(user.getEmail()) == 0) {  //Invocamos a la función search la cual nos retorna un número, si es 0, hará lo siguiente:
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
            } else {    //Si la función search retorna un valor distinto de 0, hará lo siguiente:
                return false;   //Retorna false
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

    public int search(String email) {
        int count = 0; //Declaramos un contador
        list = getUsers();  //Llenar una lista de usuarios
        for (User selectedUser : list) {    //Para cada objeto "selectedUser" dentro de la lista "list", va a hacer lo siguiente:
            if (selectedUser.getEmail().equals(email)) { //Comparando el correo del objeto iterado en este momento con el correo que llega por parametro
                count++;    //Si son iguales los correos, aumentará en 1 nuestro contador
            }
        }
        return count;
    }

    public User getUser(String email, String password) {    //Recibe un correo y una contraseña
        list = getUsers();  //Llena una lista de todos los usuarios existentes
        for (User user : list) {  //Para cada objeto usuario "user" dentro de la lista "list", haremos lo siguiente:
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) { //Compara si el correo y contraseña del objeto iterado en este momento son iguales a los parametros de entrada
                return user;    //Retorna un objeto User
            }
        }
        return null;    //Retorna nulo si no existe la combinación solicitada
    }

    public User getUserById(int id) {   //Este método irá a buscar un usuario por su ID
        list = getUsers();  //Llenamos una lista con los usuarios de la base de datos
        for (User user : list) {  //Por cada objeto User dentro de la lista, haremos lo siguiente:
            if (user.getId() == id) {   //Compara el id del objeto iterado en este momento con el id que llega por parametro
                return user;    //Si hay match entre ambos ids, retorna el objeto User completo.
            }
        }
        return null;    //Si no hay match entre los ids, retorna nulo.
    }

}

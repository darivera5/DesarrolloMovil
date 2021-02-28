package com.example.proyectosqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText et1,et2,et3,et4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = (EditText) findViewById(R.id.etCedula);
        et2 = (EditText) findViewById(R.id.etNombreApellido);
        et3 = (EditText) findViewById(R.id.etNombreColegio);
        et4 = (EditText) findViewById(R.id.eTextNumeroMesa);
    }

    public void insertar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(
                this,
                "administracion",
                null,
                1);

        SQLiteDatabase bd = admin.getWritableDatabase();
        String cedula = et1.getText().toString();
        String nombre = et2.getText().toString();
        String colegio = et3.getText().toString();
        String nromesa = et4.getText().toString();

        ContentValues registro = new ContentValues();

        registro.put("cedula", cedula);
        registro.put("nombre", nombre);
        registro.put("colegio", colegio);
        registro.put("nromesa", nromesa);

        bd.insert("votantes", null, registro);

        bd.close();

        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");

        Toast.makeText(this, "Se cargaron los datos de las persona",
                Toast.LENGTH_SHORT).show();
    }

    public void consulta(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(
                this,
                "administracion",
                null,
                1);

        SQLiteDatabase bd = admin.getWritableDatabase();

        String cedula = et1.getText().toString();
        Cursor fila = bd.rawQuery("SELECT nombre,colegio,nromesa" +
                " FROM votantes" +
                " WHERE cedula =" + cedula, null) ;

        if(fila.moveToFirst()){
            et2.setText(fila.getString(0));
            et3.setText(fila.getString(1));
            et4.setText(fila.getString(2));
        }else{
            Toast.makeText(this, "No existe persona con esa cedula",
                    Toast.LENGTH_SHORT).show();
            bd.close();
        }
    }

    public void eliminar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(
                this,
                "administracion",
                null,
                1);

        SQLiteDatabase bd = admin.getWritableDatabase();

        String cedula = et1.getText().toString();

        int cant = bd.delete("votantes", "cedula="+cedula, null);

        bd.close();

        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");

        if(cant == 1){
            Toast.makeText(this, "Se borro la persona con ese documento",
            Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "No existe la persona con dicho documento",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void modificar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(
                this,
                "administracion",
                null,
                1);

        SQLiteDatabase bd = admin.getWritableDatabase();

        String cedula = et1.getText().toString();
        String nombre = et2.getText().toString();
        String colegio = et3.getText().toString();
        String nromesa = et4.getText().toString();

        ContentValues registro = new ContentValues();

        registro.put("nombre", nombre);
        registro.put("colegio", colegio);
        registro.put("nromesa", nromesa);

        int cant = bd.update("votantes",
                registro,
                "cedula=" + cedula,
                null);

        bd.close();

        if(cant == 1){
            Toast.makeText(this, "Se modificaron los datos", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "no se modificaron los datos", Toast.LENGTH_SHORT).show();
        }

    }

}
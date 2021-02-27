package com.example.verificarformulario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et1, et2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = (EditText)findViewById(R.id.txtInputName);
        et2 = (EditText)findViewById(R.id.txtInputPassword);
    }

    public void verificar(View v){
        String clave = et2.getText().toString();
        if (clave.length() == 0){
            Toast notification = Toast.makeText(this,
                    "La clave no puede quedar vacia",
                    Toast.LENGTH_SHORT);
            notification.show();
        }
    }
}
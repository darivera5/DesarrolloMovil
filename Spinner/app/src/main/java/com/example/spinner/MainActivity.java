package com.example.spinner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Spinner spinner1;
    private EditText et1, et2;
    private TextView tv3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = (EditText)findViewById(R.id.eTValor1);
        et2 = (EditText)findViewById(R.id.etSegundoValor);
        tv3 = (TextView) findViewById(R.id.tvResultado);

        spinner1 = (Spinner) findViewById(R.id.spinner);
        String []opciones={
                "Sumar",
                "Restar",
                "Multipliar",
                "Dividir"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                opciones
        );

        spinner1.setAdapter(adapter);
    }

    public void operar(View view){
        String valor1 = et1.getText().toString();
        String valor2 = et2.getText().toString();

        int nro1 = Integer.parseInt(valor1);
        int nro2 = Integer.parseInt(valor2);

        String selec = spinner1.getSelectedItem().toString();

        if(selec.equals("Sumar")){
            int suma = nro1 + nro2;
            String resu = String.valueOf(suma);
            tv3.setText(resu);
        }else if(selec.equals("Restar")) {
            int suma = nro1 - nro2;
            String resu = String.valueOf(suma);
            tv3.setText(resu);
        }else if(selec.equals("Multipliar")) {
            int suma = nro1 * nro2;
            String resu = String.valueOf(suma);
            tv3.setText(resu);
        }else if(selec.equals("Dividir")) {
            int suma = nro1 / nro2;
            String resu = String.valueOf(suma);
            tv3.setText(resu);
        }

    }
}
package com.example.radiobutton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText et1,et2;
    private TextView tv3;
    private RadioButton r1,r2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = (EditText)findViewById(R.id.eTxtVal1);
        et2 = (EditText)findViewById(R.id.eTxtVal2);
        tv3 = (TextView)findViewById(R.id.lblResultados);
        r1 = (RadioButton) findViewById(R.id.rdBtnSumar);
        r2 = (RadioButton) findViewById(R.id.rdButtonRestar);
    }

    public  void  operar(View view){
        String valor1 = et1.getText().toString();
        String valor2 = et2.getText().toString();
        Integer nro1 = Integer.parseInt(valor1);
        Integer nro2 = Integer.parseInt(valor2);

        if (r1.isChecked()==true){
            int suma = nro1 + nro2;
            String resu = String.valueOf(suma);
            tv3.setText(resu);
        }else{
            if (r2.isChecked()==true){
                int resta = nro1 - nro2;
                String resu = String.valueOf(resta);
                tv3.setText(resu);
            }
        }
    }
}
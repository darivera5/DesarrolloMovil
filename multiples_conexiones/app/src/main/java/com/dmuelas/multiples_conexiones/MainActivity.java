package com.dmuelas.multiples_conexiones;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView placa, estado, descripcion, fecha, valor, id, cedula, modelo;
    Button consultar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        placa = (TextView)findViewById(R.id.labelPlaca);
        id = (TextView)findViewById(R.id.labelID);
        estado = (TextView)findViewById(R.id.labelEstado);
        descripcion = (TextView)findViewById(R.id.labelDesc);
        fecha = (TextView)findViewById(R.id.labelFecha);
        valor = (TextView)findViewById(R.id.labelValor);
        modelo = (TextView)findViewById(R.id.labelModelo);

        cedula = (TextView) findViewById(R.id.txtCedulaSearch);
        consultar = (Button) findViewById(R.id.btnBuscar);

        consultar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new Consultar(MainActivity.this).execute();
            }
        });

    }

    private Multa consultar() throws JSONException, IOException {

        String url = Constants.URL + "multi/get.php";

        //DATOS
        List<NameValuePair> nameValuePairs;
        nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("cedula", cedula.getText().toString().trim()));

        String json = APIHandler.POSTRESPONSE(url, nameValuePairs);
        if (json != null) {
            JSONObject object = new JSONObject(json);
            JSONArray json_array = object.optJSONArray("multas");
            if (json_array.length() > 0) {
                Multa multa = new Multa(json_array.getJSONObject(0));
                return multa;
            }
            return null;
        }
        return null;
    }


    class Consultar extends AsyncTask<String, String, String> {
        private Activity context;

        Consultar(Activity context) {
            this.context = context;
        }

        protected String doInBackground(String... params) {
            try {
                final Multa multa = consultar();
                if (multa != null)
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            cedula.setText(multa.getCedula());
                            id.setText(multa.getId());
                            placa.setText(multa.getPlaca());
                            fecha.setText(multa.getFecha());
                            descripcion.setText(multa.getDescripcion());
                            valor.setText(multa.getValor());
                            estado.setText(multa.getEstado());
                            modelo.setText(multa.getModelo());
                        }
                    });
                else
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Multa no encontrada", Toast.LENGTH_LONG).show();
                        }
                    });
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}

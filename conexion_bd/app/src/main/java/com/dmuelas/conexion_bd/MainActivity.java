package com.dmuelas.conexion_bd;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

    TextView placa, cedula, descripcion, estado, valor, id, fecha;
    Button insertar, consultar, modificar, eliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        placa = (TextView) findViewById(R.id.txtPlacaMulta);
        cedula = (TextView) findViewById(R.id.txtCedulaMulta);
        descripcion = (TextView) findViewById(R.id.txtDescripcion);
        estado = (TextView) findViewById(R.id.txtEstado);
        valor = (TextView) findViewById(R.id.txtValor);
        id = (TextView) findViewById(R.id.txtIdMulta);
        fecha = (TextView) findViewById(R.id.txtFecha);

        insertar = (Button) findViewById(R.id.btnInsertarMulta);
        consultar = (Button) findViewById(R.id.btnConsultarMulta);
        modificar = (Button) findViewById(R.id.btnModificarMulta);
        eliminar = (Button) findViewById(R.id.btnEliminarMulta);

        insertar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if ((!cedula.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!placa.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!id.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!valor.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!descripcion.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!estado.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!fecha.getText().toString().trim().equalsIgnoreCase(""))){

                    new Insertar(MainActivity.this).execute();
                } else {
                    Toast.makeText(MainActivity.this,
                            "Hay informacion por llenar",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        consultar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new Consultar(MainActivity.this).execute();
            }
        });


        modificar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new Modificar(MainActivity.this).execute();
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new Eliminar(MainActivity.this).execute();
            }
        });


    }
    // CRUD from here --------

    private boolean insertar() {

        String url = Constants.URL + "multas/add.php";

        //DATOS
        // definimos la lista de datos
        List<NameValuePair> nameValuePairs;
        nameValuePairs = new ArrayList<NameValuePair>(7); // tamaño del array
        nameValuePairs.add(new BasicNameValuePair("cedula_propiertario", cedula.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("placa_vehiculo", placa.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("id", id.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("valor", valor.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("estado", estado.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("descripcion", descripcion.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("fecha", fecha.getText().toString().trim()));

        boolean response = APIHandler.POST(url, nameValuePairs);
        return response;
    }

    private Multa consultar() throws JSONException, IOException {

        // Ruta
        String url = Constants.URL + "multas/get-by-id.php";
        Log.d("url:", url);

        //DATOS
        // lista de datos
        List<NameValuePair> nameValuePairs;
        //definimos array
        nameValuePairs = new ArrayList<NameValuePair>(1);

        nameValuePairs.add(new BasicNameValuePair("id", id.getText().toString().trim()));

        // pasamos el id al servicio php
        String json = APIHandler.POSTRESPONSE(url, nameValuePairs);
        // creamos var json que se le asocia la respuesta del webservice
        // si la respuesta no es vacia

        if (json != null) {
            // creamos el objeto json que recorrera el servicio
            JSONObject object = new JSONObject(json);
            // accedemos al objeto json llamado multas
            JSONArray json_array = object.optJSONArray("multas");
            // si lo encontrado tiene al menos un registro
            if (json_array.length() > 0) {
                // instanciamos la clase multa para obtener los datos json
                Multa multa = new Multa(json_array.getJSONObject(0));
                // retornamos la multa
                return multa;
            }
            return null;
        }
        return null;
    }


    private boolean modificar() {

        String url = Constants.URL + "multas/update.php";

        //DATOS
        List<NameValuePair> nameValuePairs;
        nameValuePairs = new ArrayList<NameValuePair>(7);
        nameValuePairs.add(new BasicNameValuePair("id", id.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("fecha", fecha.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("descripcion", descripcion.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("valor", valor.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("cedula_propiertario", cedula.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("placa_vehiculo", placa.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("estado", estado.getText().toString().trim()));


        boolean response = APIHandler.POST(url, nameValuePairs); // enviamos los datos por POST al Webservice PHP
        return response;
    }

    private boolean eliminar() {

        String url = Constants.URL + "multas/delete.php";

        //DATOS
        List<NameValuePair> nameValuePairs;
        nameValuePairs = new ArrayList<NameValuePair>(3);
        nameValuePairs.add(new BasicNameValuePair("id", id.getText().toString().trim()));
        boolean response = APIHandler.POST(url, nameValuePairs); // Enviamos el id al webservices
        return response;
    }

    // --- CRUD finalizados --------

    //----Eventos del AsyncTask para los botones ---------

    class Insertar extends AsyncTask<String, String, String> {
        private Activity context;

        Insertar(Activity context) {
            this.context = context;
        }

        protected String doInBackground(String... params) {
            if (insertar())
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Multa insertada", Toast.LENGTH_LONG).show();
                        id.setText("");
                        cedula.setText("");
                        placa.setText("");
                        fecha.setText("");
                        descripcion.setText("");
                        valor.setText("");
                        estado.setText("");
                    }
                });
            else
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Multa no insertada", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
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


    class Modificar extends AsyncTask<String, String, String> {
        private Activity context;

        Modificar(Activity context) {
            this.context = context;
        }

        protected String doInBackground(String... params) {
            if (modificar())
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Multa modificada", Toast.LENGTH_LONG).show();
                    }
                });
            else
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Multa no encontrada", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }

    class Eliminar extends AsyncTask<String, String, String> {
        private Activity context;

        Eliminar(Activity context) {
            this.context = context;
        }

        protected String doInBackground(String... params) {
            if (eliminar())
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Multa eliminada", Toast.LENGTH_LONG).show();
                        id.setText("");
                        cedula.setText("");
                        placa.setText("");
                        fecha.setText("");
                        descripcion.setText("");
                        valor.setText("");
                        estado.setText("");
                    }
                });
            else
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Multa no eliminada", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }


}

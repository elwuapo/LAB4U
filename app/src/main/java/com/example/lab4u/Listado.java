package com.example.lab4u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Listado extends AppCompatActivity {
    ListView listView;
    ArrayList<String> listado;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        cargarListado();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        listView = (ListView) findViewById(R.id.listado_listview_cuentas);
        cargarListado();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String usuario = listado.get(position).split(" ")[0];
                String email = listado.get(position).split(" ")[1];
                String clave = listado.get(position).split(" ")[2];

                Intent intent = new Intent(Listado.this, Modificar.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("email", email);
                intent.putExtra("clave", clave);
                startActivity(intent);
            }
        });

        Bundle bundle = getIntent().getExtras();

        String usuario = "";

        if (bundle != null){
            usuario = bundle.getString("usuario");
        }

        String email = traerCorreo(usuario);

        TextView textview_usuario = (TextView) findViewById(R.id.listado_textview_usuario);
        TextView textview_email = (TextView) findViewById(R.id.listado_textview_correo);

        if(usuario.equals("")){
            textview_usuario.setText("Usuario");
        }else{
            textview_usuario.setText(usuario);
        }

        textview_email.setText(email);

    }

    private void cargarListado(){

        listado = listarPersonas();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listado);
        listView.setAdapter(adapter);

    }

    private ArrayList<String> listarPersonas(){

        ArrayList<String> datos = new ArrayList<String>();
        BaseHelper helper = new BaseHelper(this, "Demo", null, 1);
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT USUARIO, EMAIL, CLAVE FROM CUENTAS";
        Cursor c = db.rawQuery(sql,null);

        if(c.moveToFirst()){

            do{

                String linea = c.getString(0) + " " + c.getString(1) +" "+ c.getString(2);
                datos.add(linea);

            }while(c.moveToNext());

        }

        db.close();
        return datos;

    }

    // BTN DEL PANICO SI ME PITEO LA BASE DE DATOS
    public void reset(View view){
        try {
            BaseHelper helper = new BaseHelper(this ,"Demo", null,1);
            SQLiteDatabase db = helper.getReadableDatabase();

            String sql = "DELETE FROM CUENTAS";
            db.execSQL(sql);
            db.close();

            Toast.makeText(Listado.this, "SE ELIMINO LA BASE DE DATOS CON EXITO", Toast.LENGTH_SHORT).show();

            Intent mostrar = new Intent(this, Listado.class);
            startActivity(mostrar);

        }catch (Exception e){
            Toast.makeText(Listado.this, "ERROR NO SE PUDO ELIMINAR LA BASE DE DATOS", Toast.LENGTH_SHORT).show();
        }
    }

    public String traerCorreo(String usuario){
        try {
            BaseHelper helper = new BaseHelper(this, "Demo", null, 1);
            SQLiteDatabase db = helper.getReadableDatabase();
            String sql = "SELECT EMAIL FROM CUENTAS WHERE USUARIO = '" + usuario + "'";
            Cursor c = db.rawQuery(sql,null);

            if(c.moveToFirst()){
                return c.getString(0);
            }
            else{
                return "usuario@email.com";
            }
        }catch (Exception e){
            return "usuario@email.com";
        }
    }

}

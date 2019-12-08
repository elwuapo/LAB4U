package com.example.lab4u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText et_usuario, et_clave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    // METODOS DE LOS BOTONES.
    public void Registrarse(View view){

        Intent registrarse = new Intent(this, Registro.class);
        startActivity(registrarse);

    }

    public void iniciar(View view){

        et_usuario = (EditText) findViewById(R.id.login_plain_usuario);
        et_clave = (EditText) findViewById(R.id.login_password_contraseña);

        if(verificar(et_usuario.getText().toString(), et_clave.getText().toString())){

            Intent iniciar = new Intent(this, Listado.class);

            iniciar.putExtra("usuario", et_usuario.getText().toString());

            startActivity(iniciar);
        } else {
            et_usuario.setText("");
            et_clave.setText("");
        }

    }

    public boolean verificar(String usuario, String clave){
        try {
            BaseHelper helper = new BaseHelper(this, "Demo", null, 1);
            SQLiteDatabase db = helper.getReadableDatabase();
            String sql = "SELECT USUARIO, CLAVE FROM CUENTAS WHERE USUARIO = '" + usuario + "' AND CLAVE = '" + clave + "'";
            Cursor c = db.rawQuery(sql,null);

            if(c.moveToFirst()){
                Toast.makeText(MainActivity.this, "VERIFICACION EXITOSA.", Toast.LENGTH_SHORT).show();
                return true;
            }
            else{
                Toast.makeText(MainActivity.this, "ERROR EN EL USUARIO O CONTRASEÑA", Toast.LENGTH_SHORT).show();
                return false;
            }
        }catch (Exception e){
            Toast.makeText(MainActivity.this, "ERROR AL CONECTAR.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}

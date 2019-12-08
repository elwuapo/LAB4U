package com.example.lab4u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registro extends AppCompatActivity {

    EditText et_usuario, et_email, et_clave, et_reclave;
    Button registro_btn_registrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        et_usuario = (EditText) findViewById(R.id.registro_plain_usuario);
        et_email = (EditText) findViewById(R.id.registro_email_correo);
        et_clave = (EditText) findViewById(R.id.registro_password_contraseña);
        et_reclave = (EditText) findViewById(R.id.registro_password_recontraseña);

        registro_btn_registrarse = (Button) findViewById(R.id.registro_btn_registrarse);

        registro_btn_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(guardar(et_usuario.getText().toString(), et_email.getText().toString(), et_clave.getText().toString(), et_reclave.getText().toString())){
                    Intent login = new Intent(Registro.this, MainActivity.class);
                    startActivity(login);
                }
            }
        });
    }

    private boolean guardar(String usuario, String email, String clave, String reclave){

        boolean usuario_vacio = usuario.equals("");
        boolean email_vacio = email.equals("");
        boolean clave_vacio = clave.equals("");
        boolean reclave_vacio = reclave.equals("");

        try {

            if(!usuario_vacio && !email_vacio && !clave_vacio && !reclave_vacio){
                if(clave.equals(reclave)){

                    BaseHelper helper = new BaseHelper(this ,"Demo", null,1);
                    SQLiteDatabase db = helper.getReadableDatabase();

                    ContentValues c = new ContentValues();
                    c.put("USUARIO", usuario);
                    c.put("EMAIL", email);
                    c.put("CLAVE", clave);

                    db.insert("CUENTAS", null, c);
                    db.close();

                    Toast.makeText(this, "USUARIO REGISTRADO CON EXITO.", Toast.LENGTH_SHORT).show();
                    return true;

                }else{
                    Toast.makeText(this, "ERROR! LAS CLAVES DEBEN SER IDENTICAS.", Toast.LENGTH_SHORT).show();
                    return false;
                }

            }else{
                Toast.makeText(this, "ERROR! TODOS LOS CAMPOS DEBEN ESTAR LLENOS.", Toast.LENGTH_SHORT).show();
                return false;
            }

        }catch (Exception e){

            Toast.makeText(this, "ERROR AL REGISTRAR USUARIO." + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    // BTN DEL PANICO SI TE PITEAS LA BASE DE DATOS

    public void eliminar(View view){
        try {
            BaseHelper helper = new BaseHelper(this ,"Demo", null,1);
            SQLiteDatabase db = helper.getReadableDatabase();

            String sql = "DELETE FROM CUENTAS";
            db.execSQL(sql);
            db.close();

            Toast.makeText(Registro.this, "SE ELIMINO LA BASE DE DATOS CON EXITO", Toast.LENGTH_SHORT).show();

            Intent mostrar = new Intent(this, Listado.class);
            startActivity(mostrar);

        }catch (Exception e){
            Toast.makeText(Registro.this, "ERROR NO SE PUDO ELIMINAR LA BASE DE DATOS", Toast.LENGTH_SHORT).show();
        }
    }

}

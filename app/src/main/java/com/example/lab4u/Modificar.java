package com.example.lab4u;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Modificar extends AppCompatActivity {

    EditText et_usuario, et_email, et_clave, et_reclave;
    Button modificar_btn_modificar, modificar_btn_eliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);

        Bundle bundle = getIntent().getExtras();

        String usuario = "";
        String email = "";
        String clave = "";
        String reclave = "";

        if (bundle != null){

            usuario = bundle.getString("usuario");
            email = bundle.getString("email");
            clave = bundle.getString("clave");
            reclave = bundle.getString("clave");
        }

        et_usuario = (EditText) findViewById(R.id.modificar_plain_usuario);
        et_email = (EditText) findViewById(R.id.modificar_email_correo);
        et_clave = (EditText) findViewById(R.id.modificar_password_contraseña);
        et_reclave = (EditText) findViewById(R.id.modificar_password_recontraseña);

        et_usuario.setText(usuario);
        et_email.setText(email);
        et_clave.setText(clave);
        et_reclave.setText(reclave);

        modificar_btn_modificar = (Button) findViewById(R.id.modificar_btn_modificar);
        modificar_btn_eliminar = (Button) findViewById(R.id.modificar_btn_eliminar);

        modificar_btn_modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificar(et_usuario.getText().toString(), et_email.getText().toString(), et_clave.getText().toString());
                onBackPressed();
            }
        });

        modificar_btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar(et_usuario.getText().toString());
                onBackPressed();
            }
        });

    }

    private void modificar(String usuario, String email, String clave){
        try {
            BaseHelper helper = new BaseHelper(this ,"Demo", null,1);
            SQLiteDatabase db = helper.getReadableDatabase();

            String sql = "UPDATE CUENTAS SET EMAIL = '" + email + "' , CLAVE = '" + clave + "' WHERE USUARIO = '" + usuario +"'";
            db.execSQL(sql);
            db.close();

        }catch (Exception e){
            Toast.makeText(Modificar.this, "ERROR NO SE PUDO MODIFICAR LA CUENTA", Toast.LENGTH_SHORT).show();
        }

    }

    private void eliminar(String usuario){
        try {
            BaseHelper helper = new BaseHelper(this ,"Demo", null,1);
            SQLiteDatabase db = helper.getReadableDatabase();

            String sql = "DELETE FROM CUENTAS WHERE USUARIO = '" + usuario + "'";
            db.execSQL(sql);
            db.close();

        }catch (Exception e){
            Toast.makeText(Modificar.this, "ERROR NO SE PUDO ELIMINAR LA CUENTA", Toast.LENGTH_SHORT).show();
        }
    }
}

package com.example.crediticio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreditoActivity extends AppCompatActivity {

    //1. Se les da nombre a las variables Java
    EditText jetIdentificacion, jetcodigoPrestamo;
    TextView jtvusuario, jtvprofesion, jtvsalario, jtvingresoExtra, jtvgastos, jtvvalorPrestamo;
    Button jbtBuscar, jbtejecutar, jbtguardar, jbtconsultar, jbtanular, jbtregresar, jbtcancelar;

    //2. Instanciar la clase que heredo de la clase SqliteOpenHelper
    ClsOpenHelper admin=new ClsOpenHelper(this,"Banco.bd",null,1);
    //3. 101
    String Identificacion;
    byte sw;
    int ingresos, salario, gastos, valorBruto, valorPrestamo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credito);
        //1. para ocultar el titulo
        getSupportActionBar().hide();
        //2. Para enlazar los objetos java con los XML
        //EditText
        jetIdentificacion=findViewById(R.id.etIdentificacion);
        jetcodigoPrestamo=findViewById(R.id.etcodigoPrestamo);
        //TextView
        jtvusuario=findViewById(R.id.tvusuario);
        jtvprofesion=findViewById(R.id.tvprofesion);
        jtvsalario=findViewById(R.id.tvsalario);
        jtvingresoExtra=findViewById(R.id.tvingresoExtra);
        jtvgastos=findViewById(R.id.tvgastos);
        jtvvalorPrestamo=findViewById(R.id.tvvalorPrestamo);
        //Button
        jbtejecutar=findViewById(R.id.btejecutar);
        jbtguardar=findViewById(R.id.btguardar);
        jbtconsultar=findViewById(R.id.btconsultar);
        jbtanular=findViewById(R.id.btanular);
        jbtregresar=findViewById(R.id.btregresar);
        jbtcancelar=findViewById(R.id.btcancelar);
        sw=0;
    }
    public void Regresar(View view){
        Intent intmenu=new Intent(this,MenuActivity.class);
        startActivity(intmenu);
    }

    public void botonCancelar(View view){
        Limpiar_campos();
    }

    private void Limpiar_campos(){
        jetIdentificacion.setText("");
        jetcodigoPrestamo.setText("");
        jtvusuario.setText("");
        jtvprofesion.setText("");
        jtvsalario.setText("");
        jtvingresoExtra.setText("");
        jtvgastos.setText("");
        jtvvalorPrestamo.setText("");
    }

    public void botonBuscar(View view){
        //
        Identificacion=jetIdentificacion.getText().toString();
        //
        if (Identificacion.isEmpty()){
            Toast.makeText(this, "Se requiere identificación", Toast.LENGTH_SHORT).show();
            //para mostrar la barra de que debe meter los datos
            jetIdentificacion.requestFocus();
        }
        else {
            //Escriba en la base de datos en ingles
            SQLiteDatabase fila=admin.getReadableDatabase();
            //
            Cursor dato=fila.rawQuery("select * from TblCliente where identificacion='" + Identificacion + "'",null);
            if(dato.moveToNext()){
                //
                sw=1;
                jtvusuario.setText(dato.getString(1));
                jtvprofesion.setText(dato.getString(2));
                jtvsalario.setText(dato.getString(4));
                jtvingresoExtra.setText(dato.getString(5));
                jtvgastos.setText(dato.getString(6));
            }
            else {
                Toast.makeText(this, "Cliente no registrado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void botonEjecutar(View view){
        if(sw==0){
            Toast.makeText(this, "Primero se debe hacer una busqueda", Toast.LENGTH_SHORT).show();
            //que debe ingresar los datos si o si
            jetIdentificacion.requestFocus();
        }
        else {
            //las variables se pasan de String a Int
            ingresos=Integer.parseInt(jtvingresoExtra.getText().toString());
            gastos=Integer.parseInt(jtvgastos.getText().toString());
            salario=Integer.parseInt(jtvsalario.getText().toString());
            //Operaciones para el resultado
            valorBruto=ingresos+salario-gastos;
            valorPrestamo=valorBruto*10;
            //se muestra en pantalla el valor del prestamos como un string
            jtvvalorPrestamo.setText(String.valueOf(valorPrestamo));
        }
    }

    public void botonGuardar(View view){

    }
}
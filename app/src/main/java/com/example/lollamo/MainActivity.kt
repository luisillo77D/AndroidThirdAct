package com.example.lollamo

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageButtonPhone: ImageButton = findViewById(R.id.imageButtonPhone)
        //declaramos la variable para el editext telefonoo
        val editTextPhone: EditText = findViewById(R.id.editTextPhone)
        imageButtonPhone!!.setOnClickListener(object :View.OnClickListener{

            override fun onClick(v: View?) {
                //declaramos la variable que contendra el numero
                val telefonoCapturado =editTextPhone!!.text.toString()

                //creamos laprimer validacion
                if(!telefonoCapturado.isEmpty()){
                    //segunda validacion
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                        Toast.makeText(this@MainActivity, "Telefono:$telefonoCapturado", Toast.LENGTH_SHORT).show()

                    }
                }
                else{
                    Toast.makeText(this@MainActivity, "Por favor ingrese el numero", Toast.LENGTH_SHORT).show()
                }

            }
        })
    }
}
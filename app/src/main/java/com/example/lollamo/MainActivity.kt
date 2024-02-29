package com.example.lollamo

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    //declaramos una varible q sera una bandera
    private val PHONE_CODE=4000
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
                        //invocamos la funcion
                        if (checarPermiso(android.Manifest.permission.CALL_PHONE)){
                            //declarar la variable a la cual le asignaremos el intent
                            val intentAceptado = Intent(Intent.ACTION_CALL,Uri.parse("tel:"+telefonoCapturado))

                            //comprabamos de manera explicita los permisos
                            if (ActivityCompat.checkSelfPermission(this@MainActivity,android.Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
                                //detener el flujo
                                return
                            }
                            //en caso de otorgar permisos
                            startActivity(intentAceptado)
                        }else{
                            //en caso de negar permiso le indicamos que puede agregarlos
                            if (!shouldShowRequestPermissionRationale(android.Manifest.permission.CALL_PHONE)){
                                requestPermissions(arrayOf(android.Manifest.permission.CALL_PHONE),PHONE_CODE)
                            }else{
                                //nos comunicamos con el usuraio pa ver si quier dar permisos
                                Toast.makeText(this@MainActivity, "deseas habilitar los permisos?", Toast.LENGTH_SHORT).show()
                                //creamos la variable que recieb el intent
                                val intentSettings = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                intentSettings.addCategory(Intent.CATEGORY_DEFAULT)
                                intentSettings.data = Uri.parse("packege"+packageName)

                                //creamos las bandera para guiar al usuario
                                intentSettings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                intentSettings.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                                intentSettings.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)

                                //inciiamo el activity
                                startActivity(intentSettings)
                            }
                        }
                    }
                }
                else{
                    Toast.makeText(this@MainActivity, "Por favor ingrese el numero", Toast.LENGTH_SHORT).show()
                }

            }
        })
    }

    //agregamos la logica de la llamada
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //estructura when
        when(requestCode){
            PHONE_CODE->{
                //declarar dos variables con la finalidad de asignar los parametros
                val permisos = permissions[0]
                val resultado = grantResults[0]

                //validamos los permisos
                if (permisos==android.Manifest.permission.CALL_PHONE){
                    //codigo
                    if (resultado==PackageManager.PERMISSION_GRANTED){
                        //declaramos val para numero
                        val phoneNumber: EditText = findViewById(R.id.editTextPhone)
                        phoneNumber.text.toString()
                        val intentCall = Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNumber))

                        //validar explicito
                        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
                            return
                        }
                        startActivity(intentCall)
                    }else{
                        //informaremos al usuario
                        Toast.makeText(this, "ha denegado el permiso", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    //creamos una funcion para validar el permiso otrogado por el usuario
    fun checarPermiso(permission: String):Boolean{

        //declaramos la variable que aloja el resultado
        val resul = this.checkCallingOrSelfPermission(permission)

        //retornamos el valor
        return resul == PackageManager.PERMISSION_GRANTED
    }
}
package com.unosoft.appcomercial.ui.login

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.unosoft.appcomercial.HomeActivity
import com.unosoft.appcomercial.R
import com.unosoft.appcomercial.models.login.Login
import com.unosoft.appcomercial.models.login.LoginResponse
import com.unosoft.appcomercial.network.APIService.client
import com.unosoft.appcomercial.network.LoginApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    var apiInterface: LoginApi? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val ingresar = findViewById<Button>(R.id.ingresar)
        val pd = ProgressDialog(this)
        pd.setMessage("Validando usuario....")
        pd.setCancelable(false)
        pd.create()
        val user = findViewById<EditText>(R.id.user)
        val pass = findViewById<EditText>(R.id.pass)
        apiInterface = client!!.create(LoginApi::class.java)
        // Boton Ingresar
        ingresar.setOnClickListener(View.OnClickListener {
            if (user.text.toString().length == 0 || pass.text.toString().length == 0) {
                AlertMessage("Datos inválidos")
                return@OnClickListener
            } else {
                pd.show()
                val _user = Login(user.text.toString(), pass.text.toString())
                val call1 = apiInterface!!.login(_user)
                call1!!.enqueue(object : Callback<LoginResponse?> {
                    override fun onResponse(
                        call: Call<LoginResponse?>,
                        response: Response<LoginResponse?>
                    ) {
                        if (response.code() == 400) {
                            AlertMessage("Usuario y/o Contraseña incorrecta")
                            pd.cancel()
                        } else {

                           val i = Intent(applicationContext, HomeActivity::class.java)
                           startActivity(i)
                            pd.cancel()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                        pd.cancel()
                        AlertMessage("Error de Conexión: " + t.message)
                        call.cancel()
                    }
                })
            }
        })
    }

    fun AlertMessage(mensaje: String?) {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Información")
        builder.setMessage(mensaje)
        builder.setCancelable(false)
        builder.setPositiveButton("Ok") { dialog, which -> dialog.dismiss() }
        val dialogMessage = builder.create()
        dialogMessage.show()
    }
}
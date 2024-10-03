package com.example.imajery_v4.ui.auth

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.imajery_v4.R
import com.example.imajery_v4.models.LoginReq
import com.example.imajery_v4.models.LoginRes
import com.example.imajery_v4.supports.APIService
import com.example.imajery_v4.supports.retrofitClient
import retrofit2.Call
import retrofit2.Response

class auth_login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_auth_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val apis = retrofitClient.instance.create(APIService::class.java)

        val tb_email : EditText = findViewById(R.id.et_login_email)
        val tb_password : EditText = findViewById(R.id.et_login_password)
        val btn_login : Button = findViewById(R.id.btn_login_submit)

        btn_login.setOnClickListener{
            val loginReq = LoginReq(
                email = tb_email.text.toString(),
                password = tb_password.text.toString()
            )

            apis.login(loginReq).enqueue(object : retrofit2.Callback<LoginRes> {
                override fun onResponse(call: Call<LoginRes>, response: Response<LoginRes>) {
                    if(response.isSuccessful){
                        response.body()?.let {
                            if(it.State == "1"){
                                Toast.makeText(this@auth_login,"Login Berhasil", Toast.LENGTH_LONG).show()
                            }else{
                                Toast.makeText(this@auth_login,"Login Gagal", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<LoginRes>, t: Throwable) {
                    Toast.makeText(this@auth_login,"Login Gagal : \n" + t.message.toString() , Toast.LENGTH_LONG).show()
                }

            })
        }

    }
}
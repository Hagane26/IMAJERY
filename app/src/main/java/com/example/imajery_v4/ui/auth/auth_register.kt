package com.example.imajery_v4.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.imajery_v4.R
import com.example.imajery_v4.models.RegisterReq
import com.example.imajery_v4.models.RegisterRes
import com.example.imajery_v4.supports.APIService
import com.example.imajery_v4.supports.retrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class auth_register : AppCompatActivity() {
    lateinit var username : EditText
    lateinit var namaDepan : EditText
    lateinit var namaBelakang : EditText
    lateinit var tanggalLahir : DatePicker
    var gender : RadioGroup? = null
    lateinit var radio : RadioButton
    lateinit var password : EditText
    lateinit var cPassword : EditText

    lateinit var btn_submit : Button
    lateinit var btn_login : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_auth_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val apis = retrofitClient.instance.create(APIService::class.java)

        username = findViewById(R.id.et_regis_username)
        namaDepan = findViewById(R.id.et_regis_nd)
        namaBelakang = findViewById(R.id.et_regis_nb)
        tanggalLahir = findViewById(R.id.dp_regis_tgl)
        gender = findViewById(R.id.rg_regis_gender)
        password = findViewById(R.id.et_regis_pass)
        cPassword = findViewById(R.id.et_regis_cpass)

        btn_submit = findViewById(R.id.btn_regis_submit)
        btn_login = findViewById(R.id.btn_regis_login)

        btn_login.setOnClickListener{
            startActivity(Intent(this@auth_register,auth_login::class.java))
        }

        btn_submit.setOnClickListener {

            val selectOption: Int = gender!!.checkedRadioButtonId
            radio = findViewById(selectOption)
            val tgl = "${tanggalLahir.year}-${tanggalLahir.month}-${tanggalLahir.dayOfMonth}"
            if (username.text.toString() == "" ||
                namaDepan.text.toString() == "" ||
                namaBelakang.text.toString() == "" ||
                tgl.toString() == "" ||
                radio.text.toString() == "" ||
                password.text.toString() == "" ||
                cPassword.text.toString() == ""
            ) {
                Toast.makeText(this@auth_register, "Mohon Lengkapi Data!", Toast.LENGTH_LONG).show()
            } else {
                if(cPassword.text.toString() == password.text.toString()){
                    var sgender = "L"
                    when(radio.text.toString()){
                        "Laki-Laki" -> sgender = "L"
                        "Perempuan" -> sgender = "P"
                    }
                    val data = RegisterReq(
                        username = username.text.toString(),
                        namaDepan = namaDepan.text.toString(),
                        namaBelakang = namaBelakang.text.toString(),
                        tanggalLahir = tgl,
                        gender = sgender,
                        password = password.text.toString(),
                        img = ""
                    )
                    apis.register(data).enqueue(object : Callback<RegisterRes> {
                        override fun onResponse(
                            call: Call<RegisterRes>,
                            response: Response<RegisterRes>
                        ) {
                            if(response.isSuccessful){
                                response.body()?.let {
                                    if(it.status == "1"){
                                        Toast.makeText(this@auth_register,"Register Berhasil",Toast.LENGTH_LONG).show()
                                        startActivity(Intent(this@auth_register,auth_login::class.java))
                                    }else{
                                        Toast.makeText(this@auth_register,"Register Gagal${it.status + ":" + it.message}",Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }

                        override fun onFailure(call: Call<RegisterRes>, t: Throwable) {
                            Toast.makeText(this@auth_register,"Error : \n ${t.message.toString()}",Toast.LENGTH_LONG).show()
                        }

                    })
                }else{
                    Toast.makeText(this@auth_register,"Password Tidak Sama : \n ${password.text} == ${cPassword.text}",Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
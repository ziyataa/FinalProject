package com.belajarandroid.finalproject.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.belajarandroid.finalproject.R
import com.belajarandroid.finalproject.databinding.ActivityLoginBinding
import com.belajarandroid.finalproject.model.Register
import com.belajarandroid.finalproject.model.ResponseLogin
import com.belajarandroid.finalproject.model.ResponseUser
import com.belajarandroid.finalproject.model.SuccessLogin
import com.belajarandroid.finalproject.service.ApiClient
import com.belajarandroid.finalproject.service.ApiInterface
import com.belajarandroid.finalproject.utils.BaseVMF
import com.belajarandroid.finalproject.utils.LocalStorageHelper
import com.belajarandroid.finalproject.viewmodel.LoginVm
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(){

    var apiInterface: ApiInterface? = null
    lateinit var localStorageHelper: LocalStorageHelper

    lateinit var dialog: AlertDialog
    lateinit var viewModel: LoginVm

    lateinit var binding: ActivityLoginBinding
    
    private var dataLogin: SuccessLogin? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        localStorageHelper = LocalStorageHelper(this)
        apiInterface = ApiClient.getClient(this)?.create(ApiInterface::class.java)

        val viewModelFactory = BaseVMF(LoginVm(applicationContext))
        viewModel = ViewModelProvider(this, viewModelFactory)[LoginVm::class.java]

        initProgressDialog()

        initLiveData()

        binding.apply {
            edtPasswordLogin.setOnTouchListener { v, event ->
                if (event.action == android.view.MotionEvent.ACTION_UP) {
                    if (edtPasswordLogin.text.toString().isNotEmpty()) {
                        edtPasswordLogin.transformationMethod = PasswordTransformationMethod.getInstance()
                    }
                }
                false
            }

            btnLogin.setOnClickListener {
                var email = edtUsernameLogin.text.toString()
                var password = edtPasswordLogin.text.toString()
                if (!email.equals("", true) && !password.equals("", true)) {
                    login(email, password)
                } else {
                    Toast.makeText(this@LoginActivity, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            }

            txtRegisterLogin.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
        }

        binding.txtForgotPasswordLogin.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }

    private fun initLiveData() {
        viewModel?.isLoadingState()?.observe(this) {
            if (it) {
                dialog.show()
                return@observe
            }
            dialog.dismiss()
        }

        viewModel?.responseStatus()?.observe(this) {
            if (it == 200 || it == 201){
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
                Toast.makeText(this@LoginActivity, "Berhasil Login", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@LoginActivity, "Gagal Login", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel?.isErrorState()?.observe(this) {
            if (it == null) return@observe
            if (it) {
                Toast.makeText(this@LoginActivity, "Gagal Login", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.dataLogin?.observe(this)
        {
            dataLogin = it
            if (dataLogin != null) {
                localStorageHelper.setUserToken(it.token?:"") }
        }
    }

    private fun login(username: String, password: String) {
       val data = ResponseLogin(username, password)
           viewModel.login(data)
    }


    private fun initProgressDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setCancelable(true)

        builder.setView(R.layout.loading_dialog)
        dialog = builder.create()

    }
}
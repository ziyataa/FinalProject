package com.belajarandroid.finalproject.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.belajarandroid.finalproject.R
import com.belajarandroid.finalproject.databinding.ActivityForgotPasswordBinding
import com.belajarandroid.finalproject.databinding.ActivityRegisterBinding
import com.belajarandroid.finalproject.model.Register
import com.belajarandroid.finalproject.model.ResponseForgot
import com.belajarandroid.finalproject.service.ApiClient
import com.belajarandroid.finalproject.service.ApiInterface
import com.belajarandroid.finalproject.utils.BaseVMF
import com.belajarandroid.finalproject.utils.LocalStorageHelper
import com.belajarandroid.finalproject.viewmodel.ForgotPasswordVm
import com.belajarandroid.finalproject.viewmodel.RegisterVm

class ForgotPasswordActivity : AppCompatActivity() {

    lateinit var viewModel: ForgotPasswordVm
    var apiInterface: ApiInterface? = null
    lateinit var dialog: AlertDialog
    lateinit var localStorageHelper: LocalStorageHelper

    private var dataUser :  ResponseForgot? = null

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        localStorageHelper = LocalStorageHelper(this)

        apiInterface = ApiClient.getClient(this)?.create(ApiInterface::class.java)
        val viewModelFactory = BaseVMF(ForgotPasswordVm(this))
        viewModel = ViewModelProvider(this, viewModelFactory)[ForgotPasswordVm::class.java]

        initProgressDialog()
        initLiveData()

        binding.apply {
            btnResetPassword.setOnClickListener {
                val user = ResponseForgot()
                val idcardnumber = edtIdCardForgotPassword.text.toString()
                val password = edtPasswordForgotPassword.text.toString()
                val confirm_password = edtRepeatPasswordForgotPassword.text.toString()
                dataUser = ResponseForgot(password, confirm_password)

                if (idcardnumber.isEmpty() || password.isEmpty() || confirm_password.isEmpty()) {
                    Toast.makeText(
                        this@ForgotPasswordActivity,
                        "Please fill all the fields",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (password != confirm_password) {
                    Toast.makeText(
                        this@ForgotPasswordActivity,
                        "Password doesn't match",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    user.password = edtPasswordForgotPassword.text.toString()
                    user.confirm_password = edtRepeatPasswordForgotPassword.text.toString()
                    dataUser?.let {
                        viewModel.forgotPassword(idcardnumber, it)
                    }
                }

            }

            txtLoginForgotPassword.setOnClickListener {
                startActivity(Intent(this@ForgotPasswordActivity, LoginActivity::class.java))
            }
        }
    }

    private fun initLiveData() {

        viewModel.responseStatus().observe(this) {
            if(it != 200){
                Toast.makeText(this, "Gagal mengupdate data", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Berhasil mengupdate data", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }

        viewModel.isErrorState().observe(this) {
            if (it == null) return@observe
            if (it) {
                Toast.makeText(this, "Gagal mengambil data", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.dataForgotPassword.observe(this) {
            if (it != null){
                Toast.makeText(this, "Berhasil mengupdate data", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun initProgressDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setCancelable(true)

        builder.setView(R.layout.loading_dialog)
        dialog = builder.create()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
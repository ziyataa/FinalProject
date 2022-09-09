package com.belajarandroid.finalproject.view

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
import com.belajarandroid.finalproject.viewmodel.ForgotPasswordVm
import com.belajarandroid.finalproject.viewmodel.RegisterVm

class ForgotPasswordActivity : AppCompatActivity() {

    lateinit var viewModel: ForgotPasswordVm
    var apiInterface: ApiInterface? = null
    lateinit var dialog: AlertDialog

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

                user.idcardnumber = idcardnumber
                user.password = password
                user.confirm_password = confirm_password

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
                    viewModel.forgotPassword(user)
                }

            }

        }
    }

    private fun initLiveData() {
        viewModel.isLoadingState()?.observe(this) {
//            if(it){
//                progressBar.visibility = View.VISIBLE
//                swiperefreshlayout.visibility = View.GONE
//                return@observe
//            }else{
//                progressBar.visibility = View.GONE
//                swiperefreshlayout.visibility = View.VISIBLE
//            }
        }

        viewModel?.responseStatus()?.observe(this) {
            if (it != 200) {
                Toast.makeText(this, "Gagal mengambil data", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel?.isErrorState()?.observe(this) {
            if (it == null) return@observe
            if (it) {
                Toast.makeText(this, "Gagal mengambil data", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.dataForgotPassword?.observe(this) {
            Toast.makeText(this, "Berhasil Merubah Password", Toast.LENGTH_SHORT).show()
            finish()
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
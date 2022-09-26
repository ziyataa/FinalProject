package com.belajarandroid.finalproject.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.belajarandroid.finalproject.R
import com.belajarandroid.finalproject.databinding.ActivityRegisterBinding
import com.belajarandroid.finalproject.model.Register
import com.belajarandroid.finalproject.service.ApiClient
import com.belajarandroid.finalproject.service.ApiInterface
import com.belajarandroid.finalproject.utils.BaseVMF
import com.belajarandroid.finalproject.viewmodel.RegisterVm

class RegisterActivity : AppCompatActivity() {

    lateinit var viewModel: RegisterVm
    var apiInterface: ApiInterface? = null
    lateinit var dialog: AlertDialog

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        apiInterface = ApiClient.getClient(this)?.create(ApiInterface::class.java)
        val viewModelFactory = BaseVMF(RegisterVm(this))
        viewModel = ViewModelProvider(this, viewModelFactory)[RegisterVm::class.java]

        initProgressDialog()
        initLiveData()

        binding.apply {
            btnDaftar.setOnClickListener {

                val user = Register()
                val username = edtUsernameRegister.text.toString()
                val fullname = edtFullnameRegister.text.toString()
                val password = edtPasswordRegister.text.toString()
                val idcard = edtIdCardRegister.text.toString()
                val repeatPassword = edtRepeatPasswordRegister.text.toString()
                val address = edtAddressRegister.text.toString()

                user.username = username
                user.fullname = fullname
                user.password = password
                user.idcardnumber = idcard
                user.address = address

                if (username.isEmpty() || password.isEmpty() || fullname.isEmpty()) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please fill all the fields",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (password != repeatPassword) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Password doesn't match",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.register(user)
                }

            }

            txtLoginRegister.setOnClickListener {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            }


        }

    }

    private fun initLiveData() {

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

        viewModel.dataRegister.observe(this) {
            Toast.makeText(this, "Berhasil mendaftar", Toast.LENGTH_SHORT).show()
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
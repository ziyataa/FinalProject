package com.belajarandroid.finalproject.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.belajarandroid.finalproject.R
import com.belajarandroid.finalproject.databinding.ActivityEditProfileBinding
import com.belajarandroid.finalproject.databinding.ActivityMainBinding
import com.belajarandroid.finalproject.model.EditProfile
import com.belajarandroid.finalproject.model.ResponseForgot
import com.belajarandroid.finalproject.service.ApiClient
import com.belajarandroid.finalproject.service.ApiInterface
import com.belajarandroid.finalproject.utils.BaseVMF
import com.belajarandroid.finalproject.utils.LocalStorageHelper
import com.belajarandroid.finalproject.viewmodel.EditProfileVm

class EditProfileActivity : AppCompatActivity() {

    lateinit var localStorageHelper: LocalStorageHelper
    var apiInterface: ApiInterface? = null
    lateinit var viewModel: EditProfileVm

    lateinit var binding: ActivityEditProfileBinding

    private var data :  EditProfile? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        apiInterface = ApiClient.getClient(this)?.create(ApiInterface::class.java)
        val viewModelFactory = BaseVMF(EditProfileVm(this))
        viewModel = ViewModelProvider(this, viewModelFactory)[EditProfileVm::class.java]
        localStorageHelper = LocalStorageHelper(this)

        val id = localStorageHelper.getUserId().toString()
        val auth = localStorageHelper.getUserToken()
        data?.let { viewModel.editProfile(id, auth, it) }

        binding.apply {
            edtUsernameEditProfile.setText(localStorageHelper.username)
            edtIdCardEditProfile.setText(localStorageHelper.getUserNik())
            edtAddressEditProfile.setText(localStorageHelper.getUserAddress())
            edtPositionEditProfile.setText(localStorageHelper.getUserPosition())
            edtFullnameEditProfile.setText(localStorageHelper.getFullName())
            Toast.makeText(this@EditProfileActivity, localStorageHelper.getUserName(), Toast.LENGTH_SHORT).show()
            btnUpdateProfile.setOnClickListener {
                val fullname = edtFullnameEditProfile.text.toString()
                val address = edtAddressEditProfile.text.toString()
                val position = edtPositionEditProfile.text.toString()

                val data = EditProfile(
                    fullname,
                    address,
                    position
                )
                viewModel.editProfile(auth, id, data)
            }
        }

        initLiveData()

    }

    private fun initLiveData() {
        viewModel.isResponseStatus().observe(this) {
            if (it != 200) {
                Toast.makeText(this, "Gagal mengupdate data", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this@EditProfileActivity, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }

        viewModel.isErrorState().observe(this) {
            if (it == null) return@observe
            if (it) {
                Toast.makeText(this, "Gagal mengupdate data", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.editUser.observe(this) {
            if (it != null) {
                Toast.makeText(this, "Berhasil mengupdate data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
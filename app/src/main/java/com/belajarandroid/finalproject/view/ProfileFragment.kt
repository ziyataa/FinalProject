package com.belajarandroid.finalproject.view

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.belajarandroid.finalproject.R
import com.belajarandroid.finalproject.databinding.FragmentProfileBinding
import com.belajarandroid.finalproject.service.ApiClient
import com.belajarandroid.finalproject.service.ApiInterface
import com.belajarandroid.finalproject.utils.BaseVMF
import com.belajarandroid.finalproject.utils.LocalStorageHelper
import com.belajarandroid.finalproject.viewmodel.ProfileVm
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_profile.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ProfileFragment : Fragment() {

    lateinit var localStorageHelper: LocalStorageHelper
    lateinit var viewModel: ProfileVm

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 20
        private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        localStorageHelper = LocalStorageHelper(requireContext())

        val viewModelFactory = BaseVMF(ProfileVm(this.requireContext()))
        viewModel = ViewModelProvider(this, viewModelFactory)[ProfileVm::class.java]

        var auth = localStorageHelper.getUserToken()
        var userId = localStorageHelper.getUserId()
        viewModel.getDataProfile(auth, userId.toString())

        binding.apply {
            imgEditProfile.setOnClickListener {
                startActivity(Intent(requireContext(), EditProfileActivity::class.java))
            }

            cvLogout.setOnClickListener {
                localStorageHelper.logout()
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                requireActivity().finish()
                Toast.makeText(requireContext(), "Logout Success", Toast.LENGTH_SHORT).show()
            }

            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.CAMERA
                ) != android.content.pm.PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    REQUIRED_PERMISSIONS,
                    REQUEST_CODE_PERMISSIONS
                )
            }

            imgCam.setOnClickListener {
                MaterialAlertDialogBuilder(it.context)
                    //vertical button
                    .setTitle("Pilih Gambar Melalui")
                    .setItems(arrayOf("Camera", "Gallery")) { dialog, which ->
                        when (which) {
                            0 -> {
                                if (allPermissionsGranted()) {
                                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                    cameraResultLauncher.launch(intent)
                                }
                            }
                            1 -> {
                                if (allPermissionsGranted()) {
                                    val intent = Intent(
                                        Intent.ACTION_PICK,
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                    )
                                    launcher.launch(intent)
                                }
                            }
                        }
                    }.show()
            }
        }

        initLiveData()
    }

    private fun initLiveData() {
        viewModel.dataProfile.observe(viewLifecycleOwner) {
            binding.apply {
                txtNikProfile.text = it.success?.result?.idcardnumber
                txtAlamatProfile.text = it.success?.result?.address
                txtNamaProfile.text = it.success?.result?.fullname
                txtTitleProfile.text = it.success?.result?.position
                Glide.with(requireContext()).load(it.success?.result?.path).into(imgProfile)
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ActivityCompat.checkSelfPermission(
            requireContext(),
            it
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED
    }

    private val cameraResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val bitmapImage: Bitmap = result.data?.extras?.get("data") as Bitmap
            imgProfile.setImageBitmap(bitmapImage)
        }
    }
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == AppCompatActivity.RESULT_OK && result.data != null) {
                val photoUri: Uri? = result.data!!.data
                imgProfile.setImageURI(photoUri)
                val file: File = File(photoUri?.getPath())
                val requestFileUser: RequestBody =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file)
                val body_image_user = MultipartBody.Part.createFormData(
                    "file_image",
                    file.getName(),
                    requestFileUser
                )
            }
        }
}
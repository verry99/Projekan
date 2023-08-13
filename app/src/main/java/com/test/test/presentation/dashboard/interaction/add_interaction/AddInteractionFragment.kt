package com.test.test.presentation.dashboard.interaction.add_interaction

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.test.test.R
import com.test.test.databinding.FragmentAddInteractionBinding
import com.test.test.presentation.utils.createTempFile
import com.test.test.presentation.utils.reduceFileImage
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

@AndroidEntryPoint
class AddInteractionFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentAddInteractionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddInteractionViewModel by viewModels()
    private var getFile: File? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddInteractionBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpActionListeners()
        setUpLiveDataObservers()
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, requireContext())
                getFile = myFile

                binding.apply {
                    imgLampiran.setImageURI(uri)
                }
            }
        }
    }

    private fun uriToFile(selectedImg: Uri, context: Context): File {
        val contentResolver: ContentResolver = context.contentResolver
        val myFile = createTempFile(context)

        val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
        val outputStream: OutputStream = FileOutputStream(myFile)
        val buf = ByteArray(1024)
        var len: Int

        while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
        outputStream.close()
        inputStream.close()

        return myFile
    }

    private fun setUpActionListeners() {
        binding.apply {
            btnBack.setOnClickListener(this@AddInteractionFragment)
            btnKirim.setOnClickListener(this@AddInteractionFragment)
            btnLampiran.setOnClickListener(this@AddInteractionFragment)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> findNavController().navigateUp()
            R.id.btn_lampiran -> startGallery()
            R.id.btn_kirim -> {
                addInteraction()
            }
        }
    }

    private fun setUpLiveDataObservers() {
        viewModel.apply {

            success.observe(viewLifecycleOwner) {
                if (it) {
                    viewModel.success.value = false
                    findNavController().navigateUp()
                    Toast.makeText(
                        requireActivity(),
                        "Berhasil menambahkan topik.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            errorMessage.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }

            isLoading.observe(viewLifecycleOwner) {
                if (it) {
                    binding.apply {
                        progressBar.visibility = View.VISIBLE
                        btnKirim.isEnabled = false
                    }
                } else {
                    binding.apply {
                        progressBar.visibility = View.GONE
                        btnKirim.isEnabled = true
                    }
                }
            }
        }
    }

    private fun addInteraction() {

        val photo: MultipartBody.Part?

        if (binding.edtJudul.text.toString()
                .isEmpty() || binding.edtJudul.text.toString().length < 5
        ) {
            Toast.makeText(
                requireContext(),
                "Judul harus diisi minimal 5 karakter.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        if (binding.edtPesan.text.toString()
                .isEmpty() || binding.edtPesan.text.toString().length < 10
        ) {
            Toast.makeText(
                requireContext(),
                "Pesan harus diisi minimal 10 karakter.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        if (getFile == null) {
            Toast.makeText(
                requireContext(),
                "Lengkapi lampiran terlebih dahulu.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val file = reduceFileImage(getFile as File)
        val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
        photo = MultipartBody.Part.createFormData(
            "image", file.name, requestImageFile
        )

        val title = binding.edtJudul.text.toString().toRequestBody("text/plain".toMediaType())

        val description =
            binding.edtPesan.text.toString().toRequestBody("text/plain".toMediaType())

        viewModel.addInteraction(
            photo,
            title,
            description
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
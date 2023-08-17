package com.test.test.presentation.dashboard.real_count.add

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.test.test.R
import com.test.test.databinding.FragmentAddRealCountBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

@AndroidEntryPoint
class AddRealCountFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentAddRealCountBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddRealCountViewModel by viewModels()
    private val pairEditTextList = mutableListOf<LinearLayout>()
    private val fullNameList = mutableListOf<String>()
    private val voiceList = mutableListOf<Int>()
    private var getFile: File? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddRealCountBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpActionListener()
        setUpLiveDataObserver()
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
        val myFile = com.test.test.presentation.utils.createTempFile(context)

        val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
        val outputStream: OutputStream = FileOutputStream(myFile)
        val buf = ByteArray(1024)
        var len: Int

        while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
        outputStream.close()
        inputStream.close()

        return myFile
    }

    private fun setUpActionListener() {
        binding.apply {
            btnBack.setOnClickListener(this@AddRealCountFragment)
            btnAddName.setOnClickListener(this@AddRealCountFragment)
            btnKirim.setOnClickListener(this@AddRealCountFragment)
            btnLampiran.setOnClickListener(this@AddRealCountFragment)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> {
                findNavController().navigateUp()
            }

            R.id.btn_lampiran -> startGallery()

            R.id.btn_add_name -> {
                addPairEditTextField()
            }

            R.id.btn_kirim -> {

                retrieveAllNameAndVoice()
            }
        }
    }

    private fun setUpLiveDataObserver() {
        viewModel.apply {

            success.observe(viewLifecycleOwner) {
                if (it) {
                    viewModel.success.value = false
                    findNavController().navigateUp()
                    Toast.makeText(
                        requireActivity(),
                        "Berhasil menambahkan TPS.",
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

//    private fun addTPS() {
//
//        val photo: MultipartBody.Part?
//
//        if (binding.edtJudul.text.toString()
//                .isEmpty() || binding.edtJudul.text.toString().length < 5
//        ) {
//            Toast.makeText(
//                requireContext(),
//                "Judul harus diisi minimal 5 karakter.",
//                Toast.LENGTH_SHORT
//            ).show()
//
//            return
//        }
//
//        if (binding.edtPesan.text.toString()
//                .isEmpty() || binding.edtPesan.text.toString().length < 10
//        ) {
//            Toast.makeText(
//                requireContext(),
//                "Pesan harus diisi minimal 10 karakter.",
//                Toast.LENGTH_SHORT
//            ).show()
//
//            return
//        }
//
//        if (getFile == null) {
//            Toast.makeText(
//                requireContext(),
//                "Lengkapi lampiran terlebih dahulu.",
//                Toast.LENGTH_SHORT
//            ).show()
//
//            return
//        }
//
//        val file = reduceFileImage(getFile as File)
//        val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
//        photo = MultipartBody.Part.createFormData(
//            "image", file.name, requestImageFile
//        )
//
//        val title = binding.edtJudul.text.toString().toRequestBody("text/plain".toMediaType())
//
//        val description =
//            binding.edtPesan.text.toString().toRequestBody("text/plain".toMediaType())
//
//        viewModel.addInteraction(
//            photo,
//            title,
//            description
//        )
//    }

    @SuppressLint("InflateParams")
    private fun addPairEditTextField() {
        val pairLayout =
            layoutInflater.inflate(R.layout.edittext_pair_layout, null) as LinearLayout
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.topMargin = 32
        pairLayout.layoutParams = layoutParams

        binding.pairEditTextContainer.addView(pairLayout)
        pairEditTextList.add(pairLayout)
    }

    private fun retrieveAllNameAndVoice(): Boolean {

        for (pair in pairEditTextList) {
            val editText1 = pair.getChildAt(0) as EditText
            val editText2 = pair.getChildAt(1) as EditText

            if (editText1.text.isEmpty() && editText2.text.isEmpty()) {
                continue
            } else if (editText1.text.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Lengkapi nama rival atau kosongkan kolom suara terlebih dahulu.",
                    Toast.LENGTH_SHORT
                ).show()

                return false
            } else if (editText2.text.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Lengkapi suara rival atau kosongkan nama terlebih dahulu.",
                    Toast.LENGTH_SHORT
                ).show()

                return false
            }

            val value1 = editText1.text.toString()
            val value2 = editText2.text.toString().toInt()

            fullNameList.add(value1)
            voiceList.add(value2)
        }
        return true
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
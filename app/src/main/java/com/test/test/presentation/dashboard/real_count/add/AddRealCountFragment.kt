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
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.test.test.R
import com.test.test.databinding.FragmentAddRealCountBinding
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
class AddRealCountFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentAddRealCountBinding
    private val viewModel: AddRealCountViewModel by viewModels()
    private val pairEditTextList = mutableListOf<LinearLayout>()
    private var getFile: File? = null
    private val voiceList = mutableListOf<Int>()
    private val candidates = listOf(
        "RB. DWI WAHYU B. S.Pd M.Si",
        "EKO SUWANTO ST M.Si",
        "NOVITA DWI ARINI",
        "IMAM PRIYONO D. PUTRANTO S.E M.Si",
        "SUSANTO BUDI RAHARJO S.H M.HM",
        "AINUN MARDZIYAH S.A.P",
        "FITRI HERTANTI"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddRealCountBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpCandidateNames()
        setUpActionListener()
        setUpLiveDataObserver()
    }

    private fun setUpCandidateNames() {
        binding.apply {
            suaraRival1.edtNamaLengkap.setText(candidates[0])
            suaraRival2.edtNamaLengkap.setText(candidates[1])
            suaraRival3.edtNamaLengkap.setText(candidates[2])
            suaraRival4.edtNamaLengkap.setText(candidates[3])
            suaraRival5.edtNamaLengkap.setText(candidates[4])
            suaraRival6.edtNamaLengkap.setText(candidates[5])
            suaraRival7.edtNamaLengkap.setText(candidates[6])
        }
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

            R.id.btn_kirim -> addRealCount()
        }
    }

    private fun addRealCount() {

        val names = listOf(
            binding.suaraRival1.edtNamaLengkap.text.toString(),
            binding.suaraRival2.edtNamaLengkap.text.toString(),
            binding.suaraRival3.edtNamaLengkap.text.toString(),
            binding.suaraRival4.edtNamaLengkap.text.toString(),
            binding.suaraRival6.edtNamaLengkap.text.toString(),
            binding.suaraRival7.edtNamaLengkap.text.toString()
        )

        val voices =
            listOf(
                binding.suaraRival1.edtSuara.text.toString(),
                binding.suaraRival2.edtSuara.text.toString(),
                binding.suaraRival3.edtSuara.text.toString(),
                binding.suaraRival4.edtSuara.text.toString(),
                binding.suaraRival6.edtSuara.text.toString(),
                binding.suaraRival7.edtSuara.text.toString()
            )

        val image: MultipartBody.Part

        if (getFile == null) {
            Toast.makeText(
                requireContext(),
                "Lengkapi bukti terlebih dahulu.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        if (binding.edtTps.text.toString().isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Lengkapi TPS terlebih dahulu.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        if (binding.edtKecamatan.text.toString().isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Lengkapi Kecamatan terlebih dahulu.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        if (binding.edtDesa.text.toString().isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Lengkapi Kelurahan/Desa terlebih dahulu.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        for (voice in voices) {
            if (voice.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Lengkapi perolehan suara terlebih dahulu.",
                    Toast.LENGTH_SHORT
                ).show()

                return
            }
        }

        if (binding.suaraRival5.edtSuara.text.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Lengkapi perolehan suara terlebih dahulu.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val file = reduceFileImage(getFile as File)

        val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
        image = MultipartBody.Part.createFormData(
            "image", file.name, requestImageFile
        )

        val count =
            binding.suaraRival5.edtSuara.text.toString().toRequestBody("text/plain".toMediaType())

        val tps =
            binding.edtTps.text.toString().toRequestBody("text/plain".toMediaType())

        val subDistrict =
            binding.edtKecamatan.text.toString().toRequestBody("text/plain".toMediaType())

        val village =
            binding.edtDesa.text.toString().toRequestBody("text/plain".toMediaType())

        val name = names.joinToString { it }.toRequestBody("text/plain".toMediaType())
        val voice = voices.joinToString { it }.toRequestBody("text/plain".toMediaType())

        viewModel.addRealCount(
            image,
            tps,
            count,
            subDistrict,
            village,
            name,
            voice
        )
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
}
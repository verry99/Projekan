package com.test.test.presentation.profile.edit_profile

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.test.test.R
import com.test.test.databinding.FragmentEditProfileBinding
import com.test.test.presentation.utils.createTempFile
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

@AndroidEntryPoint
class EditProfileFragment : Fragment(), View.OnClickListener, AdapterView.OnItemSelectedListener {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditProfileViewModel by viewModels()
    private var getFile: File? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSpinners()
        setUpActionListeners()
        setUpLiveDataObservers()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> findNavController().navigateUp()
            R.id.btn_simpan -> Log.e("#halo", binding.edtAlamat.text.toString().split("\n").toString().split(",").toString())
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
                    imgProfile.setImageURI(uri)
                    btnAddPicture.text = "Ubah"
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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedSpinnerId = parent?.id
        val selectedItem = parent?.getItemAtPosition(position).toString()

        when (selectedSpinnerId) {
            R.id.spinner_provinsi -> viewModel.setSelectedProvince(selectedItem)
            R.id.spinner_kabupaten -> viewModel.setSelectedRegency(selectedItem)
            R.id.spinner_kecamatan -> viewModel.setSelectedDistrict(selectedItem)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    private fun setUpSpinners() {
        val spinnerDataGenderItems = resources.getStringArray(R.array.spinner_gender)
        val spinnerDataReligionItems = resources.getStringArray(R.array.religion_spinner_items)
        val spinnerDataStatusItems = resources.getStringArray(R.array.spinner_marital_status)

        val adapterGender = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            spinnerDataGenderItems
        )

        val adapterReligion = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            spinnerDataReligionItems
        )

        val adapterStatus = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            spinnerDataStatusItems
        )

        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterReligion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.apply {
            spinnerJenisKelamin.adapter = adapterGender
            spinnerAgama.adapter = adapterReligion
            spinnerStatusPerkawinan.adapter = adapterStatus
        }
    }

    private fun setUpActionListeners() {
        binding.apply {
            btnBack.setOnClickListener(this@EditProfileFragment)
            btnSimpan.setOnClickListener(this@EditProfileFragment)
            btnAddPicture.setOnClickListener { startGallery() }
            spinnerProvinsi.onItemSelectedListener = this@EditProfileFragment
            spinnerKabupaten.onItemSelectedListener = this@EditProfileFragment
            spinnerKecamatan.onItemSelectedListener = this@EditProfileFragment
            spinnerDesa.onItemSelectedListener = this@EditProfileFragment
            spinnerStatusPerkawinan.onItemSelectedListener = this@EditProfileFragment
            spinnerAgama.onItemSelectedListener = this@EditProfileFragment
            spinnerJenisKelamin.onItemSelectedListener = this@EditProfileFragment
        }
    }

    private fun setUpLiveDataObservers() {
        viewModel.province.observe(viewLifecycleOwner) {
            val itemList: List<String> = it.map { province -> province.name }
            val adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, itemList)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerProvinsi.adapter = adapter
        }
        viewModel.regency.observe(viewLifecycleOwner) {
            val itemList: List<String> = it.map { regency -> regency.name }
            val adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, itemList)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerKabupaten.adapter = adapter
        }
        viewModel.district.observe(viewLifecycleOwner) {
            val itemList: List<String> = it.map { district -> district.name }
            val adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, itemList)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerKecamatan.adapter = adapter
        }
        viewModel.village.observe(viewLifecycleOwner) {
            val itemList: List<String> = it.map { village -> village.name }
            val adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, itemList)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerDesa.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
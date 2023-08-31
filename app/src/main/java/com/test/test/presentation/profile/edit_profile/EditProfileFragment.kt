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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.test.test.R
import com.test.test.common.Constants.IMAGE_URL
import com.test.test.databinding.FragmentEditProfileBinding
import com.test.test.presentation.utils.convertToDayFirst
import com.test.test.presentation.utils.convertToYearFirst
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class EditProfileFragment : Fragment(), View.OnClickListener, AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentEditProfileBinding
    private val viewModel: EditProfileViewModel by viewModels()
    private var getFile: File? = null
    private var userBirthDate: String? = null
    private var userRegency: String? = null
    private var userSubDistrict: String? = null
    private var userVillage: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)

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

            R.id.ib_tanggal_lahir -> {

                val constraintsBuilder = CalendarConstraints.Builder()

                val startTimestamp =
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        .parse("1950-01-01")?.time
                        ?: 0
                val endTimestamp = System.currentTimeMillis()

                constraintsBuilder.setStart(startTimestamp)
                constraintsBuilder.setEnd(endTimestamp)

                val datePicker =
                    MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select date")
                        .setCalendarConstraints(constraintsBuilder.build())
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build()

                datePicker.addOnPositiveButtonClickListener { selectedDate ->
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                    val formattedDate = dateFormat.format(Date(selectedDate))
                    userBirthDate = formattedDate

                    binding.tvTanggalLahir.text =
                        SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date(selectedDate))
                }

                datePicker.show(parentFragmentManager, "Edit Profile")
            }

            R.id.btn_simpan -> {
                updateProfile()
            }
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
            R.id.spinner_kabupaten -> viewModel.setSelectedRegency(selectedItem)
            R.id.spinner_kecamatan -> viewModel.setSelectedSubDistrict(selectedItem)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    private fun setUpSpinners() {
        val spinnerDataGenderItems = resources.getStringArray(R.array.spinner_gender)
        val spinnerDataReligionItems = resources.getStringArray(R.array.spinner_religion)
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
            ibTanggalLahir.setOnClickListener(this@EditProfileFragment)
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

        viewModel.apply {

            province.observe(viewLifecycleOwner) {
                val itemList: List<String> = it.map { province -> province.name }
                val adapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, itemList)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerProvinsi.adapter = adapter
            }

            regency.observe(viewLifecycleOwner) {
                val itemList: List<String> = it.map { regency -> regency.name }
                val adapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, itemList)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerKabupaten.adapter = adapter
                if (userRegency != null) {
                    val selectedIndex = itemList.indexOfFirst { it == userRegency }
                    if (selectedIndex != -1) {
                        binding.spinnerKabupaten.setSelection(selectedIndex)
                    } else {
                        binding.spinnerKabupaten.setSelection(itemList.indexOfFirst { it == "KOTA YOGYAKARTA" })
                    }
                    userRegency = null
                }
            }

            subDistrict.observe(viewLifecycleOwner) {
                val itemList: List<String> = it.map { district -> district.name }
                val adapter =
                    ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        itemList
                    )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerKecamatan.adapter = adapter
                if (userSubDistrict != null) {
                    val selectedIndex = itemList.indexOfFirst { it == userSubDistrict }
                    if (selectedIndex != -1) {
                        binding.spinnerKecamatan.setSelection(selectedIndex)
                        userSubDistrict = null
                    }
                }
            }
            village.observe(viewLifecycleOwner) {
                val itemList: List<String> = it.map { village -> village.name }
                val adapter =
                    ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        itemList
                    )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerDesa.adapter = adapter
                if (userVillage != null) {
                    val selectedIndex = itemList.indexOfFirst { it == userVillage }
                    if (selectedIndex != -1) {
                        binding.spinnerDesa.setSelection(selectedIndex)
                        userVillage = null
                    }
                }
            }

            profile.observe(viewLifecycleOwner) {
                binding.apply {
                    it.urlToImage?.let {
                        Glide.with(requireContext())
                            .load("$IMAGE_URL/$it")
                            .into(binding.imgProfile)

                        binding.btnAddPicture.text = "Ubah"
                    }
                    edtNik.setText(it.nik)
                    edtNama.setText(it.name)
                    edtAlamat.setText(it.address)
                    edtNoHp.setText(it.phone)
                    edtTempatLahir.setText(it.birthPlace)
                    edtRt.setText(it.rt)
                    edtRw.setText(it.rw)
                    edtTps.setText(it.tps)

                    it.birthDate?.let {
                        try {
                            tvTanggalLahir.text = convertToDayFirst(it)
                        } catch (e: Exception) {
                            tvTanggalLahir.text = it
                        }
                    }

                    userVillage = it.village
                    userSubDistrict = it.subDistrict
                    userRegency = it.regency

                    viewModel.setSelectedProvince("DI YOGYAKARTA")
                }
            }

            isLoading.observe(viewLifecycleOwner) {
                binding.apply {
                    if (it) {
                        progressBar.visibility = View.VISIBLE
                        btnSimpan.isEnabled = false

                    } else {
                        btnSimpan.isEnabled = true
                        progressBar.visibility = View.GONE
                    }
                }
            }

            success.observe(viewLifecycleOwner) {
                if (it) {
                    findNavController().navigateUp()
                    Toast.makeText(
                        requireActivity(),
                        "Berhasil mengubah profile.",
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.success.value = false
                }
            }

            errorMessage.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) Toast.makeText(
                    requireContext(),
                    it,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun updateProfile() {
        if (binding.tvTanggalLahir.text == "dd-mm-yyyy") {
            Toast.makeText(
                requireContext(),
                "Lengkapi tanggal lahir terlebih dahulu.",
                Toast.LENGTH_SHORT
            )
                .show()
            return
        }

        if (binding.spinnerKabupaten.selectedItem == "Pilih Kabupaten") {
            Toast.makeText(requireContext(), "Pilih kabupaten terlebih dahulu.", Toast.LENGTH_SHORT)
                .show()
            return
        }

        if (binding.spinnerKecamatan.selectedItem == "Pilih Kecamatan") {
            Toast.makeText(requireContext(), "Pilih kecamatan terlebih dahulu.", Toast.LENGTH_SHORT)
                .show()
            return
        }

        if (binding.spinnerDesa.selectedItem == "Pilih Kelurahan") {
            Toast.makeText(requireContext(), "Pilih kelurahan terlebih dahulu.", Toast.LENGTH_SHORT)
                .show()
            return
        }

        var photo: MultipartBody.Part? = null

        if (getFile != null) {
            val file = reduceFileImage(getFile as File)
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
            photo = MultipartBody.Part.createFormData(
                "photo", file.name, requestImageFile
            )
        }

        val nik = binding.edtNik.text.toString().toRequestBody("text/plain".toMediaType())

        val name = binding.edtNama.text.toString().toRequestBody("text/plain".toMediaType())

        val phone =
            binding.edtNoHp.text.toString().toRequestBody("text/plain".toMediaType())

        val birthPlace =
            binding.edtTempatLahir.text.toString().toRequestBody("text/plain".toMediaType())

        val birthDate = if (userBirthDate != null) {
            userBirthDate!!.toRequestBody("text/plain".toMediaType())
        } else {
            convertToYearFirst(
                binding.tvTanggalLahir.text.toString()
            ).toRequestBody(
                "text/plain".toMediaType()
            )
        }

        val address =
            binding.edtAlamat.text.toString().toRequestBody("text/plain".toMediaType())

        val gender = if (binding.spinnerJenisKelamin.selectedItem.toString() == "Laki-laki") {
            "L".toRequestBody("text/plain".toMediaType())
        } else {
            "P".toRequestBody("text/plain".toMediaType())
        }

        val rt = binding.edtRt.text.toString().toRequestBody("text/plain".toMediaType())

        val rw = binding.edtRw.text.toString().toRequestBody("text/plain".toMediaType())

        val tps = binding.edtTps.text.toString().toRequestBody("text/plain".toMediaType())

        val province = binding.spinnerProvinsi.selectedItem.toString()
            .toRequestBody("text/plain".toMediaType())

        val regency = binding.spinnerKabupaten.selectedItem.toString()
            .toRequestBody("text/plain".toMediaType())

        val subDistrict = binding.spinnerKecamatan.selectedItem.toString()
            .toRequestBody("text/plain".toMediaType())

        val village = binding.spinnerDesa.selectedItem.toString()
            .toRequestBody("text/plain".toMediaType())

        val religion = if (binding.spinnerAgama.selectedItem == "Pilih Agama") {
            "".toRequestBody("text/plain".toMediaType())
        } else {
            binding.spinnerAgama.selectedItem.toString().toRequestBody("text/plain".toMediaType())
        }

        val maritalStatus = if (binding.spinnerStatusPerkawinan.selectedItem == "Pilih Status") {
            "".toRequestBody("text/plain".toMediaType())
        } else {
            binding.spinnerStatusPerkawinan.selectedItem.toString()
                .toRequestBody("text/plain".toMediaType())
        }
        Log.e("#edproffrag", "${binding.spinnerStatusPerkawinan.selectedItem}")
        viewModel.updateProfile(
            photo,
            nik,
            name,
            phone,
            birthPlace,
            birthDate,
            gender,
            address,
            rt,
            rw,
            tps,
            province,
            regency,
            subDistrict,
            village,
            religion,
            maritalStatus
        )
    }
}
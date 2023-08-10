package com.test.test.presentation.dashboard.volunteer.add_volunteer

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.test.test.R
import com.test.test.databinding.FragmentAddVolunteerBinding
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
class AddVolunteerFragment : Fragment(), View.OnClickListener, AdapterView.OnItemSelectedListener {

    private var _binding: FragmentAddVolunteerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddVolunteerViewModel by viewModels()
    private var getFile: File? = null
    private var birthDate: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddVolunteerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSpinners()
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
            btnBack.setOnClickListener(this@AddVolunteerFragment)
            ibTanggalLahir.setOnClickListener(this@AddVolunteerFragment)
            btnAddPicture.setOnClickListener { startGallery() }
            spinnerProvinsi.onItemSelectedListener = this@AddVolunteerFragment
            spinnerKabupaten.onItemSelectedListener = this@AddVolunteerFragment
            spinnerKecamatan.onItemSelectedListener = this@AddVolunteerFragment
            spinnerDesa.onItemSelectedListener = this@AddVolunteerFragment
            spinnerStatusPerkawinan.onItemSelectedListener = this@AddVolunteerFragment
            spinnerAgama.onItemSelectedListener = this@AddVolunteerFragment
            spinnerJenisKelamin.onItemSelectedListener = this@AddVolunteerFragment
            btnSimpan.setOnClickListener(this@AddVolunteerFragment)
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
            }

            district.observe(viewLifecycleOwner) {
                val itemList: List<String> = it.map { district -> district.name }
                val adapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, itemList)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerKecamatan.adapter = adapter
            }

            village.observe(viewLifecycleOwner) {
                val itemList: List<String> = it.map { village -> village.name }
                val adapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, itemList)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerDesa.adapter = adapter
            }

            isLoading.observe(viewLifecycleOwner) {
                if (it) {
                    binding.apply {
                        progressBar.visibility = View.VISIBLE
                        btnSimpan.isEnabled = false
                    }
                } else {
                    binding.apply {
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
                        "Berhasil menambahkan volunteeer.",
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.success.value = false
                }
            }

            errorMessage.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
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
                    birthDate = formattedDate

                    binding.tvTanggalLahir.text =
                        SimpleDateFormat("dd/MM/yyyy", Locale.US).format(Date(selectedDate))
                }

                datePicker.show(parentFragmentManager, "Edit Profile");
            }

            R.id.btn_simpan -> {
                addVolunteer()
            }
        }
    }

    private fun addVolunteer() {
        if (binding.spinnerProvinsi.selectedItem == "Pilih Provinsi") {
            Toast.makeText(requireContext(), "Pilih provinsi terlebih dahulu.", Toast.LENGTH_SHORT)
                .show()
            return
        }

        if (binding.spinnerKabupaten.selectedItem == "Pilih Kabupaten") {
            Toast.makeText(requireContext(), "Pilih kabupaten terlebih dahulu.", Toast.LENGTH_SHORT)
                .show()
            return
        }

        if (binding.spinnerKecamatan.selectedItem == "Pilih Kecamatan") {
            Toast.makeText(requireContext(), "Pilih kabupaten terlebih dahulu.", Toast.LENGTH_SHORT)
                .show()
            return
        }

        if (binding.spinnerDesa.selectedItem == "Pilih Desa") {
            Toast.makeText(requireContext(), "Pilih desa terlebih dahulu.", Toast.LENGTH_SHORT)
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

        val email = binding.edtEmail.text.toString().toRequestBody("text/plain".toMediaType())

        val birthPlace =
            binding.edtTempatLahir.text.toString().toRequestBody("text/plain".toMediaType())

        val birthDate =
            binding.tvTanggalLahir.text.toString().toRequestBody("text/plain".toMediaType())

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
            binding.spinnerAgama.selectedItem.toString()
                .toRequestBody("text/plain".toMediaType())
        }

        val maritalStatus = if (binding.spinnerStatusPerkawinan.selectedItem == "Pilih Status") {
            "".toRequestBody("text/plain".toMediaType())
        } else {
            binding.spinnerStatusPerkawinan.selectedItem.toString()
                .toRequestBody("text/plain".toMediaType())
        }

        viewModel.addVolunteer(
            photo,
            nik,
            name,
            phone,
            email,
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
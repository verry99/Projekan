package com.test.test.presentation.utils

import android.content.Context
import android.os.Environment
import com.test.test.R
import java.io.File
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private const val FILENAME_FORMAT = "dd-MMM-yyyy"
private const val MAXIMAL_SIZE = 1000000

val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())

fun getGreetingText(context: Context): String {
    val currentTime = Calendar.getInstance().time
    val greetingText =
        when (SimpleDateFormat("HH", Locale.getDefault()).format(currentTime).toInt()) {
            in 0..11 -> context.getString(R.string.selamat_pagi)
            in 12..16 -> context.getString(R.string.selamat_siang)
            else -> context.getString(R.string.selamat_sore)
        }
    return greetingText
}

fun formatNumber(number: Long): String {
    val numberFormat = NumberFormat.getNumberInstance(Locale.GERMANY)
    return numberFormat.format(number)
}

fun createTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}
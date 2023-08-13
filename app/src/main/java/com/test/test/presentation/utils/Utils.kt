package com.test.test.presentation.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.text.format.DateUtils
import com.test.test.R
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.round

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

fun reduceFileImage(file: File): File {
    val bitmap = BitmapFactory.decodeFile(file.path)
    var compressQuality = 100
    var streamLength: Int
    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= 5
    } while (streamLength > MAXIMAL_SIZE)
    bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
    return file
}

fun convertToDayFirst(inputDate: String): String {
    val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val outputFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    val date = LocalDate.parse(inputDate, inputFormat)
    return date.format(outputFormat)
}

fun convertToYearFirst(inputDate: String): String {
    val inputFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val date = LocalDate.parse(inputDate, inputFormat)
    return date.format(outputFormat)
}

@SuppressLint("SimpleDateFormat")
fun convertToHumanReadableDate(inputDate: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    val date = inputFormat.parse(inputDate)!!

    return DateUtils.getRelativeTimeSpanString(
        date.time,
        Calendar.getInstance().timeInMillis,
        DateUtils.MINUTE_IN_MILLIS
    ).toString()
}

fun Double.roundTo(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}

fun getStringPercentage(inputNumber: Double): String {
    val number = BigDecimal(inputNumber)
    val multiplied = number.multiply(BigDecimal(100)).setScale(2, RoundingMode.HALF_EVEN)
    return "$multiplied%"
}
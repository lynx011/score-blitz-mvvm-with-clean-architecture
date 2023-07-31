package com.lynx.scoreblitz.utils

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.lynx.scoreblitz.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Fragment.navigate(resId: Int){
    findNavController().navigate(resId)
}

fun Fragment.navigateUp(){
    findNavController().navigateUp()
}

fun Fragment.toast(message: String){
    Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
}

fun timeFormat(time: String?) : String{
    val inputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val date = time?.let { inputFormat.parse(it) } ?: "-"
    val outputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return outputFormat.format(date)
}

fun dateFormat(date: String?) : String{
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val localDate = LocalDate.parse(date, formatter)
    val outputFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
    return localDate.format(outputFormatter) ?: "-"
}

package com.lynx.scoreblitz.utils

import android.app.Activity
import android.content.Context
import android.os.CountDownTimer
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.lynx.scoreblitz.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

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

fun countDownTimer(time: String?,text: TextView) : CountDownTimer? {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val parsed = time?.let { dateFormat.parse(it) }
    val currentTime = Calendar.getInstance().timeInMillis
    if (parsed != null) {
        val countDownTimer = object : CountDownTimer(parsed.time - currentTime, 1000) {
            override fun onTick(millis: Long) {
                val remainingSeconds = millis / 1000
                val days = remainingSeconds / (3600 * 24)
                val months = days / 30
                val remainingDays = days % 30
                val hours = (remainingSeconds % (3600 * 24)) / 3600
                val minutes = (remainingSeconds % 3600) / 60
                val seconds = remainingSeconds % 60

//                val timer = when {
//                    days > 0 && hours > 0 -> String.format("%dd %dh", days, hours)
//                    days > 0 -> String.format("%dd", days)
//                    else -> String.format("%dh", hours)
//                }
                val timer = when {
                    months > 0 -> {
                        if (remainingDays > 0) {
                            String.format("%d months %d days", months, remainingDays)
                        } else {
                            String.format("%d months", months)
                        }
                    }
                    remainingDays > 0 -> {
                        if (hours > 0) {
                            String.format("%d days %d hours",days,hours)
                        }
                        else{
                            String.format("%d days", remainingDays)
                        }
                    }
                    hours > 0 -> {
                        String.format("%02d:%02d:%02d",hours,minutes,seconds)
                    }
                    else -> {
                        String.format("%d hours %d minutes", hours,minutes)
                    }
                }

                text.text = timer
            }

            override fun onFinish() {
                text.text = "FullTime"
            }
        }.start()
        return countDownTimer
    }
    return null
}

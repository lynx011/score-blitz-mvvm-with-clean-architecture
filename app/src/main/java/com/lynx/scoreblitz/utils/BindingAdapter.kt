package com.lynx.scoreblitz.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@BindingAdapter("loadImage")
fun loadImage(view: ImageView, url: String?){
        if (!url.isNullOrEmpty()){
            Glide.with(view)
                .load(url)
                .into(view)
        }
}

@BindingAdapter("formatDate")
fun formatDate(view: TextView, date: String?){
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val localDate = LocalDate.parse(date, formatter)
        val outputFormatter = DateTimeFormatter.ofPattern("d MMM")
        view.text = localDate.format(outputFormatter) ?: "-"
}

@BindingAdapter("formatTime")
fun formatTime(view: TextView, time: String?){
    val inputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val date = time?.let { inputFormat.parse(it) } ?: "-"
    val outputFormat = SimpleDateFormat("hh:mm", Locale.getDefault())
    view.text = outputFormat.format(date)
}

@BindingAdapter("splitName")
fun splitName(view: TextView, name: String?){
    val words = name?.split(" ")
    val nextWord = if ((words?.size ?: 0) > 1) words?.get(1) ?: "" else name
    view.text = nextWord
}
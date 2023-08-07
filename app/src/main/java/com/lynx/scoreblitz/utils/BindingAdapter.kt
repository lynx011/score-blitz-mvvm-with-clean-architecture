package com.lynx.scoreblitz.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.lynx.scoreblitz.domain.model.FixtureResult
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@BindingAdapter("loadImage")
fun loadImage(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view)
            .load(url)
            .into(view)
    }
}

@BindingAdapter("formatDate")
fun formatDate(view: TextView, date: String?) {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val localDate = LocalDate.parse(date, formatter)
    val outputFormatter = DateTimeFormatter.ofPattern("d MMM")
    view.text = localDate.format(outputFormatter) ?: "-"
}

@BindingAdapter("formattedDate")
fun formattedDate(view: TextView, date: String?) {
    view.text = dateFormat(date)
}

@BindingAdapter("formatTime")
fun formatTime(view: TextView, time: String?) {
//    val inputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
//    val date = time?.let { inputFormat.parse(it) } ?: "-"
//    val outputFormat = SimpleDateFormat("hh:mm", Locale.getDefault())
    view.text = timeFormat(time)
}

@BindingAdapter("splitName")
fun splitName(view: TextView, name: String?) {
    val words = name?.split(" ")
    val nextWord = if ((words?.size ?: 0) > 1) words?.get(1) ?: "" else name
    view.text = nextWord
}

@BindingAdapter("result","type")
fun redCardsVisibility(view: View, result: FixtureResult?, type: Int?) {
    if (type == result?.home_team_key){
        val red = result?.cards?.find { it?.card == "red card" && it.home_fault != null }
        if (red != null) view.visibility = View.VISIBLE
        else view.visibility = View.GONE
    }
    if (type == result?.away_team_key){
        val red = result?.cards?.find { it?.card == "red card" && it.away_fault != null}
        if (red != null) view.visibility = View.VISIBLE
        else view.visibility = View.GONE
    }
}

@BindingAdapter("eventResult","eventDate")
fun eventResultOnDetail(view: TextView, result: String?, event: String?){
    val time = timeFormat(event)
    view.text = if (result == "") time else result ?: "N/A"
}

@BindingAdapter("fullResult","eventTime")
fun ftVisibility(view: TextView,fullResult: String?,eventTime: String?){
    val time = timeFormat(eventTime)
    view.text = if (fullResult.isNullOrEmpty()) time else "FT"
}

@BindingAdapter("ftResult","eventDate")
fun showHideDate(view: TextView,ftResult: String?,eventDate: String?){
//    val event = dateFormat(eventDate)
//    view.text = if (ftResult.isNullOrEmpty()) event else "FullTime"
    if (ftResult.isNullOrEmpty()) countDownTimer(eventDate,view) else view.text = "FullTime"
}

@BindingAdapter("standingString")
fun standingString(view: TextView, result: Int?){
    view.text = result?.toString() ?: "0"
}


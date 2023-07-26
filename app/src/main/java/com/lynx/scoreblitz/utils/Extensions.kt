package com.lynx.scoreblitz.utils

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun Fragment.navigate(resId: Int){
    findNavController().navigate(resId)
}

fun Fragment.toast(message: String){
    Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
}

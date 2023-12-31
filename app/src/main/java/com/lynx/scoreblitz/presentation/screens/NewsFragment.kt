package com.lynx.scoreblitz.presentation.screens

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.lynx.scoreblitz.databinding.FragmentNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Random

@AndroidEntryPoint
class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("HardwareIds")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ref.text = "Generated RefId - ${getRefID()}"
    }

    private val refDateFormat = SimpleDateFormat("HHmmssyyMMdd", Locale.ENGLISH)
    private fun getRefID() = "a" + refDateFormat.format(Date()) + "${Random().nextInt(99999)}"

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
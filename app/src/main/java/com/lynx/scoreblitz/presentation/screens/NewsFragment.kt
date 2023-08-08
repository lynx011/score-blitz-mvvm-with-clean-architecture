package com.lynx.scoreblitz.presentation.screens

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lynx.scoreblitz.R
import com.lynx.scoreblitz.databinding.FragmentNewsBinding
import com.lynx.scoreblitz.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("HardwareIds")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val deviceId: String = Settings.Secure.getString(requireContext().contentResolver, Settings.Secure.ANDROID_ID)
        toast(deviceId)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
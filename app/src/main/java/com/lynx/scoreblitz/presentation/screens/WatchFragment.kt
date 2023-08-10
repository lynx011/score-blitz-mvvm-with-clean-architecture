package com.lynx.scoreblitz.presentation.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.lynx.scoreblitz.databinding.FragmentWatchBinding
import com.lynx.scoreblitz.presentation.view_models.DashboardViewModel
import com.lynx.scoreblitz.utils.collectLatest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WatchFragment : Fragment() {
    private var _binding: FragmentWatchBinding? = null
    private val binding get() = _binding!!
    private val dashViewModel: DashboardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWatchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dashViewModel.getSmLeagues(2)
        CoroutineScope(Dispatchers.Main).launch {
            dashViewModel.smLeagues.collectLatest(viewLifecycleOwner){
                when{
                    !it.smLeagues.isNullOrEmpty() -> {
                        Log.d("sm_leagues",it.smLeagues.toString())
                    }
                    it.error.isNotEmpty() -> {
                        Log.d("sm_leagues",it.error)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

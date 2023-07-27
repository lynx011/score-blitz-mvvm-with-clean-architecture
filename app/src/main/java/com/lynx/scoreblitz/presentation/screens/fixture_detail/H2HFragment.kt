package com.lynx.scoreblitz.presentation.screens.fixture_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lynx.scoreblitz.databinding.FragmentH2HBinding
import com.lynx.scoreblitz.presentation.adapter.H2hAdapter
import com.lynx.scoreblitz.presentation.view_models.ScoreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class H2HFragment : Fragment() {
    private lateinit var binding: FragmentH2HBinding
    private val viewModel: ScoreViewModel by activityViewModels()
    private lateinit var h2hAdapter: H2hAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentH2HBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRec()
        observeH2H()
    }

    private fun setupRec() {
        binding.h2hRec.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        h2hAdapter = H2hAdapter(viewModel) { _ ->
        }
        binding.h2hRec.adapter = h2hAdapter
    }

    private fun observeH2H(){
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.h2h.collectLatest {
                when{
                    !it.h2h?.H2H.isNullOrEmpty() -> {
                        viewModel.h2hResult.value = it.h2h?.H2H
                        h2hAdapter.differ.submitList(it.h2h?.H2H)
                    }
                }
            }
        }
    }


}
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
import com.lynx.scoreblitz.presentation.view_models.DashboardViewModel
import com.lynx.scoreblitz.presentation.view_models.FixtureDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class H2HFragment : Fragment() {
    private var _binding: FragmentH2HBinding? = null
    private val binding get() = _binding!!
    private val dashboardViewModel: DashboardViewModel by activityViewModels()
    private val detailViewModel: FixtureDetailsViewModel by activityViewModels()
    private lateinit var h2hAdapter: H2hAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentH2HBinding.inflate(inflater, container, false)
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
        h2hAdapter = H2hAdapter(dashboardViewModel) { _ ->
        }
        binding.h2hRec.adapter = h2hAdapter
    }

    private fun observeH2H() {
        CoroutineScope(Dispatchers.Main).launch {
            detailViewModel.h2h.collectLatest {
                when {
                    !it.h2h?.H2H.isNullOrEmpty() -> {
                        detailViewModel.h2hResult.value = it.h2h?.H2H
                        if (it.h2h?.H2H != null) {
                            binding.notFoundH2H.visibility = View.GONE
                            h2hAdapter.differ.submitList(it.h2h.H2H)
                        } else binding.notFoundH2H.visibility = View.VISIBLE
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
package com.lynx.scoreblitz.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lynx.scoreblitz.databinding.LeagueItemsBinding
import com.lynx.scoreblitz.domain.model.Leagues
import com.lynx.scoreblitz.presentation.view_models.ScoreViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LeaguesAdapter(
    private val viewModel: ScoreViewModel,
    private val clickOnLeague: ((Leagues) -> Unit)? = null
) : RecyclerView.Adapter<LeaguesAdapter.LeaguesViewHolder>() {

    class LeaguesViewHolder(
        val binding: LeagueItemsBinding, private val viewModel: ScoreViewModel
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(leagues: Leagues) {
            binding.league = leagues
            binding.executePendingBindings()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaguesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LeagueItemsBinding.inflate(inflater, parent, false)
        return LeaguesViewHolder(binding, viewModel)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: LeaguesViewHolder, position: Int) {
        val leagues = differ.currentList[position]
        leagues?.let(holder::bind)

        holder.binding.apply {

            leagues.league_key?.let { viewModel.updateFixtures(it, fixtureRec) }

            holder.itemView.setOnClickListener {
                fixtureRec.visibility = if (fixtureRec.isShown) View.GONE else View.VISIBLE
                clickOnLeague?.invoke(leagues)
            }
        }
    }


    private val diffUtil = object : DiffUtil.ItemCallback<Leagues>() {
        override fun areItemsTheSame(oldItem: Leagues, newItem: Leagues): Boolean {
            return oldItem.league_key == newItem.league_key
        }

        override fun areContentsTheSame(oldItem: Leagues, newItem: Leagues): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)

}
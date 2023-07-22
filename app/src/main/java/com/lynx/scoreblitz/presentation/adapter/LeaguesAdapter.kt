package com.lynx.scoreblitz.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lynx.scoreblitz.R
import com.lynx.scoreblitz.databinding.LeagueItemsBinding
import com.lynx.scoreblitz.domain.model.Leagues
import com.lynx.scoreblitz.presentation.view_models.ScoreViewModel

class LeaguesAdapter(private val clickOnLeague : ((Leagues) -> Unit)? = null) : RecyclerView.Adapter<LeaguesAdapter.LeaguesViewHolder>() {

    lateinit var viewModel: ScoreViewModel

    class LeaguesViewHolder(private val binding: LeagueItemsBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(leagues: Leagues){
            binding.league = leagues
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaguesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LeagueItemsBinding.inflate(inflater,parent,false)
        return LeaguesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: LeaguesViewHolder, position: Int) {
        val leagues = differ.currentList[position]
        leagues?.let(holder::bind)
        val fixturesAdapter = FixturesAdapter()
        leagues.league_key?.let { viewModel.getFixtures(leagueId = it) }
        fixturesAdapter.differ.submitList(viewModel.subFixtures.value)
        holder.itemView.setOnClickListener {
            clickOnLeague?.invoke(leagues)
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Leagues>(){
        override fun areItemsTheSame(oldItem: Leagues, newItem: Leagues): Boolean {
            return oldItem.league_key == newItem.league_key
        }

        override fun areContentsTheSame(oldItem: Leagues, newItem: Leagues): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffUtil)
}
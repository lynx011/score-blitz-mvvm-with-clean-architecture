package com.lynx.scoreblitz.presentation.adapter

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

class LeaguesAdapter(private val viewModel: ScoreViewModel,
    private val clickOnLeague: ((Leagues) -> Unit)? = null
) : RecyclerView.Adapter<LeaguesAdapter.LeaguesViewHolder>() {

    class LeaguesViewHolder(
        val binding: LeagueItemsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(leagues: Leagues, selected: Boolean) {
            binding.league = leagues
            binding.executePendingBindings()

            if (selected){
                binding.leagueCard.isEnabled = false
                binding.leagueCard.setCardBackgroundColor(ContextCompat.getColor(binding.leagueCard.context,
                    R.color.amber))
            }else{
                binding.leagueCard.isEnabled = true
                binding.leagueCard.setCardBackgroundColor(ContextCompat.getColor(binding.leagueCard.context,
                    R.color.dark_grey))
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaguesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LeagueItemsBinding.inflate(inflater, parent, false)
        return LeaguesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: LeaguesViewHolder, position: Int) {
        val leagues = differ.currentList[position]

        viewModel.isSelectedLeague.value = viewModel.selectedLeaguePosition.value == position
        leagues?.let {
            holder.bind(it,viewModel.isSelectedLeague.value!!)
        }

        holder.binding.apply {
            holder.itemView.setOnClickListener {
                viewModel.selectedLeaguePosition.value = position
                viewModel.key.value = position
                notifyDataSetChanged()
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
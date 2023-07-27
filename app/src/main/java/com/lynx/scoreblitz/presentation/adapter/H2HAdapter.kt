package com.lynx.scoreblitz.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lynx.scoreblitz.databinding.H2hItemBinding
import com.lynx.scoreblitz.databinding.PlayedMatchItemBinding
import com.lynx.scoreblitz.domain.model.FixtureResult
import com.lynx.scoreblitz.domain.model.H2HModel
import com.lynx.scoreblitz.presentation.view_models.ScoreViewModel

class H2hAdapter(
    private val viewModel: ScoreViewModel,
    private val clickOnH2h: ((H2HModel) -> Unit)? = null
) : RecyclerView.Adapter<H2hAdapter.H2hViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): H2hAdapter.H2hViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = H2hItemBinding.inflate(inflater, parent, false)
        return H2hViewHolder(binding)
    }

    class H2hViewHolder(val binding: H2hItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: H2HModel) {
            binding.h2h = result
            binding.executePendingBindings()
        }

        fun loadImage(url: String?, view: ImageView) {
            Glide.with(itemView.context)
                .load(url)
                .into(view)
        }
    }

    override fun onBindViewHolder(holder: H2hAdapter.H2hViewHolder, position: Int) {
        val h2h = differ.currentList[position]
        h2h?.let(holder::bind)

        val homeKey =
            viewModel.fixtureList.value?.find { it?.home_team_key == h2h.home_team_key }?.home_team_key
        val awayKey =
            viewModel.fixtureList.value?.find { it?.away_team_key == h2h.away_team_key }?.away_team_key
        val filteredHome =
            viewModel.fixtureList.value?.find { it?.home_team_logo == viewModel.selectedFixture.value?.home_team_logo && it?.home_team_key == h2h.home_team_key }?.home_team_logo.toString()
        val filteredAway =
            viewModel.fixtureList.value?.find { it?.away_team_logo == viewModel.selectedFixture.value?.away_team_logo && it?.away_team_key == h2h.away_team_key }?.away_team_logo.toString()



            if(homeKey == h2h.home_team_key){
                holder.loadImage(filteredHome,holder.binding.homeLogo)
            }else{
                holder.loadImage(filteredAway,holder.binding.homeLogo)
            }

        if(awayKey == h2h.away_team_key){
            holder.loadImage(filteredAway,holder.binding.awayLogo)
        }else{
            holder.loadImage(filteredHome,holder.binding.awayLogo)
        }


        holder.itemView.setOnClickListener { _ ->
            clickOnH2h?.let { it -> it(h2h) }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val diffUtil = object : DiffUtil.ItemCallback<H2HModel>() {
        override fun areItemsTheSame(oldItem: H2HModel, newItem: H2HModel): Boolean {
            return oldItem.league_key == newItem.league_key
        }

        override fun areContentsTheSame(oldItem: H2HModel, newItem: H2HModel): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)

}
package com.lynx.scoreblitz.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lynx.scoreblitz.R
import com.lynx.scoreblitz.databinding.StandingItemBinding
import com.lynx.scoreblitz.domain.model.StandingTotal
import com.lynx.scoreblitz.presentation.view_models.DashboardViewModel

class StandingsAdapter(private val viewModel: DashboardViewModel) :
    RecyclerView.Adapter<StandingsAdapter.StandingsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StandingsAdapter.StandingsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = StandingItemBinding.inflate(inflater, parent, false)
        return StandingsViewHolder(binding)
    }

    inner class StandingsViewHolder(val binding: StandingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: StandingTotal) {
            binding.standing = result
            binding.executePendingBindings()
        }

        fun loadImage(url: String, view: ImageView) {
            Glide.with(itemView.context)
                .load(url)
                .into(view)
        }

    }

    override fun onBindViewHolder(holder: StandingsAdapter.StandingsViewHolder, position: Int) {
        val standing = differ.currentList[position]
        standing?.let(holder::bind)
        val filteredKey =
            viewModel.fixtureLiveData.value?.find { it?.home_team_key == standing.team_key || it?.away_team_key == standing.team_key }
        if (filteredKey != null) {
            if (filteredKey.home_team_key == standing.team_key) {
                filteredKey.home_team_logo?.let { holder.loadImage(it, holder.binding.imgView) }
            } else {
                filteredKey.away_team_logo?.let { holder.loadImage(it, holder.binding.imgView) }
            }
        }
        val championLeague = "Promotion - Champions League (Group Stage: )"
        val europaLeague = "Promotion - Europa League (Group Stage: )"
        val championShip = "Relegation - Championship"
        when (standing.standing_place_type.toString()) {
            championLeague -> holder.binding.viewType.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.green_1
                )
            )

            europaLeague -> holder.binding.viewType.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.blue
                )
            )

            championShip -> holder.binding.viewType.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.red
                )
            )

            else -> holder.binding.viewType.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.black_glass2
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val diffUtil = object : DiffUtil.ItemCallback<StandingTotal>() {
        override fun areItemsTheSame(oldItem: StandingTotal, newItem: StandingTotal): Boolean {
            return oldItem.league_key == newItem.league_key
        }

        override fun areContentsTheSame(oldItem: StandingTotal, newItem: StandingTotal): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)

}
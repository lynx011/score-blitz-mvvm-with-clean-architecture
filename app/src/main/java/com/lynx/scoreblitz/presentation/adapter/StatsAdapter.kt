package com.lynx.scoreblitz.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lynx.scoreblitz.databinding.H2hItemBinding
import com.lynx.scoreblitz.databinding.StatsProgressItemBinding
import com.lynx.scoreblitz.domain.model.Statistic

class StatsAdapter(private val clickOnStats: ((Statistic) -> Unit)? = null) :
    RecyclerView.Adapter<StatsAdapter.StatsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StatsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = StatsProgressItemBinding.inflate(inflater, parent, false)
        return StatsViewHolder(binding)
    }

    class StatsViewHolder(val binding: StatsProgressItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Statistic) {
            binding.stats = result
            binding.executePendingBindings()
            if (result.home?.contains("%") == true){
                binding.homeProgressBar.progress = result.home.replace("%","").toInt()
            }else{
                binding.homeProgressBar.progress = result.home?.toInt() ?: 0
            }

            if (result.away?.contains("%") == true){
                binding.awayProgressBar.progress = result.away.replace("%","").toInt()
            }else{
                binding.awayProgressBar.progress = result.away?.toInt() ?: 0
            }
            val home = binding.homeProgressBar
            val away = binding.awayProgressBar
            if (home.progress in 1..30 && away.progress in 1..30){
                home.max = 30
                away.max = 30
            }else if(home.progress in 30..60 && away.progress in 30..60){
                home.max = 60
                away.max = 60
            }else if (home.progress in 60..90 && away.progress in 60..90){
                home.max = 90
                away.max = 90
            }
        }
    }

    override fun onBindViewHolder(holder: StatsViewHolder, position: Int) {
        val stats = differ.currentList[position]
        stats?.let(holder::bind)

        holder.itemView.setOnClickListener { _ ->
            clickOnStats?.let { it -> it(stats) }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Statistic>() {
        override fun areItemsTheSame(oldItem: Statistic, newItem: Statistic): Boolean {
            return oldItem.home == newItem.home
        }

        override fun areContentsTheSame(oldItem: Statistic, newItem: Statistic): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)

}
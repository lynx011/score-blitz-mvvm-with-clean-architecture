package com.lynx.scoreblitz.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lynx.scoreblitz.databinding.FixtureItemBinding
import com.lynx.scoreblitz.domain.model.FixtureResult

class FixturesAdapter(private val clickOnFixture: ((FixtureResult,ImageView,ImageView) -> Unit)? = null) : RecyclerView.Adapter<FixturesAdapter.FixtureViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FixtureViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FixtureItemBinding.inflate(inflater,parent,false)
        return FixtureViewHolder(binding)
    }

    class FixtureViewHolder(val binding: FixtureItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(result: FixtureResult){
            binding.fixture = result
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: FixtureViewHolder, position: Int) {
        val fixtures = differ.currentList[position]
        fixtures?.let(holder::bind)

        fixtures?.let {
            val scores = fixtures.event_ft_result.toString()
            val splitScores = scores.split(" - ")
            val homeResult = holder.binding.homeResult
            val awayResult = holder.binding.awayResult
            if (splitScores.size >= 2){
                homeResult.text = splitScores[0]
                awayResult.text = splitScores[1]
                homeResult.visibility = View.VISIBLE
                awayResult.visibility = View.VISIBLE
            }else{
                homeResult.visibility = View.GONE
                awayResult.visibility = View.GONE
            }
            holder.binding.homeLogo.transitionName = "home_logo_${it.league_key}"
            holder.binding.awayLogo.transitionName = "away_logo_${it.league_key}"
        }
        holder.itemView.setOnClickListener {
            clickOnFixture?.invoke(fixtures,holder.binding.homeLogo,holder.binding.awayLogo)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val diffUtil = object : DiffUtil.ItemCallback<FixtureResult>(){
        override fun areItemsTheSame(oldItem: FixtureResult, newItem: FixtureResult): Boolean {
            return oldItem.league_key == newItem.league_key
        }

        override fun areContentsTheSame(oldItem: FixtureResult, newItem: FixtureResult): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffUtil)

}
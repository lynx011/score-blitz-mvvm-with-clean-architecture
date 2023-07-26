package com.lynx.scoreblitz.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lynx.scoreblitz.databinding.PlayedMatchItemBinding
import com.lynx.scoreblitz.domain.model.FixtureResult

class FixturesAdapter(private val clickOnFixture: ((FixtureResult) -> Unit)? = null) : RecyclerView.Adapter<FixturesAdapter.FixtureViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FixturesAdapter.FixtureViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PlayedMatchItemBinding.inflate(inflater,parent,false)
        return FixtureViewHolder(binding)
    }

    class FixtureViewHolder(val binding: PlayedMatchItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(result: FixtureResult){
            binding.fixture = result
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: FixturesAdapter.FixtureViewHolder, position: Int) {
        val fixtures = differ.currentList[position]
        fixtures?.let(holder::bind)

        val scores = fixtures.event_ft_result.toString()
        val splitScores = scores.split(" - ")
        holder.binding.homeResult.text = splitScores[0]
        holder.binding.awayResult.text = splitScores[1]
        holder.itemView.setOnClickListener {
            clickOnFixture?.invoke(fixtures)
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
package com.lynx.scoreblitz.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lynx.scoreblitz.databinding.FixtureItemsBinding
import com.lynx.scoreblitz.domain.model.FixtureResult

class FixturesAdapter() : RecyclerView.Adapter<FixturesAdapter.FixtureViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FixturesAdapter.FixtureViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FixtureItemsBinding.inflate(inflater,parent,false)
        return FixtureViewHolder(binding)
    }

    class FixtureViewHolder(private val binding: FixtureItemsBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(result: FixtureResult){
            binding.fixture = result
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: FixturesAdapter.FixtureViewHolder, position: Int) {
        val fixtures = differ.currentList[position]
        fixtures?.let(holder::bind)
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
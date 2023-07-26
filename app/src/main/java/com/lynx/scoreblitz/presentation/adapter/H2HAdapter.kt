package com.lynx.scoreblitz.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lynx.scoreblitz.databinding.H2hItemBinding
import com.lynx.scoreblitz.databinding.PlayedMatchItemBinding
import com.lynx.scoreblitz.domain.model.FixtureResult
import com.lynx.scoreblitz.domain.model.H2HModel

class H2hAdapter(private val clickOnH2h: ((H2HModel) -> Unit)? = null) : RecyclerView.Adapter<H2hAdapter.H2hViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): H2hAdapter.H2hViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = H2hItemBinding.inflate(inflater,parent,false)
        return H2hViewHolder(binding)
    }

    class H2hViewHolder(val binding: H2hItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(result: H2HModel){
            binding.h2h = result
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: H2hAdapter.H2hViewHolder, position: Int) {
        val h2h = differ.currentList[position]
        h2h?.let(holder::bind)

        holder.itemView.setOnClickListener { _ ->
            clickOnH2h?.let { it -> it(h2h) }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val diffUtil = object : DiffUtil.ItemCallback<H2HModel>(){
        override fun areItemsTheSame(oldItem: H2HModel, newItem: H2HModel): Boolean {
            return oldItem.league_key == newItem.league_key
        }

        override fun areContentsTheSame(oldItem: H2HModel, newItem: H2HModel): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffUtil)

}
package com.mcmouse88.cryptoapp.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mcmouse88.cryptoapp.R
import com.mcmouse88.cryptoapp.databinding.ItemCoinInfoBinding
import com.mcmouse88.cryptoapp.domain.CoinInfo
import com.squareup.picasso.Picasso

class CoinInfoAdapter(
    private val context: Context
) : ListAdapter<CoinInfo, CoinInfoAdapter.CoinInfoViewHolder>(CoinInfoDiffCallback()) {

    var onCoinClick: OnCoinClickListener? = null

    inner class CoinInfoViewHolder(
        private val binding: ItemCoinInfoBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(coin: CoinInfo) {
            val symbolsTemplate = context.resources.getString(R.string.view)
            val lastUpdateTemplate = context.resources.getString(R.string.time_last_update)
            binding.textViewSymbols.text =
                String.format(symbolsTemplate, coin.fromSymbol, coin.toSymbol)
            binding.textViewPrice.text = coin.price
            binding.textViewLastUpdate.text = String.format(lastUpdateTemplate, coin.lastUpdate)
            Picasso.get().load(coin.imageUrl).into(binding.imageViewPicture)
            binding.root.setOnClickListener {
                onCoinClick?.onCoinClick(coin)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding = ItemCoinInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CoinInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = getItem(position)
        holder.bind(coin)
    }

    interface OnCoinClickListener {
        fun onCoinClick(coin: CoinInfo)
    }

    class CoinInfoDiffCallback : DiffUtil.ItemCallback<CoinInfo>() {

        override fun areContentsTheSame(oldItem: CoinInfo, newItem: CoinInfo): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: CoinInfo, newItem: CoinInfo): Boolean {
            return oldItem.fromSymbol == newItem.fromSymbol
        }
    }
}
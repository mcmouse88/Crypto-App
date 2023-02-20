package com.mcmouse88.cryptoapp.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mcmouse88.cryptoapp.R
import com.mcmouse88.cryptoapp.databinding.ItemCoinInfoBinding
import com.mcmouse88.cryptoapp.domain.CoinInfo
import com.squareup.picasso.Picasso

class CoinInfoAdapter(private val context: Context) : RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var coinInfoList = listOf<CoinInfo>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onCoinClick: OnCoinClickListener? = null

    inner class CoinInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCoinInfoBinding.bind(itemView)
        val imageViewPicture = binding.imageViewPicture
        val textViewSymbols = binding.textViewSymbols
        val textViewPrice = binding.textViewPrice
        val textViewLastUpdate = binding.textViewLastUpdate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_coin_info, parent, false)
        return CoinInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        val symbolsTemplate = context.resources.getString(R.string.view)
        val lastUpdateTemplate = context.resources.getString(R.string.time_last_update)
        holder.textViewSymbols.text = String.format(symbolsTemplate, coin.fromSymbol, coin.toSymbol)
        holder.textViewPrice.text = coin.price
        holder.textViewLastUpdate.text = String.format(lastUpdateTemplate, coin.lastUpdate)
        Picasso.get().load(coin.imageUrl).into(holder.imageViewPicture)
        holder.itemView.setOnClickListener {
            onCoinClick?.onCoinClick(coin)
        }
    }

    override fun getItemCount() = coinInfoList.size

    interface OnCoinClickListener {
        fun onCoinClick(priceInfo: CoinInfo)
    }
}
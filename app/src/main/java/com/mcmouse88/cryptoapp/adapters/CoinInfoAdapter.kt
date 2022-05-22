package com.mcmouse88.cryptoapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mcmouse88.cryptoapp.R
import com.mcmouse88.cryptoapp.databinding.ItemCoinInfoBinding
import com.mcmouse88.cryptoapp.pojo.CoinInfo
import com.mcmouse88.cryptoapp.pojo.PriceInfo
import com.squareup.picasso.Picasso


/**
 * Создадим адаптор, в котором будем внутренний класс
 * (с пометкой [inner]), который будет наследоваться от
 * класса [RecyclerView.ViewHolder]. При создании класса
 * в конструктор передадим в качестве параметра [View].
 * Внутри нам нужно получить ссылки на элементы [View].
 * С помощью [Binding] получим ссылки на элементы карточки
 * для [RecyclerView]. Основной класс [CoinInfoAdapter]
 * мы наследуем от [RecyclerView.Adapter] и в качестве
 * [generics] передадим наш вложенный класс [CoinInfoViewHolder].
 * Далее нужно имплементировать три обязательных метода
 * [onCreateViewHolder], [onBindViewHolder] и [getItemCount].
 * Чтобы можно было обращаться к ресурсам приложения (строковым и т.д.),
 * нам нужен [Contex], для этого передадим его в конструктор в
 * качестве параметра
 */
class CoinInfoAdapter(private val context: Context) : RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    /**
     * Создадим список который будет хранить объекты
     * [CoinInfo], и переопределим у него метод [set],
     * чтобы вызвать метод [notifyDataSetChanged], который
     * будет обновлять список каждый раз, когда ему будет
     * присваиваться новое значение
     */
    var coinInfoList = listOf<PriceInfo>()
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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        val symbolsTemplate = context.resources.getString(R.string.view)
        val lastUpdateTemplate = context.resources.getString(R.string.time_last_update)
        holder.textViewSymbols.text = String.format(symbolsTemplate, coin.fromSymbol, coin.toSymbol)
        holder.textViewPrice.text = coin.price
        holder.textViewLastUpdate.text = String.format(lastUpdateTemplate, coin.getFormattedTime())
        Picasso.get().load(coin.getFullUrlImage()).into(holder.imageViewPicture)
        holder.itemView.setOnClickListener {
            onCoinClick?.onCoinClick(coin)
        }
        // Более упрощенная запись через метод with, но я отсавлю тот что выше, чтобы было более понятно
        /*with(holder) {
            with(coin) {
                textViewSymbols.text = "$fromSymbol / $toSymbol"
                textViewPrice.text = price
                textViewLastUpdate.text = getFormattedTime()
                Picasso.get().load(getFullUrlImage()).into(imageViewPicture)
            }
        }*/
    }

    override fun getItemCount() = coinInfoList.size

    interface OnCoinClickListener {
        fun onCoinClick(priceInfo: PriceInfo)
    }
}
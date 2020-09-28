package com.example.cryptotradingapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptotradingapp.R
import com.example.cryptotradingapp.domain.Cryptocurrency
import com.example.cryptotradingapp.network.MarketCoin
import kotlinx.android.synthetic.main.market_coin_item.view.*
import kotlinx.android.synthetic.main.wallet_item.view.*

class MarketCoinAdapter: RecyclerView.Adapter<MarketCoinAdapter.CoinViewHolder>() {

    var data =  listOf<MarketCoin>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class CoinViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val symbol: TextView = itemView.coin_symbol
        val price: TextView = itemView.coin_price
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CoinViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.market_coin_item, parent, false)
        return CoinViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val coin = data[position]
        holder.apply{
            symbol.text = coin.symbol
            price.text = coin.price.toString()
        }
    }

    override fun getItemCount() = data.size





}
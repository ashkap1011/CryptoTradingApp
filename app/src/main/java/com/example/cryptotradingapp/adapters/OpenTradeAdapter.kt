package com.example.cryptotradingapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptotradingapp.R
import com.example.cryptotradingapp.domain.OpenTrade
import com.example.cryptotradingapp.network.MarketCoin
import kotlinx.android.synthetic.main.fragment_trading.view.*
import kotlinx.android.synthetic.main.market_coin_item.view.*
import kotlinx.android.synthetic.main.open_trade_item.view.*

class OpenTradeAdapter(private val listener: MarketCoinAdapter.OnItemClickListener): RecyclerView.Adapter<OpenTradeAdapter.OpenTradeViewHolder>() {

    var data =  listOf<OpenTrade>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }



    inner class OpenTradeViewHolder(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener {
        val symbol: TextView = itemView.currency_symbol
        val quantity: TextView = itemView.currency_quantity

        init{
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            listener.onItemClick(position)
        }
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OpenTradeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.open_trade_item, parent, false)
        return OpenTradeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OpenTradeViewHolder, position: Int) {
        val coin = data[position]
        holder.apply{
            symbol.text = coin.symbol
            quantity.text = coin.quantity.toString()
        }
    }

    override fun getItemCount() = data.size



}
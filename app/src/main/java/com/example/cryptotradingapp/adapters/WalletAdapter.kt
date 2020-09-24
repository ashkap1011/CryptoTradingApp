package com.example.cryptotradingapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptotradingapp.R
import com.example.cryptotradingapp.domain.Cryptocurrency
import kotlinx.android.synthetic.main.wallet_item.view.*

class WalletAdapter : RecyclerView.Adapter<WalletAdapter.WalletViewHolder>() {

    var data =  listOf<Cryptocurrency>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class WalletViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val symbol: TextView = itemView.currency_symbol
        val quantity: TextView = itemView.currency_quantity
    }
    /*
    private val differCallback = object : DiffUtil.ItemCallback<Cryptocurrency>() {
        override fun areItemsTheSame(oldItem: Cryptocurrency, newItem: Cryptocurrency): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cryptocurrency, newItem: Cryptocurrency): Boolean {
            TODO("Not yet implemented")
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)
    */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.wallet_item, parent, false)
        return WalletViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WalletViewHolder, position: Int) {
        val currency = data[position]
        holder.itemView.apply{
            currency_symbol.text = currency.symbol
            currency_quantity.text = (currency.lockedQuantity + currency.quantity).toString()
            //todo look at philip lackner around 9:00 for implementing clicking
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }


}




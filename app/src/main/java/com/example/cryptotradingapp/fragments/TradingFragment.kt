package com.example.cryptotradingapp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptotradingapp.R
import com.example.cryptotradingapp.databinding.FragmentTradingBinding
import com.example.cryptotradingapp.viewmodels.TradingViewModel
import java.lang.Double.parseDouble
import java.util.*

class TradingFragment : Fragment() {

    private lateinit var binding: FragmentTradingBinding
    private lateinit var viewModel: TradingViewModel


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_trading, container, false
        )

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory(activity!!.application)).get(
            TradingViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = viewLifecycleOwner

        //viewModel.startConnection()


        // Create the observer which updates the UI.
        val tradingPairObserver = Observer<Double> { value ->
            // Update the UI, in this case, a TextView.
            Log.i("tradinglivedata","this is the value in observer object" + value)
            binding.coinPrice.setText(value.toString())
        }

        viewModel.coinPrice.observe(viewLifecycleOwner, tradingPairObserver)


        binding.coinPrice.doAfterTextChanged {
            Log.i("maybe", "right here")
            if(!viewModel.isMarketOrderType() && textIsNumeric(it.toString())){
                    Log.i("tradingvi", "text changed"+it.toString())
                  viewModel.setCoinOrderPrice(it.toString())
               }
        }

        binding.coinQuantity.doAfterTextChanged {
            if(it.toString() != ""){
                viewModel.coinQuantity = it.toString().toDouble()
            }
        }

        binding.isSell.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.isSellSwitchAction(isChecked)
            updateViewBalance()
        }


//        binding.coinPrice.addTextChangedListener(object :TextWatcher{
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            }
//            override fun afterTextChanged(s: Editable?) {
//                if(!viewModel.isMarketOrder.value!!){
//                    Log.i("tradingvi", "text changed"+s.toString())
//                    viewModel.setCoinOrderPrice(s.toString())
//                }
//            }
//        })


        //on trading pair selected from spinner
        binding.tradingPair.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.selectedTradingPair = parent?.getItemAtPosition(position).toString()
                setCoinPrice()
                updateViewBalance()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }


        binding.orderTypeList.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.orderType = parent?.getItemAtPosition(position).toString()
                Log.i("tradingview", "orderTYPE:" + viewModel.orderType + "and isMarketOrder" + viewModel.isMarketOrder)
                viewModel.setCoinPriceEditable()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        return binding.root
    }

    fun textIsNumeric(text: String): Boolean{
        return text.matches("-?\\d+(\\.\\d+)?".toRegex())
    }


    fun setCoinPrice(){
        viewModel.setCurrentCoinPrice()
    }

    fun updateViewBalance(){
        viewModel.updateViewBalance()
    }


}
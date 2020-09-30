package com.example.cryptotradingapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptotradingapp.R
import com.example.cryptotradingapp.adapters.MarketCoinAdapter
import com.example.cryptotradingapp.adapters.WalletAdapter
import com.example.cryptotradingapp.databinding.FragmentMarketBinding
import com.example.cryptotradingapp.viewmodels.LoginViewModel
import com.example.cryptotradingapp.viewmodels.MarketViewModel
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import org.json.JSONException
import org.json.JSONObject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MarketFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MarketFragment : Fragment(),MarketCoinAdapter.OnItemClickListener {

    private lateinit var binding: FragmentMarketBinding
    private lateinit var viewModel: MarketViewModel

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_market, container, false
        )

        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(requireActivity(),ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(
            MarketViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.startConnection()

        val adapter = MarketCoinAdapter(this)
        binding.marketCoinsList.adapter = adapter


        viewModel.marketDataFeed.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
            Log.i("PRINTINGEDINVIEWMODEL", it.toString())
        })

        return binding.root
    }


    override fun onItemClick(position: Int) {
        Toast.makeText(context, "Item $position", Toast.LENGTH_SHORT).show()
        Log.i("MARKETTRADING", "POSITION-----------------------" + position)
        viewModel.selectedTradingPair(position)
        activity!!.supportFragmentManager.beginTransaction().apply{
            replace(R.id.fl_wrapper, MarketChartFragment())
            addToBackStack(null)
            commit()
        }
        //todo get item symbol, pass it to webview

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MarketFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MarketFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
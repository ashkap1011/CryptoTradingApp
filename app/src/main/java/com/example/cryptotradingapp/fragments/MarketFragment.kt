package com.example.cryptotradingapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cryptotradingapp.R
import com.example.cryptotradingapp.databinding.FragmentMarketBinding
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
class MarketFragment : Fragment() {

    private lateinit var binding: FragmentMarketBinding
    private lateinit var viewModel: MarketViewModel

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var mSocket: Socket = IO.socket("http://10.0.2.2:3000")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        mSocket.on("someevent", onNewMessage);

        mSocket.connect()
        Log.i("message", "heyy")


    }

    private val onNewMessage = Emitter.Listener { args ->
        activity!!.runOnUiThread(Runnable {
            val data = args[0] as JSONObject
            Log.i("message", data.toString())
            try {

            } catch (e: JSONException) {
                return@Runnable
            }
            Log.i("message", "heyry")
            //Log.i("message", username + message)
            // add the message to view
            //addMessage(username, message)
        })
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment





        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_market, container, false
        )
        viewModel = ViewModelProvider(this).get(MarketViewModel::class.java)
        return binding.root    }

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